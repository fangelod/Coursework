#include <stdio.h>

main()
{
  char format;
  int col, row, max, i, r, g, b, gray;
  
  scanf("%s", &format);
  printf("P2\n");
  scanf("%d%d%d", &col, &row, &max);
  printf("%d\n%d\n255\n", col, row);
  
  for (i = 0; i < col * row; i++) {
	scanf("%d%d%d", &r, &g, &b);
	gray = ((r + g + b) * 255) / (3 * max);
	printf("%d\n", gray);
  }

  return;
}

