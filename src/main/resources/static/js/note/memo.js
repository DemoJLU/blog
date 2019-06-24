
require(['../config'], function (config) {
    var user=window.parent.user;//记录登录用户

    initDatetimepicker();
    //初始化时间框
    function initDatetimepicker(){
        require(['datetimepicker'], function () {
            $('#memoDeadline,#deadline,#memoDeadlineModify').datetimepicker({
                step: 1,
                value:  '',
                format: 'Y-m-d H:m:s'
            });
        });
    }

    //格式化输出时间
    function getMyDate(time){
        if(typeof(time)==null){
            return "";
        }
        if(time==null){
            return "";
        }
        var oDate = new Date(time),
            oYear = oDate.getFullYear(),
            oMonth = oDate.getMonth()+1,
            oDay = oDate.getDate(),
            oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) ;//最后拼接时间

        return oTime;
    };

    //补0操作,当时间数据小于10的时候，给该数据前面加一个0
    function getzf(num){
        if(parseInt(num) < 10){
            num = '0'+num;
        }
        return num;
    }

    //到期运算
    function isTimeLimit(time,hourParam){
        var date1=new Date();  //开始时间
        date = time.substring(0,19);//截止时间字符串转时间
        date = time.replace(/-/g,'/');
        var date2 = new Date(date).getTime();
        var date3 = date2 - date1.getTime(); //时间差的毫秒数
        var leave = date3%(24*3600*1000)    //计算天数后剩余的毫秒数
        var hours=Math.floor(leave/(3600*1000))
        if (hours < hourParam){
            return true;
        } else {
            return false;
        }
    };

    require(['jquery', 'bootstrap', 'common/dt', 'common/slider','common/dateFormat'], function ($, bootstrap, dataTable) {
        $('#input_people').val(user.userName);  //设置录入人
        //加载优先级下拉框
        // $.ajax({
        //     url: config.basePath + '/dayShiftPlan/dropDownListPriority',
        //     type: 'get',
        //     data: null,
        //     //contentType: 'application/json',
        //     dataType: 'json',
        //     success: function (data) {
        //         if(data){
        //             var option='';
        //             for(var i=0;i<data.length;i++){
        //                 option='<option value="'+data[i].priority_id+'">'+data[i].priority_name+'</option>';
        //                 $("#priorityYes").append(option);
        //                 $("#upPriorityYes").append(option);
        //             }
        //         }
        //     }
        // });

        var dayShiftPlan = dataTable('dayShiftPlan', {
            ajax: {
                url: config.basePath + '/memo/list',
                type: "POST",
                data:function(d){
                    d.deadline=$('#deadline').val();
                    d.matter=$('#matter').val();
                    d.done = $('#done').val();
                    d.persion = $('#persion').val();
                    d.priority = $('#priority').val();
                }
            },
            columns: [
                { className: "text-center",width:"5%",visible:false,
                    render: function (data, type, full, meta) {
                        return '<input type="checkbox" class="checkchild"/>';
                    }
                },
                { data: null,width:"5%"},
                { data: 'matter',width:"5%"},
                { data: 'priority',width:"5%",
                    render: function (data){
                        if(data==1){
                            data="看心情";
                            return data;
                        }
                        if(data==2){
                            data="尽早完成";
                            return data;
                        }else{
                            data="紧急";
                            return data;
                        }
                    }
                },
                { data: 'memo_content',width:"25%"},
                { data: 'memo_end_time',width:"10%",
                    // render: function (data){
                    //     return data==null?"":data.split(" ")[0];
                    // }
                },
                { data: 'remind',width:"8%",
                    render: function (data){
                        if(data==1){
                            data="1小时";
                            return data;
                        }
                        if(data==2){
                            data="2小时";
                            return data;
                        }
                        if(data==6){
                            data="6小时";
                            return data;
                        }
                        if(data==12){
                            data="12小时";
                            return data;
                        }
                        if(data==24){
                            data="24小时";
                            return data;
                        }
                        if(data==168){
                            data="一周";
                            return data;
                        }
                        else{
                            data="无提醒";
                            return data;
                        }
                    }
                },
                { data: 'input_persion',width:"7%"},
                { data: 'input_time',width:"10%",
                    // render: function (data){
                    //     return data==null?"":data.split(" ")[0];
                    // }
                },
                { data: null,width:"10%"},
            ],
            columnDefs: [
                {
                    targets: 0,
                    orderable: false
                },
                {
                    targets: 9,
                    render: function (data) {
                        return '<a href="#doneModal" class="btn btn-success btn-xs" data-toggle="modal" title="完成"><span class="glyphicon glyphicon-ok"></span></a>&nbsp;&nbsp;&nbsp;'
                            + '<a href="#repairModal" class="btn btn-info btn-xs" data-toggle="modal" title="修改" data-action="modify"><span class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;'
                            + '<a href="#deleteModal" class="btn btn-danger btn-xs" data-toggle="modal" title="删除"> <span class="glyphicon glyphicon-remove"></span></a>';
                    }
                }
            ],
            ordering: true,
            serverSide: false,
            paginate: true,
            "createdRow":function(row,data,index){
                if (isTimeLimit(data.memo_end_time,data.remind)) {
                    $('td',row).eq(3).css('backgroundColor' , '#FC9D99');
                }
            },
            drawCallback: function () {
                var api = this.api();
                //var startIndex = api.context[0]._iDisplayStart;//获取到本页开始的条数
                api.column(1).nodes().each(function (cell, i) {
                    cell.innerHTML =  i + 1;
                });
                var currLen=api.page.info().end- api.page.info().start;
                var currSelectLen=api.rows('.selected', {page:'current'} ).data().length;
                if(currLen==currSelectLen){
                    $(this).find('thead input[type=checkbox]').prop("checked", true);
                }else{
                    $(this).find('thead input[type=checkbox]').removeAttr('checked');
                }
            },
            initComplete: function () {

            }
        });


        //修改备忘信息
        $('#repairModal').on('show.bs.modal', function (e) {
            var rowData = dayShiftPlan.row($(e.relatedTarget).parent()).data();
            $('#memoIdModify').val(rowData.id);
            $('#inputPersionModify').val(rowData.input_persion);
            $('#memoTypeModify').val(rowData.matter);
            $('#memoRankModify').val(rowData.priority);
            $('#memoDeadlineModify').val(rowData.memo_end_time);
            $('#memoContentModify').val(rowData.memo_content);
            $('#remindModify').val(rowData.remind);
            // $("#upFullTrainNum").text("");
        });
        //保存修改的备忘信息
        $('#btnUpdateSave').on('click', function (e) {
            var params = {};
            //必填项验证
            $('#repairModal .form-group').removeClass('has-error');
            try {
                $('#repairModal').find('.form-control').each(function (i, dom) {
                    if (dom.required && !dom.value &&(!dom.disabled)) {
                        throw dom;
                    }
                });
            } catch (e) {
                $(e).parent().addClass('has-error');
                return;
            }
            var remindModify=document.getElementById('remindModify').value;
            var inputPersionModify=document.getElementById('inputPersionModify').value;
            var memoTypeModify=document.getElementById('memoTypeModify').value;
            var memoRankModify=document.getElementById('memoRankModify').value;
            var memoDeadlineModify=document.getElementById('memoDeadlineModify').value;
            var memoContentModify=document.getElementById('memoContentModify').value;
            var memoIdModify=document.getElementById('memoIdModify').value;
            params ={
                remindModify:remindModify,
                inputPersionModify:inputPersionModify,
                memoTypeModify:memoTypeModify,
                memoRankModify:memoRankModify,
                memoDeadlineModify:memoDeadlineModify,
                memoContentModify:memoContentModify,
                memoIdModify:memoIdModify,
            };
            $.post(config.basePath + '/memo/modifyMemo', params, function (data) {
                if (data === 1) {
                    $('#updateconfirmModal').modal('show');
                    $('#repairModal').modal('hide');
                    dayShiftPlan.ajax.reload();
                } else {
                    alert('修改任务信息失败！');
                }
            });
        });

        //新增备忘
        $('#btnSave').on('click', function (e) {
//        	alert($('#input_persion').val());
            var depot_dispatch = "是";
            var input_user = $('#input_user').val();
            var params = {};
            //必填项验证
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
            var remind=document.getElementById('remind').value;
            var inputPersion=document.getElementById('inputPersion').value;
            var memoType=document.getElementById('memoType').value;
            var memoRank=document.getElementById('memoRank').value;
            var memoDeadline=document.getElementById('memoDeadline').value;
            var memoContent=document.getElementById('memoContent').value;
            params ={
                remind:remind,
                inputPersion:inputPersion,
                memoType:memoType,
                memoRank:memoRank,
                memoDeadline:memoDeadline,
                memoContent:memoContent,
            };
            $.post(config.basePath + '/memo/addMemo', params, function (data) {
                if (data === 1) {
                    $('#addconfirmModal').modal('show');
                    $('#repairModalNew1').modal('hide');
                    $('#addPlanOne').modal('hide');
                    dayShiftPlan.ajax.reload();
                    // window.location.reload();
                } else {
                    alert('新增任务信息失败!');
                }
            });

        });


        // $('#upLoad').on('click', function (e) {
        //     var form = $('#form1');//上传文件
        //     var formdata = new FormData(form);
        //     $.ajax({
        //         type : "POST",
        //         url : config.basePath + '/memo/file',
        //         data : formdata,
        //         async: false,
        //         cache: false,
        //         contentType: false,
        //         processData: false,
        //         success : function(msg) {
        //             if(msg){
        //                 alert('提交成功！');
        //             }
        //         }
        //     });
        // });

        //第一层任务
        $('#btnNo').on('click', function (e) {
            var inPeo = $('#input_people').val();
            $('#input_user').val(inPeo);

            //必填项验证
            $('#addPlanOne .form-group').removeClass('has-error');
            try {
                $('#addPlanOne').find('.form-control').each(function (i, dom) {
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
            $("#repairModalNew2").modal('show');
        });
        //第一层任务
        $('#btnYes').on('click', function (e) {
            var inPeo = $('#input_people').val();
            $('#input_user').val(inPeo);

            //必填项验证
            $('#addPlanOne .form-group').removeClass('has-error');
            try {
                $('#addPlanOne').find('.form-control').each(function (i, dom) {
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
            $("#repairModalNew1").modal('show');

        });
        //显示完成modal
        $('#doneModal').on('show.bs.modal', function (e) {
            var rowData = dayShiftPlan.row($(e.relatedTarget).parent()).data();
            $('#doneId').val(rowData.id);
        });
        $('#btnDone').on('click', function (e) {
            $.get(config.basePath + '/memo/doneMemo', {memoId: $('#doneId').val()}, function (data) {
                if (data === 1) {
                    dayShiftPlan.ajax.reload();
                    $('#doneModal').modal('hide');
                } else {
                    alert('操作失败！');
                }
            })
        });
        //显示删除modal
        $('#deleteModal').on('show.bs.modal', function (e) {
            var rowData = dayShiftPlan.row($(e.relatedTarget).parent()).data();
            $('#memoId').val(rowData.id);
        });
        $('#btnDelete').on('click', function (e) {
            $.get(config.basePath + '/memo/deleteMemo', {memoId: $('#memoId').val()}, function (data) {
                if (data === 1) {
                    dayShiftPlan.ajax.reload();
                    $('#deleteModal').modal('hide');
                } else {
                    alert('删除备忘信息失败！');
                }
            })
        });
        //车号条件查询
        $('#btnQuery').on('click', function (e) {
//        	params ={
//        			vehicle_id:$('#vehicle_id').val()
//        	};
            dayShiftPlan.ajax.reload();
        });
        //今日
        $('#btnQueryToday').on('click', function (e) {
            var memo_start_time = formatDateYMD(new Date());
            var memo_end_time = formatDateYMD(new Date());
            $('#memo_start_time').val(memo_start_time);
            $('#memo_end_time').val(memo_end_time);
            dayShiftPlan.ajax.reload();
        });
        $('#btnCheck').click(function () {
//            var params = {
//            		memo_start_time: $('#memo_start_time').val()
//            };
//            dayShiftPlan.settings()[0].ajax.data = function (d) {
//                return $.extend({}, d, params);
//            };
            dayShiftPlan.ajax.reload();
        });
        //选择删除
        $("#btnExport").click(function(){
            dayShiftPlan.columns( [0] ).visible( true );
            $(".checkall")[0].checked = false;  //取消全选
            var all = $(".checkchild");
            for(var j = 0 ; j < all.length; j++){
                all[j].checked = false;
            }
            $(this).hide();
            $("#btnExportOk").show();
        })
        //确认删除
        $("#btnExportOk").click(function(){
            dayShiftPlan.columns( [0] ).visible( false );
            $(this).hide();
            $("#btnExport").show();
            var selectedTask=dayShiftPlan.rows('.selected').data();
            selectedTaskId="";
            for(var i=0;i<selectedTask.length;i++){
                selectedTaskId+=selectedTask[i].task_id+",";
            }
            console.log(selectedTaskId);
//        	var end = $('#task_end').val().split("-");
//        	var task_end = end[0]+end[1]+end[2];
            $.get(config.basePath + '/dayShiftPlan/deleteTaskInfo', {taskId: selectedTaskId}, function (data) {
                if (data === 'success') {
                    dayShiftPlan.ajax.reload();
                    $('#deleteModal').modal('hide');
                } else {
                    alert('删除任务信息失败！');
                }
            })
        })
        //复选框点击
        $('#dayShiftPlan').on('click', 'tr .checkchild', function () {
            var tr=$(this).closest("tr");
            tr.toggleClass('selected');
            //console.log(dayShiftPlan.page.info());
            var i = dayShiftPlan.row(tr).index()-dayShiftPlan.page.info().start;
            var all = $(".checkchild");
            for(var j = 0 ; j < all.length; j++){
                if ( j == i ){
                    var thisClass = tr[0].className;
                    if(thisClass.indexOf('selected')>=0){
                        all[j].checked = true;
                    }else{
                        all[j].checked = false;
                        $(".checkall")[0].checked = false;
                    }
                }
            }
            // console.log( importTaskTable.rows('.selected').data().length +' row(s) selected' );
        });
        //全选
        $("#dayShiftPlan").on('click','.checkall',function () {
            var check = $(this).prop("checked");
            $(".checkchild").prop("checked", check);
            var trs = document.getElementById("dayShiftPlan").getElementsByTagName("tr");
            if(check){
                for(var i=0;i<trs.length;i++){
                    trs[i].setAttribute("class", "selected");
                }
            }else{
                for(var i=0;i<trs.length;i++){
                    trs[i].setAttribute("class", "");
                }
            }

        });
        //取消全选
        function cancelSelect(){
            var trs = document.getElementById("dayShiftPlan").getElementsByTagName("tr");
            for(var i=0;i<trs.length;i++){
                trs[i].setAttribute("class", "");
            }
        }
        //加载消息滚动
        // getExpireNoticeDetail();
        // //触摸清空定时器,展开关闭窗口
        // expireNoticeDetailScroll();
        //清空查询框,重置、刷新查询
        $('#btnReset, #btnRefresh').on('click', function (e) {
            ResetTd();
            //dayShiftPlan.settings()[0].ajax.data ={};
            // $('#vehInfoTable').hide();
            // $('#rpStartTime,#rpStartTimeYes,#rpEndTimeYes, #memo_end_time,#memo_start_time,#rpEndTimeYes').datetimepicker({
            //     step: 1,
            //     value:  '',
            //     format: 'Y-m-d'
            // });
            // dayShiftPlan.ajax.reload();
            // getExpireNoticeDetail();
        });
        //清空td中的内容
        function ResetTd(){
            $('#persion').val('');
            $('#deadline').val('');
//      	 $('#matter_get').html("");
        }

        //比对车底号  如Z198/5-Z196/7
        // function isTrainNum(allNum,inputNum){
        //     var ret=false;
        //     if(allNum!=null&&allNum!=""){
        //         var allNumArr=allNum.split("-");
        //         for(var j=allNumArr.length-1;j>=0;j--){
        //             var lastAllNum=allNumArr[j];
        //             if(lastAllNum!=null&&lastAllNum!=""){
        //                 if(lastAllNum.indexOf("/")>0){
        //                     var lastAllNumArr=lastAllNum.split("/");
        //                     var n2=lastAllNumArr[lastAllNumArr.length-1];
        //                     var n1=lastAllNumArr[0].substring(0,lastAllNumArr[0].length-n2.length);
        //                     var newNum=n1+n2;
        //                     if(inputNum==newNum){
        //                         ret=true;
        //                         break;
        //                     }
        //                 }else{
        //                     if(inputNum==lastAllNum){
        //                         ret=true;
        //                         break;
        //                     }
        //                 }
        //             }
        //
        //         }
        //     }
        //     return ret;
        // }
        //长效命令到期提示滚动
        // function getExpireNoticeDetail(){
        //     $.ajax({
        //         url: config.basePath + '/planning/commandNotice/listExpireNoticeDetailInfo',
        //         type: 'post',
        //         data: null,
        //         //contentType: 'application/json',
        //         dataType: 'json',
        //         success: function (ret) {
        //             var msg=ret.data;
        //             //获得当前<ul>
        //             var $uList = $("#scrollBox ul");
        //             $uList.empty();
        //             //var scrollTimer = null;
        //             if(msg!=null&&msg.length>0){
        //                 detailSize=msg.length;
        //                 $("#noticeDetailSize").text(msg.length);
        //                 for(var i=0;i<msg.length;i++){
        //                     $uList.append('<li><a title="'+msg[i].detail_content+'" data-cmdNum="'+msg[i].command_num+'">'+(i+1)+'、【'+msg[i].command_num+'号第'+msg[i].detail_order+'项】'+formatText(msg[i].detail_content,35)+'</a></li>');
        //                 }
        //             }else{
        //                 detailSize=0;
        //                 $("#noticeDetailSize").text("0");
        //                 $uList.append('<li><a href="#">未查询到相关到期命令。</a></li>')
        //             }
        //         }
        //     });
        // }
        //触摸清空定时器,展开关闭窗口
        // function expireNoticeDetailScroll(){
        //     var $uList = $("#scrollBox ul");
        //     $uList.hover(function () {
        //         clearInterval(scrollTimer);
        //     },function () {//离开启动定时器
        //         scrollTimer = setInterval(function () {
        //             scrollList($uList);
        //         },5000);
        //     }).trigger("mouseleave"); //自动触发触摸事件
        //     $("#noticeDetailAlert .close .glyphicon-chevron-down").click(function(){
        //         $(this).hide();
        //         $(this).siblings().show();
        //         if(detailSize>1){
        //             $("#noticeDetailAlert").css("height",detailSize*50);
        //         }else{
        //             $("#noticeDetailAlert").css("height",50);
        //         }
        //     })
        //     $("#noticeDetailAlert .close .glyphicon-chevron-up").click(function(){
        //         $(this).hide();
        //         $(this).siblings().show();
        //         $("#noticeDetailAlert").css("height",50);
        //     })
        //     $("#scrollBox ul").on('click', 'li a', function () {
        //         var $uList = $("#scrollBox ul");
        //         scrollTimer = setInterval(function () {
        //             scrollList($uList);
        //         },5000);
        //         var cmdNum=$(this).attr("data-cmdNum"); //获取a标签存储的命令号
        //         window.parent.noticeDetailCmdNum=cmdNum;  //多条页面传参
        //         var tabId="commandNotice";
        //         var dir="planning";
        //         var tabName="长效命令公告";
        //         var index=$(window.parent.document);
        //         if (index.find('#' + tabId).length == 0) {
        //             var title = $('<a href="#' + tabId + '" role="' + dir + '" data-toggle="tab">' + tabName + '</a>');
        //             if (index.find('.nav-tabs li').length > 0) {
        //                 title.append('<i class="close">&times;</i>');
        //             }
        //             //console.log($('.tab-content'));
        //             index.find('.nav-tabs').append($('<li id="tab_' + tabId + '"></li>').append(title));
        //             index.find('.tab-content').append('<div id="'+ tabId + '" role="tabpanel" class="tab-pane active"></div>');
        //             // $('#' + tabId).load('./' + dir + '/' + tabId + '.html');
        //             index.find('#' + tabId).append('<iframe scrolling="auto" frameborder="0" src="./' + dir + '/' + tabId + '.html" style="width:100%;height:'+(document.documentElement.clientHeight-0)+'px;overflow:hidden;"></iframe>');
        //         }
        //         index.find('#tab_' + tabId).addClass('active').siblings().removeClass('active');
        //         index.find('#' + tabId).addClass('active').siblings().removeClass('active');
        //         var aaa=index.find('#' + tabId).find("iframe")[0].contentDocument;
        //         //$(aaa).find("#btnReset").click();
        //         $(aaa).find("#commandNumQuery").val(cmdNum);
        //         $(aaa).find("#commandTypeQuery").val("");
        //         $(aaa).find("#commandContentQuery").val("");
        //         $(aaa).find("#btnQuery").click();
        //     })
        // }
        //滚动动画
        // function scrollList(obj) {
        //     //获得当前<li>的高度
        //     var scrollHeight = $("ul li:first").height();
        //     //滚动出一个<li>的高度
        //     obj.stop().animate({marginTop:-scrollHeight},900,function () {
        //         //动画结束后，将当前<ul>marginTop置为初始值0状态，再将第一个<li>拼接到末尾。
        //         obj.css({marginTop:0}).find("li:first").appendTo(obj);
        //     });
        // }
        //省略长字符串
        // function formatText(str,size){
        //     if(str.length>size){
        //         str=str.substring(0,size-3)+"...";
        //     }
        //     return str;
        // }
    });
});