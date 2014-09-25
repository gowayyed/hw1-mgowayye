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

/**
 * This annotator is responsible for extracting the features for each token of the {@link Sentence}.
 * It uses {@link StanfordCoreNLP} classes to 1) tokenize 2) get POS tags. It also extracts other
 * features the same as BANNER system does: Regular Expression features and other linguistic
 * features.
 * 
 * This annotator is generic to work with either training or testing. It is also independent from
 * the learning algorithm and independent from the learning library. In contrast to the BANNER
 * library in which both feature extraction and learning are done using the Mallet library, I
 * separated feature extraction from learning for a better design, so that I have the ability to
 * compare the performance when using different learning algorithms or even different libraries
 * without needing to change my feature extraction annotator.
 * 
 * @author gowayyed
 *
 */
public class FeatureExtractorAnnotator extends JCasAnnotator_ImplBase {

  /**
   * The main method that loops over all the {@link Sentence} objects in the {@link JCas} and call
   * the {@link #extractFeatures(Sentence)} method to extract the features. Features are added to
   * the {@link Token} object to be used afterwards by the learning component either for training or
   * testing.
   * 
   */
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

  /**
   * This method uses {@link StanfordCoreNLP} to tokenize, pos, and lemmatize a sentence. Then it
   * creates {@link Token} objects and adds them to the sentence. Then, it extracts features for
   * each token and adds them to the {@link Token} objects.
   * 
   * @param sentence
   */

  private void extractFeatures(Sentence sentence) {
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

        // The following code is customised from BANNER, while BANNER uses the mallet Token class
        // and adds the features directly to it. I store them first in my ts.Token class and in the
        // learning phase, I fill a mallet Token with whatever features I extracted here.

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

  /**
   * Adds the RegEx features. It uses the features used in BANNER.
   */

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

  /**
   * This function adds the feature provided as (feature) to be one in the token if the token text
   * follows the regex pattern
   * 
   * @param token
   * @param feature
   * @param regex
   */

  private void addPattern(Token token, String feature, Pattern regex) {
    // the following method is taken from MALLET tooklkit from the implementation of RegexMatches
    // pipe
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

  // the following four functions are taken from BANNER

  /**
   * replaces all the numbers in the text to become zero
   * 
   * @param text
   * @return
   */
  private String getNumberClass(String text) {
    text = text.replaceAll("[0-9]", "0");
    return text;
  }

  /**
   * modifies the text such that it replaces all capical characters with A, all small characters
   * with a, all numbers with 0, and otherwise add x.
   * 
   * @param text
   * @return
   */
  private String getWordClass(String text) {
    text = text.replaceAll("[A-Z]", "A");
    text = text.replaceAll("[a-z]", "a");
    text = text.replaceAll("[0-9]", "0");
    text = text.replaceAll("[^A-Za-z0-9]", "x");
    return text;
  }

  /**
   * replaces all the consecutive numbers in the text to become zero
   * 
   * @param text
   * @return
   */
  private String getBriefNumberClass(String text) {
    text = text.replaceAll("[0-9]+", "0");
    return text;
  }

  /**
   * modifies the text such that it replaces all consecutive capical characters with A, all small
   * characters with a, all numbers with 0, and otherwise add x.
   * 
   * @param text
   * @return
   */
  private String getBriefWordClass(String text) {
    text = text.replaceAll("[A-Z]+", "A");
    text = text.replaceAll("[a-z]+", "a");
    text = text.replaceAll("[0-9]+", "0");
    text = text.replaceAll("[^A-Za-z0-9]+", "x");
    return text;
  }
}