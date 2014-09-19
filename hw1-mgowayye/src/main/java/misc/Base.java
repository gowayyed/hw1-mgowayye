/* 
 Copyright (c) 2007 Arizona State University, Dept. of Computer Science and Dept. of Biomedical Informatics.
 This file is part of the BANNER Named Entity Recognition System, http://banner.sourceforge.net
 This software is provided under the terms of the Common Public License, version 1.0, as published by http://www.opensource.org.  For further information, see the file 'LICENSE.txt' included with this distribution.
 */

package misc;

/** 
 * This class is customized from Banner
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.cas.FSArray;

import ts.Mention;
import ts.Sentence;
import ts.Token;
import edu.stanford.nlp.process.Tokenizer;

public abstract class Base {

  public static HashMap<String, LinkedList<Base.Tag>> getTags(BufferedReader tagFile)
          throws IOException {
    HashMap<String, LinkedList<Base.Tag>> tags = new HashMap<String, LinkedList<Base.Tag>>();

    String line = tagFile.readLine();
    while (line != null) {
      String[] split = line.split(" |\\|");
      LinkedList<Base.Tag> tagList = tags.get(split[0]);
      if (tagList == null)
        tagList = new LinkedList<Base.Tag>();
      Base.Tag tag = new Base.Tag(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
      Iterator<Base.Tag> tagIterator = tagList.iterator();
      boolean add = true;
      while (tagIterator.hasNext() && add) {
        Base.Tag tag2 = tagIterator.next();
        // FIXME Determine what to do for when A.contains(B) or
        // B.contains(A)
        if (tag.contains(tag2))
          tagIterator.remove();
        // add = false;
        else if (tag2.contains(tag))
          add = false;
        // tagIterator.remove();
        else
          assert !tag.overlaps(tag2);
      }
      if (add) {
        tagList.add(tag);
        tags.put(split[0], tagList);
      }
      line = tagFile.readLine();
    }
    return tags;
  }

  protected static HashMap<String, LinkedList<Base.Tag>> getAlternateTags(BufferedReader tagFile)
          throws IOException {
    HashMap<String, LinkedList<Base.Tag>> tags = new HashMap<String, LinkedList<Base.Tag>>();

    String line = tagFile.readLine();
    while (line != null) {
      String[] split = line.split(" |\\|");
      LinkedList<Base.Tag> tagList = tags.get(split[0]);
      if (tagList == null)
        tagList = new LinkedList<Base.Tag>();
      Base.Tag tag = new Base.Tag(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
      tagList.add(tag);
      tags.put(split[0], tagList);
      line = tagFile.readLine();
    }
    return tags;
  }

  protected static int convertNonWSIndex2FullIndex(String str, int index) {
    int nonWSIndex = -1;
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        nonWSIndex++;
        if (nonWSIndex == index)
          return i;
      }
    }
    return -1;
  }

  protected static int convertFullIndex2NonWSIndex(String str, int index) {
    int nonWSIndex = -1;
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        nonWSIndex++;
        if (i == index)
          return nonWSIndex;
      }
    }
    return -1;
  }

  private static int getTokenIndex(FSArray tokens, int index) {
    int chars = 0;
    for (int i = 0; i < tokens.size(); i++) {
      int length = ((Token) tokens.get(i)).getText().length();
      if (index >= chars && index <= chars + length - 1)
        return i;
      chars += length;
    }
    return -1;
  }

  private static boolean checkTokenBoundary(FSArray tokens, int index, boolean start) {
    int chars = 0;
    for (int i = 0; i < tokens.size(); i++) {
      int length = ((Token) tokens.get(i)).getText().length();
      if (start && index == chars)
        return true;
      if (!start && index == chars + length - 1)
        return true;
      chars += length;
    }
    return false;
  }

  public static void updateSentenceWithTags(Sentence sentence,
          HashMap<String, LinkedList<Base.Tag>> tags) {
    FSArray tokens = sentence.getTokens();

    LinkedList<Base.Tag> tagList = tags.get(sentence.getId());
    if (tagList != null)
      for (Base.Tag tag : tagList) {
        int start = getTokenIndex(tokens, tag.start);
        assert start >= 0;
        int end = getTokenIndex(tokens, tag.end);
        assert end >= start;
        try {
          sentence.addOrMergeMention(new Mention(sentence, start, end + 1));
        } catch (CASException e) {
          e.printStackTrace();
        }
      }
  }

  protected static Set<Mention> getMentions(Sentence sentence,
          HashMap<String, LinkedList<Base.Tag>> tags) {
    Set<Mention> mentions = new HashSet<Mention>();
    FSArray tokens = sentence.getTokens();
    LinkedList<Base.Tag> tagList = tags.get(sentence.getId());
    if (tagList != null)
      for (Base.Tag tag : tagList) {
        int start = getTokenIndex(tokens, tag.start);
        assert start >= 0;
        int end = getTokenIndex(tokens, tag.end);
        assert end >= start;
        try {
          mentions.add(new Mention(sentence, start, end + 1));
        } catch (CASException e) {
          e.printStackTrace();
        }
      }
    return mentions;
  }

  protected static int checkTokenBoundaries(Sentence sentence, Tokenizer tokenizer,
          HashMap<String, LinkedList<Base.Tag>> tags) {
    FSArray tokens = sentence.getTokens();
    LinkedList<Base.Tag> tagList = tags.get(sentence.getId());
    int count = 0;
    if (tagList != null)
      for (Base.Tag tag : tagList) {
        if (!checkTokenBoundary(tokens, tag.start, true)
                || !checkTokenBoundary(tokens, tag.end, false))
          count++;
      }
    return count;
  }

  /**
   * @param id
   * @param sentence
   * @param tagger
   * @param mentionOutputFile
   */
  // protected static void outputMentions(Sentence sentence,
  // PrintWriter mentionOutputFile) {
  // FSArray tokens = sentence.getTokens();
  // int charCount = 0;
  // for (int i = 0; i < tokens.size(); i++) {
  // List<Mention> mentions = sentence.getMentions(tokens.get(i));
  // assert mentions.size() == 0 || mentions.size() == 1;
  // Mention mention = null;
  // if (mentions.size() > 0)
  // mention = mentions.get(0);
  // if (mention != null && i == mention.getStart()) {
  // mentionOutputFile.print(sentence.getId());
  // mentionOutputFile.print("|");
  // mentionOutputFile.print(charCount);
  // mentionOutputFile.print(" ");
  // }
  // charCount += (((Token) tokens.get(i)).getText()).length();
  // // TODO check this
  // // if (mention != null && i == mention.getEnd() - 1) {
  // // mentionOutputFile.print(charCount - 1);
  // // mentionOutputFile.print("|");
  // // mentionOutputFile.println(mention.getText());
  // // }
  // }
  // }

  public static class Tag {
    int start;

    int end;

    /**
     * @param start
     * @param end
     */
    public Tag(int start, int end) {
      this.start = start;
      this.end = end;
    }

    public boolean overlaps(Tag tag) {
      return start <= tag.end && tag.start <= end;
    }

    public boolean contains(Tag tag) {
      return start <= tag.start && end >= tag.end;
    }
  }

  protected static double[] getResults(Set<Mention> mentionsTest, Set<Mention> mentionsFound) {
    int tp = 0;
    for (Mention mention : mentionsTest) {
      if (mentionsFound.contains(mention))
        tp++;
    }
    double[] results = new double[3];
    results[1] = (double) tp / mentionsFound.size(); // precision
    results[2] = (double) tp / mentionsTest.size(); // recall
    results[0] = 2.0 * results[1] * results[2] / (results[1] + results[2]); // f-measure
    return results;
  }

  protected static double[] getResults(Set<Mention> mentionsRequired, Set<Mention> mentionsAllowed,
          Set<Mention> mentionsFound) {
    int tp = 0;
    for (Mention mention : mentionsFound) {
      if (mentionsRequired.contains(mention) || mentionsAllowed.contains(mention))
        tp++;
    }
    double[] results = new double[3];
    results[1] = (double) tp / mentionsFound.size(); // precision
    results[2] = (double) tp / mentionsRequired.size(); // recall
    results[0] = 2.0 * results[1] * results[2] / (results[1] + results[2]); // f-measure
    return results;
  }
}
