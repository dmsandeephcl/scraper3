import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ReadLastNLines {

    private List<ExceptionHolder> lastNExcpetionList = null;

    private ExceptionProcessorHelper exceptionProcessorHelper = null;

    private Pattern p = Pattern.compile("(.*at .*)|(.*Caused by:.*)|(.*Caused by:.*)|(.*serror.*)|(.*+Exception: .*)|(.*... 1 more.*)|(.*SEVERE.*)|(.*WARNING.*)|(.*Exception.*)|(.*Error.*)");//. represents single character

    private String lineBreak = null;

    public Pattern getP() {
        return p;
    }

    public void setP(Pattern p) {
        this.p = p;
    }

    public void init(ExceptionProcessorHelper exceptionProcessorHelper,Pattern p,String lineBreak){
        this.exceptionProcessorHelper = exceptionProcessorHelper;
        this.p = p;
        this.lineBreak = lineBreak;
    }

    public List<ExceptionHolder> processErrorLogFile(File errorLogFile,int nExceptionHolders){
        List<ExceptionHolder> lastNExcpetionFinalList = new ArrayList<ExceptionHolder>();

        try {
            List<ExceptionHolder> lastNExcpetionList = getLastNExcpetionList(errorLogFile, nExceptionHolders);

            for (int i=0;i< lastNExcpetionList.size();i++) {
                ExceptionHolder exceptionHolder2 = lastNExcpetionList.get(i);
                if(getExceptionProcessorHelper().startProcessingMethodHolderObject(exceptionHolder2)){
                    lastNExcpetionFinalList.add(exceptionHolder2);
                }
//            System.out.println("---->"+exceptionHolder2.getStackTrace());
                System.out.println("---->" + exceptionHolder2.getStartLineNumber());
                System.out.println("---->" + exceptionHolder2.getEndLineNumber());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return lastNExcpetionFinalList;
    }
    private List<ExceptionHolder> getLastNExcpetionList(File errorLogFile,int nExceptionHolders) throws IOException {
        setLastNExcpetionList(new ArrayList<ExceptionHolder>());
        StringBuffer errorErrorBufferString = new StringBuffer();
        boolean matches = false;

        int n_lines = 10;
        int counter = 0;
        ReversedLinesFileReader reversedLinesFileReader = new ReversedLinesFileReader(errorLogFile);
        boolean errorLogStarted = false;

        List<String> errorList = new ArrayList<String>();

        StringBuffer errorBufferString = new StringBuffer();
       int currentLineNumber = 0;

        ExceptionHolder exceptionHolder = null;

        while(counter < nExceptionHolders) {
            String lineStr = reversedLinesFileReader.readLine();

            if (lineStr != null) {
                ++currentLineNumber;
                matches = getP().matcher(lineStr).matches();
                if (matches) {
                    if (!errorLogStarted) {
                        exceptionHolder = new ExceptionHolder();
                        errorLogStarted = true;
                        exceptionHolder.setStartLineNumber(currentLineNumber);
                    }
                    errorList.add(lineStr);
                } else {
                    if (errorLogStarted) {
                        errorLogStarted = false;
                        for (int i = errorList.size() - 1; i >= 0; i--) {
                            errorBufferString.append(errorList.get(i) + lineBreak);
                        }
                        exceptionHolder.setEndLineNumber(currentLineNumber);
                        exceptionHolder.setStackTrace(errorBufferString.toString());
                        errorBufferString = new StringBuffer();
                        getLastNExcpetionList().add(exceptionHolder);
                        errorList = new ArrayList<String>();
                        counter++;
                    }
                }
            }
        }
        reversedLinesFileReader.close();

        return getLastNExcpetionList();
    }


    public List<ExceptionHolder> getLastNExcpetionList() {
        return lastNExcpetionList;
    }

    public void setLastNExcpetionList(List<ExceptionHolder> lastNExcpetionList) {
        this.lastNExcpetionList = lastNExcpetionList;
    }

    public ExceptionProcessorHelper getExceptionProcessorHelper() {
        return exceptionProcessorHelper;
    }

    public void setExceptionProcessorHelper(ExceptionProcessorHelper exceptionProcessorHelper) {
        this.exceptionProcessorHelper = exceptionProcessorHelper;
    }

    public static void main(String[] args){
        try {
            File fileObj = new File("G:\\project\\logs\\test.txt");
            ReadLastNLines readLastNLine = new ReadLastNLines();

            ExceptionProcessorHelper exceptionProcessorHelper = new ExceptionProcessorHelper();
            exceptionProcessorHelper.setXmlFilePath(new File("G:\\project\\xml\\solutions.txt"));
            exceptionProcessorHelper.setExcludeExceptionXmlFilePath(new File("G:\\project\\gitscraper\\scraper\\scraper\\xml\\excludeExceptions.txt"));
            Pattern p = Pattern.compile("(.*at .*)|(.*Caused by:.*)|(.*Caused by:.*)|(.*serror.*)|(.*+Exception: .*)|(.*... 1 more.*)|(.*SEVERE.*)|(.*WARNING.*)|(.*Exception.*)|(.*Error.*)");//. represents single character
            readLastNLine.init(exceptionProcessorHelper,p,"\n");
            List<ExceptionHolder> exceptionHolderList = readLastNLine.processErrorLogFile(fileObj,2);
            System.out.println("exceptionHolderList --->"+exceptionHolderList.size());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
