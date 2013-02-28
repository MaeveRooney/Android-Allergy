<?php
$con = mysql_connect("localhost","maeveroo_Maeve","maeve2124");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("maeveroo_AllergyApp", $con);

$return_arr = array();

$result = mysql_query("SELECT * FROM restaurants");

while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
	$row_array['id']=$row['id'];
    $row_array['name'] = $row['name'];
    $row_array['GPSLatitude'] = $row['GPSLatitude'];
    $row_array['GPSLongitude'] = $row['GPSLongitude'];
    $row_array['wheatRating'] = $row['wheatRating'];
    $row_array['glutenRating'] = $row['glutenRating'];
    $row_array['dairyRating'] = $row['dairyRating'];
    $row_array['nutRating'] = $row['nutRating'];
    $row_array['overallRating'] = $row['overallRating'];

    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);

mysql_close($con);
?>