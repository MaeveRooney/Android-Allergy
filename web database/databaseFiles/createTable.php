<?
$user="maeveroo_Maeve";
$password="maeve2124";
$database="maeveroo_AllergyApp";
mysql_connect(localhost,$user,$password);
@mysql_select_db($database) or die( "Unable to select database");

$createUserTable="CREATE TABLE users (id int(6) NOT NULL auto_increment,username varchar(15) NOT NULL UNIQUE,
	email varchar(50) NOT NULL UNIQUE,encrypted_password varchar(80) not null, salt varchar(10) not null,wheatAllergy tinyint(1),
	glutenAllergy tinyint(1),dairyAllergy tinyint(1),nutAllergy tinyint(1),PRIMARY KEY (id))";
mysql_query($createUserTable);

$createRestaurantTable="CREATE TABLE restaurants (id int(6) NOT NULL auto_increment,name varchar(30) NOT NULL,
	address text,phone varchar(20),restaurantEmail varchar(30),GPSLatitude varchar(20),GPSLongitude varchar(20),
	wheatRating varchar(20),wheatNumVotes varchar(20),glutenRating varchar(20),glutenNumVotes varchar(20),dairyRating varchar(20),
	dairyNumVotes varchar(20),nutRating varchar(20),nutNumVotes varchar(20),overallRating varchar(20),
	overallNumVotes varchar(20), numFavourites varchar(20),imageURL varchar(100), PRIMARY KEY (id))";
mysql_query($createRestaurantTable);

/**
$alterRestaurantTable="alter table restaurants add unique index(name, GPSLongitude, GPSLatitude)";
mysql_query($alterRestaurantTable);
**/

$createReviewTable="CREATE TABLE reviews (id int(6) NOT NULL auto_increment,authorID int(6) NOT NULL,
	restaurantID int(6) NOT NULL,date datetime,reviewText text,wheatRating varchar(20),glutenRating varchar(20),
	dairyRating varchar(20),nutRating varchar(20),overallRating varchar(20), PRIMARY KEY (id), FOREIGN KEY (authorID) REFERENCES users(id),
	FOREIGN KEY (restaurantID) REFERENCES restaurants(id))";
mysql_query($createReviewTable);

/**
$alterReviewTable="alter table reviews add unique index(authorID, restaurantID)";
mysql_query($alterReviewTable);
**/

$createFavouritesTable="CREATE TABLE favourites (restaurantID int(6), userID int(6), FOREIGN KEY (restaurantID) REFERENCES restaurants(id),
	FOREIGN KEY (userID) REFERENCES users(id), PRIMARY KEY (restaurantID, userID))";
mysql_query($createFavouritesTable);

echo "tables created";

mysql_close();
?>