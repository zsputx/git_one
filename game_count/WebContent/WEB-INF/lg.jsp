<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../game_count/bdres/lib/jquery1.9.js"></script>
<script type="text/javascript" >

function send(a){

	$.ajax({
		type : "POST",
		url :a.url,
		data : JSON.stringify(a.data),
		contentType : "application/json",
		dataType : "json",
        complete:function(msg) {
         a.action(msg);
		},
		error:function(e){
          a.error(e)
			}
	   
	})


	}


</script>
<title>登录</title>
</head>
<body style="text-align: center" >
<form action='./add.do'  method="post">

   <input id="name"  name='username'  type="text"/>
   <input id=password  name='password' type="text" />
   <input type="submit"   value="login" />

</form>


</body>
</html>