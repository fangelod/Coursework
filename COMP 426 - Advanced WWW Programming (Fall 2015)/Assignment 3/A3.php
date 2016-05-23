<?php
// Connect to database
$conn = mysql_connect("classroom.cs.unc.edu","dominno","Faddomfx1812+");

if (!$conn) {
	die('Error connecting to the database: ' . mysql_error());
}

//mysql_select_db("dominnodb", $conn);

// Create table
	// Delete table if it already exists
DROP TABLE IF EXISTS test;

CREATE TABLE test (id INT PRIMARY KEY,
		   name CHAR(25)
	
);


?>