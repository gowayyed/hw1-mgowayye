/* 
 Copyright (c) 2007 Arizona State University, Dept. of Computer Science and Dept. of Biomedical Informatics.
 This file is part of the BANNER Named Entity Recognition System, http://banner.sourceforge.net
 This software is provided under the terms of the Common Public License, version 1.0, as published by http://www.opensource.org.  For further information, see the file 'LICENSE.txt' included with this distribution.
 */

package misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.cas.FSArray;

import ts.Mention;
import ts.Sentence;
import ts.Token;

/**
 * This class is taken from BANNER. It loads the tags from the file to do training or evaluation.
 */
public abstract class Base {

  /**
   * loads the tags from the tagFile
   * 
   * @param tagFile
   * @return hashmap with the string is the sentence id and the value is a linked list of the tags.
   * @throws IOException
   */
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

  /**
   * returns the token index
   * 
   * @param tokens
   * @param index
   * @return
   */
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

  /**
   * adds new mention to the {@link cc.mallet.types.Token} based on the tag parameter
   * 
   * @param sentence
   * @param tags
   */
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

  /**
   * represents a gene tag in a file
   * 
   * @author gowayyed
   *
   */
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
}
