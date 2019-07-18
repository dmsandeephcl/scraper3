
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class LuceneReadIndexExample
{
    private static final String INDEX_DIR = LuceneWriteIndexExample.INDEX_DIR;

    public static void main(String[] args) throws Exception
    {
        IndexSearcher searcher = createSearcher();

        List<String> listOfLinesForException = new ArrayList<String>();
        listOfLinesForException.add("Caused by: java.lang.OutOfMemoryError2: PermGen space");

        //Search by ID
        TopDocs foundDocs = searchBySolution("Need to check mothers madien name", searcher);

        System.out.println("Total Results :: " + foundDocs.totalHits);

        for (ScoreDoc sd : foundDocs.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            List<IndexableField> fields = d.getFields();
            for(int i=0; i< fields.size();i++){
                StoredField storedField = (StoredField)fields.get(i);
                if(i == 0) {
                    List<String> listOfLinesOfException = deSerialize(storedField.binaryValue());
                    System.out.println("Stored[" + i + "]--->" + listOfLinesOfException.size());
                    for(String str:listOfLinesOfException){
                        System.out.println("Str --->"+str);
                    }
                }
                System.out.println("storedField --->"+storedField.stringValue());
            }

            String[] listOfLinesForExceptionArray = d.getValues("listOfLinesForException");
        }

        //Search by firstName
        TopDocs foundDocs2 = searchByFirstName("Brian", searcher);

        System.out.println("Total Results :: " + foundDocs2.totalHits);

        for (ScoreDoc sd : foundDocs2.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format(d.get("id")));
        }
    }

    private static TopDocs searchByFirstName(String firstName, IndexSearcher searcher) throws Exception
    {
        QueryParser qp = new QueryParser("firstName", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(firstName);
        TopDocs hits = searcher.search(firstNameQuery, 10);
        return hits;
    }

    private static TopDocs searchById(List listOfLinesForException, IndexSearcher searcher) throws Exception
    {
        QueryParser qp = new QueryParser("listOfLinesForException", new StandardAnalyzer());
        Query idQuery = qp.parse(listOfLinesForException.toString());
        TopDocs hits = searcher.search(idQuery, 10);
        return hits;
    }

    private static TopDocs searchBySolution(String solution, IndexSearcher searcher) throws Exception
    {
        QueryParser qp = new QueryParser("solution", new StandardAnalyzer());
        Query idQuery = qp.parse(solution);
        TopDocs hits = searcher.search(idQuery, 10);
        return hits;
    }

    private static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }

    public static List<String> deSerialize(BytesRef bytesRef) throws Exception {
        ByteArrayInputStream bos = new ByteArrayInputStream(bytesRef.bytes);
        ObjectInputStream os = new ObjectInputStream(bos);
        Object obj2 = os.readObject();
        os.close();
        return (List<String>) obj2;
    }
}