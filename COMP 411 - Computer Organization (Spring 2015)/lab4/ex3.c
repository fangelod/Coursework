/* Example: matrices represented by 2-dimensional arrays */

#include <stdio.h>

main()
{
  int A[3][3];
  int B[3][3];
  int C[3][3];
  int i, j, k;
  
  printf("Please enter the values for A[0..2][0..2], one row per line:\n");
  for (i = 0; i < 3; i++) {
	scanf("%d %d %d", &A[i][0], &A[i][1], &A[i][2]);
  }

  printf("Please enter the values for B[0..2][0..2], one row per line:\n");
  for (i = 0; i < 3; i++) {
        scanf("%d %d %d", &B[i][0], &B[i][1], &B[i][2]);
  }

  printf("A=\n");
  for (i = 0; i < 3; i++) {
	printf("   %3i   %3i   %3i\n", A[i][0], A[i][1], A[i][2]);
  }
  
  printf("B=\n");
  for (i = 0; i < 3; i++) {
	printf("   %3i   %3i   %3i\n", B[i][0], B[i][1], B[i][2]);
  }

  /* multiply C = A.B: */
  
  for (i = 0; i < 3; i++)
    for (j = 0; j < 3; j++)
    {
      C[i][j] = 0;
      for (k =0; k < 3; k++)
        C[i][j] += A[i][k] * B[k][j];
    }
  
  printf("C=A.B=\n");
  for (i = 0; i < 3; i++) {
	printf("   %3i   %3i   %3i\n", C[i][0], C[i][1], C[i][2]);
  }

  /* multiply C = B.A: */
  
  for (i = 0; i < 3; i++)
    for (j = 0; j < 3; j++)
    {
      C[i][j] = 0;
      for (k =0; k < 3; k++)
        C[i][j] += B[i][k] * A[k][j];
    }

  printf("C=B.A=\n");
  for (i = 0; i < 3; i++) {
        printf("   %3i   %3i   %3i\n", C[i][0], C[i][1], C[i][2]);
  }
}
