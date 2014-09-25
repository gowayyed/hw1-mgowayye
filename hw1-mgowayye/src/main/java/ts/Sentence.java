package ts;

/* First created by JCasGen Thu Sep 18 16:28:15 EDT 2014 */

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Updated by JCasGen Sat Sep 20 00:45:09 EDT 2014 XML source:
 * /home/gowayyed/git/hw1-mgowayye/hw1-mgowayye/src/main/resources/aeTestingDescriptor.xml
 * 
 * @generated
 */
public class Sentence extends Annotation {
  /**
   * @generated
   * @ordered
   */
  @SuppressWarnings("hiding")
  public final static int typeIndexID = JCasRegistry.register(Sentence.class);

  /**
   * @generated
   * @ordered
   */
  @SuppressWarnings("hiding")
  public final static int type = typeIndexID;

  /**
   * @generated
   * @return index of the type
   */
  @Override
  public int getTypeIndexID() {
    return typeIndexID;
  }

  /**
   * Never called. Disable default constructor
   * 
   * @generated
   */
  protected Sentence() {/* intentionally empty block */
  }

  /**
   * Internal - constructor used by generator
   * 
   * @generated
   * @param addr
   *          low level Feature Structure reference
   * @param type
   *          the type of this Feature Structure
   */
  public Sentence(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }

  /**
   * @generated
   * @param jcas
   *          JCas to which this Feature Structure belongs
   */
  public Sentence(JCas jcas) {
    super(jcas);
    readObject();
  }

  /**
   * @generated
   * @param jcas
   *          JCas to which this Feature Structure belongs
   * @param begin
   *          offset to the begin spot in the SofA
   * @param end
   *          offset to the end spot in the SofA
   */
  public Sentence(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }

  public Sentence(JCas aJCas, int begin, int end, String id, String sentText) {
    super(aJCas);
    setBegin(begin);
    setEnd(end);
    setId(id);
    setText(sentText);

    readObject();
  }

  /**
   * <!-- begin-user-doc --> Write your own initialization here <!-- end-user-doc -->
   *
   * @generated modifiable
   */
  private void readObject() {/* default - does nothing empty block */
    try {
      setTokens(new FSArray(this.getCAS().getJCas(), 0));
      setMentions(new FSArray(this.getCAS().getJCas(), 0));
    } catch (CASException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // *--------------*
  // * Feature: id

  /**
   * getter for id - gets The identifier of the sentence.
   * 
   * @generated
   * @return value of the feature
   */
  public String getId() {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ts.Sentence");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Sentence_Type) jcasType).casFeatCode_id);
  }

  /**
   * setter for id - sets The identifier of the sentence.
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setId(String v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ts.Sentence");
    jcasType.ll_cas.ll_setStringValue(addr, ((Sentence_Type) jcasType).casFeatCode_id, v);
  }

  // *--------------*
  // * Feature: text

  /**
   * getter for text - gets The text content of the sentence.
   * 
   * @generated
   * @return value of the feature
   */
  public String getText() {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "ts.Sentence");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Sentence_Type) jcasType).casFeatCode_text);
  }

  /**
   * setter for text - sets The text content of the sentence.
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setText(String v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "ts.Sentence");
    jcasType.ll_cas.ll_setStringValue(addr, ((Sentence_Type) jcasType).casFeatCode_text, v);
  }

  // *--------------*
  // * Feature: tokens

  /**
   * getter for tokens - gets
   * 
   * @generated
   * @return value of the feature
   */
  public FSArray getTokens() {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "ts.Sentence");
    return (FSArray) (jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr,
            ((Sentence_Type) jcasType).casFeatCode_tokens)));
  }

  /**
   * setter for tokens - sets
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setTokens(FSArray v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "ts.Sentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_tokens,
            jcasType.ll_cas.ll_getFSRef(v));
  }

  /**
   * indexed getter for tokens - gets an indexed value -
   * 
   * @generated
   * @param i
   *          index in the array to get
   * @return value of the element at index i
   */
  public Token getTokens(int i) {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "ts.Sentence");
    jcasType.jcas.checkArrayBounds(
            jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_tokens), i);
    return (Token) (jcasType.ll_cas
            .ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(
                    addr, ((Sentence_Type) jcasType).casFeatCode_tokens), i)));
  }

  /**
   * indexed setter for tokens - sets an indexed value -
   * 
   * @generated
   * @param i
   *          index in the array to set
   * @param v
   *          value to set into the array
   */
  public void setTokens(int i, Token v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "ts.Sentence");
    jcasType.jcas.checkArrayBounds(
            jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_tokens), i);
    jcasType.ll_cas.ll_setRefArrayValue(
            jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_tokens), i,
            jcasType.ll_cas.ll_getFSRef(v));
  }

  // *--------------*
  // * Feature: mentions

  /**
   * getter for mentions - gets
   * 
   * @generated
   * @return value of the feature
   */
  public FSArray getMentions() {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "ts.Sentence");
    return (FSArray) (jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr,
            ((Sentence_Type) jcasType).casFeatCode_mentions)));
  }

  /**
   * setter for mentions - sets
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setMentions(FSArray v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "ts.Sentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_mentions,
            jcasType.ll_cas.ll_getFSRef(v));
  }

  /**
   * indexed getter for mentions - gets an indexed value -
   * 
   * @generated
   * @param i
   *          index in the array to get
   * @return value of the element at index i
   */
  public Mention getMentions(int i) {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "ts.Sentence");
    jcasType.jcas.checkArrayBounds(
            jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_mentions),
            i);
    return (Mention) (jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(
            jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_mentions),
            i)));
  }

  /**
   * indexed setter for mentions - sets an indexed value -
   * 
   * @generated
   * @param i
   *          index in the array to set
   * @param v
   *          value to set into the array
   */
  public void setMentions(int i, Mention v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "ts.Sentence");
    jcasType.jcas.checkArrayBounds(
            jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_mentions),
            i);
    jcasType.ll_cas.ll_setRefArrayValue(
            jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_mentions),
            i, jcasType.ll_cas.ll_getFSRef(v));
  }
