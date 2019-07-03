import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartExtractingLogs
{

    public static void main(String args[])throws Exception{
        BufferedReader fr=new BufferedReader(new FileReader("G:\\project\\logs\\test.txt"));
        int i;

        Pattern p = Pattern.compile("(.*at .*)|(.*Caused by:.*)|(.*Caused by:.*)|(.*serror.*)|(.*+Exception: .*)|(.*... 1 more.*)|(.*SEVERE.*)|(.*WARNING.*)|(.*Exception.*)|(.*Error.*)");//. represents single character
        Matcher m = null;
        boolean b = false;

        String lineStr = null;
        boolean errorStarted = false;
        StringBuffer errorStringBufferObj = new StringBuffer();

        long linuNumberCount = 0;
        ExceptionHolder exceptionHolder = null;
        List<ExceptionHolder> exceptionHolderList = new ArrayList<ExceptionHolder>();
        while((lineStr=fr.readLine())!= null) {
            ++linuNumberCount;
            boolean matches = p.matcher(lineStr).matches();
            if(matches){
                if(!errorStarted){
                    errorStarted = true;
                    exceptionHolder = new ExceptionHolder();
                    exceptionHolder.setStartLineNumber(linuNumberCount);
                }
                System.out.println(lineStr);
                errorStringBufferObj.append(lineStr + "\n");
            }else {
                if (errorStarted) {
                    errorStarted = false;
                    exceptionHolder.setEndLineNumber(linuNumberCount - 1);
                    exceptionHolder.setStackTrace(errorStringBufferObj.toString());
                    exceptionHolderList.add(exceptionHolder);
                    errorStringBufferObj = new StringBuffer();
                    exceptionHolder = null;
                }
            }
        }
        if (errorStarted) {
            errorStarted = false;
            exceptionHolder.setEndLineNumber(linuNumberCount - 1);
            exceptionHolder.setStackTrace(errorStringBufferObj.toString());
            exceptionHolderList.add(exceptionHolder);
            exceptionHolder = null;
        }

        ExceptionProcessorHelper exceptionProcessorHelper = new ExceptionProcessorHelper();
        exceptionProcessorHelper.setXmlFilePath(new File("G:\\project\\xml\\solutions.txt"));
        for (ExceptionHolder exceptionHolder2:exceptionHolderList
             ) {
            exceptionProcessorHelper.startProcessingMethodHolderObject(exceptionHolder2);
//            System.out.println("---->"+exceptionHolder2.getStackTrace());
            System.out.println("---->"+exceptionHolder2.getStartLineNumber());
            System.out.println("---->"+exceptionHolder2.getEndLineNumber());
        }
        fr.close();
    }
}
