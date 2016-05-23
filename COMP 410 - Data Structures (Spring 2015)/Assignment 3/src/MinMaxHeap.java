
public class MinMaxHeap {

	// DO NOT CHANGE THESE VARIABLES AND METHODS
	private int currentSize;
	private int[] arr;
	public MinMaxHeap(int capacity) { //Constructor
		arr = new int[capacity + 1];
		currentSize = 0;
	}
	
	public boolean isFull() {
		return currentSize == arr.length - 1;
	}
	
	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	// COMPLETE THE FOLLOWING METHODS
	public void insert(int x){ //PRE: The heap is not full
		currentSize++;
		arr[currentSize] = x;
		
		// Maintain min-max property
		PercolateUp(currentSize);
		
	}
	
	public int min() { //PRE: The heap is not empty
		return arr[1];
	}
	
	public int max() { //PRE: The heap is not empty
		if (currentSize > 2) {
			if (arr[2] < arr[3]) {
				return arr[3];
			} else {
				return arr[2];
			}
		} else if (currentSize > 1) {
			return arr[2];
		} else {
			return arr[1];
		}
	}
	
	public int deleteMin() { //PRE: The heap is not empty
		int deletedMin = arr[1];
		if (currentSize > 1) {
			arr[1] = arr[currentSize];
		}
		currentSize--;
		
		// Maintain min-max property
		PercolateDown(1);
		
		return deletedMin;
	}
	
	public int deleteMax() { //PRE: The heap is not empty
		int deletedMax;
		int deletedIndex;
		if (currentSize == 1) {
			deletedMax = arr[1];
			deletedIndex = 1;
		} else if (currentSize == 2) {
			deletedMax = arr[2];
			deletedIndex = 2;
		} else if (arr[2] < arr[3]) {
			deletedMax = arr[3];
			deletedIndex = 3;
		} else {
			deletedMax = arr[2];
			deletedIndex = 2;
		}
		
		deletedMax = arr[deletedIndex];
		if (currentSize > 1) {
			arr[deletedIndex] = arr[currentSize];
		}
		currentSize--;
		
		// Maintain min-max property
		PercolateDown(deletedIndex);
		
		return deletedMax;
	}
	
	// Private methods go here.
	private int findLevel(int i) {
		return (int) (Math.log(i)/Math.log(2));
	}
	
	private int findNewMin(int i) {
		int low = Integer.MAX_VALUE;
		int index = 0;
		if (currentSize > 4*i+3) {
			for (int j = 4*i; j <= 4*i+3; j++) {
				if (arr[j] < low) {
					index = j;
				}
			}
		} else if (currentSize > 4*i+2) {
			for (int j = 4*i; j <= 4*i+2; j++) {
				if (arr[j] < low) {
					index = j;
				}
			}
		} else if (currentSize > 4*i+1) {
			for (int j = 4*i; j <= 4*i+1; j++) {
				if (arr[j] < low) {
					index = j;
				}
			}
		} else if (currentSize > 4*i) {
			for (int j = 4*i; j < 4*i+1; j++) {
				if (arr[j] < low) {
					index = j;
				}
			}
		}
		
		if (currentSize > 2*i+1) {
			for (int j = 2*i; j <= 2*i+1; j++) {
				if (arr[j] < low) {
					index = j;
				}
			}
		} else if (currentSize > 2*i) {
			for (int j = 2*i; j < 2*i+1; j++) {
				if (arr[j] < low) {
					index = j;
				}
			}
		}
		
		return index;
	}
	
	private int findNewMax(int i) {
		int high = Integer.MIN_VALUE;
		int index = 0;
		if (currentSize > 4*i+3) {
			for (int j = 4*i; j <= 4*i+3; j++) {
				if (arr[j] > high) {
					index = j;
				}
			}
		} else if (currentSize > 4*i+2) {
			for (int j = 4*i; j <= 4*i+2; j++) {
				if (arr[j] > high) {
					index = j;
				}
			}
		} else if (currentSize > 4*i+1) {
			for (int j = 4*i; j <= 4*i+1; j++) {
				if (arr[j] > high) {
					index = j;
				}
			}
		} else if (currentSize > 4*i) {
			for (int j = 4*i; j < 4*i+1; j++) {
				if (arr[j] > high) {
					index = j;
				}
			}
		}
		
		if (currentSize > 2*i+1) {
			for (int j = 2*i; j <= 2*i+1; j++) {
				if (arr[j] > high) {
					index = j;
				}
			}
		} else if (currentSize > 2*i) {
			for (int j = 2*i; j < 2*i+1; j++) {
				if (arr[j] > high) {
					index = j;
				}
			}
		}
		
		return index;
	}
	
	private void doSwap(int iOne, int iTwo) {
		int temp = arr[iOne];
		arr[iOne] = arr[iTwo];
		arr[iTwo] = temp;
	}
	
	private void PercolateUp(int i) {
		if (findLevel(i) % 2 == 0) {
			if (i/2 >= 1) {
				if (arr[i] > arr[i/2]) {
					doSwap(i, i/2);
					PercolateUpMax(i/2);
				} else {
					PercolateUpMin(i);
				}
			} else {
				PercolateUpMin(i);
			}
		} else {
			if (i/2 >= 1) {
				if (arr[i] < arr[i/2]) {
					doSwap(i, i/2);
					PercolateUpMin(i/2);
				} else {
					PercolateUpMax(i);
				}
			} else {
				PercolateUpMax(i);
			}
		}
	}
	
	private void PercolateUpMin(int i) {
		if (i/4 >= 1) {
			if (arr[i] < arr[i/4]) {
				doSwap(i, i/4);
				PercolateUpMin(i/4);
			}
		}
	}
	
