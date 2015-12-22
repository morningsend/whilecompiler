package Semantics;

import Model.DataType;
import Model.Kind;
import Model.Node;
import Tree.Identifier;

/**
 * Created by Zaiyang on 13/12/2015.
 */
public class Element {
    public SymbolKind kind = SymbolKind.Name;
    public DataType type = DataType.Unresolved;
    public Node node;
    public String name;
    public Scope boundScope;

    public Scope getBoundScope() {
        return boundScope;
    }

    public void setBoundScope(Scope boundScope) {
        this.boundScope = boundScope;
    }

    public Element(){}
    public void setKind(SymbolKind kind) {
        this.kind = kind;
    }


    public SymbolKind getKind() {
        return kind;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void copyElementAttributeToIdentifier(Identifier iden, Element e){
        iden.setDataType(e.getType());
        iden.setKind(Kind.Variable);
        iden.setBoundScope(e.getBoundScope());
    }
}
