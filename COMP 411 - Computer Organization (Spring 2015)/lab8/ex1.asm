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
	
	#addi	$sp, $sp, -4		#allocate space on stack
	#sw	$ra, 0($sp)		#push $ra onto stack
	#mult	$a0, $a0, $a0
	#mult	$a1, $a1, $a1
	#add	$a0, $a0, $a1
while:
	beq	$8, $0, exit		#while (result != 0) {
	li	$v0, 8			#fgets(input, 20, stdin);
	la	$a0, buffer
	li	$a1, 20
	#move	$9, $a0			# $9 = input
	syscall												
	jal a_to_i			#result = a_to_i(input);
	move	$8, $v0			# store the value returned by a_to_i()
	li	$v0, 1			#printf("%d\n", result);
	move	$a0, $8		
	syscall
	beq	$8, $0, exit
	addi 	$a0, $0, 0xA 
        addi 	$v0, $0, 0xB 
        syscall
	j 	while		

a_to_i:
	#move	$9, $a0			# store string in $a0 to use in $9
	la	$9, buffer
	addi	$12, $0, 10
	addi	$14, $0, 9
	add	$15, $0, $0
	addi	$16, $0, 10
	add	$13, $0, $0
loop:
	lbu	$10, ($9)		# store digit in $10
	beq	$10, $16, div
	addi	$11, $10, -48		# convert digit to decimal
	add	$13, $13, $11		# add to result
	bge	$15, $14, return
	mult	$13, $12		# multiply by 10
	mflo	$13
	addi	$9, $9, 1
	addi	$15, $15, 1		
	j	loop
div:
	div	$13, $12
	mflo	$13
return:	
	move	$v0, $13
	jr	$ra			#return									
      		 
exit:
	ori 	$v0, $0, 10		#System call code 10 for exit
	syscall				#exit the program
