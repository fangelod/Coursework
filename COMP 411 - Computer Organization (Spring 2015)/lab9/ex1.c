#include <stdio.h>
#include <string.h>
#include <stdlib.h>

unsigned int fibonacci(unsigned int n)
{
  if (n > 1) {
        return (fibonacci((n-1)) + fibonacci((n-2)));
  } else if (n == 1) {
	return 1;
  } else if (n == 0) {
	return 0;
  }
}


main()
{
  int input;
  unsigned int num, result;

  result = 1;
  while (result != 0) {
	scanf("%d", &input);
	if (input != 0) {
		num = input;
		result = fibonacci(num);
		printf("%u\n", result);
	} else {
		num = 0;
		result = fibonacci(num);
		printf("%d\n", result);
		break;
	}
  }

  return;
}
