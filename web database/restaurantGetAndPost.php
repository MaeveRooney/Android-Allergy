<?php
 /**
 * check for POST request
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    require_once 'restaurantFunctions.php';
    $db = new restaurantFunctions();

    // response Array
    $response = array("tag" => $tag, "success" => 0, "error" => 0);

    // check for tag type
    if ($tag == 'restaurant') {
        $name= $_POST['name'];
        $email= $_POST['email'];
        $GPSLatitude= $_POST['latitude'];
        $GPSLongitude= $_POST['longitude'];
        $phone= $_POST['phone'];
        $address= $_POST['address'];
        $wheat= $_POST['wheat'];
        $gluten= $_POST['gluten'];
        $dairy= $_POST['dairy'];
        $nut= $_POST['nut'];
        $overall= $_POST['overall'];

		$restaurant = $db->storeRestaurant($name, $email, $address, $GPSLatitude, $GPSLongitude, $phone, $wheat, $gluten, $dairy, $nut, $overall);
        if ($restaurant) {
                // restaurant stored successfully
                $response["success"] = 1;
                $response["restaurant"]["id"] = $restaurant["id"];
                echo json_encode($response);
            } else {
                // restaurant failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error storing restaurant";
                echo json_encode($response);
            }

    } else if ($tag == 'review') {
    	$authorID= $_POST['userID'];
    	$restaurantID= $_POST['restaurantID'];
    	$wheat= $_POST['wheat'];
        $gluten= $_POST['gluten'];
        $dairy= $_POST['dairy'];
        $nut= $_POST['nut'];
        $overall= $_POST['overall'];
    	$reviewText= $_POST['reviewText'];

    	$review = $db->storeReview($authorID, $restaurantID, $wheat, $gluten, $dairy, $nut, $overall, $reviewText);
    	if ($review) {
                // review stored successfully
                $response["success"] = 1;
                $response["user"] = $authorID;
                $response["restaurant"] = $restaurantID;
                echo json_encode($response);
            } else {
                // review failed to store
                $response["error"] = 1;
                $response["user"] = $authorID;
                $response["restaurant"] = $restaurantID;
                $response["error_msg"] = "Error adding review";
                echo json_encode($response);
            }

    } else if ($tag == 'alter') {
        $id= $_POST['id'];
        $wheat= $_POST['wheat'];
        $wheatNum= $_POST['wheatNum'];
        $gluten= $_POST['gluten'];
        $glutenNum= $_POST['glutenNum'];
        $dairy= $_POST['dairy'];
        $dairyNum= $_POST['dairyNum'];
        $nut= $_POST['nut'];
        $nutNum= $_POST['nutNum'];
        $overall= $_POST['overall'];
        $overallNum= $_POST['overallNum'];

        $restaurant = $db->addReviewToRestaurant($id, $wheat, $wheatNum, $gluten, $glutenNum, $dairy, $dairyNum, $nut, $nutNum, $overall, $overallNum);
		if ($restaurant) {
                // restaurant stored successfully
                $response["success"] = 1;
                $response["restaurant"]["id"] = $restaurant["id"];
                echo json_encode($response);
            } else {
                // restaurant failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in altering restaurant";
                echo json_encode($response);
            }
    } else if ($tag == 'favourites') {
        $userID= $_POST['userID'];

        $favourites = $db->getUserFavourites($userID);
		if ($favourites) {
                $response["array"] = $favourites;
                $response["success"] = 1;
                echo json_encode($response);
            } else {
                $response["error"] = 1;
                $response["id"] = $userID;
                $response["error_msg"] = "Error occured getting favourites";
                echo json_encode($response);
            }
    } else if ($tag == 'addfavourites') {
        $userID= $_POST['userID'];
        $restaurantID= $_POST['restaurantID'];

        $add = $db->addToFavourites($userID, $restaurantID);
		if ($add) {
                $response["success"] = 1;
                echo json_encode($response);
            } else {
                $response["error"] = 1;
                $response["id"] = $userID;
                $response["error_msg"] = "Error occured adding favourite";
                echo json_encode($response);
            }
    } else if ($tag == 'deletefavourites') {
        $userID= $_POST['userID'];
        $restaurantID= $_POST['restaurantID'];

        $delete = $db->removeFromFavourites($userID, $restaurantID);
		if ($delete) {
                $response["success"] = 1;
                echo json_encode($response);
            } else {
                $response["error"] = 1;
                $response["id"] = $userID;
                $response["error_msg"] = "Error occured removing favourite";
                echo json_encode($response);
            }
    } else if ($tag == 'changecount') {
        $restaurantID= $_POST['restaurantID'];
        $numFavourites= $_POST['numFavourites'];

        $increase = $db->changeFavouriteCount($restaurantID, $numFavourites);
		if (increase) {
                $response["success"] = 1;
                echo json_encode($response);
            } else {
                $response["error"] = 1;
                $response["id"] = $userID;
                $response["error_msg"] = "Error occured changing count";
                echo json_encode($response);
            }
    }

} else {
    echo "Access Denied";
}
?>