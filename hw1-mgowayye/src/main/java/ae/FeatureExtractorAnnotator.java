package ae;
import java.util.Iterator;

import misc.SimpleTokenizer;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ts.Sentence;
import ts.Token;

public class FeatureExtractorAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    Iterator<Annotation> fs = aJCas.getAnnotationIndex().iterator();
    while (fs.hasNext()) {
      Annotation ann = fs.next();
      if (ann.getClass() == Sentence.class) {
        // here we should tokenize and add a feature vector for each token
        extractFeatures((Sentence) ann);
      }
    }
  }

  private void extractFeatures(Sentence sentence) {
    // The following code is customised from BANNER
    try {
      SimpleTokenizer.tokenize(sentence);
    } catch (CASException e) {
      e.printStackTrace();
    }
    // TokenSequence data = new TokenSequence(tokens.length);
    // LabelSequence target = new LabelSequence((LabelAlphabet) getTargetAlphabet(), tokens.length);
    // StringBuffer source = new StringBuffer();
    int size = sentence.getTokens().size();

    for (int i = 0; i < size; i++) {
      Token token = sentence.getTokens(i);
      String text = token.getText();

      // Add features to token
      token.addFeatureValue("W=" + text.toLowerCase(), 1);

    }
    
    // int[] pos = null;
    // if (posTagger != null) {
    // pos = getPOS(tokens);
    // token.addFeatureValue("POS=" + pos[i], 1);
    // }
    // if (lemmatiser != null) {
    // String lemma;
    // if (pos == null) {
    // lemma = lemmatiser.lemmatize(text);
    // } else
    // lemma = lemmatiser.lemmatize(text, pos[i]);
    // token.setFeatureValue("LW=" + lemma, 1);
    // }
    // if (useNumericNormalization) {
    // token.setFeatureValue("NC=" + getNumberClass(text), 1);
    // token.setFeatureValue("BNC=" + getBriefNumberClass(text), 1);
    // }
    // token.setFeatureValue("WC=" + getWordClass(text), 1);
    // token.setFeatureValue("BWC=" + getBriefWordClass(text), 1);
    //
    // // Add token to data
    // data.add(token);
    // target.add(tag);
    //
    // source.append(token.getText());
    // source.append(" ");
    // }
    //
    // carrier.setData(data);
    // carrier.setTarget(target);
    // carrier.setSource(source);
    // return carrier;
  }
}