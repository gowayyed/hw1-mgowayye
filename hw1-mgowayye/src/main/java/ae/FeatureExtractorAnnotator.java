package ae;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ts.Sentence;
import ts.Token;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.util.CoreMap;

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
    // try {
    // SimpleTokenizer.tokenize(sentence);
    // } catch (CASException e) {
    // e.printStackTrace();
    // }
    // TokenSequence data = new TokenSequence(tokens.length);
    // LabelSequence target = new LabelSequence((LabelAlphabet) getTargetAlphabet(), tokens.length);
    // StringBuffer source = new StringBuffer();
    // int size = sentence.getTokens().size();

    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

    // read some text in the text variable
    String text = sentence.getText();

    // create an empty Annotation just with the given text
    edu.stanford.nlp.pipeline.Annotation document = new edu.stanford.nlp.pipeline.Annotation(text);

    // run all Annotators on this text
    pipeline.annotate(document);

    // these are all the sentences in this document
    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom
    // types
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);

    for (CoreMap sen : sentences) {
      // traversing the words in the current sentence
      // a CoreLabel is a CoreMap with additional token-specific methods
      int i = 0;
      int star = 0;
      for (CoreLabel t : sen.get(TokensAnnotation.class)) {
        // this is the text of the token
        String word = t.get(TextAnnotation.class);

        // this is the POS tag of the token
        String pos = t.get(PartOfSpeechAnnotation.class);
        
        star = sentence.getText().indexOf(PTBTokenizer.ptbToken2Text(word));
        if (star > -1) {
          try {
            sentence.addToken(new Token(sentence, star, star + word.length()));
          } catch (CASException e) {
            e.printStackTrace();
          }

          Token token = sentence.getTokens(i++);
          // Add features to token
          token.addFeatureValue("W=" + token.getText().toLowerCase(), 1);
          token.addFeatureValue("TA=" + word, 1);
          token.addFeatureValue("POS=" + pos, 1);

        } else
          System.out.println("msa2 el 5eer");
        // this is the parse tree of the current sentence
        // Tree tree = sen.get(TreeAnnotation.class);

        // this is the Stanford dependency graph of the current sentence
        // SemanticGraph dependencies = sen.get(CollapsedCCProcessedDependenciesAnnotation.class);
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
}