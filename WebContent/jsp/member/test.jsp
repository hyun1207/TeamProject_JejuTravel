<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="EUC-KR">
<title>Insert title here</title>

<link rel="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">

<script src="https://kit.fontawesome.com/d42fd504d6.js" crossorigin="anonymous"></script>


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
	
<script type="text/javascript">
$(document).ready(function(){
    $('.main i').on('click',function(){
        $('input').toggleClass('active');
        if($('input').hasClass('active')){
            $(this).attr('class',"fa fa-eye-slash fa-lg")
            .prev('input').attr('type',"text");
        }else{
            $(this).attr('class',"fa fa-eye fa-lg")
            .prev('input').attr('type','password');
        }
    });
    
    
    var no = "${mem.no}";
    $('#btn_delete').ready(function(){
		$(location).attr('href','delete?no=' + no );
	});
	

	
	var select = $('select');
	
	$('option').each(function(){
		if($(this).val() == select){
			$(this).attr('checked','checked');
		}
	});
});
</script>
<style type="text/css">
div {
	margin: 15px;
}

label {
	display: inline-block;
	width: 150px;
}

input, select {
	border-radius: .25rem;
	border-color: #ddd;
	width: 300px;
	height: 30px;
	margin: 10px;
}

input[type="text"], input[type="password"], select {
	width: 190px;
	height: 30px;
}

.btn {
	width: 120px;
	height: 40px;
	border: 1px solid transparent;
	padding: .375rem .75rem;
	font-size: 1rem;
	border-radius: .25rem;
	transition: color .15s;
}

.btn_p {
	color: #fff;
	background-color: #0d6efd;
	border-color: #0d6efd;
}

.btn_cancle {
	color: #fff;
	background-color: #ccc;
	border-color: #ccc;
}
</style>
</head>
<body>

<h2>회원정보</h2>
	<h4>상세보기</h4>
	<hr>
	<form action="update">
		<div>
			<label>아이디</label><input type="text" name="id" value="${mem.id}"
				disabled />

		</div>
<div class="main">
<label>비밀번호</label> 
  <input type="password" placeholder="비밀번호를 입력하세요">
  <i class="fa fa-eye fa-lg"></i>
 

</div>
<div>
			<label>이름</label><input type="text" name="name" value="${mem.name}">
		</div>
		<div>
			<label>비밀번호 힌트</label> <select name="hint" value="${mem.hint}">
				<option value="school">본인이 졸업한 초등학교는?</option>
				<option value="city">본인이 태어난 도시는?</option>
				<option value="food">본인이 가장 좋아하는 음식은?</option>
			</select>
		</div>
		<div>
			<label>답변</label><input type="text" name="answer"
				value="${mem.answer}">
		</div>
		<div>
			<label>전화번호</label><input type="text" name="phone"
				value="${mem.phone}">
		</div>
		<div>
			<label>이메일</label><input type="text" name="email"
				value="${mem.email}">
		</div>
		<input type="submit" value="수정" class="btn btn_p" /> <input
			type="button" value="탈퇴" class="btn btn_cancle" id="btn_delete" /> <input
			type="text" name="no" value="${mem.no}" hidden="hidden">


	</form>


	<div class="main">
		<input type="password" placeholder="비밀번호를 입력하세요"> <i
			class="fa fa-eye fa-lg"></i>

	</div>
</body>
</html>