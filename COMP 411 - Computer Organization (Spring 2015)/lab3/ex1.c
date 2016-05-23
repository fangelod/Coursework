#include <stdio.h>

main()
{
  int num, x;
  
  printf("Please enter a number from 1 to 5: ");
  scanf("%d", &num);
  
  if (num > 0 && num < 6) {
  	for (x = 1; x <= num; x++) {
		printf("Hello World\n");
  	}
  } else {
	printf("Number is not in the range from 1 to 5\n");
  }

  return;
}
