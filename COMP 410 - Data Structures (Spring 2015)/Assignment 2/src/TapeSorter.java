/**
 * Represents a machine with limited memory that can sort tape drives.
 */
import java.util.Random;


public class TapeSorter {

    private int memorySize;
    private int tapeSize;
    public int[] memory;

    public TapeSorter(int memorySize, int tapeSize) {
        this.memorySize = memorySize;
        this.tapeSize = tapeSize;
        this.memory = new int[memorySize];
    }

    /**
     * Sorts the first `size` items in memory via quicksort
     */
    public void quicksort(int size) {
        // TODO: Implement me for 10 points 		
    	partitionStep(0, size - 1);
    }
    
    /**
     * Helper method for quicksort()
     */
    public void partitionStep(int start, int end) {
    	if ((end - start + 1) > 1) {
    		int pivotIndex = start + (end - start) / 2;
    		int pivot = memory[pivotIndex];
        	
        	int left = start;
        	int right = end;
        	
        	while (left <= right) {
        		while (memory[left] < pivot) {
                	left++;
                }
            		
            	while (memory[right] > pivot) {
                	right--;
                }
            	if (left <= right) {
            		int temp = memory[left];
            		memory[left] = memory[right];
            		memory[right] = temp;
            		left++;
            		right--;
            	}
        	}
        	
        	if (start < right) {
        		partitionStep(start, right);
        	}
        	if (left < end) {
        		partitionStep(left, end);
        	}
    	}
    	
    }

    /**
     * Reads in numbers from drive `in` into memory (a chunk), sorts it, then writes it out to a different drive.
     * It writes chunks alternatively to drives `out1` and `out2`.
     *
     * If there are not enough numbers left on drive `in` to fill memory, then it should read numbers until the end of
     * the drive is reached.
     *
     * Example 1: Tape size = 8, memory size = 2
     * ------------------------------------------
     *   BEFORE:
     * in: 4 7 8 6 1 3 5 7
     *
     *   AFTER:
     * out1: 4 7 1 3 _ _ _ _
     * out2: 6 8 5 7 _ _ _ _
     *
     *
     * Example 2: Tape size = 10, memory size = 3
     * ------------------------------------------
     *   BEFORE:
     * in: 6 3 8 9 3 1 0 7 3 5
     *
     *   AFTER:
     * out1: 3 6 8 0 3 7 _ _ _ _
     * out2: 1 3 9 5 _ _ _ _ _ _
     *
     *
     * Example 3: Tape size = 13, memory size = 4
     * ------------------------------------------
     *   BEFORE:
     * in: 6 3 8 9 3 1 0 7 3 5 9 2 4
     *
     *   AFTER:
     * out1: 3 6 8 9 2 3 5 9 _ _ _ _ _
     * out2: 0 1 3 7 4 _ _ _ _ _ _ _ _
     */
    public void initialPass(TapeDrive in, TapeDrive out1, TapeDrive out2) {
        // TODO: Implement me for 15 points!
    	int tapeCount = 0;
    	int outCount = 2;
    	while (tapeCount < tapeSize) {
    		if (outCount % 2 == 0) {
    			for (int i = 0; i < memorySize; i++) {
            		memory[i] = in.read();
            	}
            	quicksort(memorySize);
            	for (int i = 0; i < memorySize; i++) {
            		out1.write(memory[i]);
            	}
            	tapeCount += memorySize;
    		} else if (outCount % 2 == 1) {
    			for (int i = 0; i < memorySize; i++) {
            		memory[i] = in.read();
            	}
            	quicksort(memorySize);
            	for (int i = 0; i < memorySize; i++) {
            		out2.write(memory[i]);
            	}
            	tapeCount += memorySize;
    		}
    	}
    }

