package Code;

import Code.Memory.StringObject;
import IR.ThreeAdressTransformer;
import Model.DataType;
import Model.Operator;
import Semantics.ArrayElement;
import Semantics.Element;
import Semantics.Scope;
import Semantics.SemanticsException;
import Tree.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Zaiyang on 16/12/2015.
 */
public class AbstractMachine {
    public int maxRegister = -1;
    public Memory memory = new Memory();
    public Register zeroRegister;
    public Register booleanTestRegister;
    public Register stackPointer;
    public Register basePointer;
    public Register memoryAddressBuffer;
    public Register memoryReadBuffer;
    public CompilationUnit unit;
    public CodeGen codeGenerator;
    public Stack<Scope> scopeStack;
    public Stack<StackFrame> programStack;
    public CodeGen getGenerator() {
        return codeGenerator;
    }
    public RegisterAllocator allocator;

    public void setGenerator(CodeGen generator) {
        this.codeGenerator = generator;
    }

    public CompilationUnit getUnit() {
        return unit;
    }

    public void setUnit(CompilationUnit unit) {
        this.unit = unit;
    }


    private static final String NEWLINE="\n";
    private static final String TRUE = "true\0\0\0";
    private static final String FALSE = "false\0\0";

    public static final String[] defaultValues = {FALSE, TRUE, NEWLINE};
    public AbstractMachine(){

        zeroRegister = Register.create();
        stackPointer = Register.create();
        basePointer = Register.create();
        booleanTestRegister = Register.create();
        memoryAddressBuffer = Register.create();
        memoryReadBuffer = Register.create();

    }
    public AbstractMachine(CompilationUnit unit){
        this.unit = unit;
    }

    private void injectConstantsInGlobalScope(String[] constants) {
        for(String s : constants){
            StringObject stringObject = new StringObject(s);
            memory.insert(stringObject);
        }
        Set<String> strings = unit.getGlobalScope().getStringConstantTable().keySet();
        for(String s : strings){
            StringObject stringObject = new StringObject(s);
            memory.insert(stringObject);
        }

    }
    private void injectGlobalVariables(){
        Scope scope = unit.getGlobalScope();
        HashMap<String, Element> symbols = scope.getSymbolTable();
        Set<String> keySet = symbols.keySet();
        for(String s: keySet){
            Element e = symbols.get(s);
            if(e instanceof ArrayElement){
                ArrayElement a = (ArrayElement) e;
                int length = a.getDefinition().getLength().getIntValue();
                Memory.ArrayObject ao = new Memory.ArrayObject(e.getName(), length);
                memory.insert(ao);
            }else {
                Memory.VariableObject var = new Memory.VariableObject(e.getName());
                memory.insert(var);
            }
        }
    }
    public int getMaxRegister(){
        return maxRegister;
    }
    public void setMaxRegister(int max){
        this.maxRegister = max;
    }
    public void setRegisterAllocator(RegisterAllocator a){
        this.allocator = a;
    }
    public RegisterAllocator getRegisterAllocator(){
        return allocator;
    }
    public void execute(){
        injectConstantsInGlobalScope(defaultValues);
        injectGlobalVariables();
        memory.createObjectLookupTable();
        codeGenerator.beginProgram(new Instruction("begin",OpCode.XOR,zeroRegister,zeroRegister,zeroRegister));
        List<Statement> quads = unit.getProgramBody().getStatements();
        scopeStack = new Stack<>();
        scopeStack.push(unit.getGlobalScope());
        for(Statement stmt: quads){
            executeStmt(stmt);
        }
        codeGenerator.endProgram(new Instruction("end", OpCode.HALT));
        codeGenerator.writeMemory(memory);
    }

