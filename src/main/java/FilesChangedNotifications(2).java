import java.io.File;
import java.util.*;

public class FilesChangedNotifications {

    private String vmName;

    private HashMap<String,Long> filesAtCaptureSnapshot = new HashMap();

    private HashMap<String,Long> filesAtNewCaptureSnapshot = new HashMap();

    private List<File> listOfFileDifferences = new ArrayList<File>();

    private File folderPath = null;

    public void captureBeforeSnapshot(){
        filesAtCaptureSnapshot.clear();
        if(getFolderPath().isDirectory()){
            File[] files = getFolderPath().listFiles();
            for (File fileObj: files ) {
                filesAtCaptureSnapshot.put(fileObj.getAbsolutePath(),fileObj.lastModified());
            }
        }
    }

    public void captureBeforeSnapshot(File fileObj){
        filesAtCaptureSnapshot.clear();
        if(fileObj.isDirectory()){
            File[] files = getFolderPath().listFiles();
            for (File tempFileObj: files ) {
                if(tempFileObj.isFile()) {
                    filesAtCaptureSnapshot.put(tempFileObj.getAbsolutePath(), tempFileObj.lastModified());
                }else if(tempFileObj.isDirectory()){
                    captureBeforeSnapshot(tempFileObj);
                }
            }
        }else{
            filesAtCaptureSnapshot.put(fileObj.getAbsolutePath(), fileObj.lastModified());
        }
    }

    public void captureAfterSnapshot(){
        filesAtNewCaptureSnapshot.clear();
        if(getFolderPath().isDirectory()){
            File[] files = getFolderPath().listFiles();
            for (File fileObj: files ) {
                filesAtNewCaptureSnapshot.put(fileObj.getAbsolutePath(),fileObj.lastModified());
            }
        }
    }

    public void captureAfterSnapshot(File fileObj){
        filesAtNewCaptureSnapshot.clear();
        if(fileObj.isDirectory()){
            File[] files = getFolderPath().listFiles();
            for (File tempFileObj: files ) {
                if(tempFileObj.isFile()) {
                    filesAtNewCaptureSnapshot.put(tempFileObj.getAbsolutePath(), tempFileObj.lastModified());
                }else if(tempFileObj.isDirectory()){
                    captureAfterSnapshot(tempFileObj);
                }
            }
        }else{
            filesAtNewCaptureSnapshot.put(fileObj.getAbsolutePath(), fileObj.lastModified());
        }
    }

    public List<File>  getDifferenceFiles(){
        Set<Map.Entry<String,Long>> set1= filesAtCaptureSnapshot.entrySet();
        Iterator<Map.Entry<String,Long>> iterator = set1.iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Long> mapObj = iterator.next();
            String key = mapObj.getKey();
            Long value = mapObj.getValue();
            Long afterSnapshot = filesAtNewCaptureSnapshot.get(key);
            if(!(afterSnapshot != null && value.equals(afterSnapshot))){
                getListOfFileDifferences().add(new File(key));
            }else{
                filesAtNewCaptureSnapshot.remove(key);
            }
        }

        Set<Map.Entry<String,Long>> set2= filesAtNewCaptureSnapshot.entrySet();
        Iterator<Map.Entry<String,Long>> iterator2 = set2.iterator();
        while(iterator2.hasNext()){
            Map.Entry<String,Long> mapObj = iterator2.next();
            String key = mapObj.getKey();
            if(!filesAtCaptureSnapshot.containsKey(key)) {
                getListOfFileDifferences().add(new File(key));
            }
        }

        return getListOfFileDifferences();

    }


    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public List<File> getListOfFileDifferences() {
        return listOfFileDifferences;
    }

    public void setListOfFileDifferences(List<File> listOfFileDifferences) {
        this.listOfFileDifferences = listOfFileDifferences;
    }

    public File getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(File folderPath) {
        this.folderPath = folderPath;
    }
}