/**
 * add {@link Token} to the list of tokens.
 * @param token
 */
  public void addToken(Token token) {
    FSArray fna = getTokens();
    Token[] fn = new Token[fna.size() + 1];
    fna.copyToArray(0, fn, 0, fna.size());
    fn[fn.length - 1] = token;
    try {
      fna = new FSArray(this.getCAS().getJCas(), fn.length); // TODO need to release the old one.
                                                             // read that it is not release
                                                             // automatically
    } catch (CASException e) {
      e.printStackTrace();
    }
    fna.copyFromArray(fn, 0, 0, fn.length);
    setTokens(fna);
  }

  /**
   * it adds or merge (in case it is already here) a {@link Mention} to the list of mentions.
   * @param mention
   */
  public void addOrMergeMention(Mention mention) {
    if (!mention.getSentence().equals(this))
      throw new IllegalArgumentException();
    List<Mention> overlapping = new ArrayList<Mention>();
    FeatureStructure[] mens = getMentions().toArray();
    for (int i = 0; i < mens.length; i++) {
      Mention mention2 = (Mention) mens[i];
      if (mention.overlaps(mention2) && mention.getType().equals(mention2.getType()))
        overlapping.add(mention2);
    }
    if (overlapping.size() == 0) {
      addMention(mention);
    } else {
      for (Mention mention2 : overlapping) {
        removeMention(mention2);
        try {
          addMention(new Mention(this, Math.min(mention.getStartIndex(), mention2.getStartIndex()),
                  Math.max(mention.getEndIndex(), mention2.getEndIndex())));
        } catch (CASException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * removes a {@link Mention} from the list of mentions
   * @param mention2
   */
  private void removeMention(Mention mention2) {
    FSArray fna = getMentions();
    Mention[] fn = new Mention[fna.size() - 1];
    int ind = 0;
    for (int i = 0; i < fna.size(); i++) {
      if (!((Mention) fna.get(i)).equalsM(mention2)) // NOT sure of using .equals here
        fn[ind++] = (Mention) fna.get(i);
    }
    try {
      fna = new FSArray(this.getCAS().getJCas(), fn.length);
    } catch (CASException e) {
      e.printStackTrace();
    }
    fna.copyFromArray(fn, 0, 0, fn.length);
    setMentions(fna);
  }
  /**
   * adds a {@link Mention} to the list of mentions.
   * @param mention
   */
  private void addMention(ts.Mention mention) {
    FSArray fna = getMentions();
    Mention[] fn = new Mention[fna.size() + 1];
    fna.copyToArray(0, fn, 0, fna.size());
    fn[fn.length - 1] = mention;
    try {
      fna = new FSArray(this.getCAS().getJCas(), fn.length);
    } catch (CASException e) {
      e.printStackTrace();
    }
    fna.copyFromArray(fn, 0, 0, fn.length);
    setMentions(fna);
  }

  /**
   * updates the {@link Token} list with the {@link Mention} list obtained.
   */
  public void UpdateTokensWithTags() {
    FSArray mentions = getMentions();
    for (int i = 0; i < mentions.size(); i++) {
      Mention m = (Mention) mentions.get(i);
      getTokens(m.getStartIndex()).setLabel("B");
      for (int j = m.getStartIndex() + 1; j < m.getEndIndex(); j++) {
        getTokens(j).setLabel("I");
      }
    }
    FSArray tokens = getTokens();
    for (int i = 0; i < tokens.size(); i++) {
      Token token = (Token) tokens.get(i);
      if (token.getLabel() == null)
        token.setLabel("O");
    }
  }
}
