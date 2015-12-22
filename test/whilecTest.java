import junit.framework.TestCase;

import java.io.*;

public class whilecTest extends TestCase {
    public static final String baseDir = "example\\";

    public void testRunTest4() throws IOException {

        compileFileAndRun("test4.w", "test4.ass");
    }
    public void testRunTest3() throws IOException {

        compileFileAndRun("test3.w", "test3.ass");
    }
    public void testRunTest2() throws IOException {

        compileFileAndRun("test2.w", "test2.ass");
    }
    public void testRunTest1() throws IOException {

        compileFileAndRun("test1.w", "test1.ass");
    }
    public void compileFileAndRun(String sourceFile, String outputFile) throws IOException {
        String[] args = { baseDir+sourceFile, "-out", baseDir+outputFile
        };
        whilec.main(args);
        Process assmuleProcess = new ProcessBuilder(baseDir+"assmule.exe", baseDir+outputFile).start();
        InputStream out = assmuleProcess.getInputStream();
        InputStream err = assmuleProcess.getErrorStream();
        System.out.println("output from running assmule on file" + args[0]);
        BufferedReader reader = new BufferedReader(new InputStreamReader(out));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(err));

        displayStreamContent(reader);
        System.out.println("\nErrors:");
        displayStreamContent(errorReader);

    }

    private void displayStreamContent(BufferedReader reader) throws IOException {
        String s;
        while((s = reader.readLine())!=null){
            System.out.println(s);
        }
    }
    public void testRunTest5() throws IOException {
        compileFileAndRun("test5.w", "test5.ass");
    }

    public void testRunTest5a() throws IOException {
        compileFileAndRun("test5a.w", "test5a.ass");
    }

    public void testRunTest6() throws IOException {
        compileFileAndRun("test6.w", "test6.ass");
    }
    public void testRunTest6a() throws IOException{
        compileFileAndRun("test6a.w", "test6a.ass");
    }

    public void testRunTest7() throws IOException{
        compileFileAndRun("test7.w", "test7.ass");
    }
    public void testRunBulleye() throws IOException{
        compileFileAndRun("bulleye.w", "bulleye.ass");
    }
    public void testRunTest8() throws IOException {
        compileFileAndRun("test8.w", "test8.ass");
    }
    public void testScope() throws IOException{
        compileFileAndRun("test9.w", "test9.ass");
    }

    public void testFloat() throws IOException{
        compileFileAndRun("test10.w", "test10.ass");
    }

    public void testString() throws IOException{
        compileFileAndRun("test10.w", "test10.ass");
    }
}