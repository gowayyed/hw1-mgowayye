package ae;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import misc.Base;
import misc.ExtractionPipe;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ts.Sentence;
import cc.mallet.fst.CRF;
import cc.mallet.fst.CRFOptimizableByLabelLikelihood;
import cc.mallet.fst.CRFTrainerByLabelLikelihood;
import cc.mallet.fst.CRFTrainerByValueGradients;
import cc.mallet.fst.CRFWriter;
import cc.mallet.fst.PerClassAccuracyEvaluator;
import cc.mallet.fst.TransducerTrainer;
import cc.mallet.optimize.Optimizable;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureVectorSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

public class TrainingAnnotator extends JCasAnnotator_ImplBase {

  private String tagFilename = "src/main/resources/data/sample.out";

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    Iterator<Annotation> fs = aJCas.getAnnotationIndex().iterator();
    ArrayList<Sentence> sentences = new ArrayList<Sentence>();
    while (fs.hasNext()) {
      Annotation ann = fs.next();
      if (ann.getClass() == Sentence.class) {
        sentences.add((Sentence) ann);
      }
    }
    loadLabels(sentences);
    String[] possibleLabels = { "B", "I", "O" };
    train(sentences, new ExtractionPipe(possibleLabels));
  }

  private void loadLabels(ArrayList<Sentence> sentences) {
    BufferedReader tagFile;
    try {
      tagFile = new BufferedReader(new FileReader(tagFilename));
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

  // use the example of training CRF using Mallet:
  // https://github.com/jmcejuela/mallet/blob/master/src/cc/mallet/examples/TrainCRF.java
  private CRF train2(List<Sentence> sentences, ExtractionPipe fpipe) {

    ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    pipes.add(fpipe);
    pipes.add(new TokenSequence2FeatureVectorSequence(true, true));

    Pipe pipe = new SerialPipes(pipes);

    InstanceList trainingInstances = new InstanceList(pipe);

    String[] labelsAlphabet = { "B", "I", "O" }; // TODO move this to a configuration class
    for (Sentence sentence : sentences) {
      trainingInstances.addThruPipe(new Instance(sentence, labelsAlphabet, sentence.getId(),
              sentence.getId()));
    }
    CRF crf = new CRF(pipe, null);
    // TODO try different orders and report the difference in f measure
    crf.addStatesForBiLabelsConnectedAsIn(trainingInstances);
    // crf.addStatesForThreeQuarterLabelsConnectedAsIn(trainingInstances);

    crf.addStartState();

    CRFTrainerByLabelLikelihood trainer = new CRFTrainerByLabelLikelihood(crf);
    trainer.setGaussianPriorVariance(10.0);

    trainer.addEvaluator(new PerClassAccuracyEvaluator(trainingInstances, "training"));
    trainer.train(trainingInstances);

    return trainer.getCRF();
  }

  private CRF train(List<Sentence> sentences, ExtractionPipe fpipe) {

    ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    pipes.add(fpipe);
    pipes.add(new TokenSequence2FeatureVectorSequence(true, true));

    Pipe pipe = new SerialPipes(pipes);

    InstanceList trainingInstances = new InstanceList(pipe);

    String[] labelsAlphabet = { "B", "I", "O" }; // TODO move this to a configuration class
    for (Sentence sentence : sentences) {
      trainingInstances.addThruPipe(new Instance(sentence, labelsAlphabet, sentence.getId(),
              sentence.getId()));
    }

    CRF crf = new CRF(pipe, null);
    //crf.addStatesForLabelsConnectedAsIn(trainingInstances);
    crf.addStatesForThreeQuarterLabelsConnectedAsIn(trainingInstances);
    crf.addStartState();

    CRFTrainerByLabelLikelihood trainer = 
      new CRFTrainerByLabelLikelihood(crf);
    trainer.setGaussianPriorVariance(10.0);

    //CRFTrainerByStochasticGradient trainer = 
    //new CRFTrainerByStochasticGradient(crf, 1.0);

    //CRFTrainerByL1LabelLikelihood trainer = 
    //  new CRFTrainerByL1LabelLikelihood(crf, 0.75);

    trainer.addEvaluator(new PerClassAccuracyEvaluator(trainingInstances, "training"));
//    trainer.addEvaluator(new PerClassAccuracyEvaluator(testingInstances, "testing"));
//    trainer.addEvaluator(new TokenAccuracyEvaluator(testingInstances, "testing"));
    trainer.train(trainingInstances);
    
    return crf;
  }
}