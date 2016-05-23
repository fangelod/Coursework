#include <stdio.h>
#include <stdlib.h>


int compare_fn(float a, float b) {
  if (a < b) {
        return -1;
  } else if (a > b) {
        return 1;
  } else {
        return 0;
  }
}


// Bubble sort method. Order parameter takes either -1 or 1. 
// -1 indicates descending, 1 indicates ascending
float* bubble_sort_generic(float *data, int order, int size) {
  int x, y;
  float swap;

  if (order == 1) {
        for (x = 0; x < size; x++) {
                for (y = 0; y < size - 1; y++) {
                        if (compare_fn(data[y], data[y+1]) == 1) {
                                swap = data[y+1];
                                data[y+1] = data[y];
                                data[y] = swap;
                        }
                }
        }               
  } else if (order == -1) {
        for (x = 0; x < size; x++) {
                for (y = 0; y < size - 1; y++) {
                        if (compare_fn(data[y], data[y+1]) == -1) {
                                swap = data[y+1];
                                data[y+1] = data[y];
                                data[y] = swap;         
                        }               
                }               
        }  
  }

  return data;
}


main() {
  int size, i;
  float *sci, *temp, *sorted;
  float input;

  sci = malloc(sizeof(float));
  size = 0;
  input = 1;

  while (input != 0) {
	// Read in number
	//scanf("%f", &input);
	scanf("%G", &input);
	
	// Check if input is 0, break if true
	if (input == 0) {
		break;
	}

	// Store number in dynamic array
	if (size == 0) {
		sci[size] = input;
		size++;
	} else if (size > 0) {
		temp = realloc(sci, (size+1)*sizeof(float));
		sci = temp;
		sci[size] = input;
		size++;
	}
  }

  // Print original list
  printf("The original values are:\n");
  for (i = 0; i < size; i++) {
	if (i < (size - 1)) {
		printf("%G ", sci[i]);
	} else {
		printf("%G\n", sci[i]);
	}
  }

  // Sort ascending, print list
  printf("The sorted values are:\n");
  sorted = bubble_sort_generic(sci, 1, size);
  for (i = 0; i < size; i++) {
        if (i < (size - 1)) {
                printf("%G ", sorted[i]);
        } else {
                printf("%G\n", sorted[i]);
        }
  }


  // Sort descending, print list
  printf("The sorted values are:\n");
  sorted = bubble_sort_generic(sci, -1, size);
  for (i = 0; i < size; i++) {
        if (i < (size - 1)) {
                printf("%G ", sorted[i]);
        } else {
                printf("%G\n", sorted[i]);
        }
  }

}