    public void executeStmt(Statement stmt) {
        if(stmt instanceof ConditionGotoStatement){
            executeCondGotoStmt((ConditionGotoStatement) stmt);
        }else if(stmt instanceof GotoStatement ){
            codeGenerator.emitCode(new Instruction(OpCode.JMP, new Literal<String>(((GotoStatement) stmt).getDestination().getLabel())));
        }else if(stmt instanceof ExpressionStatement){
            executeExpStmt((ExpressionStatement)stmt);
        }else if(stmt instanceof ReadStatement){
            executeRdStmt((ReadStatement) stmt);
        }else if(stmt instanceof WriteLineStatement){
            executeWrLnStmt((WriteLineStatement)stmt);
        }else if(stmt instanceof WriteStatement){
            executeWrStmt((WriteStatement) stmt);
        }else if(stmt instanceof SkipStatement){
            executeSkipStmt((SkipStatement)stmt);
        }else if(stmt instanceof ProgramEndStatement){
            executeProgEnd((ProgramEndStatement)stmt);
        }else if(stmt instanceof ProgramStartStatement){
            executeProgStart((ProgramStartStatement) stmt);
        }else if(stmt instanceof BlockStatement){
            executeBlock((BlockStatement) stmt);
        }
    }

    private void executeBlock(BlockStatement stmt) {
        List<Statement> list = stmt.getBlockBody();
        String label = stmt.getLabel();
        stmt.getBlockBody().get(0).setLabel(label);
        scopeStack.push(stmt.getInnerScope());
        for(Statement t: list){
            executeStmt(t);
        }
        scopeStack.pop();
    }
    private void executeFunctionCall(FunctionCall call){

    }
    private void executeProgEnd(ProgramEndStatement stmt) {
        codeGenerator.emitCode(new Instruction(OpCode.HALT));
    }

    private void executeSkipStmt(SkipStatement stmt) {
        codeGenerator.emitCode(new Instruction(stmt.getLabel(),OpCode.NOP));
    }

    private void executeWrLnStmt(WriteLineStatement stmt) {
        int address = memory.findString(NEWLINE).getAddress();
        Literal<Integer> add = new Literal<Integer>(address);
        Instruction i = new Instruction(stmt.label, OpCode.WRS, add);
        codeGenerator.emitCode(i);
    }

    private void executeProgStart(ProgramStartStatement stmt) {
        codeGenerator.endProgram(new Instruction("init", OpCode.HALT));
    }

    private void executeWrStmt(WriteStatement stmt) {
        Expression exp = stmt.getExpression();
        if(exp instanceof BooleanLiteral || exp instanceof StringLiteral){
            executeWriteStringBoolLiteral(stmt);
        }
        else {
            executeWriteVar(stmt);
        }

    }

    private void executeWriteVar(WriteStatement stmt) {
        Identifier id = (Identifier) stmt.getExpression();
        Register r = null;
        OpCode code;
        if(id instanceof ArrayAccess){
            r = allocator.allocate();
            Register offset = calcuateArrayOffset((ArrayAccess) id);
            Instruction loadArrayEl = new Instruction(OpCode.LOAD, r, offset, new Literal<>(0));
            codeGenerator.emitCode(loadArrayEl);
        }else if(id instanceof FunctionCall) {

        }else {
            r = allocator.allocate(id.getName());
        }
        switch(id.getDataType()){
            case Boolean:
                codeGenerator.emitCode(new Instruction(stmt.getLabel(), OpCode.WR, r));
                break;
            case Integer:
                codeGenerator.emitCode(new Instruction(stmt.getLabel(),OpCode.WR, r));
                break;
            case Real:
                codeGenerator.emitCode(new Instruction(stmt.getLabel(),OpCode.WRR, r));
                break;
            case String:
                throw new RuntimeException("write statement cannot write string variable, this is a limitation imposed by underlying platform");
            default:
                codeGenerator.emitCode(new Instruction(stmt.getLabel(),OpCode.WR, r));
                break;
        }
    }

    private void executeWriteStringBoolLiteral(WriteStatement stmt) {
        Expression exp = stmt.getExpression();
        Literal<Integer> address = new Literal<>(-1);
        if(exp instanceof BooleanLiteral){
            boolean bool = ((BooleanLiteral) exp).getBoolValue();
            address = getBooleanMemLocation(bool);
        }else if(exp instanceof StringLiteral){
            address = lookupStringMemory( ((StringLiteral) exp).getString());
        }
        codeGenerator.emitCode(new Instruction(stmt.getLabel(), OpCode.WRS, address));
    }

