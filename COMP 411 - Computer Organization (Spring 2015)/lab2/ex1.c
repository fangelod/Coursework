/*  Example: C program to find area of a circle */

#include <stdio.h>
#define PI 3.14159

main()
{
  float r, m, t, a;
  
  printf("Enter the circle's radius (in centimeters): ");
  scanf("%f", &r);

  m = r / 2.54;
  a = PI * m * m;

  printf("Its area is %3.2f square inches.\n", a);
  return;
}
