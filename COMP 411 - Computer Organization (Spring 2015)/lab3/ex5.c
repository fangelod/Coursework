#include <stdio.h>

main()
{
  int x, i, d;
  
  while (x != 0) {
	printf("Number [1-100]: ?\n");
	scanf("%d", &x);

	if (x == 0) {
		printf("Done\n");
		break;
	}	

	d = 1;
	
	for (i = 2; i < x; i++) {
		if (x % i == 0) {
			d = i;
			break;
		}
	}
	if (d > 1) {
		printf("Non-prime, divisible by %d\n", d);
	} else {
		printf("Prime\n");
	}
  }
  
  return;
}