    private void executeRdStmt(ReadStatement stmt) {
        Expression e = stmt.getLocation();
        if(e instanceof ArrayAccess){
            Register temp = Register.create();
            Instruction readtoTemp = new Instruction(stmt.getLabel(),OpCode.RD, temp);
            Literal<Integer> addr = getAddressOfArray(((ArrayAccess) e).getName());
            Instruction addrInMAB = new Instruction(OpCode.ADDI, memoryAddressBuffer, zeroRegister, addr);
            Instruction storeVal = new Instruction(OpCode.STORE, temp, memoryAddressBuffer, new Literal<>(0));
            codeGenerator.emitCode(readtoTemp);
            codeGenerator.emitCode(addrInMAB);
            codeGenerator.emitCode(storeVal);
        }else if(e instanceof Identifier) {
            OpCode op = OpCode.RD;
            if(e.getDataType()== DataType.Real)
                op = OpCode.RDR;
            Register r = allocator.allocate(((Identifier) e).getName());
            Instruction i = new Instruction(stmt.getLabel(), op,r );
            codeGenerator.emitCode(i);
        }
    }

    private Literal<Integer> getAddressOfArray(String name) {
        Memory.ArrayObject ao = (Memory.ArrayObject) memory.findArray(name);
        return new Literal<Integer>(ao.getAddress());
    }

    private void executeCondGotoStmt(ConditionGotoStatement condGoto) {
        ConditionGotoStatement.JumpCondition branching = condGoto.getCondition();
        Operand condition= allocateOperand(condGoto.getVariable());
        OpCode op=OpCode.BEQZ;
        switch(branching){
            case JumpIfZero:
                op = OpCode.BEQZ;
                break;
            case JumpIfNonZero:
                op = OpCode.BNEZ;
                break;
        }
        Literal<String> jumpTo = new Literal<>(condGoto.getDestination().getLabel());
        Instruction i = new Instruction(condGoto.getLabel(), op, condition, jumpTo);
        codeGenerator.emitCode(i);
    }

    public void executeExpStmt(ExpressionStatement stmt) {

        Expression e = stmt.getExpression();
        Instruction i = null;
        assert(e instanceof AssignmentExpression);
        if (((AssignmentExpression) e).getVariable() == ((AssignmentExpression) e).getValue()) return;

        AssignmentExpression ae = (AssignmentExpression) e;
        Identifier id = (Identifier) ((AssignmentExpression) e).getVariable();
        if(id instanceof ArrayAccess){
            executeArrayElementAssign(stmt.getLabel(), (ArrayAccess) id, ae.getValue());
        }else if(id!=null) {
            executeSimpleAssExpIns(stmt.getLabel(), (Identifier) ae.getVariable(), ae.getValue());
        }
    }

    private void executeArrayElementAssign(String label, ArrayAccess id, Expression value) {
        Operand val = moveConstantToRegister(allocateOperand(value));
        Register memoryAddress = calcuateArrayOffset(id);
        Instruction storeData = new Instruction(OpCode.STORE, val, memoryAddress, new Literal<>(0));
        codeGenerator.emitCode(storeData);
    }

    private Register calcuateArrayOffset(ArrayAccess id) {
        Register base = moveConstantToRegister(getAddressOfArray(id.getName()));
        Register indexExp = moveConstantToRegister(allocateOperand(id.getIndexExpression()));
        Register offset = Register.create();
        Instruction mul4 = new Instruction(OpCode.MULI, offset, indexExp, new Literal<>(4));

        Register addr = Register.create();
        Instruction calAddr = new Instruction(OpCode.ADD, addr, base, offset);

        codeGenerator.emitCode(mul4);
        codeGenerator.emitCode(calAddr);
        return addr;
    }

