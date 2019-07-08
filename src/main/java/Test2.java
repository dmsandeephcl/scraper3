import java.io.File;
import java.util.List;

public class Test2 {

    public static void main(String[] args){
        File fileObj = new File("G:\\project\\files");
        File fileObj2 = new File("G:\\project\\files2");
        File fileObj3 = new File("G:\\project\\files3");
        FileChangeNotificationsHelper fileChangeNotificationsHelper = new FileChangeNotificationsHelper();
        fileChangeNotificationsHelper.setExtapiFolderPath(fileObj);
        fileChangeNotificationsHelper.setSapiFolderPath(fileObj2);
        fileChangeNotificationsHelper.setSoastsFolderPath(fileObj3);
        fileChangeNotificationsHelper.init();
        fileChangeNotificationsHelper.captureBefore();

        try{
            Thread.sleep(20000);
        }catch (Exception e){
            e.printStackTrace();
        }
        fileChangeNotificationsHelper.captureAfter();
        System.out.println("<html><body>"+fileChangeNotificationsHelper.buildTotalFileDifferences()+"</body></html>");


    }
}
