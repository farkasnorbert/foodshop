<?php
$con=mysqli_connect("localhost","project","1234","foodshop");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
   $r = array();
	$result = mysqli_query($con,"select item from mainpage where visible=1");
	if (mysqli_num_rows($result) > 0) {
	   while($row = mysqli_fetch_assoc($result)){
			$id = $row["item"];
			$result2 = mysqli_query($con,"select name,text,picture from item where id='$id'");
			if (mysqli_num_rows($result2) > 0) {
				while($row2 = mysqli_fetch_assoc($result2)){
					$r[] = $row2;
				}
			}
	   }
	}
	echo json_encode($r);
   mysqli_close($con);