

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class LuceneWriteIndexExample
{
    public static final String INDEX_DIR = "G:\\project\\xml\\lucene";

    public static final String XML_FILE_PATH = "G:\\project\\xml\\solutions.txt";

    private static AllExceptionTypes allExceptionTypes = null;

    public static void main(String[] args) throws Exception
    {
        loadAllExceptionTypes();
        IndexWriter writer = createWriter();
        List<Document> documents = new ArrayList<>();
        for(ExceptionType exceptionType : getAllExceptionTypes().getExceptionType()) {



            Document document1 = createDocument(exceptionType.getListOfLinesForException(), exceptionType.getSolution());
            documents.add(document1);
        }

        //Let's clean everything first
        writer.deleteAll();

        writer.addDocuments(documents);
        writer.commit();
        writer.close();
    }

    private static void loadAllExceptionTypes() {
        try {
            StringBuffer errorErrorBufferString = new StringBuffer();

            BufferedReader fr = new BufferedReader(new FileReader(getXmlFilePath()));
            String lineStr = null;
            while ((lineStr = fr.readLine()) != null) {
                errorErrorBufferString.append(lineStr);
            }
            fr.close();

            if (errorErrorBufferString.toString().trim().length() == 0) {
                System.err.println("Error config file is empty");
                return;
            }

            JAXBContext jaxbContext;


            jaxbContext = JAXBContext.newInstance(AllExceptionTypes.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            setAllExceptionTypes((AllExceptionTypes) jaxbUnmarshaller.unmarshal(new StringReader(errorErrorBufferString.toString())));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private static Document createDocument(List listOfLinesForException, String solution)
            throws Exception
    {
        Document document = new Document();
        document.add(new StoredField("listOfLinesForException", serialize(listOfLinesForException)));
        document.add(new TextField("solution", solution , Field.Store.YES));
        return document;
    }

    private static IndexWriter createWriter() throws IOException
    {
        FSDirectory dir = FSDirectory.open(Paths.get(getIndexDir()));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(200000);
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(obj);
        os.close();
        return bos.toByteArray();
    }

    public static String getIndexDir() {
        return INDEX_DIR;
    }

    public static String getXmlFilePath() {
        return XML_FILE_PATH;
    }

    public static AllExceptionTypes getAllExceptionTypes() {
        return allExceptionTypes;
    }

    public static void setAllExceptionTypes(AllExceptionTypes allExceptionTypes) {
        LuceneWriteIndexExample.allExceptionTypes = allExceptionTypes;
    }
}