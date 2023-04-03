$(document).ready(function(e){
	$("#loginForm").submit(function(e){
		var userId = $("#userId").val();
		var userPwd = $("#userPwd").val();

		if(userId == null || userId == "") {
			alert("아이디를 입력하세요.");
			$("#userId").focus();
			
			return false;
		} else if(userPwd == null || userPwd == "") {
			alert("패스워드를 입력하세요.");
			$("#userpwd").focus();

			return false;
		}
		
		$.ajax({
			  type: "POST",
			  url: "/login/encodePwd.json",
			  async : false,
			  dataType: "json",
			  success: function(data) {
				  var rsaKey = new RSAKey();
				  
				  rsaKey.setPublic(data.pModules, data.pExponent);
				  $("#enUserPwd").val(rsaKey.encrypt(userPwd));
				  
				  $("#userPwd").val("");
				  userPwd = null;
			  },
			  error : function(jqXHR, textStatus, errorThrown){
					console.log("FAIL");
			  }
		});
		
		return true;
	});
});
