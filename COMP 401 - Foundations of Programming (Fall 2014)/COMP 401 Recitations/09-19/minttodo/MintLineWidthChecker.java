package minttodo;

public class MintLineWidthChecker extends MintSingleLineChecker {
  // **** TODO ****
  // Constructor
	public MintLineWidthChecker() {
		
	}
	
  // **** TODO ****
  // Constructor with maxWidth parameter.
	public MintLineWidthChecker(int maxWidth) {
		
	}
	
  // **** TODO ****
  // Constructor with both maxWidth and tabSize parameters.
	public MintLineWidthChecker(int maxWidth, int tabSize) {
		
	}

  // **** TODO ****
  // Override checkLine()
	@Override
	public boolean checkLine(String line) {
		int tabCount = 0;
	    for(int i = 0; i < line.length(); ++i) {
	      if (line.charAt(i) == '\t') {
	        ++tabCount;
	      }
	    }
	    return line.length() + tabCount * (tabSize-1) <= maxWidth;
	}
	
  // A helper method to set the error message.
  private void setErrorMessage() {
    errorMessage = "Line width exceeded " + maxWidth + " characters.";
  }
  
  private int maxWidth;
  private int tabSize;
}
