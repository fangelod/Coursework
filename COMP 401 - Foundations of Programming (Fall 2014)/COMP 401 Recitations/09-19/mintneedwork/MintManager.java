package mintneedwork;

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
    // Initialize checkers
    spaceAtEndOfLineChecker = new MintSpaceAtEndOfLineChecker();
    lineWidthChecker = new MintLineWidthChecker();

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
      if (!spaceAtEndOfLineChecker.checkLine(line)) {
        spaceAtEndOfLineChecker.failedAtLine(i);
      }
      if (!lineWidthChecker.checkLine(line)) {
        lineWidthChecker.failedAtLine(i);
      }
    }
  }

  // Collect error lines
  private void collectErrors() {
    // Put all errors from the spaceAtEndOfLinechecker into a set so we can go
    // through them.
    Set<Map.Entry<Integer, String>> error_set = 
      spaceAtEndOfLineChecker.getErrors(pathToFile).entrySet();
    for (Map.Entry<Integer, String> e : error_set) {
      Integer error_line_num = e.getKey();
      // If the error line exists in the TreeMap named errors, append the
      // message to its message list; otherwise create a new arraylist to hold
      // messages for this line and add the message to the new arraylist.
      if (errors.containsKey(error_line_num)) {
        errors.get(error_line_num).add(e.getValue());
      } else {
        errors.put(error_line_num, new ArrayList<String>());
        errors.get(error_line_num).add(e.getValue());
      }
    }
    numErrors += error_set.size();

    // Similar as the abvoe, but for the lineWidthChecker.
    error_set = lineWidthChecker.getErrors(pathToFile).entrySet();
    for (Map.Entry<Integer, String> e : error_set) {
      Integer error_line_num = e.getKey();
      // If the error line exists in the TreeMap named errors, append the
      // message to its message list; otherwise create a new arraylist to hold
      // messages for this line and add the message to the new arraylist.
      if (errors.containsKey(error_line_num)) {
        errors.get(error_line_num).add(e.getValue());
      } else {
        errors.put(error_line_num, new ArrayList<String>());
        errors.get(error_line_num).add(e.getValue());
      }
    }
    numErrors += error_set.size();
  }

  // Private checkers
  private MintSpaceAtEndOfLineChecker spaceAtEndOfLineChecker;
  private MintLineWidthChecker lineWidthChecker;

  private ArrayList<String> originalLines;
  private TreeMap<Integer, ArrayList<String>> errors;
  private String pathToFile;
  private int numErrors;
}
