
require(['config'], function (config) {
    var Login = {
        username: $('#username'),
        password: $('#password'),
        errorInfo: {
            error1: '用户名密码不正确'
        },
        /*
        * 以下为回车逻辑 最后一个回车登录
        * 回车函数 ，回车next focus
        * shift + 回车 up focus
        */
        keyMove: function(e, next, up){
            if (e.keyCode === 13){
                $(next).focus()
            }
            if (e.keyCode == 13 && e.shiftKey){
                $(up).focus()
            }
        },
        getNextInputId(id){
            let inputIdArr = Login.getInputIdArr()
            return inputIdArr[(inputIdArr.indexOf(id) + 1 + inputIdArr.length ) % inputIdArr.length]
        },
        getPrevInputId(id){
            let inputIdArr = Login.getInputIdArr()
            return inputIdArr[(inputIdArr.indexOf(id) - 1 + inputIdArr.length ) % inputIdArr.length]
        },
        getInputIdArr(){
            let inputIdArr = []
            $('.form-horizontal').find('.form-control').each(function(index, item){
                inputIdArr.push(item.id)
            })
            return inputIdArr
        },
        /*
        * validate逻辑
        */
        hasError: function (input) {
            input.parents('.form-group').addClass('has-error');
        },
        remError: function (input) {
            input.parents('.form-group').removeClass('has-error');
        },
        showError: function (error) {
            $('.text-danger').text(error).show();
        },
        hideError: function () {
            $('.text-danger').hide();
        },
        validate: function () {
            if ($.trim(this.username.val()) == '') {
                this.hasError(this.username);
            }
            if ($.trim(this.password.val()) == '') {
                this.hasError(this.password);
            }
            return this.username.val() && this.password.val();
        },
        login: function () {
            var self = this;
            if (this.validate()) {
                $.ajax({
                    url: config.basePath + '/loginCheck',
                    type: 'POST',
                    contentType: 'application/json',
                    dataType : "json",
                    data: JSON.stringify({
                        username: self.username.val(),
                    	password: self.password.val()
                    }),
                    success: function (msg) {
                        if(msg == "1"){
                            self.showError("成功");
                            // window.location.href = "./pages/index.html";
                            window.location.href = "./pages/articles/article.html";
                        }else{
                            self.showError("登录异常，请检查数据库");
                        }
                    },
                });
            }
        }
    };

    $('.form-horizontal .form-control').keypress(function (e) {
        if (Login.getNextInputId(e.target.id) === Login.getInputIdArr()[0] && !e.shiftKey){ // 最后一个回车提交
            console.log('提交')
        } else {
            Login.keyMove(e, '#' + Login.getNextInputId(e.target.id), '#' + Login.getPrevInputId(e.target.id));
        }
        
        // Login.remError($(this));
        // if (e.which == 13) {
        //     Login.hideError();
        //     Login.login();
        //     return false;
        // }
    });

    $('button[type="submit"]').on('click', function (e) {
        e.preventDefault();
        Login.hideError();
        Login.login();
    });

    //点击注册按钮
    $('#register').on('click', function (e) {
        window.location.href = "./pages/register.html";
        // $('#registerModal').modal('show');
    });
    //确认注册信息
    $('#registerConfirm').on('click', function (e) {
        // return
//        	alert($('#input_persion').val());
//        必填项验证
        $('#repairModalLabel .form-group').removeClass('has-error');
        try {
            $('#repairModalLabel').find('.form-control').each(function (i, dom) {
                if (dom.required && !dom.value) {
                    throw dom;
                }
            });
        } catch (e) {
            $(e).parent().addClass('has-error');
            // var input_persion=document.getElementById('input_persion').value;
            //if(input_persion==''){
            return;
            //}
        }
        var userNameR=document.getElementById('userNameR').value;
        var passwordR=document.getElementById('passwordR').value;
        var passwordRC=document.getElementById('passwordRC').value;
        var email=document.getElementById('email').value;
        var nickname=document.getElementById('nickname').value;
        params ={
            userNameR:userNameR,
            passwordR:passwordR,
            passwordRC:passwordRC,
            email:email,
            nickname:nickname,
        };
        $.post(config.basePath + '/register', params, function (data) {
            console.log(data)
            if (data === '1') {
                alert('成功');
                var el=document.getElementById('a1');
                el.target = '_new'; //指定在新窗口打开
                el.click();//触发打开事件
            }else if(data === '2'){
                alert('两次输入密码不一致!');
            }else if(data === '3'){
                alert('用户名已被占用!');
            }else{
                alert('失败!');
            }
        });
    });
    //已有账号，去登陆
    $('#goLogin').on('click', function (e) {
         window.location.href = "login.html";
    });
});