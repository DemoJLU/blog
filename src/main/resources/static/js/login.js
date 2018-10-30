//js代码
//找到XX元素，一般给元素加id
// yuansuojb=document.getElementById('loginBtn');
// //给xx元素加事件
// yuansuojb.onclick=function(){
//     //代码段
//     var str="hello world !!!";
//     alert(str);
// }
$("#loginBtn").click(function(){
    var username = $('#username').val();
    var password = $('#password').val();
    var str="hello world !!!";
    alert(str);
    $.post('/login/loginCheck',
        { username: username},
        { password: password}
        )
})
