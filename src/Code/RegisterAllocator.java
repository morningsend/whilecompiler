package Code;

import Tree.CompilationUnit;
import Tree.Identifier;

import java.util.HashMap;

/**
 * Created by Zaiyang on 20/12/2015.
 */
public class RegisterAllocator {
    public CompilationUnit unit;
    HashMap<String, Register> allocationTable = new HashMap<>();
    public RegisterAllocator (CompilationUnit unit){
        this.unit = unit;
    }
    public Register allocate(String name){
        Register r = allocationTable.get(name);
        if(r==null){
            r = Register.create();
            allocationTable.put(name, r);
        }
        return r;
    }
    public Register allocate(Identifier id){
        return allocate(id.getName());
    }
    public Register allocate(){
        return Register.create();
    }
}
