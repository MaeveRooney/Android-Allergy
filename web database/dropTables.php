<?
$user="maeveroo_Maeve";
$password="maeve2124";
$database="maeveroo_AllergyApp";
mysql_connect(localhost,$user,$password);
@mysql_select_db($database) or die( "Unable to select database");

mysql_query('DROP TABLE IF EXISTS `maeveroo_AllergyApp`.`users`') or die(mysql_error());
mysql_query('DROP TABLE IF EXISTS `maeveroo_AllergyApp`.`restaurants`') or die(mysql_error());
mysql_query('DROP TABLE IF EXISTS `maeveroo_AllergyApp`.`reviews`') or die(mysql_error());
mysql_query('DROP TABLE IF EXISTS `maeveroo_AllergyApp`.`favourites`') or die(mysql_error());

echo "tables dropped";

mysql_close();
?>