package Common;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Zaiyang on 19/12/2015.
 */
public class OptionParser {
    private boolean hasTreeOption=false;
    private boolean hasOutOption=false;
    private String outFileName = "";
    private String inFileName = null;
    public OptionParser(String[] args) {
        List<String> options = Arrays.asList(args);
        Iterator<String> iter = options.iterator();
        while(iter.hasNext()){
            String arg = iter.next();
            if(arg.equals("-tree"))
                hasTreeOption = true;
            else if(arg.equals("-out")){
                hasOutOption = true;
                if(iter.hasNext()){
                    outFileName = iter.next();
                }else {
                    System.err.println("-out option is specified but no file name is found");
                }
            }else {
                inFileName = arg;
            }
        }
    }
    public boolean hasInputFileName(){
        return inFileName!=null;
    }

    public boolean isTreeFlagSet(){
        return hasTreeOption;
    }
    public String getInputFileName(){
        return inFileName;
    }
    public boolean hasOutFileName(){
        return hasOutOption;
    }
    public String getOutFileName(){
        return outFileName;
    }

}
