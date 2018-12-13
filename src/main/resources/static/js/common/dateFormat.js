function formatDateYMD(date){
		var day = date.getDate();
		var month = date.getMonth() + 1;
		var year = date.getFullYear();//获得年、月、日
		if (month < 10) {
			month = "0" + month;
		}
		if (day < 10) {
			day = "0" + day;
		}
		var res = year + "-" + month + "-" + day;
		return res;
	}
	
	function formatDateYMDHM(date){
		var day = date.getDate();
		var month = date.getMonth() + 1;
		var year = date.getFullYear();//获得年、月、日
		if (month < 10) {
			month = "0" + month;
		}
		if (day < 10) {
			day = "0" + day;
		}
		var hour = date.getHours(); //获得小时、分钟、秒 
		var minute = date.getMinutes();
		if (minute < 10) //如果分钟只有1位，补0显示 
			minute = "0" + minute;
		var res = year + "-" + month + "-" + day + " " + hour + ":" + minute;
		return res;
	}
	
	// alert(formatDateBy(new Date().getTime(), 'yyyy-MM-dd HH:mm:ss'))
	var formatDateBy = function(time, format){
		if(time==null||time==""||time==undefined)
			return "";
	    var t = new Date(time);
	    var tf = function(i){return (i < 10 ? '0' : '') + i};
	    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
	        switch(a){
	            case 'yyyy':
	                return tf(t.getFullYear());
	                break;
	            case 'MM':
	                return tf(t.getMonth() + 1);
	                break;
	            case 'mm':
	                return tf(t.getMinutes());
	                break;
	            case 'dd':
	                return tf(t.getDate());
	                break;
	            case 'HH':
	                return tf(t.getHours());
	                break;
	            case 'ss':
	                return tf(t.getSeconds());
	                break;
	        }
	    })
	}
	//格式化时长  
	function calculateDuration(oldtime,newtime){
		var  diff = (newtime-oldtime)/1000;//得到秒差
		var result = parseInt(diff/3600)+" 小时 "+parseInt((diff%3600)/60)+" 分 "+parseInt((diff%3600)%60)+" 秒";
		return result
	}
	//计算日期相差几天
	function getDiffDays(strDateStart,strDateEnd){
	   var strSeparator = "-"; //日期分隔符
	   var oDate1;
	   var oDate2;
	   var iDays;
	   oDate1= strDateStart.split(strSeparator);
	   oDate2= strDateEnd.split(strSeparator);
	   var strDateS = new Date(oDate1[0] + "-" + oDate1[1] + "-" + oDate1[2]);
	   var strDateE = new Date(oDate2[0] + "-" + oDate2[1] + "-" + oDate2[2]);
	   iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数 

	   return iDays ;
	}