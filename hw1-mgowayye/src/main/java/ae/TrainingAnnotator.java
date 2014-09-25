package ae;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import misc.Base;
import misc.Config;
import misc.ExtractionPipe;
import misc.Util;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ts.Sentence;
import cc.mallet.fst.CRF;
import cc.mallet.fst.CRFTrainerByLabelLikelihood;
import cc.mallet.fst.PerClassAccuracyEvaluator;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureVectorSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
/**
 * This analysis engine is responsible for training a CRF model using Mallet library and stores the model to a file with path {@link #tagFilename}.
 *  
 * @author gowayyed
 *
 */
public class TrainingAnnotator extends JCasAnnotator_ImplBase {

  
  /**
   * The main process method that tarins, and saves the model.
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    Iterator<Annotation> fs = aJCas.getAnnotationIndex().iterator();
    ArrayList<Sentence> sentences = new ArrayList<Sentence>();
    // filtering the annotations to take only the Sentences
    while (fs.hasNext()) {
      Annotation ann = fs.next();
      if (ann.getClass() == Sentence.class) {
        sentences.add((Sentence) ann);
      }
    }
    loadLabels(sentences); // load labels from the 
    CRF crf = train(sentences, new ExtractionPipe(Config.labelsAlphabet, true)); // train a CRF
    Util.saveModel(crf); // saves the CRF by serializing it to a file
  }

  /**
   * loads the tags using the BANNER method of (Base.getTags) and add them to the {@link Sentence} objects.
   * @param sentences
   */
  private void loadLabels(ArrayList<Sentence> sentences) {
    BufferedReader tagFile;
    try {
      tagFile = new BufferedReader(new FileReader(Config.tagFilename));
      HashMap<String, LinkedList<Base.Tag>> tags = Base.getTags(tagFile);
      tagFile.close();
      for (Sentence sentence : sentences) {
        Base.updateSentenceWithTags(sentence, tags);
        sentence.UpdateTokensWithTags();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * The main training method. Following the example of training CRF using Mallet:
   * // https://github.com/jmcejuela/mallet/blob/master/src/cc/mallet/examples/TrainCRF.java
   * @param sentences
   * @param fpipe
   * @return
   */
  
  private CRF train(List<Sentence> sentences, ExtractionPipe fpipe) {

    ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    pipes.add(fpipe);
    pipes.add(new TokenSequence2FeatureVectorSequence(true, true));

    Pipe pipe = new SerialPipes(pipes);

    InstanceList trainingInstances = new InstanceList(pipe);

    for (Sentence sentence : sentences) {
      trainingInstances.addThruPipe(new Instance(sentence, Config.labelsAlphabet, sentence.getId(),
              sentence.getId()));
    }

    CRF crf = new CRF(pipe, null);
    crf.addStatesForThreeQuarterLabelsConnectedAsIn(trainingInstances);
    crf.addStartState();

    CRFTrainerByLabelLikelihood trainer = 
      new CRFTrainerByLabelLikelihood(crf);
    trainer.setGaussianPriorVariance(10.0);

    trainer.addEvaluator(new PerClassAccuracyEvaluator(trainingInstances, "training"));
    trainer.train(trainingInstances);
    
    return crf;
  }
}