package misc;

import cc.mallet.fst.CRF;
import cr.BioCollectionReader;
import ts.Token;
import ae.TestingAnnotator;
import ae.TrainingAnnotator;

/**
 * This class contains the configurations to be easy to maintain.
 * 
 * @author gowayyed
 *
 */
public class Config {
  /**
   * The path of the file that stores the {@link CRF} model. I originally saved the model on the
   * src/main/resources/data. I am not sure where it will be on the server.
   */
//  public static String modelFile = "src/main/resources/data/model";

   public static String modelFile = "smodel";

  /**
   * The path of the original file that we want to process, used by {@link BioCollectionReader}
   */
//  public static String inputPath = "src/main/resources/data/sample.in";

   public static String inputPath = "hw1.in";
  /**
   * The path of the output file to save the predictions of the model. Used by
   * {@link TestingAnnotator}
   */
  public static String outputFilename = "hw1.out";

  // public static String outputFilename = "src/main/resources/data/sample-predicted.out";

  /**
   * The path of the tagging file to be used in the evaluation by {@link TestingAnnotator}
   */
  public static String referenceFilename = "src/main/resources/data/sample.out";

  /**
   * The path of the file to load the training labels to be used by {@link TrainingAnnotator}
   */
  public static String tagFilename = "src/main/resources/data/sample.out";

  /**
   * The possible labels for each {@link Token}
   */
  public static String[] labelsAlphabet = { "N", "B", "I", "O" }; // TODO move this to a
                                                                  // configuration class
}
