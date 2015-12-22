package Semantics;

import Model.DataType;
import Tree.BooleanLiteral;
import Tree.Expression;
import Tree.FloatLiteral;
import Tree.IntegerLiteral;

/**
 * Created by Zaiyang on 14/12/2015.
 */
public class TypeRules {
    public static DataType numericalBinopType(DataType left, DataType right) throws InvalidTypeException{
        if(!isNumericType(left))
            return DataType.Unresolved;
        if(!isNumericType(right))
            return DataType.Unresolved;
        if(left == DataType.Integer && right == DataType.Integer){
            return DataType.Integer;
        }else{
            return DataType.Real;
        }
    }
    public static boolean canConvertType(DataType from, DataType to){
        boolean conversionAllowed = from==to? true:false;

        switch(from){
            case Integer:
            case Real:
                if(to== DataType.Real || to==DataType.Integer)
                    conversionAllowed = true;
                break;
        }
        return conversionAllowed;
    }

    public static boolean isNumericType(DataType type) {
        return type==DataType.Integer || type == DataType.Real ;
    }
    public static boolean isBooleanType(DataType type){
        return type==DataType.Boolean;
    }
    public static boolean isUndefinedType(DataType type){ return type==DataType.Unresolved; }
    public static boolean isArrayType(DataType type) { return type== DataType.Array; }

    public static boolean canAssign(DataType lhsType, DataType rhsType) {
        if(isUndefinedType(lhsType)){
            return isBooleanType(rhsType) || isNumericType(rhsType) || isArrayType(rhsType) || isStringType(rhsType);
        }
        else
            return canConvertType(lhsType, rhsType);
    }

    private static boolean isStringType(DataType rhsType) {
        return rhsType==DataType.String;
    }

    public static boolean isExpressionLiteral(Expression e){
        boolean isLiteral = e instanceof BooleanLiteral || e instanceof IntegerLiteral
                || e instanceof FloatLiteral;
        return isLiteral;
    }
}
