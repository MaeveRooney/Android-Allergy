<?php

class DB_Connect {

    // constructor
    function __construct() {

    }

    // destructor
    function __destruct() {
        // $this->close();
    }

    // Connecting to database
    public function connect() {
        // connecting to mysql

        $user="maeveroo_Maeve";
		$password="maeve2124";
		$database="maeveroo_AllergyApp";
		$con = mysql_connect(localhost,$user,$password);
		// selecting database
		@mysql_select_db($database) or die( "Unable to select database");

        // return database handler
        return $con;
    }

    // Closing database connection
    public function close() {
        mysql_close();
    }

}

?>