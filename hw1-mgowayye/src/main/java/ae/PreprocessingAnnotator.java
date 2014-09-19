package ae;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import ts.Sentence;

public class PreprocessingAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    String text = aJCas.getDocumentText();
    StringBuffer lineBuffer = new StringBuffer();
    int i = 0;
    char ch = ' ';
    int documentLength = text.length();
    String line = "";
    while(i < documentLength)
    {
      ch = text.charAt(i);
      if(ch == '\n')
      {
        line = lineBuffer.toString();
        lineBuffer.delete(0, lineBuffer.length());
        // The following three trivial lines are from BANNER
        int space = line.indexOf(' ');
        String id = line.substring(0, space).trim();
        String sentText = line.substring(space).trim();
        Sentence sentence = new Sentence(aJCas, i-line.length(), i, id, sentText);
        sentence.addToIndexes();
      }    
      else
      {
        lineBuffer.append(ch);
      }
      i++;
    }
  }
}