    private void executeSimpleAssExpIns(String label, Identifier variable, Expression exp) {
        Instruction i = null;
        OpCode op;
        Operand source1;
        Operand source2;
        Register dest = allocator.allocate(variable.getName());
        if(exp instanceof NumericalBinaryExpression){
            executeNumBinExp(label, variable, (NumericalBinaryExpression) exp);
        }else if(exp instanceof NumericalUnaryExpression){
            executeNumUnExp(label, variable, (NumericalUnaryExpression)exp);
        }else if(exp instanceof BooleanUnaryExpression){
            executeBoolUnExp(label, variable, (BooleanUnaryExpression)exp);
        }else if(exp instanceof RelationExpression){
            executeSimpleRelExp(label, variable, (RelationExpression) exp);
        }else if(exp instanceof BooleanBinaryExpression){
            executeBoolBinExp(label, variable, (BooleanBinaryExpression) exp);
        }else if(exp instanceof StringLiteral){
            source1 = allocateOperand(exp);
            i = new Instruction(label, OpCode.ADDI, dest, zeroRegister, source1);
        }else if(exp instanceof IntegerLiteral){
            source1 = allocateOperand(exp);
            i = new Instruction(label,OpCode.ADDI, dest, zeroRegister, source1);
        }else if(exp instanceof FloatLiteral) {
            source1 = allocateOperand(exp);
            i = new Instruction(label, OpCode.MOVIR, dest, source1);
        }else if(exp instanceof BooleanLiteral){
            source1 = allocateOperand(exp);
            i = new Instruction(label, OpCode.ADDI, dest, zeroRegister, source1);
        }else if(exp instanceof ArrayAccess){
            Literal<Integer> addr = getAddressOfArray( ((ArrayAccess) exp).getName());
            i = new Instruction(label, OpCode.LOAD, dest, zeroRegister, addr);
        }else if(exp instanceof ArrayDefinition){
            Literal<Integer> addr = getAddressOfArray(variable.getName());
            i = new Instruction(label,OpCode.ADDI, dest, zeroRegister, addr);
        }else if(exp instanceof Identifier){
            source1 = allocateOperand(exp);
            i = new Instruction(label, OpCode.ADD, dest, source1, zeroRegister);
        }
        if(i!=null) codeGenerator.emitCode(i);
    }

    private void executeBoolBinExp(String label, Identifier variable, BooleanBinaryExpression exp) {
        Register out = allocator.allocate(variable.getName());
        Operand left = moveConstantToRegister(allocateOperand(exp.getLeftOperand()));
        Operand right = moveConstantToRegister(allocateOperand(exp.getRightOperand()));
        switch(exp.getOperator()){
            case BooleanAnd:
                Instruction and = new Instruction(label, OpCode.MUL, out, left, right);
                codeGenerator.emitCode(and);
                break;
            case BooleanOr:
                String skipSetOne="BOR"+ThreeAdressTransformer.getRandomId();
                Instruction add = new Instruction(label, OpCode.ADD, out, left, right);
                Instruction testZero = new Instruction(OpCode.BEQZ, out, new Literal<>(skipSetOne));
                Instruction setOne = new Instruction(OpCode.ADDI, out, zeroRegister, new Literal<>(1));
                Instruction end = new Instruction(skipSetOne, OpCode.NOP);
                codeGenerator.emitCode(add);
                codeGenerator.emitCode(testZero);
                codeGenerator.emitCode(setOne);
                codeGenerator.emitCode(end);
                break;
            default:
                break;
        }
    }

    private void executeBoolUnExp(String label, Identifier variable, BooleanUnaryExpression exp) {
        Register ret = allocator.allocate(variable.getName());
        if(exp.getOperator()==Operator.BooleanNot){

            Operand opr = moveConstantToRegister(allocateOperand(exp.getOperand()));
            Instruction negate = new Instruction(label, OpCode.SUB, ret, zeroRegister, opr);
            Instruction addOne = new Instruction(OpCode.ADDI, ret, ret, new Literal<>(1));
            codeGenerator.emitCode(negate);
            codeGenerator.emitCode(addOne);
        }
    }

