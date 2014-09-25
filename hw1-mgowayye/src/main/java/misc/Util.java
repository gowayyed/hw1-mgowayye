package misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import cc.mallet.fst.CRF;
/**
 * a simple Util class
 * 
 * @author gowayyed
 *
 */
public class Util {

  /**
   * loads the {@link CRF} model from the file to be used in testing.
   * @return
   */
  public static CRF loadModel() {
    FileInputStream fileIn;
    CRF crf = null;
    try {
      fileIn = new FileInputStream(Config.modelFile);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      crf = (CRF) in.readObject();
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return crf;
  }
  /**
   * serializes a {@link CRF} model obtained after training to a file.
   * @param crf
   */
  public static void saveModel(CRF crf) {
    crf.write(new File(Config.modelFile));
  }
}