    /**
     * Merges the first chunk on drives `in1` and `in2` and writes the sorted, merged data to drive `out`.
     * The size of the chunk on drive `in1` is `size1`.
     * The size of the chunk on drive `in2` is `size2`.
     *
     *          Example
     *       =============
     *
     *  (BEFORE)
     * in1:  [ ... 1 3 6 8 9 ... ]
     *             ^
     * in2:  [ ... 2 4 5 7 8 ... ]
     *             ^
     * out:  [ ... _ _ _ _ _ ... ]
     *             ^
     * size1: 4, size2: 4
     *
     *   (AFTER)
     * in1:  [ ... 1 3 6 8 9 ... ]
     *                     ^
     * in2:  [ ... 2 4 5 7 8 ... ]
     *                     ^
     * out:  [ ... 1 2 3 4 5 6 7 8 _ _ _ ... ]
     *                             ^
     */
    public void mergeChunks(TapeDrive in1, TapeDrive in2, TapeDrive out, int size1, int size2) {
        // TODO: Implement me for 10 points
    	int outCount = 0;
    	int oneCount = 0;
    	int twoCount = 0;
    	
    	int onePlace;
    	int twoPlace;
    	
    	if (size1 > 0) {
    		onePlace = in1.read();
    	} else {
    		onePlace = Integer.MAX_VALUE;
    	}
    	
    	if (size2 > 0) {
    		twoPlace = in2.read();
    	} else {
    		twoPlace = Integer.MAX_VALUE;
    	}
    	
    	while (outCount < (size1 + size2)) {
    		if (onePlace < twoPlace) {
    			out.write(onePlace);
    			outCount++;
    			onePlace = in1.read();
    		} else if (twoPlace < onePlace) {
    			out.write(twoPlace);
    			outCount++;
    			twoPlace = in2.read();
    		} else if (onePlace == twoPlace) {
    			if (oneCount < size1) {
    				out.write(onePlace);
    				outCount++;
    			} else if (twoCount < size2) {
    				out.write(twoPlace);
    				outCount++;
    			}
    			
    		}
    	}
    	
    }

    /**
     * Merges chunks from drives `in1` and `in2` and writes the resulting merged chunks alternatively to drives `out1`
     * and `out2`.
     *
     * The `runNumber` argument denotes which run this is, where 0 is the first run.
     *
     * -- Math Help --
     * The chunk size on each drive prior to merging will be: memorySize * (2 ^ runNumber)
     * The number of full chunks on each drive is: floor(tapeSize / (chunk size * 2))
     *   Note: If the number of full chunks is 0, that means that there is a full chunk on drive `in1` and a partial
     *   chunk on drive `in2`.
     * The number of leftovers is: tapeSize - 2 * chunk size * number of full chunks
     *
     * To help you better understand what should be happening, here are some examples of corner cases (chunks are
     * denoted within curly braces {}):
     *
     * -- Even number of chunks --
     * in1 ->   { 1 3 5 6 } { 5 7 8 9 }
     * in2 ->   { 2 3 4 7 } { 3 5 6 9 }
     * out1 ->  { 1 2 3 3 4 5 6 7 }
     * out2 ->  { 3 5 5 6 7 8 9 9 }
     *
     * -- Odd number of chunks --
     * in1 ->   { 1 3 5 } { 6 7 9 } { 3 4 8 }
     * in2 ->   { 2 4 6 } { 2 7 8 } { 0 3 9 }
     * out1 ->  { 1 2 3 4 5 6 } { 0 3 3 4 8 9 }
     * out2 ->  { 2 6 7 7 8 9 }
     *
     * -- Number of leftovers <= the chunk size --
     * in1 ->   { 1 3 5 6 } { 5 7 8 9 }
     * in2 ->   { 2 3 4 7 }
     * out1 ->  { 1 2 3 3 4 5 6 7 }
     * out2 ->  { 5 7 8 9 }
     *
     * -- Number of leftovers > the chunk size --
     * in1 ->   { 1 3 5 6 } { 5 7 8 9 }
     * in2 ->   { 2 3 4 7 } { 3 5 }
     * out1 ->  { 1 2 3 3 4 5 6 7 }
     * out2 ->  { 3 5 5 7 8 9 }
     *
     * -- Number of chunks is 0 --
     * in1 ->   { 2 4 5 8 9 }
     * in2 ->   { 1 5 7 }
     * out1 ->  { 1 2 4 5 5 7 8 9 }
     * out2 ->
     */
    public void doRun(TapeDrive in1, TapeDrive in2, TapeDrive out1, TapeDrive out2, int runNumber) {
        // TODO: Implement me for 15 points
    	int chunkSize = (int) (memorySize * (Math.pow(2, runNumber)));
    	int chunkNum = (int)Math.floor(tapeSize / (chunkSize * 2));
    	int numLeft = tapeSize - 2 * chunkSize * chunkNum;
    	
    	if (chunkNum % 2 == 0) {
    		if (chunkNum == 0) {
    			if (numLeft != 0 && numLeft <= chunkSize) {
    				mergeChunks(in1, in2, out1, chunkSize, 0);
    			} else if (numLeft != 0 && numLeft > chunkSize) {
    				mergeChunks(in1, in2, out1, chunkSize, (numLeft - chunkSize));
    			}
    			
    		} else if (chunkNum > 0) {
    			for (int i = 0; i < chunkNum; i++) {
					mergeChunks(in1, in2, out1, chunkSize, chunkSize);
	    			mergeChunks(in1, in2, out2, chunkSize, chunkSize);
				}
    			if (numLeft != 0 && numLeft <= chunkSize) {
    				mergeChunks(in1, in2, out1, numLeft, 0);
    			} else if (numLeft != 0 && numLeft > chunkSize) {
    				mergeChunks(in1, in2, out1, chunkSize, (numLeft - chunkSize));
    			}
    		}
    	} else if (chunkNum % 2 == 1) {
    		if (chunkNum == 1) {
    			mergeChunks(in1, in2, out1, chunkSize, chunkSize);
    			if (numLeft != 0 && numLeft <= chunkSize) {
    				mergeChunks(in1, in2, out2, numLeft, 0);
    			} else if (numLeft != 0 && numLeft > chunkSize) {
    				mergeChunks(in1, in2, out2, chunkSize, (numLeft - chunkSize));
    			}
    		} else if (chunkNum > 1) {
    			for (int i = 0; i < chunkNum; i++) {
					mergeChunks(in1, in2, out1, chunkSize, chunkSize);
	    			mergeChunks(in1, in2, out2, chunkSize, chunkSize);
	    			mergeChunks(in1, in2, out1, chunkSize, chunkSize);
				}
    			if (numLeft != 0 && numLeft <= chunkSize) {
    				mergeChunks(in1, in2, out2, numLeft, 0);
    			} else if (numLeft != 0 && numLeft > chunkSize) {
    				mergeChunks(in1, in2, out2, chunkSize, (numLeft - chunkSize));
    			}
    		}
    	}
    }

