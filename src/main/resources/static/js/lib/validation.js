//验证框验证
var  errInfo=""
//验证是不是纯数字组成
function   verifyNumb(a){
	if(a==""){
		 errInfo="输入数字类型数据";
		 return errInfo;
	}
	 var  reg = /^[0-9]*$/;
	 if(!reg.test(a)){
		 errInfo="请输入数字类型的数据!";
		 return errInfo;
	 }else{
		 return true;
	 }
}
function   checkNumb(a){
	if(a==""){
		 errInfo="输入数字";
		 return errInfo;
	}
	 var  reg =  /^[1-9]+[0-9]*]*$/ ;
	 if(!reg.test(a)){
		 errInfo="请输出大于零的数字";
		 return errInfo;
	 }else{
		 return true;
	 }
}
//验证是不是有数字和字母组成
function  checkRate(a){
	    if(a=="" || a==null){
	      errInfo="请输入数字和字母";
		  return errInfo;
	   }
	     var re =  /^[0-9a-zA-Z]*$/g;  //判断字符串是否为数字和字母组合     //判断正整数 /^[1-9]+[0-9]*]*$/    
	     if (!re.test(a)){  
	    	errInfo="由数字或字母或者数字和字母组合";
	        return errInfo;  
	     }else{  
	    return true;  
	     }  
}  
	   //判断是否为纯汉字
   function checkName(a){
	   if(a=="" || a==null){
		      errInfo="不能为空";
			  return errInfo;
	   }
	   var regex =/^[\u4E00-\u9FA5]+$/;  
	   if(!regex.test(a)){ 
		  errInfo="请输入汉字"
	   return errInfo;  
	   }else{  
	   return true;  
	             }  
	        }  

//验证手机号码
function checkPhone(a){ 
    if(!(/^1[34578]\d{9}$/.test(a))){ 
        errInfo="手机号码有误，请重填";  
        return errInfo; 
    } else{
		return true;
	}
}


//验证固定电话
function checkTel(a){
	if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(a)){
   errInfo='固定电话有误，请重填';
	return errInfo;
	}else{
		return true;
	}
}

//验证邮政编码

function checkCode(a){
	    if (a != "") {   //邮政编码判断
	          var pattern = /^[0-9]{6}$/;
	          flag = pattern.test(a);
	          if (!flag) {
	                errInfo="非法的邮政编码！";
	                return errInfo;
	                }else{
	              	return true;
	            	}
	            }
	}
//验证邮箱
function checkEmail(a){
            if(a!=""){
            	var pattern =/^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/;
                if(!pattern.exec(a)){
                	errInfo='请输入正确的邮箱地址';
                	return errInfo;
                }else{
                	return true;
                }
            }
}


/**
* 验证输入身份证号
* 
* @param 待验证的字符串
* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
*/
function IsIDcard(a) {
   var  regex = "(^\\d{18}$)|(^\\d{15}$)";
  
}


function isBankCard(a){
	if (a != "") {   //邮政编码判断
		var reg = /^(\d{16}|\d{19})$/;
        flag = reg.test(a);
        if (!flag) {
              errInfo="银行卡号不正确，请验证查看！";
              return errInfo;
              }else{
            	return true;
          	}
          }
}
/**
 * 验证传真
 * @param a
 * @returns
 */
function checkFax(a){
	if (a != "") {   
		var fax = /^(\d{3,4}-)?\d{7,8}$/;
        flag = fax.test(a);
        if (!flag) {
              errInfo="传真格式为:XXX-12345678或XXXX-1234567或XXXX-12345678";
              return errInfo;
              }else{
            	return true;
          	}
          }
}
