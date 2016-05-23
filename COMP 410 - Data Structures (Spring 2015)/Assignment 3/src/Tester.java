import java.util.Arrays;
import java.util.Random;

public class Tester {
	public static void main(String[] args) {
		
		MinMaxHeap theHeapToRuleThemAll = new MinMaxHeap(97);
		MinMaxHeap myPreciousHeap = new MinMaxHeap(89);

		int[] testArray = new int[97];
		int[] testArray2 = new int[89];

		for (int i = 0; i < testArray.length; i++) {
			testArray[i] = i;
			theHeapToRuleThemAll.insert(testArray.length - (i + 1));
		}
		theHeapToRuleThemAll.printHeap();
		System.out.println(Arrays.toString(testArray));
		
		for (int i = 0; i < testArray.length; i++) {
			int a = theHeapToRuleThemAll.deleteMin();
			if (i == 0) {
				System.out.print("[" + a + ", ");
			} else if (i == testArray.length - 1) {
				System.out.println(a + "]");
			} else {
				System.out.print(a + ", ");
			}
		}
		
		for (int i = 0; i < testArray2.length; i++) {
			testArray2[i] = testArray2.length - (i + 1);
			myPreciousHeap.insert(i);
		}
		
		myPreciousHeap.printHeap();
		System.out.println(Arrays.toString(testArray2));

		for (int i = 0; i < testArray2.length; i++) {
			int a = myPreciousHeap.deleteMax();
			if (i == 0) {
				System.out.print("[" + a + ", ");
			} else if (i == testArray.length - 1) {
				System.out.println(a + "]");
			} else {
				System.out.print(a + ", ");
			}
		}
		
		
		/*
		MinMaxHeap test = new MinMaxHeap(50);
		int[] randNum = new int[50];
		Random random = new Random();
		int min = -50;
		int max = 50;
		
		for (int i = 0; i < randNum.length; i++) {
			randNum[i] = random.nextInt(max - min + 1) + min;
			test.insert(randNum[i]);
		}
		
		test.printHeap();
		for (int i = 0; i < randNum.length; i++) {
			test.deleteMin();
			test.printHeap();
		}
		
		for (int i = 0; i < randNum.length; i++) {
			randNum[i] = random.nextInt(max - min + 1) + min;
			test.insert(randNum[i]);
		}
		
		//test.printHeap();
		*/
		
		/*
		MinMaxHeap theHeapToRuleThemAll = new MinMaxHeap(97);
		MinMaxHeap myPreciousHeap = new MinMaxHeap(89);
		MinMaxHeap sting = new MinMaxHeap(3);

		boolean passedTest = true;

		int[] testArray = new int[97];
		int[] testArray2 = new int[89];

		for (int i = 0; i < testArray.length; i++) {
			testArray[i] = i;
			theHeapToRuleThemAll.insert(testArray.length - (i + 1));
		}

		for (int i = 0; i < testArray.length; i++) {
			int a = theHeapToRuleThemAll.deleteMin();
			if (a != testArray[i])
				passedTest = false;
		}

		System.out.println(passedTest ? "Congrats!! you rocked the delete Min test":
             "Oh NO... Gollum took the ring back --try debugging deleteMin() or insert");

		passedTest = true;
		for (int i = 0; i < testArray2.length; i++) {
			testArray2[i] = testArray2.length - (i + 1);
			myPreciousHeap.insert(i);
		}

		for (int i = 0; i < testArray2.length; i++) {
			int a = myPreciousHeap.deleteMax();
			if (a != testArray2[i])
				passedTest = false;
		}

		System.out.println(passedTest ? "Congrats!! you are amazing and passed the delete Max test":
             "Fight a belrog --I think you have some debugging to do in deleteMax() or insert");

		long oneInsert = System.nanoTime();
		sting.insert(1);
		long oneInsertEnd = System.nanoTime();
		System.out.println(sting.min() == sting.max() ? "YAY!!! you thought of this, you must be a wizard." :
             "Go back to the shire --min and max should be equal here");

		oneInsert = oneInsertEnd - oneInsert;
		long logTest = 0;
		long logTestEnd = 0;

		MinMaxHeap logTimeTest = new MinMaxHeap(1024);
		
		for (int i = 0; i < 1024; i++) {
			if (i == 1023) {
				logTest = System.nanoTime();
			}
			logTimeTest.insert(1024 - i);
			
			if (i == 1023) {
				logTestEnd = System.nanoTime();
			}
		}

		logTest = logTestEnd - logTest;
		System.out.println(logTest < oneInsert * 10 ? "insert looks logrithmic": "insert takes to long");
		
		long oneDeleteMin = System.nanoTime();
		sting.deleteMin();
		long oneDeleteMinEnd = System.nanoTime();
		oneDeleteMin = oneDeleteMinEnd - oneDeleteMin;
		
		long oneDeleteMinLogTest = System.nanoTime();
		logTimeTest.deleteMin();
		long oneDeleteMinEndTest = System.nanoTime();
		oneDeleteMinLogTest = oneDeleteMinEndTest - oneDeleteMinLogTest;

		System.out.println(oneDeleteMinLogTest < oneDeleteMin * 10? "delete min looks logrithmic":
             "delete min takes to long");

		sting.insert(1);
		
		long oneDeleteMax = System.nanoTime();
		sting.deleteMax();
		long oneDeleteMaxEnd = System.nanoTime();
		oneDeleteMax = oneDeleteMaxEnd - oneDeleteMax;
		
		long oneDeleteMaxLogTest = System.nanoTime();
		logTimeTest.deleteMax();
		long oneDeleteMaxEndTest = System.nanoTime();
		oneDeleteMaxLogTest = oneDeleteMaxEndTest - oneDeleteMaxLogTest;

		System.out.println(oneDeleteMaxLogTest < oneDeleteMax * 10? "delete max looks logrithmic": "delete max takes to long");
		*/
		
		/*
		loops:
		      for(int i = 0; i < 1000; i++ ){
		          MinMaxHeap heapheapDeleteMin = new MinMaxHeap(1000);
		          MinMaxHeap heapheapDeleteMax = new MinMaxHeap(1000);
		          MinMaxHeap heapheapDeleteMinMax = new MinMaxHeap(1000);
		          int[] arr = new int[1000];
		          for(int j = 0; j < 1000; j++){

		              Random random = new Random();
		              int x = random.nextInt(1000);
		              heapheapDeleteMin.insert(x);   
		              heapheapDeleteMax.insert(x);
		              heapheapDeleteMinMax.insert(x);
		              arr[j] = x;
		          }
		          for(int j = 0; j < 1000; j++){
		              System.out.print(arr[j] + "  ");
		          }
		          System.out.println("");

		          for(int j = 0; j < 1000; j++){
		              int b = heapheapDeleteMin.min();
		              int a = heapheapDeleteMin.deleteMin();
		              System.out.println("Deleted Min at "+ j + " On the " + i + "th try");
		              if(a != b){
		                  System.out.println("DELETEMIN NO GOOD >:(");
		                  break loops;
		              }	
		          }
		          for(int j = 0; j < 1000; j++){
		              int b = heapheapDeleteMax.max();
		              int a = heapheapDeleteMax.deleteMax();
		              System.out.println("DELETED MAX "+ j + " On the " + i + "th try");
		              if(j >= 994){   //prints out the last couple of deletes because thats where I was messing up for a while
		                  System.out.println("Right now a is " + a);
		                  System.out.println("Right now b is " + b);
		                  heapheapDeleteMax.printHeap();
		                  System.out.println("");
		              }
		              if(a != b){
		                  System.out.println("DELETEMAX NO GOOD >:(");
		                  heapheapDeleteMax.printHeap();
		                  System.out.println("Right now a is " + a);
		                  System.out.println("Right now b is " + b);
		                  //break;

		                  break loops;
		              }	
		          }
		          for(int j = 0; j < 1000; j++){
		              if(j % 2 == 0){
		                  int a = heapheapDeleteMinMax.max();
		                  int b = heapheapDeleteMinMax.deleteMax();
		                  System.out.println("Deleted Max at "+ j + " On the " + i + "th try");
		                  if(a != b){
		                      System.out.println("SOMETHING IS WRONG WITH DELETEMIN OR DELETEMAX >:(");
		                      break loops;
		                  }
		              }
		              else if(j % 2 != 0){
		                  int a = heapheapDeleteMinMax.min();
		                  int b = heapheapDeleteMinMax.deleteMin();
		                  System.out.println("Deleted Min at "+ j + " On the " + i + "th try");
		                  if(a != b){
		                      System.out.println("SOMETHING IS WRONG WITH DELETEMIN OR DELETEMAX >:(");
		                      break loops;
		                  }
		              }


		          }
		          System.out.println("MINMAXHEAPTREES AINT GOT NOTHING ON ME ");
		      }*/
		}
		
	
}
