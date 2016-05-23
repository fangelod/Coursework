#include <stdio.h>

main()
{
  int num, i, calc;

  while (num != 0) {
	printf("Enter a number: ");
	scanf("%d", &num);
	calc = 1;
	for (i = 1; i <= num; i++) {
		calc = calc * i;
	}
	printf("%i! = %i\n", num, calc);
  }
}
