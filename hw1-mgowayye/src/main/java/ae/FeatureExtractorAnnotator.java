package ae;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

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
      
      for (CoreLabel t : sen.get(TokensAnnotation.class)) {
        // this is the text of the token
        String word = t.get(TextAnnotation.class);

        // this is the POS tag of the token
        String pos = t.get(PartOfSpeechAnnotation.class);

        try {
          sentence.addToken(new Token(sentence, t.beginPosition(), t.endPosition()));
        } catch (CASException e) {
          e.printStackTrace();
        }

        Token token = sentence.getTokens(i++);
        // Add features to token
        token.addFeatureValue("W=" + token.getText().toLowerCase(), 1);
        token.addFeatureValue("TA=" + word, 1);
        token.addFeatureValue("POS=" + pos, 1);
        token.addFeatureValue("NC=" + getNumberClass(text), 1);
        token.addFeatureValue("BNC=" + getBriefNumberClass(text), 1);
        token.addFeatureValue("WC=" + getWordClass(text), 1);
        token.addFeatureValue("BWC=" + getBriefWordClass(text), 1);

        addRegExFeatures(token);

      }

    }
  }

  // taken from BANNER pipes
  private void addRegExFeatures(Token token) {
    addPattern(token, "ALPHA", Pattern.compile("[A-Za-z]+"));
    addPattern(token, "INITCAPS", Pattern.compile("[A-Z].*"));
    addPattern(token, "UPPER-LOWER", Pattern.compile("[A-Z][a-z].*"));
    addPattern(token, "LOWER-UPPER", Pattern.compile("[a-z]+[A-Z]+.*"));
    addPattern(token, "ALLCAPS", Pattern.compile("[A-Z]+"));
    addPattern(token, "MIXEDCAPS", Pattern.compile("[A-Z][a-z]+[A-Z][A-Za-z]*"));
    addPattern(token, "SINGLECHAR", Pattern.compile("[A-Za-z]"));
    addPattern(token, "SINGLEDIGIT", Pattern.compile("[0-9]"));
    addPattern(token, "DOUBLEDIGIT", Pattern.compile("[0-9][0-9]"));
    addPattern(token, "NUMBER", Pattern.compile("[0-9,]+"));
    addPattern(token, "HASDIGIT", Pattern.compile(".*[0-9].*"));
    addPattern(token, "ALPHANUMERIC", Pattern.compile(".*[0-9].*[A-Za-z].*"));
    addPattern(token, "ALPHANUMERIC", Pattern.compile(".*[A-Za-z].*[0-9].*"));
    addPattern(token, "LETTERS_NUMBERS", Pattern.compile("[0-9]+[A-Za-z]+"));
    addPattern(token, "NUMBERS_LETTERS", Pattern.compile("[A-Za-z]+[0-9]+"));

    addPattern(token, "HAS_DASH", Pattern.compile(".*-.*"));
    addPattern(token, "HAS_QUOTE", Pattern.compile(".*'.*"));
    addPattern(token, "HAS_SLASH", Pattern.compile(".*/.*"));

    // Start second set of new features (to handle improvements in
    // BaseTokenizer)
    addPattern(token, "REALNUMBER", Pattern.compile("(-|\\+)?[0-9,]+(\\.[0-9]*)?%?"));
    addPattern(token, "REALNUMBER", Pattern.compile("(-|\\+)?[0-9,]*(\\.[0-9]+)?%?"));
    addPattern(token, "START_MINUS", Pattern.compile("-.*"));
    addPattern(token, "START_PLUS", Pattern.compile("\\+.*"));
    addPattern(token, "END_PERCENT", Pattern.compile(".*%"));
  }

  // the following method is taken from MALLET tooklkit from the implementation of RegexMatches pipe
  private void addPattern(Token token, String feature, Pattern regex) {
    String s = token.getText();
    String conS = s;
    if (conS.startsWith("("))
      conS = conS.substring(1);
    if (conS.endsWith(")") || conS.endsWith("."))
      conS = conS.substring(0, conS.length() - 1);
    if (regex.matcher(s).matches())
      token.addFeatureValue(feature, 1.0);
    if (conS.compareTo(s) != 0) {
      if (regex.matcher(conS).matches())
        token.addFeatureValue(feature, 1.0);
    }
  }

  // the following functions are taken from BANNER
  private String getNumberClass(String text) {
    text = text.replaceAll("[0-9]", "0");
    return text;
  }

  private String getWordClass(String text) {
    text = text.replaceAll("[A-Z]", "A");
    text = text.replaceAll("[a-z]", "a");
    text = text.replaceAll("[0-9]", "0");
    text = text.replaceAll("[^A-Za-z0-9]", "x");
    return text;
  }

  private String getBriefNumberClass(String text) {
    text = text.replaceAll("[0-9]+", "0");
    return text;
  }

  private static String getBriefWordClass(String text) {
    text = text.replaceAll("[A-Z]+", "A");
    text = text.replaceAll("[a-z]+", "a");
    text = text.replaceAll("[0-9]+", "0");
    text = text.replaceAll("[^A-Za-z0-9]+", "x");
    return text;
  }
}