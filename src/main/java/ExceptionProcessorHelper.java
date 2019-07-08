import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

public class ExceptionProcessorHelper {

    private AllExceptionTypes allExceptionTypes = new AllExceptionTypes();

    private IgnoreAllExceptionTypes ignoreAllExceptionTypes = new IgnoreAllExceptionTypes();

    private File xmlFilePath = null;

    private File excludeExceptionXmlFilePath = null;

    public boolean startProcessingMethodHolderObject(ExceptionHolder exceptionHolder) {
        if (getAllExceptionTypes() == null || getAllExceptionTypes().getExceptionType() != null && getAllExceptionTypes().getExceptionType().size() == 0 ) {
            if (!init()) {
                return false;
            }
        }
        if(isExceptionalHolderIsExcluded(exceptionHolder)){
            return false;
        }
        for (ExceptionType exceptionType : getAllExceptionTypes().getExceptionType()) {
            if (exceptionType.processExcpetionHolder(exceptionHolder)) {
                return true;
            }
        }
        return true;
    }

    private boolean init() {
        try {
            StringBuffer errorErrorBufferString = new StringBuffer();

            BufferedReader fr = new BufferedReader(new FileReader(xmlFilePath));
            String lineStr = null;
            while ((lineStr = fr.readLine()) != null) {
                errorErrorBufferString.append(lineStr);
            }
            fr.close();

            if (errorErrorBufferString.toString().trim().length() == 0) {
                System.err.println("Error config file is empty");
                return false;
            }

            JAXBContext jaxbContext;


            jaxbContext = JAXBContext.newInstance(AllExceptionTypes.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            setAllExceptionTypes((AllExceptionTypes) jaxbUnmarshaller.unmarshal(new StringReader(errorErrorBufferString.toString())));


            errorErrorBufferString = new StringBuffer();

            fr = new BufferedReader(new FileReader(excludeExceptionXmlFilePath));
            lineStr = null;
            while ((lineStr = fr.readLine()) != null) {
                errorErrorBufferString.append(lineStr);
            }
            fr.close();

            if (errorErrorBufferString.toString().trim().length() == 0) {
                System.err.println("Exclude exceptions Error config file is empty");
                return false;
            }


            jaxbContext = JAXBContext.newInstance(IgnoreAllExceptionTypes.class);

            jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            setIgnoreAllExceptionTypes((IgnoreAllExceptionTypes) jaxbUnmarshaller.unmarshal(new StringReader(errorErrorBufferString.toString())));

            System.out.println(getAllExceptionTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (getAllExceptionTypes() != null && getAllExceptionTypes().getExceptionType().size() != 0);
    }

    public File getXmlFilePath() {
        return xmlFilePath;
    }

    public void setXmlFilePath(File xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public AllExceptionTypes getAllExceptionTypes() {
        return allExceptionTypes;
    }

    public void setAllExceptionTypes(AllExceptionTypes allExceptionTypes) {
        this.allExceptionTypes = allExceptionTypes;
    }

    public File getExcludeExceptionXmlFilePath() {
        return excludeExceptionXmlFilePath;
    }

    public void setExcludeExceptionXmlFilePath(File excludeExceptionXmlFilePath) {
        this.excludeExceptionXmlFilePath = excludeExceptionXmlFilePath;
    }

    public IgnoreAllExceptionTypes getIgnoreAllExceptionTypes() {
        return ignoreAllExceptionTypes;
    }

    public void setIgnoreAllExceptionTypes(IgnoreAllExceptionTypes ignoreAllExceptionTypes) {
        this.ignoreAllExceptionTypes = ignoreAllExceptionTypes;
    }

    private boolean isExceptionalHolderIsExcluded(ExceptionHolder exceptionHolder){
        String stackTrace = exceptionHolder.getStackTrace().toLowerCase();
        for (String excludeString: ignoreAllExceptionTypes.getIgnoreExceptionType()) {
            excludeString = excludeString.toLowerCase();
            if(stackTrace.indexOf(excludeString) != -1){
                    return  true;
            }

        }
        return false;
    }
}
