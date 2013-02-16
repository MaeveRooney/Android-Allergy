<?
$user="maeveroo_Maeve";
$password="maeve2124";
$database="maeveroo_AllergyApp";
mysql_connect(localhost,$user,$password);
@mysql_select_db($database) or die( "Unable to select database");

$insertUserOne="INSERT INTO users VALUES('johnsmith','example@email.com','secret1',0,0,1,0)";
$insertUserTwo="INSERT INTO users VALUES('johndoe','example2@email.com','secret1',0,1,1,0)";
$insertUserThree="INSERT INTO users VALUES('janesmith','example3@email.com','secret1',1,0,0,0)";
$insertUserFour="INSERT INTO users VALUES('fredwhite','example4@email.com','secret1',1,1,1,1)";
mysql_query($insertUserOne);
mysql_query($insertUserTwo);
mysql_query($insertUserThree);
mysql_query($insertUserFour);

$insertRestaurantOne="INSERT INTO restaurants(name,GPSLongitude,GPSLatitude) VALUES('Yummy Place',53344800,-6266100)";
$insertRestaurantTwo="INSERT INTO restaurants(name,GPSLongitude,GPSLatitude) VALUES('Food Stuffs',53342700,-6267200)";
$insertRestaurantThree="INSERT INTO restaurants(name,GPSLongitude,GPSLatitude) VALUES('Nosh',53312700,-6268200)";
mysql_query($insertRestaurantOne);
mysql_query($insertRestaurantTwo);
mysql_query($insertRestaurantThree);

$nowFormat = date('Y-m-d H:i:s');

$insertReviewOne="INSERT INTO reviews(authorID,restaurantID,date,reviewText,wheatRating,glutenRating,overallRating)
					VALUES(1,2,'$nowFormat','Really nice place, great food',4,3,4)";
$updateRestaurantTableOne="UPDATE restaurant SET wheatRating=4, glutenRating=3, overallRating=4 WHERE id=2";

$insertReviewTwo="INSERT INTO reviews(authorID,restaurantID,date,reviewText,wheatRating,dairyRating,overallRating)
					VALUES(1,3,'$nowFormat','it was not good at all',2,1,2)";
$updateRestaurantTableTwo="UPDATE restaurant SET wheatRating=2, dairyRating=3, overallRating=2 WHERE id=3";

$insertReviewThree="INSERT INTO reviews(authorID,restaurantID,date,reviewText,glutenRating)
					VALUES(2,2,'$nowFormat','good food for all tastes',5)";
$updateRestaurantTableThree="UPDATE restaurant SET glutenRating=4 WHERE id=2";

$insertReviewFour="INSERT INTO reviews(authorID,restaurantID,date,reviewText,nutRating,overallRating)
					VALUES(2,1,'$nowFormat','wouldn\'t recommend this place at all',1,1)";
$updateRestaurantTableFour="UPDATE restaurant SET nutRating=1 overallRating=1 WHERE id=1";

$insertReviewFive="INSERT INTO reviews(authorID,restaurantID,date,reviewText,glutenRating,overallRating)
					VALUES(3,2,'$nowFormat','was very happy with meal',3,4)";
$updateRestaurantTableFive="UPDATE restaurant SET glutenRating=2.3, overallRating=4 WHERE id=2";

$insertReviewSix="INSERT INTO reviews(authorID,restaurantID,date,reviewText,wheatRating,overallRating)
					VALUES(4,3,'$nowFormat','will come here again',4,5)";
$updateRestaurantTableSix="UPDATE restaurant SET wheatRating=3, overallRating=3.5 WHERE id=3";

mysql_query($insertReviewOne);
mysql_query($updateRestaurantTableOne);
mysql_query($insertReviewTwo);
mysql_query($updateRestaurantTableTwo);
mysql_query($insertReviewThree);
mysql_query($updateRestaurantTableThree);
mysql_query($insertReviewFour);
mysql_query($updateRestaurantTableFour);
mysql_query($insertReviewFive);
mysql_query($updateRestaurantTableFive);
mysql_query($insertReviewSix);
mysql_query($updateRestaurantTableSix);

$insertFavouritesOne="INSERT INTO favourites(1,2)";
$insertFavouritesTwo="INSERT INTO favourites(2,2)";
$insertFavouritesThree="INSERT INTO favourites(1,1)";
$insertFavouritesFour="INSERT INTO favourites(3,2)";
$insertFavouritesFive="INSERT INTO favourites(4,3)";


mysql_close();
?>