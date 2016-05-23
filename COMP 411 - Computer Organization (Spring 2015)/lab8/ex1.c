#include <stdio.h>
#include <string.h>
#include <stdlib.h>

main()
{
  char* input;
  int result, stop;

  result = 1;
  while (result != 0) {
	input = malloc(21);
	fgets(input, 20, stdin);
	result = a_to_i(input);
	if (result > 0) {
		printf("%d\n", result);
	} else {
		printf("0\n");
		free(input);
		break;
	}
	free(input);
  }

  return;
}

int a_to_i(char* str)
{
  int i, x, convert;
  convert = 0;
  i = 0;
  while (i < 10) {
	if (str[i] == 48) {
		x = 0;
	} else if ((str[i] < 48 || str[i] > 57) && i < 9) {
		convert /= 10;
		break;
	} else {
        	x = str[i] - '0';
	}

        if(x >= 0 && x < 10) {
                convert += x;
        }
	
	if (i < 9) {
		convert *= 10;
	}
	
	i++;
  }
  return convert;
}
