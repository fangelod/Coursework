package shakesviewer_todo;

// A viewer class that implements BookViwerDecorator
public class ScrollableBookViewer implements BookViewerDecorator {
  // ****** TODO: write a constructor that does the following: ******
  // 1. takes a Book object and store the reference in the private field "book".
  // 2. initializ beginning to 1 and numberOfLines to 10, so by default we start
  // from the beginning of the book and has a range of 10 lines.
	public ScrollableBookViewer(Book b) {
		book = b;
		beginning = 1;
		numberOfLines = 10;
	}

  
  @Override
  public void setViewerRange(int beginning, int numberOfLines) {
    // Adjust the numberOfLines so it does not exceeds the book length.
    if(numberOfLines > book.getNumberOfLines()) {
      numberOfLines = book.getNumberOfLines();
    }
    // The beginning does not go beyond the first line.
    if(beginning <= 0) {
      beginning = 1;
    }
    // Ajust the beginning so the last line in the view does not go beyond the
    // last line of the book.
    int endLine = getEnd();
    if(endLine > book.getNumberOfLines()) {
      beginning = book.getNumberOfLines() - numberOfLines + 1;
    }
    this.beginning = beginning;
    this.numberOfLines = numberOfLines;
  }

  
  // ****** TODO: implement scrollUp ******
  //
  // 1. Make sure beginning does not go beyond the first line of the book.
  // 2. the numberOfLines of the viewer should not change.
  // ** Tip: Take a peek at scrollDown below.
  	@Override
  	public void scrollUp(int amount) {
  		if(amount <= 0) {
  			return;
  		}
  		
  		if(amount > book.getNumberOfLines() - beginning - numberOfLines - 1) {
  			beginning = 1;
  		} else {
  			beginning = beginning - amount;
  		}
  	}













  @Override
  public void scrollDown(int amount) {
    // Ignore negative or zero amount.
    if(amount <= 0) {
      return;
    }
    // Adjust the beginning as long as the end does not go beyond the last line.
    int wouldBeEndLine = beginning + amount + numberOfLines - 1;
    if(wouldBeEndLine > book.getNumberOfLines()) {
      beginning = book.getNumberOfLines() - numberOfLines + 1;
    } else {
      beginning = beginning + amount;
    }
  }

  @Override
  public String getTextInViewer() {
    StringBuilder builder = new StringBuilder();
    for(int i = beginning; i <= getEnd(); ++i) {
      builder.append(String.format("[%6d] %s%n", i, book.getLine(i)));
    }
    return builder.toString();
  }

  @Override
  public int getBeginning() { return beginning; }

  @Override
  public int getEnd() { return beginning + numberOfLines - 1; }

  @Override
  public int getNumberOfLinesInViewer() { return numberOfLines; }
  
  @Override
  public Book getOriginalBook() { return book; }

  // ****** TODO: Delegate getAuthor() to the book object ******
  // ** tip: Take a peek at the other delegations below.
  	public String getAuthor() { return book.getAuthor(); }


  public String getTitle() { return book.getTitle(); }

  public int getNumberOfLines() { return book.getNumberOfLines(); }

  public String getLine(int lineNum) throws IndexOutOfBoundsException {
    return book.getLine(lineNum);
  }

  private Book book;
  private int beginning;
  private int numberOfLines;
  
}
