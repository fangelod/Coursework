/*  Example: C program to find area of a circle */

#include <stdio.h>
#define PI 3.14159

main()
{
  float r, a;
  
  printf("Enter the circle's radius (in inches): ");
  scanf("%f", &r);

  a = PI * r * r;

  printf("Its area is %3.2f square inches.\n", a);
  return;
}
