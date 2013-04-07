<?php
$con = mysql_connect("localhost","maeveroo_Maeve","maeve2124");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("maeveroo_AllergyApp", $con);

$return_arr = array();

$restaurantID = (int) $_POST['id'];

$result = mysql_query("SELECT * FROM restaurants WHERE id='$restaurantID'");

while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
	$row_array['id']=$row['id'];
    $row_array['name'] = $row['name'];
    $row_array['address'] = $row['address'];
    $row_array['phone'] = $row['phone'];
    $row_array['restaurantEmail'] = $row['restaurantEmail'];
    $row_array['GPSLatitude'] = $row['GPSLatitude'];
    $row_array['GPSLongitude'] = $row['GPSLongitude'];
    $row_array['wheatRating'] = $row['wheatRating'];
    $row_array['wheatNumVotes'] = $row['wheatNumVotes'];
    $row_array['glutenRating'] = $row['glutenRating'];
    $row_array['glutenNumVotes'] = $row['glutenNumVotes'];
    $row_array['dairyRating'] = $row['dairyRating'];
    $row_array['dairyNumVotes'] = $row['dairyNumVotes'];
    $row_array['nutRating'] = $row['nutRating'];
    $row_array['nutNumVotes'] = $row['nutNumVotes'];
    $row_array['overallRating'] = $row['overallRating'];
    $row_array['overallNumVotes'] = $row['overallNumVotes'];
    $row_array['numFavourites'] = $row['numFavourites'];

    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);

mysql_close($con);
?>