    /**
     * Sorts the data on drive `t1` using the external sort algorithm. The sorted data should end up on drive `t1`.
     *
     * Initially, drive `t1` is filled to capacity with unsorted numbers.
     * Drives `t2`, `t3`, and `t4` are empty and are to be used in the sorting process.
     */
    public void sort(TapeDrive t1, TapeDrive t2, TapeDrive t3, TapeDrive t4) {
        // TODO: Implement me for 15 points
    	int size = 0;
    	int runCount = 0;
    	while (size < tapeSize) {
    		size = (int) (memorySize * (Math.pow(2, runCount)));
    		if (size >= tapeSize) {
    			break;
    		}
    		initialPass(t1, t2, t3);
        	t1.reset();
        	t2.reset();
        	t3.reset();
    		
    		doRun(t2, t3, t1, t4, runCount);
        	t1.reset();
        	t2.reset();
        	t3.reset();
        	t4.reset();
        	runCount++;
        	
        	doRun(t1, t4, t2, t3, runCount);
        	t1.reset();
        	t2.reset();
        	t3.reset();
        	t4.reset();
        	runCount++;
        	
        	doRun(t2, t3, t1, t1, runCount);
        	t1.reset();
        	t2.reset();
        	t3.reset();
        	t4.reset();
        	runCount++;
    	}
    }

    public static void main(String[] args) {
        // Example of how to test
        TapeSorter tapeSorter = new TapeSorter(10, 80);
        TapeDrive t1 = TapeDrive.generateRandomTape(80);
        TapeDrive t2 = new TapeDrive(80);
        TapeDrive t3 = new TapeDrive(80);
        TapeDrive t4 = new TapeDrive(80);

        tapeSorter.sort(t1, t2, t3, t4);
        int last = Integer.MIN_VALUE;
        boolean sorted = true;
        for (int i = 0; i < 80; i++) {
            int val = t1.read();
            sorted &= last <= val; // <=> sorted = sorted && (last <= val);
            last = val;
        }
        if (sorted)
            System.out.println("Sorted!");
        else
            System.out.println("Not sorted!");
    }

}
