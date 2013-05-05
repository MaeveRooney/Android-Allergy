<?php
$con = mysql_connect("localhost","maeveroo_Maeve","maeve2124");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("maeveroo_AllergyApp", $con);

$return_arr = array();

$reviewID = (int) $_POST['id'];

$result = mysql_query("SELECT * FROM reviews
						INNER JOIN users
						ON users.id=reviews.authorID
						WHERE reviews.id='$reviewID'")or die(mysql_error());

while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    $row_array['text'] = $row['reviewText'];
    $row_array['wheatRating'] = $row['wheatRating'];
    $row_array['glutenRating'] = $row['glutenRating'];
    $row_array['dairyRating'] = $row['dairyRating'];
    $row_array['nutRating'] = $row['nutRating'];
    $row_array['overallRating'] = $row['overallRating'];
    $row_array['username'] = $row['username'];

    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);

mysql_close($con);
?>