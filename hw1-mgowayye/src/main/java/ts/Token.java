package ts;

/* First created by JCasGen Thu Sep 18 16:28:15 EDT 2014 */

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.DoubleArray;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.DocumentAnnotation;

/** 
 * Updated by JCasGen Wed Sep 24 23:33:23 EDT 2014
 * XML source: /home/gowayyed/git/hw1-mgowayye/hw1-mgowayye/src/main/resources/aePreprocessingAnnotatorDescriptor.xml
 * @generated */
public class Token extends DocumentAnnotation {
  /**
   * @generated
   * @ordered
   */
  @SuppressWarnings("hiding")
  public final static int typeIndexID = JCasRegistry.register(Token.class);

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
  public int getTypeIndexID() {return typeIndexID;}
 
  /**
   * Never called. Disable default constructor
   * 
   * @generated
   */
  protected Token() {/* intentionally empty block */}
    
  /**
   * Internal - constructor used by generator
   * 
   * @generated
   * @param addr
   *          low level Feature Structure reference
   * @param type
   *          the type of this Feature Structure
   */
  public Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /**
   * @generated
   * @param jcas
   *          JCas to which this Feature Structure belongs
   */
  public Token(JCas jcas) {
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
  public Token(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  public Token(Sentence sentence, int begin, int end) throws CASException {
    super(sentence.getCAS().getJCas());
    setSentence(sentence);
    setStartIndex(begin);
    setEndIndex(end);
    readObject();
  }

  /** 
   * <!-- begin-user-doc --> Write your own initialization here <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/* default - does nothing empty block */
    try {
      setFeatureNames(new StringArray(this.getCAS().getJCas(), 0));
      setFeatureVector(new DoubleArray(this.getCAS().getJCas(), 0));
    } catch (CASException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // *--------------*
  // * Feature: sentence

  /**
   * getter for sentence - gets
   * 
   * @generated
   * @return value of the feature
   */
  public Sentence getSentence() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "ts.Token");
    return (Sentence)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_sentence)));}
    
  /**
   * setter for sentence - sets
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setSentence(Sentence v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "ts.Token");
    jcasType.ll_cas.ll_setRefValue(addr, ((Token_Type)jcasType).casFeatCode_sentence, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  // *--------------*
  // * Feature: startIndex

  /**
   * getter for startIndex - gets The starting index of the token within the sentence.
   * 
   * @generated
   * @return value of the feature
   */
  public int getStartIndex() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_startIndex == null)
      jcasType.jcas.throwFeatMissing("startIndex", "ts.Token");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Token_Type)jcasType).casFeatCode_startIndex);}
    
  /**
   * setter for startIndex - sets The starting index of the token within the sentence.
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setStartIndex(int v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_startIndex == null)
      jcasType.jcas.throwFeatMissing("startIndex", "ts.Token");
    jcasType.ll_cas.ll_setIntValue(addr, ((Token_Type)jcasType).casFeatCode_startIndex, v);}    
   
    
  // *--------------*
  // * Feature: endIndex

  /**
   * getter for endIndex - gets The index of the character after the last character of the token in
   * the sentence (to use substring directly).
   * 
   * @generated
   * @return value of the feature
   */
  public int getEndIndex() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_endIndex == null)
      jcasType.jcas.throwFeatMissing("endIndex", "ts.Token");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Token_Type)jcasType).casFeatCode_endIndex);}
    
  /**
   * setter for endIndex - sets The index of the character after the last character of the token in
   * the sentence (to use substring directly).
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setEndIndex(int v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_endIndex == null)
      jcasType.jcas.throwFeatMissing("endIndex", "ts.Token");
    jcasType.ll_cas.ll_setIntValue(addr, ((Token_Type)jcasType).casFeatCode_endIndex, v);}    
   
    
  // *--------------*
  // * Feature: label

  /**
   * getter for label - gets
   * 
   * @generated
   * @return value of the feature
   */
  public String getLabel() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "ts.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_label);}
    
  /**
   * setter for label - sets
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setLabel(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "ts.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_label, v);}    
   
    
  // *--------------*
  // * Feature: featureVector

  /**
   * getter for featureVector - gets
   * 
   * @generated
   * @return value of the feature
   */
  public DoubleArray getFeatureVector() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_featureVector == null)
      jcasType.jcas.throwFeatMissing("featureVector", "ts.Token");
    return (DoubleArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureVector)));}
    
  /**
   * setter for featureVector - sets
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setFeatureVector(DoubleArray v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_featureVector == null)
      jcasType.jcas.throwFeatMissing("featureVector", "ts.Token");
    jcasType.ll_cas.ll_setRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureVector, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /**
   * indexed getter for featureVector - gets an indexed value -
   * 
   * @generated
   * @param i
   *          index in the array to get
   * @return value of the element at index i
   */
  public double getFeatureVector(int i) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_featureVector == null)
      jcasType.jcas.throwFeatMissing("featureVector", "ts.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureVector), i);
    return jcasType.ll_cas.ll_getDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureVector), i);}

  /**
   * indexed setter for featureVector - sets an indexed value -
   * 
   * @generated
   * @param i
   *          index in the array to set
   * @param v
   *          value to set into the array
   */
  public void setFeatureVector(int i, double v) { 
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_featureVector == null)
      jcasType.jcas.throwFeatMissing("featureVector", "ts.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureVector), i);
    jcasType.ll_cas.ll_setDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureVector), i, v);}
   
    
  // *--------------*
  // * Feature: featureNames

  /**
   * getter for featureNames - gets
   * 
   * @generated
   * @return value of the feature
   */
  public StringArray getFeatureNames() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_featureNames == null)
      jcasType.jcas.throwFeatMissing("featureNames", "ts.Token");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureNames)));}
    
  /**
   * setter for featureNames - sets
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setFeatureNames(StringArray v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_featureNames == null)
      jcasType.jcas.throwFeatMissing("featureNames", "ts.Token");
    jcasType.ll_cas.ll_setRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureNames, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /**
   * indexed getter for featureNames - gets an indexed value -
   * 
   * @generated
   * @param i
   *          index in the array to get
   * @return value of the element at index i
   */
  public String getFeatureNames(int i) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_featureNames == null)
      jcasType.jcas.throwFeatMissing("featureNames", "ts.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureNames), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureNames), i);}

  /**
   * indexed setter for featureNames - sets an indexed value -
   * 
   * @generated
   * @param i
   *          index in the array to set
   * @param v
   *          value to set into the array
   */
  public void setFeatureNames(int i, String v) { 
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_featureNames == null)
      jcasType.jcas.throwFeatMissing("featureNames", "ts.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureNames), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_featureNames), i, v);}
    /**
   * gets the text of the {@link Token}
   * @return
   */
  public String getText() {
    return getSentence().getText().substring(getStartIndex(), getEndIndex());
  }

  /**
   * This method adds feature values to the {@link Token}
   * @param string
   * @param val
   */
  public void addFeatureValue(String string, double val) {
    StringArray fna = getFeatureNames();
    String[] fn = new String[fna.size() + 1];
    fna.copyToArray(0, fn, 0, fna.size());
    fn[fn.length - 1] = string;
    try {
      fna = new StringArray(this.getCAS().getJCas(), fn.length);
    } catch (CASException e) {
      e.printStackTrace();
    }
    fna.copyFromArray(fn, 0, 0, fn.length);
    setFeatureNames(fna);

    DoubleArray dna = getFeatureVector();
    double[] dn = new double[dna.size() + 1];
    dna.copyToArray(0, dn, 0, dna.size());
    dn[dn.length - 1] = val;
    try {
      dna = new DoubleArray(this.getCAS().getJCas(), fn.length);
    } catch (CASException e) {
      e.printStackTrace();
    }
    dna.copyFromArray(dn, 0, 0, dn.length);
    setFeatureVector(dna);
  }
}
