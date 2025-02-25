<?php

class userFunctions {

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
     * Storing new user
     * returns user details
     */
    public function storeUser($username, $email, $password, $wheat, $gluten, $dairy, $nut) {
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
        $result = mysql_query("INSERT INTO users(username, email, encrypted_password, salt, wheatAllergy, glutenAllergy, dairyAllergy, nutAllergy) VALUES('$username', '$email', '$encrypted_password', '$salt','$wheat','$gluten','$dairy','$nut')");
        // check for successful store
        if ($result) {
            // get user details
            $result = mysql_query("SELECT * FROM users WHERE username = '$username'");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    public function changePassword($id, $password) {
    	$hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
        $result = mysql_query("UPDATE users SET encrypted_password='$encrypted_password', salt='$salt' WHERE id='$id'");
        if ($result) {
            // get user details
            $result = mysql_query("SELECT * FROM users WHERE id = '$id'");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    public function changeUserDeatils($id, $username, $email, $wheat, $gluten, $dairy, $nut) {
        $result = mysql_query("UPDATE users SET username='$username', email='$email', wheat='$wheat', gluten='$gluten', dairy='$dairy', nut='$nut' WHERE id='$id'");
        if ($result) {
            // get user details
            $result = mysql_query("SELECT * FROM users WHERE username = '$username'");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {
        $result = mysql_query("SELECT * FROM users WHERE email = '$email'") or die(mysql_error());
        // check for result
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            $salt = $result['salt'];
            $encrypted_password = $result['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
    }

    /**
     * Get user by username and password
     */
    public function getUserByUsernameAndPassword($username, $password) {
        $result = mysql_query("SELECT * FROM users WHERE username = '$username'") or die(mysql_error());
        // check for result
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            $salt = $result['salt'];
            $encrypted_password = $result['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
    }

    /**
     * Get user by id and password
     */
    public function getUserByIdAndPassword($id, $password) {
        $result = mysql_query("SELECT * FROM users WHERE id = '$id'") or die(mysql_error());
        // check for result
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            $salt = $result['salt'];
            $encrypted_password = $result['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
    }

    /**
     * Check user is existed or not
     */
    public function isEmailExisted($email) {
        $result = mysql_query("SELECT email from users WHERE email = '$email'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed
            return true;
        } else {
            // user not existed
            return false;
        }
    }

	public function isUsernameExisted($username) {
        $result = mysql_query("SELECT email from users WHERE username = '$username'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed
            return true;
        } else {
            // user not existed
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

    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {

        $hash = base64_encode(sha1($password . $salt, true) . $salt);

        return $hash;
    }

        /**
     * Get user by email and password
     */
    public function getUsersReviews($userID) {
        $uid = (int)$userID;
        $result = mysql_query("SELECT * FROM reviews WHERE authorID = '$uid'") or die(mysql_error());
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $return_arr = array();
			while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
				$row_array['id'] = $row['id'];
				$row_array['authorID'] = $row['authorID'];
				$row_array['restaurantID'] = $row['restaurantID'];
				$row_array['reviewText'] = $row['reviewText'];
				$row_array['wheatRating'] = $row['wheatRating'];
				$row_array['glutenRating'] = $row['glutenRating'];
				$row_array['dairyRating'] = $row['dairyRating'];
				$row_array['nutRating'] = $row['nutRating'];
				$row_array['overallRating'] = $row['overallRating'];
				array_push($return_arr,$row_array);
			}
			return $return_arr;
        } else {
            return false;
        }
    }

}

?>