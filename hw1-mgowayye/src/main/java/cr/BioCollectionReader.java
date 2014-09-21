package cr;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

public class BioCollectionReader extends CollectionReader_ImplBase {

  //TODO move this to a configuration file
  private String path = "src/main/resources/data/sample.in";
  private boolean read;
  
  public void initialize(){
    read = false;
  }
  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    // open input stream to file
    File file = new File(path);
    BufferedInputStream fis = 
            new BufferedInputStream(new FileInputStream(file));
    try {
      byte[] contents = new byte[(int) file.length()];
      fis.read(contents);
      String text;
      text = new String(contents);
    
      // put document in CAS
      jcas.setDocumentText(text);
      read = true;
    } finally {
      if (fis != null)
        fis.close();
    }

  }
  @Override
  public boolean hasNext() throws IOException, CollectionException {
    // because I am just handling one file
    return !read;
  }

  @Override
  public Progress[] getProgress() {
    return new Progress[]{
            new ProgressImpl(read ? 1 : 0, 1,Progress.ENTITIES)};
  }

  @Override
  public void close() throws IOException {


  }

}
