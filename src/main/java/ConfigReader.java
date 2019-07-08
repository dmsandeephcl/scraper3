import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {


    private String choosenRegionName;
    private String choosenSAPIName;
    private String choosenSAPIInetName;
    private String choosenSOASTSName;
    private String choosenSOASTSInetName;
    private String choosenExtAPIName;
    private String choosenExtAPIInetName;
    private File configFilePath = null;

    private String SAPI_LOG_CONST = "-sapi-log-services";
    private String SAPI_LOG_INET_CONST = "-sapi-inetpub";
    private String EXTAPI_LOG_CONST = "-extapi-log-services";
    private String EXTAPI_LOG_INET_CONST = "-extapi-inetpub";
    private String SOASTS_LOG_CONST = "-soasts-log-services";
    private String SOASTS_LOG_INET_CONST = "-soasts-inetpub";

    public void init(){
    if(configFilePath == null || !configFilePath.exists()){
            System.err.println("Please choose config file path");
        }
        if(choosenRegionName == null){
            System.err.println("Please choosen Region Name");
        }

        try {

            Properties prop = new Properties();
            prop.load(new FileInputStream(configFilePath));
            choosenSAPIName = prop.getProperty(choosenRegionName + SAPI_LOG_CONST);
            choosenSAPIInetName = prop.getProperty(choosenRegionName + SAPI_LOG_INET_CONST);
            choosenExtAPIName = prop.getProperty(choosenRegionName + EXTAPI_LOG_CONST);
            choosenExtAPIInetName = prop.getProperty(choosenRegionName + EXTAPI_LOG_INET_CONST);
            choosenSAPIName = prop.getProperty(choosenRegionName + SAPI_LOG_CONST);
            choosenSAPIName = prop.getProperty(choosenRegionName + SAPI_LOG_CONST);
            choosenSOASTSName = prop.getProperty(choosenRegionName + SOASTS_LOG_CONST);
            choosenSOASTSInetName = prop.getProperty(choosenRegionName + SOASTS_LOG_INET_CONST);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getChoosenRegionName() {
        return choosenRegionName;
    }

    public void setChoosenRegionName(String choosenRegionName) {
        this.choosenRegionName = choosenRegionName;
    }

    public String getChoosenSAPIName() {
        return choosenSAPIName;
    }

    public void setChoosenSAPIName(String choosenSAPIName) {
        this.choosenSAPIName = choosenSAPIName;
    }

    public String getChoosenSAPIInetName() {
        return choosenSAPIInetName;
    }

    public void setChoosenSAPIInetName(String choosenSAPIInetName) {
        this.choosenSAPIInetName = choosenSAPIInetName;
    }

    public String getChoosenSOASTSName() {
        return choosenSOASTSName;
    }

    public void setChoosenSOASTSName(String choosenSOASTSName) {
        this.choosenSOASTSName = choosenSOASTSName;
    }

    public String getChoosenSOASTSInetName() {
        return choosenSOASTSInetName;
    }

    public void setChoosenSOASTSInetName(String choosenSOASTSInetName) {
        this.choosenSOASTSInetName = choosenSOASTSInetName;
    }

    public String getChoosenExtAPIName() {
        return choosenExtAPIName;
    }

    public void setChoosenExtAPIName(String choosenExtAPIName) {
        this.choosenExtAPIName = choosenExtAPIName;
    }

    public String getChoosenExtAPIInetName() {
        return choosenExtAPIInetName;
    }

    public void setChoosenExtAPIInetName(String choosenExtAPIInetName) {
        this.choosenExtAPIInetName = choosenExtAPIInetName;
    }

    public File getConfigFilePath() {
        return configFilePath;
    }

    public void setConfigFilePath(File configFilePath) {
        this.configFilePath = configFilePath;
    }
}
