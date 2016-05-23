package minttodo;

import java.util.HashMap;

public interface MintChecker {
  // **** TODO ****
  // Check one line of code. For SingleLineChecker, return whether the line
  // satisfies the checkers rule.
	public boolean checkLine(String line);
	
  // **** TODO ****
  // Record a failed line.
	public void failedAtLine(int line_num);
	
  // **** TODO ****
  // Get error messages associated to previously recorded failed
  // lines.  The input filename will appear in the error message. You might
  // want to look at one of the checker's implementation to get the signature
  // for getErrors.
	public HashMap<Integer, String> getErrors(String filename);
}
