package ae;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import misc.Base;
import misc.Base.Tag;
import misc.ExtractionPipe;
import misc.Util;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ts.Sentence;
import cc.mallet.fst.CRF;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureVectorSequence;
import cc.mallet.types.FeatureVectorSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.Sequence;

public class TestingAnnotator extends JCasAnnotator_ImplBase {

  private String outputFilename = "src/main/resources/data/sample-predicted.out";

  private String referenceFilename = "src/main/resources/data/sample.out";

  private FileWriter outStream;

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    try {
      outStream = new FileWriter(new File(outputFilename));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Iterator<Annotation> fs = aJCas.getAnnotationIndex().iterator();
    ArrayList<Sentence> sentences = new ArrayList<Sentence>();
    while (fs.hasNext()) {
      Annotation ann = fs.next();
      if (ann.getClass() == Sentence.class) {
        sentences.add((Sentence) ann);
      }
    }
    String[] possibleLabels = { "N", "B", "I", "O" };
    test(Util.loadModel(), sentences, new ExtractionPipe(possibleLabels, false));
    evaluate(sentences);
  }

  private void evaluate(ArrayList<Sentence> sentences) {
    BufferedReader br, pbr;
    try {
      br = new BufferedReader(new FileReader(new File(referenceFilename)));
      pbr = new BufferedReader(new FileReader(new File(outputFilename)));
      HashMap<String, LinkedList<Base.Tag>> tags = Base.getTags(br);
      HashMap<String, LinkedList<Base.Tag>> predictedTags = Base.getTags(pbr);
      br.close();
      pbr.close();
      boolean found = false;
      double truePositive = 0;
      for (String key : tags.keySet()) {
        for (Base.Tag tag : tags.get(key)) {
          found = false;
          if (predictedTags.get(key) != null)
            for (Base.Tag ptag : predictedTags.get(key)) {
              if (tag.contains(ptag))
                found = true; // correct
            }
          if (found)
            truePositive++;
        }
      }
      double precision = truePositive / getMentionsLength(predictedTags);
      double recall = truePositive / getMentionsLength(tags);
      double f1Score = 2 * precision * recall / (precision + recall);
      System.out.println("Precision = " + precision + "recall = " + recall + " F1 score = "
              + f1Score);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private double getMentionsLength(HashMap<String, LinkedList<Tag>> tags) {
    double res = 0;
    for (String key : tags.keySet()) {
      for (Base.Tag tag : tags.get(key)) {
        res++;
      }
    }
    return res;
  }

  private void test(CRF crf, List<Sentence> sentences, ExtractionPipe fpipe) {

    ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    pipes.add(fpipe);
    pipes.add(new TokenSequence2FeatureVectorSequence(true, true));

    Pipe pipe = new SerialPipes(pipes);

    String[] labelsAlphabet = { "N", "B", "I", "O" }; // TODO move this to a configuration class
    for (Sentence sentence : sentences) {
      Instance instance = pipe.instanceFrom(new Instance(sentence, labelsAlphabet,
              sentence.getId(), sentence.getId()));
      Sequence predicted = crf.getMaxLatticeFactory()
              .newMaxLattice(crf, (FeatureVectorSequence) instance.getData()).bestOutputSequence();
      writeMentions(sentence, predicted);
    }
    try {
      outStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeMentions(Sentence sentence, Sequence predicted) {
    for (int i = 0; i < predicted.size(); i++) {
      if ("B".equals(predicted.get(i))) {
        int en = i + 1;
        while (en < predicted.size() && "I".equals(predicted.get(en)))
          en++;
        writePrediction(sentence, i, en);
        i = en - 1;
      }
    }
  }

  private void writePrediction(Sentence sentence, int st, int en) {
    try {
      outStream.write((sentence.getId() + "|").toCharArray());
      outStream.write((getPreceedingChars(sentence.getText(), sentence.getTokens(st)
              .getStartIndex()) + " ").toCharArray());
      outStream.write((getPreceedingChars(sentence.getText(), sentence.getTokens(en - 1)
              .getEndIndex() - 1) + "|").toCharArray());
      outStream.write((sentence.getText().substring(sentence.getTokens(st).getStartIndex(),
              sentence.getTokens(en - 1).getEndIndex()) + "\n").toCharArray());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private int getPreceedingChars(String text, int charIndex) {
    int c = 0;
    for (int i = 0; i < charIndex; i++) {
      if (!Character.isWhitespace(text.charAt(i)))
        c++;
    }
    return c;
  }
}