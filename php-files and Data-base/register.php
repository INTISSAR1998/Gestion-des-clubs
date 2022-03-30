<?php
include 'connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];
	$age = $_POST['age'];
	$class = $_POST['class'];
	$description = $_POST['description'];
    $password = password_hash($password, PASSWORD_DEFAULT);

    $qr = new \MongoDB\Driver\Query(['email' => $email]);
    $bw = new \MongoDB\Driver\BulkWrite();

    $rows = $mng->executeQuery('ClubApp.users', $qr);

    $result = 0;
    foreach ($rows as $row) {
        if (($row->email) == $email) {
            $result = 2;
            break;
        }
    }

    if ($result == 0) {
        $doc = ["name" => "$name", "email" => "$email", "password" => "$password", "age" => "$age", "class" => "$class", "description" => "$description",];
        $bw->insert($doc);
        $mng->executeBulkWrite('ClubApp.users', $bw);
        $result = 1;
    }
    $rst["success"] = "$result";
    echo json_encode($rst);
}