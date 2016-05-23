package mintneedwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MintLineWidthChecker {
  // Default constructor
  public MintLineWidthChecker() {
    failedLines = new HashSet<Integer>();
    maxWidth = 80;
    tabSize = 4;
    setErrorMessage();
  }

  public MintLineWidthChecker(int maxWidth) {
    failedLines = new HashSet<Integer>();
    this.maxWidth = maxWidth;
    tabSize = 4;
    setErrorMessage();
  }
  
  public MintLineWidthChecker(int maxWidth, int tabSize) {
    failedLines = new HashSet<Integer>();
    this.maxWidth = maxWidth;
    this.tabSize = tabSize;
    setErrorMessage();
  }

  // Check if a line statisfies the rule:
  //   The length of a line should not exceed max_width.
  // Return true if the input line passes the check.
  // If the line has tabs, use tabSize for the length of a tab.
  public boolean checkLine(String line) {
    int tabCount = 0;
    for(int i = 0; i < line.length(); ++i) {
      if (line.charAt(i) == '\t') {
        ++tabCount;
      }
    }
    return line.length() + tabCount * (tabSize-1) <= maxWidth;
  }

  public void failedAtLine(int line_num) {
        failedLines.add(line_num);
  }

  public HashMap<Integer, String> getErrors(String filename) {
    HashMap<Integer, String> errors = new HashMap<Integer, String>();
    for(Integer i : failedLines) {
      String err = filename + ":" + i + ": error: " + errorMessage;
      errors.put(i, err);
    }
    return errors;
  }

  // A helper method to set the error message.
  private void setErrorMessage() {
    errorMessage = "Line width exceeded " + maxWidth + " characters.";
  }

  private int maxWidth;
  private int tabSize;
  private HashSet<Integer> failedLines;
  private String errorMessage;
}
