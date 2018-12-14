
require(['config'], function (config) {

	require(['jquery', 'bootstrap', 'common/nav'], function ($, Bootstrap, nav) {
        function init () {
            var user = {};
            $.ajax({
                async : false,
                url : config.basePath + "/user/getRoleIDbyUser?_=" + new Date().getTime(),
                dataType : 'json',
                success : function(result) {
                    user.userId = result.userId;
                    user.userName = result.userName;
                    // user.departmentId=result.departmentId;
                    user.roles = result.roles;
                    user.menus = result.menus;
                    user.idxUrl = result.idxUrl;
                    window.self.user=user;
                    var validate = false;
                    if (result.menus) {
                        $('#navigation').empty();
                        $.each(result.menus, function(i, menu) {
                            var seMenu = $('<ul class="dropdown-menu"></ul>');
                            $.each(menu.children || [], function (idx, item) {
                                if (result.idxUrl === item.code) validate = true;
                                seMenu.append('<li><a href="#' + item.code + '" role="' + menu.code + '" data-toggle="tab">' + item.name + '</a></li>');
                            });
                            $('<li id="' + menu.code + '" class="dropdown"></li>')
                                .append('<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">' + menu.name + ' <span class="caret"></span></a>')
                                .append(seMenu).appendTo($('#navigation'));
                        });
                    }
                    if (!validate) {
                        user.idxUrl = result.menus[0] && result.menus[0].children[0] ? result.menus[0].children[0].code : '';
                    }
                    $('#userName').text(result.userName); // 显示登录用户
                    $('#quit').attr('href', config.basePath + '/logout');
                },
                error : function(msg) {
                    document.location.href = config.basePath;
                }
            });
            return user;
        }

        var user = init();

        $('a[data-toggle="tab"]').on('shown.bs.tab', function () {
            nav.createTab($(this).attr('href').substr(1), $(this).attr('role'), $(this).text());
        });

        $(".nav-tabs").on('click', 'i', function (e) {
            e.stopPropagation();
            nav.closeTab($(this).parent().attr('href').substr(1));
        });

        $('.nav-tabs').on('click', 'a', function (e) {
            $('#navigation li').removeClass('active');
            $('#navigation a[href="' + $(this).attr('href') + '"]').parents('li').addClass('active');
        });

        $('.navbar-nav .dropdown').hover(function () {
            $(this).addClass('open').siblings().removeClass('open');
        }, function () {
            $(this).removeClass('open');
        });

        nav.openTab(user.idxUrl);
        
        $('#userInfoModal').on('show.bs.modal', function () {
            $("#user-name").text(user.userName);
            $("#user-role").text(user.roles.map(function (role) {
                return role.role_name;
            }).join(','));
            $("#user-index").empty();
            $.each(user.menus, function (idx, menu) {
                $.each(menu.children, function (i, item) {
                    $('#user-index').append('<option value="' + item.code + '">' + item.name + '</option>')
                });
            });
            $('#user-index').val(user.idxUrl);
        });

        $("#subIdx").on('click', function (e) {
            e.preventDefault();
            //判断修改首页是否与当前首页相同
            if(user.idxUrl == $('#user-index').val()){
            	$('#updateModal').modal('show');
            }
            if (user.idxUrl != $('#user-index').val()) {
                $.ajax({
                    url: config.basePath + '/user/modifyIdxUrl',
                    type: 'POST',
                    data: $.param({
                        userId: user.userId,
                        idxUrl: $('#user-index').val()
                    }),
                    dataType: 'json',
                    success: function (result) {
                        if (result.code != 0) {
                            alert(result.msg);
                        } else {
                            user.idxUrl = $('#user-index').val();
                          //首页修改完成后自动刷新页面
                            window.location.reload();
                        }
                    }
                });
            }
        });

        $("#btnModifyPsOk").on('click', function (e) {
            e.preventDefault();
            if ($('#old-ps').val() == '' || $('#new-ps').val() == "" || $('#cf-ps').val() == "") {
            	$('#noModal').modal('show');
                //清空密码输入框
                $('#old-ps').val("");
                $('#new-ps').val("");
                $('#cf-ps').val("");
                return false;
            } else {
                if ($('#new-ps').val() != $('#cf-ps').val()) {
                	$('#noSameModal').modal('show');
                    //清空密码输入框
                    $('#old-ps').val("");
                    $('#new-ps').val("");
                    $('#cf-ps').val("");
                    return false;
                } else {
                    $.ajax({
                        url: config.basePath + '/user/modifyPassword',
                        type: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({
                            userId: user.userId,
                            password: $('#old-ps').val(),
                            newPas: $('#new-ps').val()
                        }),
                        dataType: "json",
                        success: function(result) {
                            if (result.code != 0) {
                                alert(result.msg);
                                //清空密码输入框
                                $('#old-ps').val("");
                                $('#new-ps').val("");
                                $('#cf-ps').val("");
                                return false;
                            } else {
                            	$('#updatePassModal').modal('show');
                                //清空密码输入框
                                $('#old-ps').val("");
                                $('#new-ps').val("");
                                $('#cf-ps').val("");
                                //隐藏密码修改页面
                                $('#userInfoModal').modal('hide');
                            }
                        }
                    });
                }
            }
        });
    });

});