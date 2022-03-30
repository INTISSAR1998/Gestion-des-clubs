<?php
include 'connect.php';
if($_SERVER['REQUEST_METHOD']=='POST') {
    $email = $_POST['email'];
    $password = $_POST['password'];

    $qr = new \MongoDB\Driver\Query(['email' => $email, 'password' => $password]);

    $rows = $mng->executeQuery('ClubApp.users', $qr);

    $result = 0;
    foreach ($rows as $row) {
        if ((($row->email) == $email) && (($row->password) == $password)) {
            $result = 1;
            break;
        }
    }

    $rst["success"] = "$result";
    echo json_encode($rst);
}
?>