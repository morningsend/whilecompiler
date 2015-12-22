package Code;

import java.util.*;

/**
 * Created by Zaiyang on 16/12/2015.
 */
public class Memory {
    private ArrayList<Object> ram = new ArrayList<>();
    private int size = 0;
    public Object getObject(int i ){
        return ram.get(i);
    }
    public List<Object> getObjects(){
        return ram;
    }
    public HashMap<String, Object> objectLookupTable = null;
    public static abstract class Object {
        public int address =0;
        public abstract int getSize();

        public abstract String getId();

        public abstract byte[] toBytes();
        public Object(int address){
            this.address = address;
        }
        public Object(){

        }
        public int getAddress(){
            return address;
        }
        public void setAddress(int address){
            this.address = address;
        }

    }
    public static class WordObject extends Object{
        private String id;
        private static int counter = 0;
        public static String typePrefix = "wordobject";
        private static int nextCount(){
            return ++counter;
        }

        public WordObject(){
            id = typePrefix+System.currentTimeMillis()+counter;
        }
        @Override
        public int getSize() {
            return 4;
        }

        @Override
        public String getId() {
            return typePrefix + System.currentTimeMillis();
        }

        @Override
        public byte[] toBytes() {
            return new byte[4];
        }

    }

    public static class VariableObject extends WordObject{
        protected String name;
        public static String typePrefix = "variable";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public VariableObject(String name){
            super();
            this.setName(name);
        }
        @Override
        public String getId(){
            return typePrefix+name;
        }


    }
    public static class ArrayObject extends VariableObject{
        public static final String typePrefix="array";
        public int length;
        public ArrayObject(String name) {
            super(name);
            length = 1;
        }
        public ArrayObject(String name, int length){
            super(name);
            this.length = length;
        }
        public int getLength(){
            return length;
        }
        public void setLength(int length){
            this.length = length;
        }
        @Override
        public int getSize(){
            return length*4;
        }
        @Override
        public byte[] toBytes(){
            return new byte[4*length];
        }

        @Override
        public String getId(){
            return typePrefix+getName();
        }
    }
    public static class StringObject extends Object{
        public String data;

        public static String typePrefix = "string";

        public StringObject(int address, String data){
            super(address);
            this.data = data;
        }
        public StringObject(String data){
            this(0,data);
        }
        public StringObject(){
            super();
        }
        @Override
        public int getSize() {
            int length = data.length()+1;
            // find nearest multiple of 4
            int byteLength = (length % 4 == 0)? length : ((length>>2)+1)<<2;
            return byteLength;
        }

        @Override
        public String getId() {
            return typePrefix+data;
        }


        @Override
        public byte[] toBytes() {
            byte[] bytes = new byte[getSize()];
            Arrays.fill(bytes, (byte) 0);
            System.arraycopy(data.getBytes(), 0, bytes, 0, data.length());
            return bytes;
        }
        public String getString(){
            return data;
        }
    }
    public static class IntObject extends WordObject{
        public int data;

        @Override
        public int getSize() {
            return 4;
        }

        @Override
        public byte[] toBytes() {
            byte[] bytes = new byte[4];
            for(int i = 0; i<4;i++){
                bytes[i] = (byte) (data>>(i*8) & 0xff);
            }
            return bytes;
        }
        public int getInt(){
            return data;
        }
    }
    public Memory(){

    }
    public void insert(Object memoryObject){

        int add = size;
        memoryObject.setAddress(add);
        ram.add(memoryObject);
        size+=memoryObject.getSize();
    }

    public static byte[] padBytesToMultipleOfFour(byte[] bytes) {
        int length = bytes.length;
        if(length%4 != 0){
            length = ((length>>2)+1)<<2;
        }
        byte[] bytesPadded = new byte[length];
        Arrays.fill(bytesPadded,(byte) 0);
        System.arraycopy(bytes,0,bytesPadded,0,bytes.length);
        return bytesPadded;
    }
    public boolean containsInt(int i){
        boolean found = false;
        Iterator<Object> iter = ram.iterator();
        while(iter.hasNext()){
            Object o = iter.next();
            if(o instanceof IntObject){
                if (((IntObject) o).getInt()==i){
                    return true;
                }

            }
        }
        return false;
    }
    public boolean containsString(String s){
        boolean found = false;
        Iterator<Object> iter = ram.iterator();
        while(iter.hasNext()){
            Object o = iter.next();
            if(o instanceof StringObject){
                if (((StringObject) o).getString().equals(s)){
                    return true;
                }

            }
        }
        return false;
    }
    public StringObject findString(String s){
        if(objectLookupTable ==null){
            return null;
        }
        return (StringObject) objectLookupTable.get(StringObject.typePrefix+s);
    }
    public Object findObject(String s){
        if(objectLookupTable == null)
            return null;
        return objectLookupTable.get(s);
    }
    public Object findVariable(String name){
        if(objectLookupTable == null)
            return null;
        return objectLookupTable.get(VariableObject.typePrefix+name);
    }
    public Object findArray(String name){
        if(objectLookupTable == null)
            return null;
        return objectLookupTable.get(ArrayObject.typePrefix+name);
    }
    public void createObjectLookupTable(){
        objectLookupTable = new HashMap<String, Object>(ram.size());
        for(Object o : ram){
            objectLookupTable.put(o.getId(), o);
        }
    }
}
