//工具类
define(['jquery'], function ($) {

    var dateExtend = function (dateValue,minusnum) {
    	//格式要求是: "2013-01-01 01:01:00";
    	var addnum = Math.abs(minusnum);
        var createDT = new Date(); //创建时间
        createDT.setFullYear(dateValue.substr(0, 4));
        createDT.setMonth(dateValue.substr(5, 2)-1);
        createDT.setDate(dateValue.substr(8, 2));
        createDT.setHours(dateValue.substr(11, 2));
        var minu = dateValue.substr(14, 2);
        if(Number(minusnum) >=0){        	
        	createDT.setMinutes(Number(minu) + Number(minusnum));
        }else{
        	createDT.setMinutes(Number(minu) - addnum);
        }        
        createDT.setSeconds(0);
        var month=createDT.getMonth()+1;
        var strdate = createDT.getFullYear() + "-" + month + "-" + createDT.getDate() + " " + createDT.getHours() + ":" + createDT.getMinutes() + ":" + createDT.getSeconds();
        return strdate;
    };   
    
    return {
    	dateExtend: dateExtend
    };

});