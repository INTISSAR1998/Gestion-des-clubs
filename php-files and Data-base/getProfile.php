<?php
include 'connect.php';
if($_SERVER['REQUEST_METHOD']=='POST') {
    $id = $_POST['id'];

    $idm = new \MongoDB\BSON\ObjectId($id);

    $qr = new \MongoDB\Driver\Query(['_id' => $idm]);

    $rows = $mng->executeQuery('ClubApp.users', $qr);

    foreach ($rows as $row) {
        //echo"$row->_id";
        $rst["name"] = "$row->name";
        $rst["email"] = "$row->email";
        $rst["age"] = "$row->age";
		$rst["class"] = "$row->class";
		$rst["description"] = "$row->description";
    }
    echo json_encode($rst);
}
?>