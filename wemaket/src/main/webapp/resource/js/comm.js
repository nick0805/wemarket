var contextRoot = "/web";


var comAjax = function(url, param, callBack, options, async) {

	var returnVal ; 
	// var sendData = {"ID": "1","PWD":"1"};
	if (url == "" || url == undefined)
		return false;
	if (async == undefined)
		async = true;
	$.ajax({
		type : "post",
		datatype : "json",
		contentType : "application/json; UTF-8",
		url : url,
		data : JSON.stringify(param),
		async : async,
		success : function(data) {
			if (data.errorMsg == "") {
				// callback 이 함수인지를 확인 했으니까 함수호출합니다.
				if (typeof callBack === "function") {
					if (options == undefined) {
						callBack(data);
						return;	
					} else {
						data.options = options;
						callBack(data);
						return;
					}
				} else {
					
				}
			} else {

				//alert(data.errorMsg);.
				callBack(data);
				return;
			}
			
			returnVal = data;
		},
		error : function(req, status, error) {
			
			callBack(req);
			/*if (req.responseJSON.errorCode == "-9") {
				alert("세션이 만료되었습니다. 다시 로그인해 주세요.");
				top.location.reload();
				return;
			} else {
				alert("ERROR!!!");
				return;
			}
*/
		}

	});
 
	return returnVal;
};

$.fn.serializeObject = function () {
    "use strict";
    var result = {};
    var extend = function (i, element) {
        var node = result[element.name];
        if ('undefined' !== typeof node && node !== null) {
           if ($.isArray(node)) {
               node.push(element.value);
           } else {
               result[element.name] = [node, element.value];
           }
        } else {
            result[element.name] = element.value;
        }
    };
 
    $.each(this.serializeArray(), extend);
    return result;
};

var calcHeight = function() {
	// find the height of the internal page
	var the_height = 0;
	 	the_height = document.getElementById('ifrm').contentWindow.document.getElementById("content_inner").scrollHeight;
	// change the height of the iframe
	//the_height = $('#content_wrap').outerHeight();
	document.getElementById('ifrm').height = the_height;
}

var onlyNumber = function(event) {
	
	var key = event.which || event.keyCode;
	 
	if ((key >= 48 && key <= 57) || (key >= 96 && key <= 105)  || key == 110 || key == 8 || key == 9
			|| key == 13 || key == 46) {
		event.returnValue = true;
		return;
	}
	event.returnValue = false;
}

//숫자만 입력과  특수문자('.')만 허용한다.
var onlyNumDecimalInput = function(event) {
	var key = event.which || event.keyCode;
	if ((key >= 48 && key <= 57) || (key >= 96 && key <= 105)
			|| key == 110 || key == 190 || key == 8 || key == 9
			|| key == 13 || key == 46) {
		event.returnValue = true;
		return;
	}
	event.returnValue = false;
}

//키 이벤트 값으로 판단
var checkForNumber = function(event) {
 var key = event.which || event.keyCode;
  alert(key);
}
 
// 숫자만 입력과  특수문자('-','.',...)도 허용한다.
var numberNSp = function (event) {
	
  var key = event.which || event.keyCode;
   if((key > 31) && (key < 45) || (key > 57)) {
      event.returnValue = false;
   }
}
 
//한글 방지
var press_nonHan = function(event, obj){
	var key = event.which || event.keyCode;
	if ( key == 8 || key == 9 ||
		 key == 37 || key == 39 || key == 46) {
		//event.returnValue = true;
		return;
	}
	obj.value=obj.value.replace(/[^0-9]/g,'');
	//event.returnValue = false;
}

//한글 방지
var press_nonHanAndDot = function(event, obj){
	var key = event.which || event.keyCode;
	if ( key == 8 || key == 9 ||
		 key == 37 || key == 39 || key == 46) {
		//event.returnValue = true;
		return;
	}
	
	var objValue = obj.value.replace(/[^0-9.]/g,''); 
	obj.value = objValue;
	//event.returnValue = false;
}

//한글 방지
var press_numberAndComma = function(event, obj){
	var key = event.which || event.keyCode;
	
 
	if ( key == 8 || key == 9 ||
		 key == 37 || key == 39 || key == 46 || key == 118) {
		//event.returnValue = true;
		return;
	}
	
	var objValue = obj.value.replace(/[^0-9,]/g,''); 
	obj.value = objValue;
	//event.returnValue = false;
}
 
//한글 방지
var press_nonHanEmail = function(event, obj){
	var key = event.which || event.keyCode;
	if ( key == 8 || key == 9 ||
		 key == 37 || key == 39 || key == 46) {
		 return;
	}
	
	var objValue = obj.value.replace(/[^0-9a-zA-Z@.]/g,''); 
	obj.value = objValue;
 
}

//영문/숫자만 입력
var press_onlyEngNum = function(event, obj){
	var key = event.which || event.keyCode;
	if ( key == 8 || key == 9 ||
		 key == 37 || key == 39 || key == 46) {
		 return;
	}
	
	var objValue = obj.value.replace(/[^0-9a-zA-Z]/g,''); 
	obj.value = objValue;
 
}

