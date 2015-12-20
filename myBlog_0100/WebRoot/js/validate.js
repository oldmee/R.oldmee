
function checkUserName(userName) {
	var xmlhttp;
	try {
		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	}
	catch (e) {
		try {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		catch (e) {
			try {
				xmlhttp = new XMLHttpRequest();
			}
			catch (e) {
			}
		}
	}
	
	if(userName==null || userName.length==0) {
		alert("用户名不能为空！");
		return;
	}
	
//创建请求,并使用escape对userName编码,以避免乱码
	xmlhttp.open("get", "LogonAction?action=validate&userName=" + escape(userName),true);
	
	xmlhttp.onreadystatechange = function () {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
        //根据responseText判断用户名是否存在
				if (xmlhttp.responseText == "1") {
					alert("对不起,用户名已经存在!");
				} else {
					alert("恭喜,该用户未被注册!");
				}
			} else {
				alert("网络链接失败!");
			}
		}
	};
	xmlhttp.send(null);
}

