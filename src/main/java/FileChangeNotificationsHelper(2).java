import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileChangeNotificationsHelper {

    private File sapiFolderPath;
    private File sapiInetFolderPath;
    private File extapiFolderPath;
    private File extapiInetFolderPath;
    private File soastsFolderPath;
    private File soastsInetFolderPath;

    private List<File> listOfFileDifferencesForSAPI = null;
    private List<File> listOfFileDifferencesForEXTAPI = null;
    private List<File> listOfFileDifferencesForSOASTS = null;

    FilesChangedNotifications sapiFilesChangedNotificationsObj = new FilesChangedNotifications();
    FilesChangedNotifications extapiFilesChangedNotificationsObj = new FilesChangedNotifications();
    FilesChangedNotifications soastsFilesChangedNotificationsObj = new FilesChangedNotifications();

    FilesChangedNotifications sapiInetFilesChangedNotificationsObj = new FilesChangedNotifications();
    FilesChangedNotifications extapiInetFilesChangedNotificationsObj = new FilesChangedNotifications();
    FilesChangedNotifications soastsInetFilesChangedNotificationsObj = new FilesChangedNotifications();


    public File getSapiFolderPath() {
        return sapiFolderPath;
    }

    public void setSapiFolderPath(File sapiFolderPath) {
        this.sapiFolderPath = sapiFolderPath;
    }

    public File getExtapiFolderPath() {
        return extapiFolderPath;
    }

    public void setExtapiFolderPath(File extapiFolderPath) {
        this.extapiFolderPath = extapiFolderPath;
    }

    public File getSoastsFolderPath() {
        return soastsFolderPath;
    }

    public void setSoastsFolderPath(File soastsFolderPath) {
        this.soastsFolderPath = soastsFolderPath;
    }


    public void init() {
        sapiFilesChangedNotificationsObj.setVmName("SAPI");
        sapiFilesChangedNotificationsObj.setFolderPath(sapiFolderPath);
        extapiFilesChangedNotificationsObj.setVmName("EXTAPI");
        extapiFilesChangedNotificationsObj.setFolderPath(extapiFolderPath);
        soastsFilesChangedNotificationsObj.setVmName("SOASTS");
        soastsFilesChangedNotificationsObj.setFolderPath(soastsFolderPath);

        sapiInetFilesChangedNotificationsObj.setFolderPath(getSapiInetFolderPath());
        extapiInetFilesChangedNotificationsObj.setFolderPath(getExtapiInetFolderPath());
        soastsInetFilesChangedNotificationsObj.setFolderPath(getSoastsInetFolderPath());
    }

    public void captureBefore() {
        sapiFilesChangedNotificationsObj.captureBeforeSnapshot();
        extapiFilesChangedNotificationsObj.captureBeforeSnapshot();
        soastsFilesChangedNotificationsObj.captureBeforeSnapshot();

        sapiInetFilesChangedNotificationsObj.captureBeforeSnapshot(sapiInetFilesChangedNotificationsObj.getFolderPath());
        extapiInetFilesChangedNotificationsObj.captureBeforeSnapshot(extapiInetFilesChangedNotificationsObj.getFolderPath());
        soastsInetFilesChangedNotificationsObj.captureBeforeSnapshot(soastsInetFilesChangedNotificationsObj.getFolderPath());
    }

    public void captureAfter() {
        sapiFilesChangedNotificationsObj.captureAfterSnapshot();
        extapiFilesChangedNotificationsObj.captureAfterSnapshot();
        soastsFilesChangedNotificationsObj.captureAfterSnapshot();

        sapiInetFilesChangedNotificationsObj.captureAfterSnapshot(sapiInetFilesChangedNotificationsObj.getFolderPath());
        extapiInetFilesChangedNotificationsObj.captureAfterSnapshot(extapiInetFilesChangedNotificationsObj.getFolderPath());
        soastsInetFilesChangedNotificationsObj.captureAfterSnapshot(soastsInetFilesChangedNotificationsObj.getFolderPath());
    }

    public String buildTotalFileDifferences() {
        StringBuffer finalFileDifferences = new StringBuffer();
        finalFileDifferences.append("<table border=\"2\">");
        finalFileDifferences.append("<tr><th><b>SAPI Log file differences</b></th><tr>");
        for (File fileObj :
                sapiFilesChangedNotificationsObj.getDifferenceFiles()) {
            finalFileDifferences.append("<tr><td>File Changed is " + "<a href='" + fileObj + "'>" + fileObj.getName() + "</a></td></tr>");

        }
        finalFileDifferences.append("</table>");
        finalFileDifferences.append("<br/><br/>");

        finalFileDifferences.append("<table border=\"2\">");
        finalFileDifferences.append("<tr><th><b>SAPI Inet Pub Log file differences</b></th><tr>");
        for (File fileObj :
                sapiInetFilesChangedNotificationsObj.getDifferenceFiles()) {
            finalFileDifferences.append("<tr><td>File Changed is " + "<a href='" + fileObj + "'>" + fileObj.getName() + "</a></td></tr>");

        }
        finalFileDifferences.append("</table>");
        finalFileDifferences.append("<br/><br/>");


        finalFileDifferences.append("<table border=\"2\">");
        finalFileDifferences.append("<tr><th><b>EXTAPI Log file differences</b></th><tr>");
        for (File fileObj :
                extapiFilesChangedNotificationsObj.getDifferenceFiles()) {
            finalFileDifferences.append("<tr><td>File Changed is " + "<a href='" + fileObj + "'>" + fileObj.getName() + "</a></td></tr>");

        }
        finalFileDifferences.append("</table>");
        finalFileDifferences.append("<br/><br/>");

        finalFileDifferences.append("<table border=\"2\">");
        finalFileDifferences.append("<tr><th><b>EXTAPI Inet Pub Log file differences</b></th></tr>");

        for (File fileObj :
                extapiInetFilesChangedNotificationsObj.getDifferenceFiles()) {
            finalFileDifferences.append("<td><tr>File Changed is " + "<a href='" + fileObj + "'>" + fileObj.getName() + "</a></td></tr>");

        }
        finalFileDifferences.append("</table>");

        finalFileDifferences.append("<br/><br/>");
        finalFileDifferences.append("<table border=\"2\">");
        finalFileDifferences.append("<tr><th><b>SOASTS Log file differences</b></th></tr>");

        for (File fileObj :
                soastsFilesChangedNotificationsObj.getDifferenceFiles()) {
            finalFileDifferences.append("<td><tr>File Changed is " + "<a href='" + fileObj + "'>" + fileObj.getName() + "</a></tr></td>");

        }
        finalFileDifferences.append("</table>");

        finalFileDifferences.append("<br/><br/>");

        finalFileDifferences.append("<table border=\"2\">");
        finalFileDifferences.append("<tr><th><b>SOASTS Inet Pub Log file differences</b></th></tr>");

        for (File fileObj :
                soastsInetFilesChangedNotificationsObj.getDifferenceFiles()) {
            finalFileDifferences.append("<td><tr>File Changed is " + "<a href='" + fileObj + "'>" + fileObj.getName() + "</a></td></tr>");

        }
        finalFileDifferences.append("</table>");

        finalFileDifferences.append("<br/><br/>");

        return finalFileDifferences.toString();

    }

    public File getSapiInetFolderPath() {
        return sapiInetFolderPath;
    }

    public void setSapiInetFolderPath(File sapiInetFolderPath) {
        this.sapiInetFolderPath = sapiInetFolderPath;
    }

    public File getExtapiInetFolderPath() {
        return extapiInetFolderPath;
    }

    public void setExtapiInetFolderPath(File extapiInetFolderPath) {
        this.extapiInetFolderPath = extapiInetFolderPath;
    }

    public File getSoastsInetFolderPath() {
        return soastsInetFolderPath;
    }

    public void setSoastsInetFolderPath(File soastsInetFolderPath) {
        this.soastsInetFolderPath = soastsInetFolderPath;
    }
}
