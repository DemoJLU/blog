
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
        var total = (date2 - date1.getTime()) / 1000;
        var day = parseInt(total / (24 * 60 * 60)); //计算整数天数
        var afterDay = total - day * 24 * 60 * 60; //取得算出天数后剩余的秒数
        var hours = parseInt(afterDay / (60 * 60))+(day*24); //计算整数小时数
        if (hours < hourParam){
            return true;
        } else {
            return false;
        }
    };

    require(['jquery', 'bootstrap', 'common/dt', 'common/slider','common/dateFormat'], function ($, bootstrap, dataTable) {
        $('#input_people').val(user.userName);  //设置录入人

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
        //条件查询
        $('#btnQuery').on('click', function (e) {
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
    });
});