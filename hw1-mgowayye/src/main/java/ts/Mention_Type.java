package ts;

/* First created by JCasGen Thu Sep 18 16:28:15 EDT 2014 */

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Sat Sep 20 00:45:09 EDT 2014
 * @generated */
public class Mention_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Mention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Mention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Mention(addr, Mention_Type.this);
  			   Mention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Mention(addr, Mention_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Mention.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ts.Mention");
 
  /** @generated */
  final Feature casFeat_startIndex;
  /** @generated */
  final int     casFeatCode_startIndex;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getStartIndex(int addr) {
        if (featOkTst && casFeat_startIndex == null)
      jcas.throwFeatMissing("startIndex", "ts.Mention");
    return ll_cas.ll_getIntValue(addr, casFeatCode_startIndex);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setStartIndex(int addr, int v) {
        if (featOkTst && casFeat_startIndex == null)
      jcas.throwFeatMissing("startIndex", "ts.Mention");
    ll_cas.ll_setIntValue(addr, casFeatCode_startIndex, v);}
    
  
 
  /** @generated */
  final Feature casFeat_endIndex;
  /** @generated */
  final int     casFeatCode_endIndex;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEndIndex(int addr) {
        if (featOkTst && casFeat_endIndex == null)
      jcas.throwFeatMissing("endIndex", "ts.Mention");
    return ll_cas.ll_getIntValue(addr, casFeatCode_endIndex);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEndIndex(int addr, int v) {
        if (featOkTst && casFeat_endIndex == null)
      jcas.throwFeatMissing("endIndex", "ts.Mention");
    ll_cas.ll_setIntValue(addr, casFeatCode_endIndex, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentence;
  /** @generated */
  final int     casFeatCode_sentence;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSentence(int addr) {
        if (featOkTst && casFeat_sentence == null)
      jcas.throwFeatMissing("sentence", "ts.Mention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_sentence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSentence(int addr, int v) {
        if (featOkTst && casFeat_sentence == null)
      jcas.throwFeatMissing("sentence", "ts.Mention");
    ll_cas.ll_setRefValue(addr, casFeatCode_sentence, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Mention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_startIndex = jcas.getRequiredFeatureDE(casType, "startIndex", "uima.cas.Integer", featOkTst);
    casFeatCode_startIndex  = (null == casFeat_startIndex) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_startIndex).getCode();

 
    casFeat_endIndex = jcas.getRequiredFeatureDE(casType, "endIndex", "uima.cas.Integer", featOkTst);
    casFeatCode_endIndex  = (null == casFeat_endIndex) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_endIndex).getCode();

 
    casFeat_sentence = jcas.getRequiredFeatureDE(casType, "sentence", "ts.Sentence", featOkTst);
    casFeatCode_sentence  = (null == casFeat_sentence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentence).getCode();

  }
}



    