<?php
	header("Content-Type: text/html; charset=utf-8");
	
	////////// GET //////////////
	
	// http://www.cuiweiyou.com/sharepoint/test.php?url=cuiweiyou&age=28
	if ( is_array ($_GET) && count ($_GET) > 0 ) { //判断是否有Get参数 
		$url = isset($_GET['url']) ? $_GET['url'] : '';
		file_put_contents("netpal_list.txt", $url.PHP_EOL, FILE_APPEND);
		echo "1";
	} else {
		
		////////// POST /////////////
		
		$str = file_get_contents("php://input");  
		$arr = array();
		parse_str($str, $arr); // 把str的字符串数据拆分到arr数组
		$url = $arr['url'];
		file_put_contents("netpal_list.txt", $url.PHP_EOL, FILE_APPEND);
		echo "1";
	}
?> 