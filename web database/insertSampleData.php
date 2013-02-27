<?
$user="maeveroo_Maeve";
$password="maeve2124";
$database="maeveroo_AllergyApp";
mysql_connect(localhost,$user,$password);
@mysql_select_db($database) or die( "Unable to select database");

$insertUserOne="INSERT INTO users(username,email,password,wheatAllergy,glutenAllergy,dairyAllergy,nutAllergy) VALUES('johnsmith','example@email.com','secret1',0,0,1,0)";
$insertUserTwo="INSERT INTO users(username,email,password,wheatAllergy,glutenAllergy,dairyAllergy,nutAllergy) VALUES('johndoe','example2@email.com','secret1',0,1,1,0)";
$insertUserThree="INSERT INTO users(username,email,password,wheatAllergy,glutenAllergy,dairyAllergy,nutAllergy) VALUES('janesmith','example3@email.com','secret1',1,0,0,0)";
$insertUserFour="INSERT INTO users(username,email,password,wheatAllergy,glutenAllergy,dairyAllergy,nutAllergy) VALUES('fredwhite','example4@email.com','secret1',1,1,1,1)";
mysql_query($insertUserOne);
mysql_query($insertUserTwo);
mysql_query($insertUserThree);
mysql_query($insertUserFour);

$insertRestaurantOne="INSERT INTO restaurants(name,GPSLatitude,GPSLongitude) VALUES('Yummy Place',53344800,-6266100)";
$insertRestaurantTwo="INSERT INTO restaurants(name,GPSLatitude,GPSLongitude) VALUES('Food Stuffs',53342700,-6267200)";
$insertRestaurantThree="INSERT INTO restaurants(name,GPSLatitude,GPSLongitude) VALUES('Nosh',53312700,-6268200)";
mysql_query($insertRestaurantOne);
mysql_query($insertRestaurantTwo);
mysql_query($insertRestaurantThree);

$nowFormat = date('Y-m-d H:i:s');

$insertReviewOne="INSERT INTO reviews(authorID,restaurantID,date,reviewText,wheatRating,glutenRating,overallRating)
					VALUES(1,2,'$nowFormat','Really nice place, great food',4,3,4)";
$updateRestaurantTableOne="UPDATE restaurants SET wheatRating=4, wheatNumVotes=1, glutenRating=3, glutenNumVotes=1, overallRating=4, overallNumVotes=1 WHERE id=2";

$insertReviewTwo="INSERT INTO reviews(authorID,restaurantID,date,reviewText,wheatRating,dairyRating,overallRating)
					VALUES(1,3,'$nowFormat','it was not good at all',2,1,2)";
$updateRestaurantTableTwo="UPDATE restaurants SET wheatRating=2, wheatNumVotes=1, dairyRating=3, dairyNumVotes=1, overallRating=2, overallNumVotes=1 WHERE id=3";

$insertReviewThree="INSERT INTO reviews(authorID,restaurantID,date,reviewText,glutenRating)
					VALUES(2,2,'$nowFormat','good food for all tastes',5)";
$updateRestaurantTableThree="UPDATE restaurants SET glutenRating=4, glutenNumVotes=2 WHERE id=2";

$insertReviewFour="INSERT INTO reviews(authorID,restaurantID,date,reviewText,nutRating,overallRating)
					VALUES(2,1,'$nowFormat','wouldn\'t recommend this place at all',1,1)";
$updateRestaurantTableFour="UPDATE restaurants SET nutRating=1, nutNumVotes=1, overallRating=1, overallNumVotes=1 WHERE id=1";

$insertReviewFive="INSERT INTO reviews(authorID,restaurantID,date,reviewText,glutenRating,overallRating)
					VALUES(3,2,'$nowFormat','was very happy with meal',3,4)";
$updateRestaurantTableFive="UPDATE restaurants SET glutenRating=2.3, glutenNumVotes=3, overallRating=4, overallNumVotes=2 WHERE id=2";

$insertReviewSix="INSERT INTO reviews(authorID,restaurantID,date,reviewText,wheatRating,overallRating)
					VALUES(4,3,'$nowFormat','will come here again',4,5)";
$updateRestaurantTableSix="UPDATE restaurants SET wheatRating=3, wheatNumVotes=3, overallRating=3.5, overallNumVotes=1 WHERE id=3";

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

$insertFavouritesOne="INSERT INTO favourites(restaurantID,userID) VALUES(1,2)";
$insertFavouritesTwo="INSERT INTO favourites(restaurantID,userID) VALUES(2,2)";
$insertFavouritesThree="INSERT INTO favourites(restaurantID,userID) VALUES(1,1)";
$insertFavouritesFour="INSERT INTO favourites(restaurantID,userID) VALUES(3,2)";
$insertFavouritesFive="INSERT INTO favourites(restaurantID,userID) VALUES(4,3)";

mysql_query($insertFavouritesOne);
mysql_query($insertFavouritesTwo);
mysql_query($insertFavouritesThree);
mysql_query($insertFavouritesFour);
mysql_query($insertFavouritesFive);
mysql_query($insertFavouritesSix);

echo "Data Inserted!";

mysql_close();
?>