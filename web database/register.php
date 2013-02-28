<?php
$con = mysql_connect("localhost","maeveroo_Maeve","maeve2124");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("maeveroo_AllergyApp", $con);

$wheat=0;
$gluten=0;
$dairy=0;
$nut=0;

if ($_POST['wheat'] == 'yes')
	{
		$wheat=1;
	}
if ($_POST['gluten'] == 'yes')
	{
		$gluten=1;
	}
if ($_POST['dairy'] == 'yes')
	{
		$dairy=1;
	}
if ($_POST['nut'] == 'yes')
	{
		$nut=1;
	}


$sql="INSERT INTO users(username,email,password,wheatAllergy,glutenAllergy,dairyAllergy,nutAllergy)
VALUES
('$_POST[username]','$_POST[email]','$_POST[password]','$wheat','$gluten','$dairy','$nut')";

if (!mysql_query($sql,$con))
  {
  die('Error: ' . mysql_error());
  }
echo "1 record added";

mysql_close($con);
?>