    Register moveConstantToRegister(Operand op1){
        if(op1 instanceof Register){
            return (Register) op1;
        }else {
            Register r = allocator.allocate();
            codeGenerator.emitCode(new Instruction(OpCode.ADDI, r, zeroRegister, op1));
            return r;
        }
    }
    private void executeSimpleRelExp(String label, Identifier variable, RelationExpression exp) {
        String branchLabel = "BR"+ ThreeAdressTransformer.getRandomId();
        Operand op1 = moveConstantToRegister(allocateOperand(exp.getLeftOperand()));
        Operand op2 = moveConstantToRegister(allocateOperand(exp.getRightOperand()));
        Operator operator = exp.getOperator();
        OpCode opcode;
        Operand temp;
        Operand dest = allocateOperand(variable);
        switch(operator){
            case Equals://branch beqz
                opcode = OpCode.BEQZ;
                break;
            case NotEquals:
                opcode = OpCode.BNEZ;
                break;
            case GreatEqualThan://branch nez
                opcode = OpCode.BGEZ;
                temp = op1;
                op1 = op2;
                op2 = temp;
                break;
            case LessEqualThan:
                opcode = OpCode.BGEZ;

                break;
            case GreaterThan:
                opcode = OpCode.BLTZ;

                break;
            case LessThan:
                opcode = OpCode.BLTZ;
                temp = op1;
                op1 = op2;
                op2 = temp;
                break;
            default:
                opcode = OpCode.BNEZ;
                break;
        }
        Register diff = allocator.allocate();
        String endLabel = branchLabel+"ed";
        String condTrueLabel = branchLabel+"tr";
        Instruction comp = new Instruction (label, OpCode.SUB,diff, op1, op2);
        Instruction gotoCondTrue = new Instruction (opcode, diff, new Literal<>(condTrueLabel));

        Instruction gotoEnd = new Instruction(OpCode.JMP, new Literal<>(endLabel));
        Instruction end = new Instruction(endLabel, OpCode.NOP);
        Instruction setBoolTestTrue =new Instruction(condTrueLabel, OpCode.ADDI,dest , zeroRegister, new Literal<>(1));
        Instruction setBoolTestFalse = new Instruction(OpCode.ADDI, dest, zeroRegister, new Literal<>(0));
        codeGenerator.emitCode(comp);
        codeGenerator.emitCode(gotoCondTrue);
        codeGenerator.emitCode(setBoolTestFalse);
        codeGenerator.emitCode(gotoEnd);
        codeGenerator.emitCode(setBoolTestTrue);
        codeGenerator.emitCode(end);
    }

    private void executeNumUnExp(String label, Identifier variable, NumericalUnaryExpression exp) {
        OpCode op;
        Operand source1 = allocateOperand(exp);
        Register dest = allocator.allocate(variable.getName());
        switch(exp.getOperator()){
            case Add:
                op = OpCode.ADD;
                break;
            case Sub:
                op = OpCode.SUB;
                break;
            default:
                op = OpCode.ADD;
        }
        codeGenerator.emitCode(new Instruction(op, dest, zeroRegister, source1));
    }
    private void executeNumBinExp(String label, Identifier variable, NumericalBinaryExpression exp) {
        Operand source1 = allocateOperand(((NumericalBinaryExpression) exp).getLeftOperand());
        Operand source2 = allocateOperand(((NumericalBinaryExpression) exp).getRightOperand());
        source1 = moveConstantToRegister(source1);
        source2 = moveConstantToRegister(source2);
        OpCode op;
        Register dest = allocator.allocate(variable.getName());
        switch(((NumericalBinaryExpression) exp).getOperator()){
            case Add:
                op = OpCode.ADD;
                break;
            case Sub:
                op = OpCode.SUB;
                break;
            case Mul:
                op = OpCode.MUL;
                break;
            case Div:
                op = OpCode.DIV;
                break;
            default:
                op=OpCode.ADD;
        }

        Instruction i = new Instruction(label, op, dest, source1, source2);
        codeGenerator.emitCode(i);
    }

    private Operand allocateOperand(Expression v){
        Operand opr = null;
        if(v instanceof IntegerLiteral){
            opr = new  Literal<Integer>(((IntegerLiteral) v).getIntValue());
        }else if(v instanceof FloatLiteral) {
            opr= new Literal<Float>(((FloatLiteral) v).getFloatValue());
        }else if(v instanceof BooleanLiteral){
            boolean b = ((BooleanLiteral) v).getBoolValue();
            int boolInt = b?1:0;
            opr = new Literal<>(boolInt);
        }else if(v instanceof StringLiteral){
            opr = lookupStringMemory(((StringLiteral) v).getString());
        }else if(v instanceof ArrayAccess){

        }else if(v instanceof FunctionCall){

        }else if(v instanceof Identifier){
            opr =allocator.allocate(((Identifier) v).getName());
        }


        return opr;
    }

    private Instruction createInstruction(Statement st){
        return new Instruction(st.label,null);
    }
    private Literal<Integer> lookupStringMemory(String s){
        StringObject o = memory.findString(s);
        if (o ==null){
            return new Literal<>(-1);
        }else
        return new Literal<>(o.getAddress());
    }
    private Literal<Integer> getBooleanMemLocation(boolean bool){
        int add = bool? 8:0;
        return new Literal<>(add);
    }
}
