package mintneedwork;

import java.lang.Character;
import java.util.HashMap;
import java.util.HashSet;

public class MintSpaceAtEndOfLineChecker {
  // Constructor.
  public MintSpaceAtEndOfLineChecker() {
    failedLines = new HashSet<Integer>();
    errorMessage = "Extra space at the end of a line.";
  }
  // Check if a line satisfies the rull:
  //   A line should not end with blank space.
  // Returns true if the input line passes the check.
  public boolean checkLine(String line) {
    int len = line.length();
    return !(len > 0 && Character.isWhitespace(line.charAt(len-1)));
  }

  public void failedAtLine(int line_num) {
    // Add the line number to the failed lines set. 
    // The set is unchanged if the line_num already exist in the set.
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

  private HashSet<Integer> failedLines;
  private String errorMessage;
}
