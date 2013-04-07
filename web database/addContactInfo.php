<?php
$con = mysql_connect("localhost","maeveroo_Maeve","maeve2124");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("maeveroo_AllergyApp", $con);

$updateRestaurantTable="UPDATE restaurants SET address='1 main street, big town, dublin 2', phone='01-555 111', restaurantEmail='food@food.com'";

$query = mysql_query($updateRestaurantTable) or die(mysql_error());

if (query) {
	echo "Data Inserted!";
}

mysql_close($con);
?>