	private void PercolateUpMax(int i) {
		if (i/4 >= 1) {
			if (arr[i] > arr[i/4]) {
				doSwap(i, i/4);
				PercolateUpMax(i/4);
			}
		}
	}
	
	private void PercolateDown(int i) {
		if (findLevel(i) % 2 == 0) {
			PercolateDownMin(i);
		} else {
			PercolateDownMax(i);
		}
	}
	
	private void PercolateDownMin(int i) {
		int m = findNewMin(i);
		if (m != 0) {
			if (findLevel(m) - findLevel(i) == 2) {
				if (arr[m] < arr[i]) {
					doSwap(i, m);
					if (arr[m] > arr[m/2]) {
						doSwap(m, m/2);
					}
					PercolateDownMin(m);
				}
			} else if (findLevel(m) - findLevel(i) == 1) {
				if (arr[m] < arr[i]) {
					doSwap(i, m);
				}
			}
		}
	}
	
	private void PercolateDownMax(int i) {
		int m = findNewMax(i);
		if (m != 0) {
			if (findLevel(m) - findLevel(i) == 2) {
				if (arr[m] > arr[i]) {
					doSwap(i, m);
					if (arr[m] < arr[m/2]) {
						doSwap(m, m/2);
					}
					PercolateDownMax(m);
				}
			} else if (findLevel(m) - findLevel(i) == 1) {
				if (arr[m] > arr[i]) {
					doSwap(i, m);
				}
			}
		}
	}
	
	public String printHeap(){ 
		int depth = (int)(Math.log(currentSize)/Math.log(2)); 
		double width = Math.pow(2, depth) * 5-3;

		for (int i=0;i<=depth;i++){ //i is the level
			int max_j = (int) Math.pow(2, i);
			for (int j=0;j<max_j;j++){ //j is position in that level

				double interval=(width-2*Math.pow(2, i))/(max_j+1);
				if (j==0) interval-=i;

				int spaces = (int)Math.round(interval*j-interval*(j-1));
				if (i!=depth) {
					if (i!=0 && j==(max_j-1)/2+1) spaces+=3;
					for (int k=0;k< spaces;k++){
						System.out.print(" ");
					}
				} else {
					int x = 3;
					if (j==0) x=0;

					for (int k=0;k< x;k++){
						System.out.print(" ");
					}
				}

				int pos = (int)(j+Math.pow(2, i));
				if (pos>currentSize){System.out.println("\n"); return "";}
				System.out.print(arr[pos]);
			}
			System.out.println();
		}
		System.out.println("\n");
		return "";
	}
	
	// Unused methods
	private void TrickleDown(int i) {
		if (i == 1) { // MIN
			// Do nothing
		} else if (i < 4) { // MAX
			if (arr[1] > arr[i]) {
				doSwap(i, 1);
			}
		} else if (i < 8) { // MIN
			if (arr[1] > arr[i]) {
				doSwap(i, i/4);
			} else if (arr[i] > arr[i/2]) {
				doSwap(i, i/2);
			}
		} else if (findLevel(i) % 2 == 1) { // MAX
			if (arr[1] > arr[i]) {
				doSwap(i, i/2);
				TrickleDown(i/2);
			} else if (arr[i] > arr[i/4]) {
				doSwap(i, i/4);
				TrickleDown(i/4);
			} else if (arr[i] < arr[i/2]) {
				doSwap(i, i/2);
				TrickleDown(i/2);
			}
		} else if (findLevel(i) % 2 == 0) { // MIN
			if (arr[1] > arr[i]) {
				doSwap(i, i/4);
				TrickleDown(i/4);
			} else if (arr[i] > max()) {
				doSwap(i, i/2);
				TrickleDown(i/2);
			} else if (arr[i] < arr[i/4]) {
				doSwap(i, i/4);
				TrickleDown(i/4);
			} else if (arr[i] > arr[i/2]) {
				doSwap(i, i/2);
				TrickleDown(i/2);
			} 
		}
	}
	
	private void siftDownMin(int i) {
		int one;
		int two;
		int three;
		int four;
		int min = 0;
		int stop;
		
		one = 4*i;
		two = 4*i+1;
		three = 4*i+2;
		four = 4*i+3;
		stop = four;
		
		if (four > currentSize) {
			if (three > currentSize) {
				if (two > currentSize) {
					if (one > currentSize) {
						return;
					}
				} else {
					min = two;
					stop = two;
				}
			} else {
				min = three;
				stop = three;
			}
		} else {
			int low = Integer.MAX_VALUE;
			for (int j = one; j < stop; j++) {
				if (arr[j] < low) {
					low = arr[j];
					min = j;
				}
			}
		}
		
		if (arr[i] > arr[min] && (min != 0)) {
			doSwap(i, min);
			siftDownMin(min);
		}
	}
   
	private void siftDownMax(int i) {
		int one;
		int two;
		int three;
		int four;
		int max = 0;
		int stop;
		
		one = 4*i;
		two = 4*i+1;
		three = 4*i+2;
		four = 4*i+3;
		stop = four;
		
		if (four > currentSize) {
			if (three > currentSize) {
				if (two > currentSize) {
					if (one > currentSize) {
						return;
					}
				} else {
					max = two;
					stop = two;
				}
			} else {
				max = three;
				stop = three;
			}
		} else {
			int high = Integer.MIN_VALUE;
			for (int j = one; j < stop; j++) {
				if (arr[j] > high) {
					high = arr[j];
					max = j;
				}
			}
		}
		
		if (arr[i] < arr[max] && (max != 0)) {
			doSwap(i, max);
			siftDownMax(max);
		}
	}
}
