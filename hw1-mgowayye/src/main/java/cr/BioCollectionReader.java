package cr;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import misc.Config;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

/**
 * This collection reader loads a whole text document and stores its contents in the JCas document
 * text.
 * 
 * @author gowayyed
 *
 */
public class BioCollectionReader extends CollectionReader_ImplBase {

  /**
   * represents whether the document is read or not. If it is not read, the next call to collection
   * reader will return false;
   */
  private boolean read;

  /**
   * Initialises the collection reader.
   */
  public void initialize() {
    read = false;
  }

  /**
   * When called the first time, this function loads the document whose path is specified in
   * {@link #inputPath}. When called afterwards, it does nothing.
   */
  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    if (!read) {
      JCas jcas;
      try {
        jcas = aCAS.getJCas();
      } catch (CASException e) {
        throw new CollectionException(e);
      }
      // open input stream to file
      File file = new File(Config.inputPath);
      BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
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
  }

  /**
   * Because we apply this to only one file, there will be next only if the files is not read.
   */
  @Override
  public boolean hasNext() throws IOException, CollectionException {
    // because I am just handling one file
    return !read;
  }

  /**
   * The progress will be zero if the file is not read and one if it is read.
   */
  @Override
  public Progress[] getProgress() {
    return new Progress[] { new ProgressImpl(read ? 1 : 0, 1, Progress.ENTITIES) };
  }

  /**
   * getNext() does not leave any open streams; so the method is empty.
   */
  @Override
  public void close() throws IOException {

  }

}
