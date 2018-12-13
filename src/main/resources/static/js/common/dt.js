/**
 * Created by YFZX-WB on 2017/4/1.
 */
define(['jquery', 'datatables'], function ($) {

    const options = {
        processing: true,
        searching: false,
        lengthChange: false,
        info: true,
        paginate: true,
        pageLength: 15,
        pagingType: 'full_numbers',
        language: {
            processing: '正在加载...',
            zeroRecords: '暂无数据显示',
            lengthMenu: "显示 _MENU_ 条记录，",
            info: '当前显示 _START_ 至 _END_ 项结果，共 _TOTAL_ 项',
            infoEmpty: '显示第 0 至 0 项结果，共 0 项',
            infoFiltered: '',
            paginate: {
                first: '<span title="第一页"><<</span>',
                previous: '<span title="上一页"><</span>',
                next: '<span title="下一页">></span>',
                last: '<span title="最后一页">>></span>'
            }
        }
    };

    $.fn.dataTable.ext.errMode = function (settings, techNode, message) {
        if (techNode === 1 && settings.jqXHR.responseText.indexOf('login-container') !== -1) {
            alert('登陆超时，请重新登陆...');
        } else {
            alert(message);
        }
    };

    return function (dom, obj) {
        return $('#' + dom).DataTable($.extend({}, options, obj));
    }
});