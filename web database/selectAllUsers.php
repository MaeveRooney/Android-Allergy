<?php
$con = mysql_connect("localhost","maeveroo_Maeve","maeve2124");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("maeveroo_AllergyApp", $con);

$return_arr = array();

$result = mysql_query("SELECT * FROM users");

while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
	$row_array['id']=$row['id'];
    $row_array['name'] = $row['username'];
    $row_array['email'] = $row['email'];
    $row_array['encrypted_password'] = $row['encrypted_password'];
    $row_array['salt'] = $row['salt'];
    $row_array['wheatAllergy'] = $row['wheatAllergy'];
    $row_array['glutenAllergy'] = $row['glutenAllergy'];
    $row_array['dairyAllergy'] = $row['dairyAllergy'];
    $row_array['nutAllergy'] = $row['nutAllergy'];

    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);

mysql_close($con);
?>