package Semantics;

import Tree.CompilationUnit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Zaiyang on 19/12/2015.
 */
public abstract class SemanticsAnalyzer {

    protected List<SemanticsException> errors = new ArrayList<SemanticsException>();
    protected CompilationUnit unit;

    public SemanticsAnalyzer(CompilationUnit unit) {
        this.unit = unit;
    }
    public SemanticsAnalyzer(){

    }

    public boolean hasErrors(){
        return errors.size()>0;
    }

    public List<SemanticsException> getErrors(){
        return errors;
    }

    protected void error(SemanticsException e){
        getErrors().add(e);
    }

    protected boolean assertCondition(boolean pred, SemanticsException e){
        if(!pred)
            error(e);
        return pred;
    }

    void assertEquals(Object o1, Object o2, SemanticsException e){
        assertCondition(o1.equals(o2), e);
    }

    public static <T extends Comparable<T>> boolean areElementsUnqiue(List<T> list){
        if(list.size()<2) return true;

        Collections.sort(list);
        T current = list.get(0);
        for(int i = 1; i < list.size(); i++){
            if(current.equals(list.get(i))){
                return false;
            }
            current=list.get(i);
        }
        return true;
    }
}
