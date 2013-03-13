<?
$user="maeveroo_Maeve";
$password="maeve2124";
$database="maeveroo_AllergyApp";
mysql_connect(localhost,$user,$password);
@mysql_select_db($database) or die( "Unable to select database");

storeUser('johnsmith','example@email.com','secret1',0,0,1,0);
storeUser('johndoe','example2@email.com','secret1',0,1,1,0);
storeUser('janesmith','example3@email.com','secret1',1,0,0,0);
storeUser('fredwhite','example4@email.com','secret1',1,1,1,1);

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


     public function storeUser($name, $email, $password, $wheat, $gluten, $dairy) {
     	echo "working";
        $id = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
        $result = mysql_query("INSERT INTO users(id, name, email, encrypted_password, salt, wheatAllergy, glutenAllergy, dairyAllergy, nutAllergy) VALUES('$id','$username', '$email', '$encrypted_password', '$salt','$wheat','$gluten','$dairy','$nut'");
        // check for successful store
        if ($result) {
        	echo "success";
            // get user details
            $uid = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM users WHERE id = '$id'");
            // return user details
            return mysql_fetch_array($result);
        } else {
        	echo "fail";
            return false;
        }
    }

 /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {

        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

echo "Data Inserted! Boom";

mysql_close();
?>