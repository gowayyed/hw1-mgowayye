package ts;

/* First created by JCasGen Thu Sep 18 16:28:15 EDT 2014 */

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;

/** 
 * Updated by JCasGen Wed Sep 24 23:33:23 EDT 2014
 * XML source: /home/gowayyed/git/hw1-mgowayye/hw1-mgowayye/src/main/resources/aePreprocessingAnnotatorDescriptor.xml
 * @generated */
public class Mention extends Annotation {
  /**
   * @generated
   * @ordered
   */
  @SuppressWarnings("hiding")
  public final static int typeIndexID = JCasRegistry.register(Mention.class);

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
  protected Mention() {/* intentionally empty block */}
    
  /**
   * Internal - constructor used by generator
   * 
   * @generated
   * @param addr
   *          low level Feature Structure reference
   * @param type
   *          the type of this Feature Structure
   */
  public Mention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /**
   * @generated
   * @param jcas
   *          JCas to which this Feature Structure belongs
   */
  public Mention(JCas jcas) {
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
  public Mention(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /**
   * constructs a {@link Mention} in a specific sentence and a start and end.
   * @param sentence
   * @param start
   * @param end
   * @throws CASException
   */
  public Mention(Sentence sentence, int start, int end) throws CASException {
    super(sentence.getCAS().getJCas());
    setSentence(sentence);
    setStartIndex(start);
    setEndIndex(end);
    readObject();
  }

  /** 
   * <!-- begin-user-doc --> Write your own initialization here <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/* default - does nothing empty block */
  }

  // *--------------*
  // * Feature: startIndex

  /**
   * getter for startIndex - gets
   * 
   * @generated
   * @return value of the feature
   */
  public int getStartIndex() {
    if (Mention_Type.featOkTst && ((Mention_Type)jcasType).casFeat_startIndex == null)
      jcasType.jcas.throwFeatMissing("startIndex", "ts.Mention");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Mention_Type)jcasType).casFeatCode_startIndex);}
    
  /**
   * setter for startIndex - sets
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setStartIndex(int v) {
    if (Mention_Type.featOkTst && ((Mention_Type)jcasType).casFeat_startIndex == null)
      jcasType.jcas.throwFeatMissing("startIndex", "ts.Mention");
    jcasType.ll_cas.ll_setIntValue(addr, ((Mention_Type)jcasType).casFeatCode_startIndex, v);}    
   
    
  // *--------------*
  // * Feature: endIndex

  /**
   * getter for endIndex - gets
   * 
   * @generated
   * @return value of the feature
   */
  public int getEndIndex() {
    if (Mention_Type.featOkTst && ((Mention_Type)jcasType).casFeat_endIndex == null)
      jcasType.jcas.throwFeatMissing("endIndex", "ts.Mention");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Mention_Type)jcasType).casFeatCode_endIndex);}
    
  /**
   * setter for endIndex - sets
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setEndIndex(int v) {
    if (Mention_Type.featOkTst && ((Mention_Type)jcasType).casFeat_endIndex == null)
      jcasType.jcas.throwFeatMissing("endIndex", "ts.Mention");
    jcasType.ll_cas.ll_setIntValue(addr, ((Mention_Type)jcasType).casFeatCode_endIndex, v);}    
   
    
  // *--------------*
  // * Feature: sentence

  /**
   * getter for sentence - gets
   * 
   * @generated
   * @return value of the feature
   */
  public Sentence getSentence() {
    if (Mention_Type.featOkTst && ((Mention_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "ts.Mention");
    return (Sentence)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Mention_Type)jcasType).casFeatCode_sentence)));}
    
  /**
   * setter for sentence - sets
   * 
   * @generated
   * @param v
   *          value to set into the feature
   */
  public void setSentence(Sentence v) {
    if (Mention_Type.featOkTst && ((Mention_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "ts.Mention");
    jcasType.ll_cas.ll_setRefValue(addr, ((Mention_Type)jcasType).casFeatCode_sentence, jcasType.ll_cas.ll_getFSRef(v));}    
          // taken from BANNER
  public boolean overlaps(Mention mention2) {
    return this.getEndIndex() > mention2.getStartIndex()
            && this.getStartIndex() < mention2.getEndIndex();
  }

  /**
   * compares two {@link Mention} objects and returns true if they are equal.
   * @param mention2
   * @return
   */
  public boolean equalsM(Mention mention2) {
    return this.getSentence().getId().equals(mention2.getSentence().getId())
            && this.getStartIndex() == mention2.getStartIndex()
            && this.getEndIndex() == mention2.getEndIndex();
  }
}
