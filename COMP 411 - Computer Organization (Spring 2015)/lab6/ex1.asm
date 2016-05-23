#---------------------------------
# Lab 5: Pixel Conversion
#
# Name: Franz Dominno
# Onyen: dominno
#
# --------------------------------
# Below is the expected output.
# 
# Converting pixels to grayscale:
# 0
# 1
# 2
# 34
# 5
# 67
# 89
# Finished.
# -- program is finished running --
#---------------------------------

.data 0x0
	startString:	.asciiz	"Converting pixels to grayscale:\n"
	finishString:	.asciiz	"Finished."
	newline:	.asciiz	"\n"
	pixels:		.word 	0x00010000,	0x010101,	0x6,		0x3333, 
				0x030c,		0x700853,	0x294999,	-1

.text 0x3000

main:
	ori 	$v0, $0, 4			#System call code 4 for printing a string
	ori	$a0, $0, 0x0   	 		#address of startString is in $a0
	syscall					#print the string


#------- INSERT YOUR CODE HERE -------
#
#(You may delete the comment here when you insert your code)
#
#------------ END CODE ---------------
      		 
     	la	$8, pixels			# get array address
	
while: 						# while (a[i] != -1) {
	lw	$10, ($8)			# load a[i]				= $10
	li	$18, -1				# check value if = -1
	beq	$10, $18, exit
convert:
	srl	$11, $10, 16			# $11 = $10 >> 8 = R			R = $11
	sll	$11, $11, 16
	sub	$10, $10, $11
	srl	$11, $11, 16
	srl	$12, $10, 8			# $12 = $10 >> 4 = G			G = $12
	sll	$12, $12, 8
	sub	$13, $10, $12			#					B = $13
	srl	$12, $12, 8
	addi	$14, $0, 3			# set $14 to 3 for div
	add	$15, $11, $12			# $15 = sum of RGB			R+G+B = $15
	add	$15, $15, $13
	div	$15, $14			# Gray = sum / 3
	mflo	$16				# store quotient in $16			Gray = $16		
	li	$v0, 1				# System call for printing integer
	move	$a0, $16			# Move gray value to $a0
	syscall					# Print the gray value
	addi 	$a0, $0, 0xA 
        addi 	$v0, $0, 0xB 
        syscall
	addi	$8, $8, 4			# increment i
	bne	$10, $18, while			# }

exit:

	ori 	$v0, $0, 4			#System call code 4 for printing a string
	ori 	$a0, $0, 33  			#address of finishString is in $a0; we computed this
						#  simply by counting the number of chars in startString,
						#  including the \n and the terminating \0

	syscall					#print the string

	ori 	$v0, $0, 10			#System call code 10 for exit
	syscall					#exit the program
