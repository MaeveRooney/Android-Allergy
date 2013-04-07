<?php
/**
 * File to handle all API requests
 * Accepts GET and POST
 *
 * Each request will be identified by TAG
 * Response will be JSON data

  /**
 * check for POST request
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    require_once 'userFunctions.php';
    $db = new userFunctions();

    // response Array
    $response = array("tag" => $tag, "success" => 0, "error" => 0);

    // check for tag type
    if ($tag == 'login') {
        // Request type is check Login
        $email = $_POST['email'];
        $password = $_POST['password'];
        $username = $_POST['username'];

        // check for user
        $user = $db->getUserByEmailAndPassword($email, $password);
        if ($user != false) {
            // user found
            // echo json with success = 1
            $response["success"] = 1;
            $response["user"]["id"] = $user["id"];
            $response["user"]["username"] = $user["username"];
            $response["user"]["email"] = $user["email"];
            $response["user"]["wheatAllergy"] = $user["wheatAllergy"];
            $response["user"]["glutenAllergy"] = $user["glutenAllergy"];
            $response["user"]["dairyAllergy"] = $user["dairyAllergy"];
            $response["user"]["nutAllergy"] = $user["nutAllergy"];
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    } else if ($tag == 'register') {
        // Request type is Register new user
        $username = $_POST['username'];
        $email = $_POST['email'];
        $password = $_POST['password'];
        $wheat = $_POST['wheat'];
        $gluten = $_POST['gluten'];
        $dairy = $_POST['dairy'];
        $nut = $_POST['nut'];

        // check if user is already existed
        if ($db->isEmailExisted($email)) {
            // user is already existed - error response
            $response["error"] = 2;
            $response["error_msg"] = "Email already registered";
            echo json_encode($response);
        } else if ($db->isUsernameExisted($username)) {
            // user is already existed - error response
            $response["error"] = 3;
            $response["error_msg"] = "Username in use";
            echo json_encode($response);
        } else {
            // store user
            $user = $db->storeUser($username, $email, $password, $wheat, $gluten, $dairy, $nut);
            if ($user) {
                // user stored successfully
                $response["success"] = 1;
                $response["user"]["id"] = $user["id"];
                $response["user"]["username"] = $user["username"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["wheatAllergy"] = $user["wheatAllergy"];
                $response["user"]["glutenAllergy"] = $user["glutenAllergy"];
                $response["user"]["dairyAllergy"] = $user["dairyAllergy"];
                $response["user"]["nutAllergy"] = $user["nutAllergy"];
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in Registration";
                echo json_encode($response);
            }
        }
    } else if ($tag == 'checkusername') {
		$username = $_POST['username'];

         if ($db->isUsernameExisted($username)) {
            // user is already existed - error response
            $response["error"] = 3;
            $response["error_msg"] = "Username in Use";
            echo json_encode($response);
        } else {
        	$response["success"] = 1;
        	echo json_encode($response);
        }
    } else if ($tag == 'checkemail') {
		$email = $_POST['email'];

        if ($db->isEmailExisted($email)) {
            // user is already existed - error response
            $response["error"] = 2;
            $response["error_msg"] = "Email already registered";
            echo json_encode($response);
        } else {
        	$response["success"] = 1;
        	echo json_encode($response);
        }
    } else if ($tag == "changedetails") {
    	$id = $_POST['id'];
    	$username = $_POST['username'];
        $email = $_POST['email'];
        $wheat = $_POST['wheat'];
        $gluten = $_POST['gluten'];
        $dairy = $_POST['dairy'];
        $nut = $_POST['nut'];

        $user = $db->changeUserDetails($id, $username, $email, $wheat, $gluten, $dairy, $nut);
            if ($user) {
                // user stored successfully
                $response["success"] = 1;
                $response["user"]["id"] = $user["id"];
                $response["user"]["username"] = $user["username"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["wheatAllergy"] = $user["wheatAllergy"];
                $response["user"]["glutenAllergy"] = $user["glutenAllergy"];
                $response["user"]["dairyAllergy"] = $user["dairyAllergy"];
                $response["user"]["nutAllergy"] = $user["nutAllergy"];
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in changing details";
                echo json_encode($response);
            }
    } else if ($tag == "changepassword") {
    	$id = $_POST['id'];
    	$password = $_POST['password'];

    	$user = $db->changePassword($id, $password);
            if ($user) {
            	$response["success"] = 1;
            	echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in changing password";
                echo json_encode($response);
            }
    } else if ($tag == 'checkpassword') {
        // Request type is check Login
        $id = $_POST['id'];
        $password = $_POST['password'];

        // check for user
        $user = $db->getUserByIdAndPassword($id, $password);
        if ($user != false) {
            // user found
            // echo json with success = 1
            $response["success"] = 1;
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    } else if ($tag == 'getreviews') {
        // Request type is check Login
        $userID = $_POST['userID'];
        $reviews = $db->getUsersReviews($userID);
        if ($reviews != false) {
            $response["success"] = 1;
            $response["array"]=$reviews;
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "error getting reviews";
            echo json_encode($response);
        }
    } else {
        echo "Invalid Request";
    }
} else {
    echo "Access Denied";
}
?>