//영문/숫자만 입력
var press_onlyEngHan = function(event, obj){
	var key = event.which || event.keyCode;
	if ( key == 8 || key == 9 ||
		 key == 37 || key == 39 || key == 46) {
		 return;
	}
	
	var objValue = obj.value.replace(/[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]/g,''); 
	obj.value = objValue;
 
}

function commify(obj) {
	
	var objVal = obj.value;
	objVal = replaceAll(objVal,",","");
    var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
    objVal += '';                          // 숫자를 문자열로 변환
    
    while (reg.test(objVal)){
    	objVal = objVal.replace(reg, '$1' + ',' + '$2');
    }
    
    obj.value = objVal; 
 
}


//url check 
var urlckeck = function(event, obj){
	var key = event.which || event.keyCode;
	if ( key == 8 || key == 9 ||
		 key == 37 || key == 39 || key == 46) {
		//event.returnValue = true;
		return;
	}
	obj.value=obj.value.replace(/[^0-9a-zA-Z/.:]/g,'');
	//event.returnValue = false;
}

var isValidFormPassword = function isValidFormPassword(pw) {
	 var check = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,16}$/;
	  
	 if (!check.test(pw))     {
	        return false;
	    }
	          
	 if (pw.length < 6 || pw.length > 16) {
	  return false;
	 }
	  
	    return true;
}
 
var replaceAll = function (str, searchStr, replaceStr) {
    return str.split(searchStr).join(replaceStr);
}
 
var dataCheck = function(startDt, endDt, mod){
  		
  var startDate = $("#" + startDt ).val();
  var endDate = $("#" + endDt ).val();
  if(startDate != "" && endDate != ""){
  	
  	
  	startDate = replaceAll(startDate, ".", "");
  	endDate = replaceAll(endDate, ".", "");
  	
      if(mod=="start"){
      	if(startDate > endDate){
      		alert("시작일보다 종료일이 작습니다.");
      		$("#" + startDt).val("");
      		return;
      	}	
      }
      
      if(mod=="end"){
      	if(startDate > endDate){
      		alert("시작일보다 종료일이 작습니다.");
      		$("#" + endDt).val("");
      		return;
      	}	
      }
  }
  		
}

var emailCheck = function(obj){
	
	var objValue = obj.value.replace(/[^0-9a-zA-Z@.]/g,''); 
	obj.value = objValue;
	
	var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;   
	if(obj.value != null && obj.value != ""){
		if(regex.test(obj.value) === false) {  
			alert("잘못된 이메일 형식입니다.");  
			obj.focus();
			obj.value = "";
		} else {
		}  
		
	}  
}

var jsonDataCheck = function(jsonObj){
	var jsonText = JSON.stringify(jsonObj, "\t");
	console.log(jsonText);
}

var jsonStingToObj = function(jsonString){
	
	var jsonObj = JSON.parse(jsonString);
	return jsonObj;
}

var closePop = function(){
	window.close();
}


