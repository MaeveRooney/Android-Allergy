<?php
$con = mysql_connect("localhost","maeveroo_Maeve","maeve2124");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("maeveroo_AllergyApp", $con);

$result = mysql_query("SELECT * FROM users");

$num=mysql_numrows($result);

echo "<table border='1'>
<tr>
<th>ID</th>
<th>Username</th>
<th>email</th>
<th>password</th>
<th>salt</th>
<th>WheatAllergy</th>
<th>GlutenAllergy</th>
<th>DairyAllergy</th>
<th>NutAllergy</th>
</tr>";

while($row = mysql_fetch_array($result))
  {
  echo "<tr>";
  echo "<td>" . $row['id'] . "</td>";
  echo "<td>" . $row['username'] . "</td>";
  echo "<td>" . $row['email'] . "</td>";
  echo "<td>" . $row['encrypted_password'] . "</td>";
  echo "<td>" . $row['salt'] . "</td>";
  echo "<td>" . $row['wheatAllergy'] . "</td>";
  echo "<td>" . $row['glutenAllergy'] . "</td>";
  echo "<td>" . $row['dairyAllergy'] . "</td>";
  echo "<td>" . $row['nutAllergy'] . "</td>";
  echo "</tr>";
  }
echo "</table></br></br>";

$result = mysql_query("SELECT * FROM restaurants");

$num=mysql_numrows($result);

echo "<table border='1'>
<tr>
<th>ID</th>
<th>Name</th>
<th>Address</th>
<th>Phone</th>
<th>Email</th>
<th>GPSLong</th>
<th>GPSLat</th>
<th>wheatRating</th>
<th>WheatNumVotes</th>
<th>glutenRating</th>
<th>glutenNumVotes</th>
<th>dairyRating</th>
<th>dairyNumVotes</th>
<th>nutRating</th>
<th>nutNumVotes</th>
<th>overallRating</th>
<th>overallNumVotes</th>
<th>numFavourites</th>
</tr>";

while($row = mysql_fetch_array($result))
  {
  echo "<tr>";
  echo "<td>" . $row['id'] . "</td>";
  echo "<td>" . $row['name'] . "</td>";
  echo "<td>" . $row['address'] . "</td>";
  echo "<td>" . $row['phone'] . "</td>";
  echo "<td>" . $row['restaurantEmail'] . "</td>";
  echo "<td>" . $row['GPSLongitude'] . "</td>";
  echo "<td>" . $row['GPSLatitude'] . "</td>";
  echo "<td>" . $row['wheatRating'] . "</td>";
  echo "<td>" . $row['wheatNumVotes'] . "</td>";
  echo "<td>" . $row['glutenRating'] . "</td>";
  echo "<td>" . $row['glutenNumVotes'] . "</td>";
  echo "<td>" . $row['dairyRating'] . "</td>";
  echo "<td>" . $row['dairyNumVotes'] . "</td>";
  echo "<td>" . $row['nutRating'] . "</td>";
  echo "<td>" . $row['nutNumVotes'] . "</td>";
  echo "<td>" . $row['overallRating'] . "</td>";
  echo "<td>" . $row['overallNumVotes'] . "</td>";
  echo "<td>" . $row['numFavourites'] . "</td>";
  echo "</tr>";
  }
echo "</table></br></br>";


$result = mysql_query("SELECT * FROM reviews");

$num=mysql_numrows($result);

echo "<table border='1'>
<tr>
<th>ID</th>
<th>AuthorID</th>
<th>RestaurantID</th>
<th>date</th>
<th>reviewText</th>
<th>WheatRating</th>
<th>GlutenRating</th>
<th>DairyRating</th>
<th>NutRating</th>
<th>overallRating</th>
</tr>";

while($row = mysql_fetch_array($result))
  {
  echo "<tr>";
  echo "<td>" . $row['id'] . "</td>";
  echo "<td>" . $row['authorID'] . "</td>";
  echo "<td>" . $row['restaurantID'] . "</td>";
  echo "<td>" . $row['date'] . "</td>";
  echo "<td>" . $row['reviewText'] . "</td>";
  echo "<td>" . $row['wheatRating'] . "</td>";
  echo "<td>" . $row['glutenRating'] . "</td>";
  echo "<td>" . $row['dairyRating'] . "</td>";
  echo "<td>" . $row['nutRating'] . "</td>";
  echo "<td>" . $row['overallRating'] . "</td>";
  echo "</tr>";
  }
echo "</table></br></br>";

$result = mysql_query("SELECT * FROM favourites");

$num=mysql_numrows($result);

echo "<table border='1'>
<tr>
<th>restaurantID</th>
<th>userID</th>
</tr>";

while($row = mysql_fetch_array($result))
  {
  echo "<tr>";
  echo "<td>" . $row['restaurantID'] . "</td>";
  echo "<td>" . $row['userID'] . "</td>";
  echo "</tr>";
  }
echo "</table>";

mysql_close($con);
?>
