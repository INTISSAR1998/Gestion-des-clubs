<?php
try {
    $mng = new \MongoDB\Driver\Manager('mongodb://127.0.0.1:27017/');
}catch (MongoDB\Driver\Exception\ServerException $e){
    //echo "Erreur";
}
/*if($mng)
    echo "ok";*/