var openPopupWindow = function(url, title, param , w, h) {
	
	var check = browserCheck();
	var newWindow = null;
 
	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
	var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;
	width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
	height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

	
	var left = ((width / 2) - (w / 2)) + dualScreenLeft;
	var top = ((height / 2) - (h / 2)) + dualScreenTop;
	window.open(url+"?"+param , title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
 
}

var browserCheck = function(){
	var agent = navigator.userAgent.toLowerCase();
    var browser = "";
	
    if(name === 'Microsoft Internet Explorer' || agent.indexOf('trident') > -1 || agent.indexOf('edge/') > -1) {
        browser = 'ie';
        if(name === 'Microsoft Internet Explorer') { // IE old version (IE 10 or Lower)
            agent = /msie ([0-9]{1,}[\.0-9]{0,})/.exec(agent);
            browser += parseInt(agent[1]);
        } else { // IE 11+
            if(agent.indexOf('trident') > -1) { // IE 11 
                browser += 11;
            } else if(agent.indexOf('edge/') > -1) { // Edge
                browser = 'edge';
            }
        }
    } else if(agent.indexOf('safari') > -1) { // Chrome or Safari
        if(agent.indexOf('opr') > -1) { // Opera
            browser = 'opera';
        } else if(agent.indexOf('chrome') > -1) { // Chrome
            browser = 'chrome';
        } else { // Safari
            browser = 'safari';
        }
    } else if(agent.indexOf('firefox') > -1) { // Firefox
        browser = 'firefox';
    }

    return browser; 
}

var ckBisNo = function (bisNo) {
	if(bisNo == undefined){ return false; }
	// 넘어온 값의 정수만 추츨하여 문자열의 배열로 만들고 10자리 숫자인지 확인합니다.
	if ((bisNo = (bisNo+'').match(/\d{1}/g)).length != 10) { return false; }
	
	// 합 / 체크키
	var sum = 0, key = [1, 3, 7, 1, 3, 7, 1, 3, 5];
	
	// 0 ~ 8 까지 9개의 숫자를 체크키와 곱하여 합에더합니다.
	for (var i = 0 ; i < 9 ; i++) { sum += (key[i] * Number(bisNo[i])); }
	
	// 각 8번배열의 값을 곱한 후 10으로 나누고 내림하여 기존 합에 더합니다.
	// 다시 10의 나머지를 구한후 그 값을 10에서 빼면 이것이 검증번호 이며 기존 검증번호와 비교하면됩니다.
	return (10 - ((sum + Math.floor(key[8] * Number(bisNo[8]) / 10)) % 10)) == Number(bisNo[9]);
    //return true;
}


var fileCheck = function(fileid, allowList, maxLength){
    var fileSize = getFileSize(fileid);
    
    if(fileSize == 0){
        alert("파일크기가 0mb입니다.");
	    return false;
    } else if(fileSize != 0 && fileSize > maxLength){
    	alert(maxLength + "mb를 초과했습니다.");
    	return false;
    }
    
    if(!fileExeCheck(fileid, allowList)){
        alert( allowList   +  ' 파일만 업로드 할수 있습니다.');
        return false;
    }
    
    return true;
}


var fileExeCheck =  function(fileid, allowList){
    var arrAloowList = allowList.split(",") ;
    var ext = $('#' + fileid).val().split('.').pop().toLowerCase();
    if($.inArray(ext, arrAloowList) == -1) {
         return false;
    }
    return true;
}	



var getFileSize = function(fileid) {
    try {
          var fileSize = 0;
          //for IE
          var browser = browserCheck();
       	  
          if (browser.indexOf("ie") > 0 &&  (browser.indexOf("11") < 0 || browser.indexOf("10") < 0 ) ) {
        	  //before making an object of ActiveXObject, 
              //please make sure ActiveX is enabled in your IE browser
                var objFSO = new ActiveXObject("Scripting.FileSystemObject"); 
                var filePath = $("#" + fileid)[0].value;
                var objFile = objFSO.getFile(filePath);
                fileSize = objFile.size; //size in kb
                fileSize = fileSize / 1024/1024; //size in mb  

          } else {
           //for FF, Safari, Opeara and Others
           fileSize = $("#" + fileid)[0].files[0].size //size in kb
           fileSize = fileSize / 1024/1024; //size in mb 
         }
          
          return fileSize;
         //alert("Uploaded File Size is" + fileSize + "MB");
    } catch (e) {
    	  return 0;
        //alert("Error is :" + e);
    }
}

var comma = function (num){
    var len, point, str; 
       
    num = num + ""; 
    point = num.length % 3 ;
    len = num.length; 
    
    str = num.substring(0, point); 
    while (point < len) { 
        if (str != "") str += ","; 
        str += num.substring(point, point + 3); 
        point += 3; 
    } 
     
    return str;
}

var addDate = function(yy,mm,dd,addMode,addNum){
 	 
	var dateArr = new Array();
	var selDate = new Date(yy,mm,dd);
	
	if(addMode == "yy"){
      selDate.setYear(parseInt(selDate.getFullYear()) + parseInt(addNum));
	}
 
	if(addMode == "mm"){
      selDate.setMonth(parseInt(selDate.getMonth()) + parseInt(addNum));
	}
	
	if(addMode == "dd"){
      selDate.setDay(parseInt(selDate.getDay()) + parseInt(addNum));
	}
	
  var yy = selDate.getFullYear();
  var mm = selDate.getMonth(); 
     if(addMode == "yy") mm = mm - 1;
     if(mm < 0) mm = 0;
  if(mm == 0){
  	yy = yy -1;
  	mm = 12;
  	if(addMode == "yy") mm = mm - 1;
  }
  
  mm = (mm < 10) ? '0' + mm : mm;
  var dd = selDate.getDate(); 
  dd = (dd < 10) ? '0' + dd : dd;
  

  
  dateArr[0] = yy + "." + mm + "." + dd;
  dateArr[1] = yy
  dateArr[2] = mm
  dateArr[3] = dd;
  
  return dateArr;
   
}

var searchAddress = function()
{
	var param = "";
	var w = 462;
	var h = 424;
	var title = "searchAddress";
	var url   = contextRoot + "/popup/addressSearch";
	openPopupWindow(url, title,param,w,h);
}

var searchAddress2 = function()
{
	var param = "";
	var w = 462;
	var h = 424;
	var title = "searchAddress2";
	var url   = contextRoot + "/popup/addressSearchPop";
	openPopupWindow(url, title,param,w,h);
}

var IsNumericData = function (strField)
{
    var i;
    var ch;
    var isNumeric = false;
    
    for (i = 0; i < strField.length; i++)
    {
        ch = strField.charAt(i);
        if ((ch >= '0') && (ch <= '9')){
        	  isNumeric = true;
        }
    }
    return isNumeric;
}

