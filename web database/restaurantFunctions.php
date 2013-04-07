<?php

class restaurantFunctions {

    private $db;

    //put your code here
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }

    // destructor
    function __destruct() {

    }

    /**
     * Storing new restaurant
     * returns restaurant details
     */
    public function storeRestaurant($name, $email, $address, $GPSLatitude, $GPSLongitude, $phone, $wheat, $gluten, $dairy, $nut, $overall) {
        $insertString = "INSERT INTO restaurants(name, GPSLatitude, GPSLongitude, restaurantEmail,
        			address, phone, wheatRating, wheatNumVotes, glutenRating, glutenNumVotes, dairyRating, dairyNumVotes
        			, nutRating, nutNumVotes, overallRating, overallNumVotes, numFavourites)";
        $valuesString = " VALUES('$name', '$GPSLatitude','$GPSLongitude', '$email', '$address', '$phone', '$wheat', '1',
        				'$gluten', '1', '$dairy', '1', '$nut', '1', '$overall', '1', '0')";

        $queryString = $insertString.$valuesString;
        $result = mysql_query($queryString);
        // check for successful store
        if ($result) {
            // get restaurant details
            $uid = mysql_insert_id();// last inserted id
            $result = mysql_query("SELECT * FROM restaurants WHERE id = '$uid'");
            // return restaurant details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    public function addReviewToRestaurant($id, $wheat, $wheatNum, $gluten, $glutenNum, $dairy, $dairyNum, $nut, $nutNum, $overall, $overallNum){
    	$uid = (int)$id;
    	$queryString="UPDATE restaurants SET";
    	if ($wheat != "0.0"){
         	$queryString.= " wheatRating='$wheat', wheatNumVotes='$wheatNum'";
         }
         if ($gluten != "0.0"){
         	$queryString.= " glutenRating='$gluten', glutenNumVotes='$glutenNum'";
         }
         if ($dairy != "0.0"){
         	$queryString.= " dairyRating='$dairy', dairyNumVotes='$dairyNum'";
         }
         if ($nut != "0.0"){
         	$nutNumVotes = int($nutNum);
         	$queryString.= " nutRating='$nut', nutNumVotes='$nutNum'";
         }
         if ($overall != "0.0"){
         	$queryString.= " overallRating='$overall', overallNumVotes='$overallNum'";
         }
        $queryString .= "WHERE id='$uid'";
        $result = mysql_query($queryString);
        // check for successful store
        if ($result) {
            // get restaurant details
            $result = mysql_query("SELECT * FROM restaurants WHERE id = '$id'");
            // return restaurant details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    public function storeReview($authorID, $restaurantID, $wheat, $gluten, $dairy, $nut, $overall, $reviewText) {
        $nowFormat = date('Y-m-d H:i:s');
        $uid = (int)$authorID;
        $rid = (int)$restaurantID;
        $insertString = "INSERT INTO reviews(authorID,restaurantID,date";
        $valuesString = " VALUES('$uid','$rid','$nowFormat'";
        if ($wheat != "0.0"){
         	$insertString.=", wheatRating";
         	$valuesString.=", '$wheat'";
         }
         if ($gluten != "0.0"){
         	$insertString.=", glutenRating";
         	$valuesString.=", '$gluten'";
         }
         if ($dairy != "0.0"){
         	$insertString.=", dairyRating";
         	$valuesString.=", '$dairy'";
         }
         if ($nut != "0.0"){
         	$insertString.=", nutRating";
         	$valuesString.=", '$nut'";
         }
         if ($overall != "0.0"){
         	$insertString.=", overallRating";
         	$valuesString.=", '$overall'";
         }
         if ($reviewText != ""){
         	$insertString.=", reviewText";
         	$valuesString.=", '$reviewText'";
         }

         $insertString.=")";
         $valuesString.=")";
         $queryString = $insertString.$valuesString;
        $result = mysql_query($queryString);
        // check for successful store
        if ($result) {
            // get restaurant details
            $uid = mysql_insert_id();// last inserted id
            $result = mysql_query("SELECT * FROM reviews WHERE id = '$uid'");
            // return restaurant details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    public function getUserFavourites($userID) {
		$uid = (int)$userID;
        $result = mysql_query("SELECT * FROM favourites WHERE userID = '$uid'") or die(mysql_error());
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $return_arr = array();
			while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
				$row_array['userID']=$row['userID'];
				$row_array['restaurantID'] = $row['restaurantID'];
				array_push($return_arr,$row_array);
			}
			return $return_arr;
        } else {
            return false;
        }
    }

    public function addToFavourites($userID, $restaurantID) {
		$uid = (int)$userID;
		$rid = (int)$restaurantID;
		$queryString = "INSERT INTO favourites(userID,restaurantID) VALUES('$uid','$rid')";
        $result = mysql_query($queryString) or die(mysql_error());
        if ($result) {
            $result = mysql_query("SELECT * FROM favourites WHERE userID = '$uid' AND restaurantID = '$rid'");
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    public function removeFromFavourites($userID, $restaurantID) {
		$uid = (int)$userID;
		$rid = (int)$restaurantID;
		$queryString = "DELETE FROM favourites WHERE userID = '$uid' AND restaurantID = '$rid'";
        $result = mysql_query($queryString) or die(mysql_error());
        if ($result) {
            return true;
        } else {
            return false;
        }
    }

    public function changeFavouriteCount($restaurantID, $numFavourites) {
		$rid = (int)$restaurantID;
		$queryString="UPDATE restaurants SET numFavourites = '$numFavourites' WHERE id = '$rid'";
        $result = mysql_query($queryString) or die(mysql_error());
        if ($result) {
            return true;
        } else {
            return false;
        }
    }

}

?>