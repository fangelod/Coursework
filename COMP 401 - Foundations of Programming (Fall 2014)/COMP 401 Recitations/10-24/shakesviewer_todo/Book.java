package shakesviewer_todo;

public interface Book {
  // Returns the author.
  String getAuthor();
  // Returns the title.
  String getTitle();
  // Returns the total number of lines.
  int getNumberOfLines();
  // Return a certain line (line numbers start from 1).
  String getLine(int lineNum) throws IndexOutOfBoundsException;
}
