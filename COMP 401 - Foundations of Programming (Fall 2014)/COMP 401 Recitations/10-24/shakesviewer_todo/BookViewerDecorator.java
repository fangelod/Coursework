package shakesviewer_todo;

// BookViewerDecorator provides methods for accessing a fixed number of
// consecutive lines.
public interface BookViewerDecorator extends Book {
  // Specify the range with the first line and the number of lines.
  void setViewerRange(int beginning, int numberOfLines);
  // Get all the lines in the viewer's range.
  String getTextInViewer();
  // Scroll up by certain lines.
  void scrollUp(int amount);
  // Scroll down by certain lines.
  void scrollDown(int amount);
  // Get the begining line number in the current view.
  int getBeginning();
  // Get the end line number int the current view.
  int getEnd();
  // Get the number of lines;
  int getNumberOfLinesInViewer();
  // Get the original book object.
  Book getOriginalBook();
}
