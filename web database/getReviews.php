<?php
$con = mysql_connect("localhost","maeveroo_Maeve","maeve2124");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("maeveroo_AllergyApp", $con);

$return_arr = array();

$restaurantID = (int) $_POST['id'];

$result = mysql_query("SELECT * FROM reviews WHERE restaurantID='$restaurantID'");

while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    $row_array['author'] = $row['authorID'];
    $row_array['restaurant'] = $row['restaurantID'];
    $row_array['text'] = $row['reviewText'];
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