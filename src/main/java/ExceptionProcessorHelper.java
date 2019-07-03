import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

public class ExceptionProcessorHelper {

    private AllExceptionTypes allExceptionTypes = new AllExceptionTypes();

    private File xmlFilePath = null;



    public void startProcessingMethodHolderObject(ExceptionHolder exceptionHolder) {
        if (getAllExceptionTypes() == null || getAllExceptionTypes().getExceptionType() != null && getAllExceptionTypes().getExceptionType().size() == 0 ) {
            if (!init()) {
                return;
            }
        }
        for (ExceptionType exceptionType : allExceptionTypes.getExceptionType()) {
            if (exceptionType.processExcpetionHolder(exceptionHolder)) {
                return;
            }
        }
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
}
