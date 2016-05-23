#---------------------------------
# Lab 7: Image Conversion
#
# Name: Franz Dominno
# Onyen: dominno
#
# --------------------------------

.data
	pgm:		.asciiz	"P2\n"
	buffer:		.space	20
	max:		.asciiz "255\n"

.text

main:
	#Read in magic number of PPM file
	li	$v0, 8
	la	$a0, buffer
	li	$a1, 20
	move 	$8, $a0
	syscall
	
	#Print magic number for PGM file
	li 	$v0, 4				#System call code 4 for printing a string
	la	$a0, pgm   	 		#Store pgm in $a0
	syscall					#print the string
	
	#Read in columns
	li 	$v0, 5
	syscall
	move	$9, $v0				# column value
	
	#Print column number
	li	$v0, 1				# System call for printing integer
	move	$a0, $9				# Move $9 to $a0
	syscall					# Print integer
	
	addi 	$a0, $0, 0xA 
        addi 	$v0, $0, 0xB 
        syscall
	
	#Read in rows
	li 	$v0, 5
	syscall
	move	$10, $v0			# row value
	
	#Print row number
	li	$v0, 1				# System call for printing integer
	move	$a0, $10			# Move $10 to $a0
	syscall					# Print integer
	
	addi 	$a0, $0, 0xA 
        addi 	$v0, $0, 0xB 
        syscall
	
	#Read in max value
	li 	$v0, 5
	syscall
	move	$11, $v0			# color max value
	
	#Print PGM max value
	li 	$v0, 4				# System call code 4 for printing a string
	la	$a0, max   	 		# Store max in $a0
	syscall					# Print the max value for grayscale

	mult	$9, $10				# Find number of pixels = col * max
	mflo	$16				# total pixels
	addi	$20, $16, -1
	
	li	$17, 0				# initialize counter i
	
loop:	# for (i = 0; i < col * row; i++)
	#Start a loop
	beq	$17, $16, exit
	
	#Read in three integers
	li 	$v0, 5
	syscall
	move	$12, $v0			# R value
	
	li 	$v0, 5
	syscall
	move	$13, $v0			# G value
	
	li 	$v0, 5
	syscall
	move	$14, $v0			# B value
	
	#Convert to grayscale value		= ((R + G + B) * 255) / (3 * max)
	add	$15, $12, $13			# calculate (R + G + B)
	add	$15, $15, $14
	li	$18, 255			# calculate ((R + G + B) * 255)
	mult	$15, $18
	mflo	$15
	li	$19, 3				# calculate (3 * max)
	mult	$19, $11
	mflo	$19
	div	$15, $19			# calculate ((R + G + B) * 255) / (3 * max)
	mflo	$15
	
	#Print grayscale value
	li	$v0, 1				# System call for printing integer
	move	$a0, $15			# Move $15 to $a0
	syscall					# Print integer
	
	beq	$17, $20, increment
	addi 	$a0, $0, 0xA 
        addi 	$v0, $0, 0xB 
        syscall

increment:	
	addi	$17, $17, 1			# increment i
	j	loop
      		 
exit:
	ori 	$v0, $0, 10			#System call code 10 for exit
	syscall					#exit the program
