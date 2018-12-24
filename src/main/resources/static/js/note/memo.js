
require(['../config'], function (config) {
    var user=window.parent.user;//记录登录用户



    initDatetimepicker();
    //初始化时间框
    function initDatetimepicker(){
        require(['datetimepicker'], function () {
            $('#rpStartTime,#memo_end_time,#input_time,#rpStartTimeYes,#rpEndTimeYes,#memo_start_time,#upRpStartTime,#upRpEndTime').datetimepicker({
                step: 1,
                value:  '',
                format: 'Y-m-d'
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
    require(['jquery', 'bootstrap', 'common/dt', 'common/slider','common/dateFormat'], function ($, bootstrap, dataTable) {
        $('#input_people').val(user.userName);  //设置录入人
//         //加载matter下拉框
//         $.ajax({
//             url: config.basePath + '/dayShiftPlan/dropDownListMatter',
//             type: 'get',
//             data: null,
//             //contentType: 'application/json',
//             dataType: 'json',
//             success: function (data) {
//                 if(data){
//                     var option='';
// // 					var option2='<option value="'+404+'">'+无+'</option>';
// // 					$("#matter_get").append(option2);
//                     for(var i=0;i<data.length;i++){
//                         option='<option value="'+data[i].matter_id+'">'+data[i].matter_name+'</option>';
//                         $("#matter_get").append(option);
//                         if(data[i].matter_id>200){
//                             $("#matterYes").append(option);
//                             $("#upMatterYes").append(option);
//                             continue;
//                         }else{
//                             $("#matter").append(option);
//                             $("#upMatter").append(option);
//                             continue;
//                         }
//                     }
//                 }
//             }
//         });
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
                    d.memo_start_time=$('#memo_start_time').val();
                    d.task_endtime=$('#memo_end_time').val();
                    d.input_time=$('#input_time').val();
                    d.matter_get=$('#matter_get').val();
                    d.done = $('#done').val();
                }
            },
            columns: [
                { className: "text-center",width:"5%",visible:false,
                    render: function (data, type, full, meta) {
                        return '<input type="checkbox" class="checkchild"/>';
                    }
                },
                { data: null,width:"5%"},
                { data: 'memo_start_time',width:"10%",
                    render: function (data){
                        return data==null?"":data.split(" ")[0];
                    }
                },
                { data: 'matter',width:"10%"},
                { data: 'priority',width:"5%",
                    render: function (data){
                        if(data==1){
                            data="必做";
                            return data;
                        }
                        if(data==2){
                            data="优先";
                            return data;
                        }else{
                            data="一般";
                            return data;
                        }
                    }
                },
                { data: 'memo_end_time',width:"10%",
                    render: function (data){
                        return data==null?"":data.split(" ")[0];
                    }
                },
                { data: 'memo_content',width:"25%"},
                { data: 'input_persion',width:"10%"},
                { data: 'input_time',width:"10%",
                    render: function (data){
                        return data==null?"":data.split(" ")[0];
                    }
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
                        return '<a href="#repairModal" class="btn btn-info btn-xs" data-toggle="modal" title="修改" data-action="modify"><span class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;'
                            + '<a href="#deleteModal" class="btn btn-danger btn-xs" data-toggle="modal" title="删除"> <span class="glyphicon glyphicon-remove"></span></a>';
                    }
                }
            ],
            ordering: true,
            serverSide: false,
            paginate: true,
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
        //添加计划第2层窗口 是
        // $('#repairModalNew1').on('show.bs.modal', function (e) {
        //     $("#train_numberYes").val("");
        //     $("#command_contentYes").val("");
        //     $("#task_cycleYes").val(1);
        //     $("#fullTrainNum").text("");
        // });
        // //添加计划第2层窗口 否
        // $('#repairModalNew2').on('show.bs.modal', function (e) {
        //     $("#command_content").val("");
        // });
        //车次框焦点消失
        // $("#train_numberYes").blur(function(){
        //     var inputNum= document.getElementById("train_numberYes").value.toUpperCase();;
        //     var name="请核对车次";
        //     var value=1;
        //     if(trainNumList!=null){
        //         for(var i=0;i<trainNumList.length;i++){
        //             if(isTrainNum(trainNumList[i].name,inputNum)){
        //                 name=trainNumList[i].name;
        //                 value=trainNumList[i].value;
        //                 break;
        //             }
        //         }
        //     }
        //     $("#fullTrainNum").text(name);
        //     $("#task_cycleYes").val(value);
        // })
        // $("#upTrain_number").blur(function(){
        //     var inputNum= document.getElementById("upTrain_number").value.toUpperCase();;
        //     var name="请核对车次";
        //     var value=1;
        //     if(trainNumList!=null){
        //         for(var i=0;i<trainNumList.length;i++){
        //             if(isTrainNum(trainNumList[i].name,inputNum)){
        //                 name=trainNumList[i].name;
        //                 value=trainNumList[i].value;
        //                 break;
        //             }
        //         }
        //     }
        //     $("#upFullTrainNum").text(name);
        //     $("#upTask_cycle").val(value);
        // })

        //修改任务信息
        $('#repairModal').on('show.bs.modal', function (e) {
            var rowData = dayShiftPlan.row($(e.relatedTarget).parent()).data();
            $('#upTaskId').val(rowData.task_id);
            $('#upDepotDispatch').val(rowData.depot_dispatch);
            if(rowData.depot_dispatch==0){
                $("#upPriorityYes,#upRpEndTime,#upTrain_number").attr("disabled",true);
                $('#upMatterYes').hide();
                $('#upMatter').show();
                $('#upMatter').val(rowData.matter);
            }else{
                $("#upPriorityYes,#upRpEndTime,#upTrain_number").attr("disabled",false);
                $('#upMatterYes').show();
                $('#upMatterYes').val(rowData.matter);
                $('#upMatter').hide();
            }
            $('#upTrain_number').val(rowData.train_number);
            $('#upRpStartTime').val(rowData.memo_start_time.split(" ")[0]);
            $('#upRpEndTime').val(rowData.task_expire==null?"":rowData.task_expire.split(" ")[0]);
            $('#upDayNightType').val(rowData.dayNight_type);
            $('#upCommand_content').val(rowData.command_content);
            $('#upPriorityYes').val(rowData.priority);
            $('#upTask_cycle').val(rowData.task_cycle);
            $("#upFullTrainNum").text("");
        });
        //保存修改的任务信息
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
            var rpStartTime=document.getElementById('upRpStartTime').value;
            var priority=document.getElementById('upPriorityYes').value;
            var memo_end_time=document.getElementById('upRpEndTime').value;
            var command_content=document.getElementById('upCommand_content').value;
            var train_number=document.getElementById('upTrain_number').value;
            var depotDispatch=document.getElementById('upDepotDispatch').value;
            var task_cycle=document.getElementById('upTask_cycle').value;
            var matter;
            if(depotDispatch==1){
                matter=document.getElementById('upMatterYes').value;
            }else{
                matter=document.getElementById('upMatter').value;
            }
            var dayNightType=document.getElementById('upDayNightType').value;
            var taskId=document.getElementById('upTaskId').value;
            params ={
                rpStartTime:rpStartTime,
                priority:priority,
                memo_end_time:memo_end_time,
                command_content:command_content,
                train_number:train_number,
                matter:matter,
                dayNightType:dayNightType,
                taskId:taskId,
                updateUser:user.userName,
                task_cycle:task_cycle,
                departmentId:user.departmentId
            };
            $.post(config.basePath + '/todayTask/updateImportTaskInfo', params, function (data) {
                if (data === 'success') {
                    $('#updateconfirmModal').modal('show');
                    $('#repairModal').modal('hide');
                    dayShiftPlan.ajax.reload();
                } else {
                    alert('修改任务信息失败！');
                }
            });
        });
        //第二层否的确定
//         $('#btn_save').on('click', function (e) {
// //        	alert($('#input_persion').val());
//             var depot_dispatch = "否";
//             var input_user = $('#input_user').val();
//             var params = {};
//             //必填项验证
//             $('#repairModalNew2 .form-group').removeClass('has-error');
//             try {
//                 $('#repairModalNew2').find('.form-control').each(function (i, dom) {
//                     if (dom.required && !dom.value) {
//                         throw dom;
//                     }
//                 });
//             } catch (e) {
//                 $(e).parent().addClass('has-error');
//                 // var input_persion=document.getElementById('input_persion').value;
//                 //if(input_persion==''){
//                 return;
//                 //}
//             }
//             var rpStartTime=document.getElementById('rpStartTime').value;
//             var command_content=document.getElementById('command_content').value;
//             var matter=document.getElementById('matter').value;
//             params ={
//                 rpStartTime:rpStartTime,
//                 command_content:command_content,
//                 depot_dispatch:depot_dispatch,
//                 input_user:input_user,
//                 priority:1,
//                 matter:matter,
//                 departmentId:user.departmentId
//             };
//             $.post(config.basePath + '/dayShiftPlan/addTaskInfo', params, function (data) {
//                 if (data === 'success') {
//                     $('#addconfirmModal').modal('show');
//                     $('#repairModalNew2').modal('hide');
//                     $('#addPlanOne').modal('hide');
//                     dayShiftPlan.ajax.reload();
//                     // window.location.reload();
//                 } else {
//                     alert('新增任务信息失败！请检查您输入的车号、车次及回段日期是否正确！');
//                 }
//             });
//
//         });
        //第二层是的确定
//         $('#btnSaveYes').on('click', function (e) {
// //        	alert($('#input_persion').val());
//             var depot_dispatch = "是";
//             var input_user = $('#input_user').val();
//             var params = {};
//             //必填项验证
//             $('#repairModalNew1 .form-group').removeClass('has-error');
//             try {
//                 $('#repairModalNew1').find('.form-control').each(function (i, dom) {
//                     if (dom.required && !dom.value) {
//                         throw dom;
//                     }
//                 });
//             } catch (e) {
//                 $(e).parent().addClass('has-error');
//                 // var input_persion=document.getElementById('input_persion').value;
//                 //if(input_persion==''){
//                 return;
//                 //}
//             }
//             var rpStartTime=document.getElementById('rpStartTimeYes').value;
//             var priority=document.getElementById('priorityYes').value;
//             var rpEndTime=document.getElementById('rpEndTimeYes').value;
//             var command_content=document.getElementById('command_contentYes').value;
//             var train_number=document.getElementById('train_numberYes').value;
//             var matter=document.getElementById('matterYes').value;
//             var dayNight_type=document.getElementById('dayNightTypeYes').value;
//             var task_cycle=document.getElementById('task_cycleYes').value;
//             params ={
//                 rpStartTime:rpStartTime,
//                 priority:priority,
//                 rpEndTime:rpEndTime,
//                 command_content:command_content,
//                 train_number:train_number,
//                 matter:matter,
//                 dayNight_type:dayNight_type,
//                 depot_dispatch:depot_dispatch,
//                 input_user:input_user,
//                 task_cycle:task_cycle,
//                 departmentId:user.departmentId
//             };
//             $.post(config.basePath + '/dayShiftPlan/addTaskInfo', params, function (data) {
//                 if (data === 'success') {
//                     $('#addconfirmModal').modal('show');
//                     $('#repairModalNew1').modal('hide');
//                     $('#addPlanOne').modal('hide');
//                     dayShiftPlan.ajax.reload();
//                     // window.location.reload();
//                 } else {
//                     alert('新增任务信息失败！请检查您输入的车号、车次及回段日期是否正确！');
//                 }
//             });
//
//         });


        $('#upLoad').on('click', function (e) {
            var form = $('#form1');//上传文件
            var formdata = new FormData(form);
            $.ajax({
                type : "POST",
                url : config.basePath + '/memo/file',
                data : formdata,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success : function(msg) {
                    if(msg){
                        alert('提交成功！');
                    }
                }
            });
        });

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
        //显示删除modal
        $('#deleteModal').on('show.bs.modal', function (e) {
            var rowData = dayShiftPlan.row($(e.relatedTarget).parent()).data();
            $('#taskId').val(rowData.task_id);
        });
        $('#btnDelete').on('click', function (e) {
            $.get(config.basePath + '/dayShiftPlan/deleteTaskInfo', {taskId: $('#taskId').val()}, function (data) {
                if (data === 'success') {
                    dayShiftPlan.ajax.reload();
                    $('#deleteModal').modal('hide');
                } else {
                    alert('删除任务信息失败！');
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
            $('#vehInfoTable').hide();
            $('#rpStartTime,#rpStartTimeYes,#rpEndTimeYes, #memo_end_time,#memo_start_time,#rpEndTimeYes').datetimepicker({
                step: 1,
                value:  '',
                format: 'Y-m-d'
            });
            dayShiftPlan.ajax.reload();
            getExpireNoticeDetail();
        });
        //清空td中的内容
        function ResetTd(){
            $('#memo_start_time').val('');
            $('#memo_end_time').val('');
            $('#vehicle_id').val('');
            $('#veh_specie').html("");
            $('#train_num').html("");
            $('#veh_id').html("");
            $('#fixed_number').html("");
            $('#wind_supply_form').html("");
            $('#main_line_volt').html("");
            $('#hook_type').html("");
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