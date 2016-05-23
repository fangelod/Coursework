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
	jal factorial
	move	$20, $v0		# store the value returned by factorial()
	li	$v0, 1			#printf("%d\n", result);
	move	$a0, $20		
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
	
factorial:
	move	$8, $v0
	move	$17, $8			# store the value passed into $17 to use
	addiu	$19, $0, 1		# set $19 = 1 to use to stop the loop
	addu	$18, $17, $0

calc:
	beq	$17, $0, fix		# if (n == 1) {return 1;}
	addiu	$18, $18, -1		# decrement n
	multu	$17, $18		# multiply n * (n - 1)
	ble	$18, $19, done
	mflo	$17
	j	calc	
	
done:							
      	move	$v0, $17
      	jr	$ra
      	
fix:
	addiu	$17, $0, 1
	move	$v0, $17
	jr	$ra
      		
exit:
	ori 	$v0, $0, 10		#System call code 10 for exit
	syscall				#exit the program
