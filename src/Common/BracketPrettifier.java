package Common;

import java.util.StringTokenizer;

/**
 * Created by Zaiyang on 19/12/2015.
 */
public class BracketPrettifier {
    public String source;
    private StringBuilder sb;
    private int indentLevel=-1;
    private StringTokenizer tokenizer;
    public BracketPrettifier(String printableTree) {
        source = printableTree;
    }
    public String prettify(){
        sb = new StringBuilder();
        tokenizer = new StringTokenizer(source, "()", true);

        while(tokenizer.hasMoreElements()){
            String token = tokenizer.nextToken();
            switch (token){
                case "(":
                    indentLevel++;
                    break;
                case ")":
                    indentLevel--;
                    break;
                case " ":
                    break;
                case " ;":
                    break;
                default:
                    sb.append(repeat("|\t",indentLevel))
                            .append(token)
                            .append("\n");
            }
        }
        return sb.toString();
    }
    private String repeat(String s, int n){
        StringBuilder sb = new StringBuilder();
        if(n < 0){
            sb.append("");
        }else {
            for(int i = 0; i<n; i++)
                sb.append(s);
        }
        return sb.toString();
    }
}
