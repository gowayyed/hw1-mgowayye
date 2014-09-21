package misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import cc.mallet.fst.CRF;

public class Util {
  public static String modelFile = "src/main/resources/data/model"; // TODO move this to a configuration file

  public static CRF loadModel() {
    FileInputStream fileIn;
    CRF crf = null;
    try {
      fileIn = new FileInputStream(modelFile);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      crf = (CRF) in.readObject();
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return crf;
  }

  public static void saveModel(CRF crf) {
    crf.write(new File(modelFile));
  }
}
