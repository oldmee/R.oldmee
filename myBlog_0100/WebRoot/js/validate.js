
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
		alert("�û�������Ϊ�գ�");
		return;
	}
	
//��������,��ʹ��escape��userName����,�Ա�������
	xmlhttp.open("get", "LogonAction?action=validate&userName=" + escape(userName),true);
	
	xmlhttp.onreadystatechange = function () {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
        //����responseText�ж��û����Ƿ����
				if (xmlhttp.responseText == "1") {
					alert("�Բ���,�û����Ѿ�����!");
				} else {
					alert("��ϲ,���û�δ��ע��!");
				}
			} else {
				alert("��������ʧ��!");
			}
		}
	};
	xmlhttp.send(null);
}

