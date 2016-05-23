package minttodo;

import java.util.HashSet;
import java.util.HashMap;

// The abstract parent class for all the single line checkers.
public abstract class MintSingleLineChecker implements MintChecker{
  // Checks if a line satisfies the checker's rull:
  public abstract boolean checkLine(String line);
  
  // Records a failed line.
  public void failedAtLine(int line_num) {
    failedLines.add(line_num);
  }
  
  // Returns error messages for all the failed lines found by the checker.
  public HashMap<Integer, String> getErrors(String filename) {
    HashMap<Integer, String> errors = new HashMap<Integer, String>();
    for(Integer i : failedLines) {
      String err = filename + ":" + i + ": error: " + errorMessage;
      errors.put(i, err);
    }
    return errors;
  }
  
  // **** TODO ****
  // A protected constructor. ×
  protected MintSingleLineChecker() {
	  failedLines = new HashSet<Integer>();
  }
  
  protected HashSet<Integer> failedLines;
  protected String errorMessage;
}
