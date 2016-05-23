package minttodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MintManager {
  // Constructor
  public MintManager(String pathToFile) throws IOException {
    // **** TODO **** 
    // Initialize singleLineCheckers. Initialize two checkers and add
    // them to singleLineCheckers

	  
    errors = new TreeMap<Integer, ArrayList<String>>();
    originalLines = new ArrayList<String>();
    // Add a dummy line so originalLines[1] stores the first line.
    originalLines.add("");
    this.pathToFile = pathToFile;
    numErrors = 0;
    // Read all the lines in the input file.
    readLinesFromFile();
  }

  // Returns path to the file to check.
  public String getPathToFile() {
    return pathToFile;
  }

  // Returns the total number of errors.
  public int getNumErrors() {
    return numErrors;
  }

  // Check each line
  public void check() {
    checkLines();
    collectErrors();
  }

  // Return errors as a string.  The lines will be in order since we use
  // TreeMap which keeps its key sorted.
  public String getErrors() {
    StringBuilder b = new StringBuilder();
    for (Map.Entry<Integer, ArrayList<String>> error : errors.entrySet()) {
      for (String msg : error.getValue()) {
        b.append(msg + System.lineSeparator());
      }
    }
    return b.toString();
  }

  // Read all the lines from the input file.
  private void readLinesFromFile() throws IOException {
    // Try to open the given file and reate a scanner to read in each line.
    try (Scanner scanner = new Scanner(new File(pathToFile))) {
      while (scanner.hasNextLine()) {
        originalLines.add(scanner.nextLine());
      }
    }
  }

  // Check lines with checkers
  private void checkLines() {
    for (int i = 1; i < originalLines.size(); ++i) {
      String line = originalLines.get(i);
      for (MintChecker c : singleLineCheckers) {
        if (!c.checkLine(line)) {
          c.failedAtLine(i);
        }
      }
    }
  }

  // Collect error lines
  private void collectErrors() {
    numErrors = 0;
    
    for (MintChecker c : singleLineCheckers) {
      // **** TODO **** 
      // Accumulate errors found by each checker in "errors" and
      // update numErrors.
    }
  }

  // Checkers
  private ArrayList<MintChecker> singleLineCheckers;
  private MintSpaceAtEndOfLineChecker spaceAtEndOfLineChecker;
  private MintLineWidthChecker lineWidthChecker;

  private ArrayList<String> originalLines;
  // Use a TreeMap to keep the keys (line numbers) sorted.
  private TreeMap<Integer, ArrayList<String>> errors;
  private String pathToFile;
  private int numErrors;
}
