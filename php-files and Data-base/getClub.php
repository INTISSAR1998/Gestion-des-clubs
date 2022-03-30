<?php
include"connect.php";
$qr = new \MongoDB\Driver\Query();
$rows = $mng->executeQuery('ClubApp.clubs', $qr);
$club_array=array();
foreach ($rows as $row) {
    $club_array[]=$row;
}
echo json_encode($club_array);
?>