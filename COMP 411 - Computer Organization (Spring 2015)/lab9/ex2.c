#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void bedtimestory(char words[20][15], int current, int number) {
  // Print something before
  // Recursive call to bedtimestory()
  // Print something after
  int i;
  if (current == number || current >= 20) {
	// STOP recursion
	return;
  } else if (current == 0) {
	// First line & Last line
	printf("A %s couldn't sleep, so her mother told a story about a little %s,\n", words[current], words[current + 1]);
	bedtimestory(words, (current + 1), number);
	printf("... and then the %s fell asleep.\n", words[current]);
  } else if (current < (number - 1) && current > 0) {
	// Every other line
	for (i = 0; i < current; i++) {
		printf("  ");
	}
	printf("who couldn't sleep, so the %s's mother told a story about a little %s,\n", words[current], words[current + 1]);
	bedtimestory(words, (current + 1), number);
	for (i = 0; i < current; i++) {
		printf("  ");
	}
	printf("... and then the little %s fell asleep;\n", words[current]);
  } else if (current == (number - 1)) {
	// Middle line
	for (i = 0; i < current; i++) {
		printf("  ");
	}
	printf("... who fell asleep.\n");
  } else {
	return;
  }
}

main()
{
  char names[20][15];	// Up to 20 names, each up to 15 letters long (incl. NULL)
  int num, len;
  char input[15];
  char stop[15];

  strcpy(stop, "END");

  // Read the names from the input
  // until you read "END"
  num = 0;
  while (strcmp(input, stop) != 0 && num <= 20) {
	fgets(input, 15, stdin);
	len = strlen(input);
	if (input[len -1] = '\n') {	
		input[len - 1] = '\0';
	}
	if (strcmp(input, stop) == 0) {
		break;
	} else {
		strcpy(names[num], input);
		num++;
	}
  }  

  bedtimestory(names, 0, num);

  return;
}
