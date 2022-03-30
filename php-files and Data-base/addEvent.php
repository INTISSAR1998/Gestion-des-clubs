<?php
if ($_SERVER['REQUEST_METHOD'] =='POST'){
    $name = $_POST['name'];
    $begin_date = $_POST['begin_date'];
	$end_date = $_POST['end_date'];
    $place = $_POST['place'];
	$description = $_POST['description'];
    require_once 'connect.php';
	
	$bw = new \MongoDB\Driver\BulkWrite();
    
	$doc = ["name" => "$name", "begin_date" => "$begin_date", "end_date" => "$end_date", "place" => "$place", "description" => "$description",];
        $bw->insert($doc);
        $mng->executeBulkWrite('ClubApp.events', $bw);
        
}
?>