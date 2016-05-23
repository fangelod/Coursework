
public class DebuggerDemo {
	public static int[] selectionSort(int[] unsorted) {
		int[] sorted = new int[unsorted.length];
		// Iterate through sorted array
		for (int i = 0; i < sorted.length; i++) {
			// Take out smallest element from unsorted array
			int min = unsorted[0];
			for (int j = 0; j < unsorted.length; j++) {
				if (unsorted[j] < min)
					min = unsorted[j];
			}
			
			// Put next smallest element into sorted array
			sorted[i] = min;
		}
		
		return sorted;
	
	}
	
	public static void main(String[] args) {
		int[] unsorted = {5, 2, 6, 1, -1};
		int[] sorted = selectionSort(unsorted);
		
		// Print output
		for (int i = 0; i < sorted.length; i++)
			System.out.println(sorted[i]);
	}
}
