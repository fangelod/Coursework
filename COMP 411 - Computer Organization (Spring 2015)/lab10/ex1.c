#include <stdio.h>
#include <stdlib.h>

struct point {
  int x;
  int y;
  struct point *next;
};

main () {
  struct point *head;
  struct point *curr; 

  // Create linked list from input
  int inone, intwo, counter;
  counter = 0;
  inone = 1;
  intwo = 1;
  while ((inone != 0) || (intwo != 0)) {
  	scanf("%d %d", &inone, &intwo);
	if ((inone == 0) && (intwo == 0)) {
		// Stop, end of linked list
	} else {
		if (counter == 0) {
			struct point *a = (struct point*)malloc(sizeof(struct point));

			a->x = inone;
			a->y = intwo;
			a->next = NULL;

			head = curr = a;

			counter++;
		} else {
			struct point *a = (struct point*)malloc(sizeof(struct point));

			a->x = inone;
			a->y = intwo;
			a->next = NULL;

			curr->next = a;
			curr = a;

			counter++;
		}
	}
  }
  
  // Calculate and print distance from origin for each pair of numbers
  int xsq, ysq, sumsq;
  struct point *b = head;
  while (b != NULL) {
	xsq = (*b).x * (*b).x;
	ysq = (*b).y * (*b).y;
	sumsq = xsq + ysq;
	printf("%d\n", sumsq);

	b = b->next;
  }
}
