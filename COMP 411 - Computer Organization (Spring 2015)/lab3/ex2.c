#include <stdio.h>

main()
{
  float x, y, z, sum, product;
  
  printf("Enter three floating-point numbers:\n");
  scanf("%f %f %f", &x, &y, &z);

  sum = x + y + z;
  product = x * y * z;

  printf("Sum is %.4f\n", sum);
  printf("Product is %.4f\n", product);

  return;
}
