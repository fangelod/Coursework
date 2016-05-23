#---------------------------------
# Lab 8: String conversion
#
# Name: Franz Dominno
# Onyen: dominno
#
# --------------------------------

.data
	buffer:		.space	20
	
.text


main:
	addi	$8, $0, 1		# $8 = result = 1;
	
while:
	beq	$8, $0, exit		#while (result != 0) {
	li	$v0, 5			#scanf("%d", &input);
	syscall
	move	$a0, $v0
				
	jal fibonacci
	
	move	$8, $v0			# store the value returned by fibonacci()
	li	$v0, 1			#printf("%d\n", result);
	move	$a0, $8		
	syscall
	beq	$8, $0, exit
	addi 	$a0, $0, 0xA 
        addi 	$v0, $0, 0xB 
        syscall
	j 	while		
	
fibonacci:				# fibonacci(n) = fibonacci(n-1) + fibonacci(n-2)
	addi	$sp, $sp, -12		# save registers on stack
	sw	$a0, 0($sp)		
	sw	$s0, 4($sp)		
	sw	$ra, 8($sp)		
	bgt	$a0, 1, calc
	move	$v0, $a0
	j	return
	
calc:
	addi	$a0, $a0, -1
	jal	fibonacci
	move	$s0, $v0
	addi	$a0, $a0, -1
	jal	fibonacci
	addu	$v0, $v0, $s0
	
return:
	lw	$a0, 0($sp)		# restore registers from stack
	lw	$s0, 4($sp)
	lw	$ra, 8($sp)
	addi	$sp, $sp, 12		# decrease the stack size
	jr	$ra
 		
exit:
	ori 	$v0, $0, 10		#System call code 10 for exit
	syscall				#exit the program
