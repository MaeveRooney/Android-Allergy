<?
$user="maeveroo_Maeve";
$password="maeve2124";
$database="maeveroo_AllergyApp";
mysql_connect(localhost,$user,$password);
@mysql_select_db($database) or die( "Unable to select database");

$createUserTable="CREATE TABLE users (id int(6) NOT NULL auto_increment,username varchar(15) NOT NULL UNIQUE,
	email varchar(50) NOT NULL UNIQUE,password varchar(20) NOT NULL,wheatAllergy tinyint(1),
	glutenAllergy tinyint(1),dairyAllergy tinyint(1),nutAllergy tinyint(1),PRIMARY KEY (id))";
mysql_query($createUserTable);

$createRestaurantTable="CREATE TABLE restaurants (id int(6) NOT NULL auto_increment,name varchar(30) NOT NULL,
	address text UNIQUE,phone varchar(20) UNIQUE,restaurantEmail varchar(30) UNIQUE,GPSLongitude int(10),GPSLatitude int(10),
	wheatRating float(2,1),wheatNumVotes int(6),glutenRating float(2,1),glutenNumVotes int(6),dairyRating float(2,1),
	dairyNumVotes int(6),nutRating float(2,1),nutNumVotes int(6),overallRating float(2,1),numReviews int(6), PRIMARY KEY (id))";
mysql_query($createRestaurantTable);

$createReviewTable="CREATE TABLE reviews (id int(6) NOT NULL auto_increment,authorID int(6) NOT NULL,
	restaurantID int(6),date datetime,reviewText text,wheatRating int(1),glutenRating int(1),
	dairyRating int(1),nutRating int(1),overallRating int(1), PRIMARY KEY (id), FOREIGN KEY (authorID) REFERENCES users(id),
	FOREIGN KEY (restaurantID) REFERENCES restaurants(id))";
mysql_query($createReviewTable);

$createFavouritesTable="CREATE TABLE favourites (restaurantID int(6), userID int(6), FOREIGN KEY (restaurantID) REFERENCES restaurants(id),
	FOREIGN KEY (userID) REFERENCES users(id), PRIMARY KEY (restaurantID, userID))";
mysql_query($createFavouritesTable);

mysql_close();
?>