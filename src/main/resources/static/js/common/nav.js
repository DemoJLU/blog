/**
 * Created by YFZX-WB on 2017/3/31.
 */
define(['jquery'], function($) {

    return {    	
        openTab: function (tab) {
            $('#navigation a[href="#' + tab + '"]').tab('show');
        },
        closeTab: function (tab) {
            if ($('#tab_' + tab).hasClass('active')) {
                $("#tab_" + tab).prev().addClass('active');
                $("#" + tab).prev().addClass('active');
                $('#navigation li').removeClass('active');
                $('#navigation a[href$="' + $("#" + tab).prev().attr('id') + '"]').parents('li').addClass('active');
            }
            $('#tab_' + tab).remove();
            $("#"+ tab).remove();
        },
        createTab: function (tabId, dir, tabName) {
            if ($('#' + tabId).length == 0) {
                var title = $('<a href="#' + tabId + '" role="' + dir + '" data-toggle="tab">' + tabName + '</a>');
                if ($('.nav-tabs li').length > 0) {
                    title.append('<i class="close">&times;</i>');
                }
                $('.nav-tabs').append($('<li id="tab_' + tabId + '"></li>').append(title));
                $('.tab-content').append('<div id="'+ tabId + '" role="tabpanel" class="tab-pane active"></div>');
                // $('#' + tabId).load('./' + dir + '/' + tabId + '.html');
                $('#' + tabId).append('<iframe scrolling="auto" frameborder="0" src="./' + dir + '/' + tabId + '.html" style="width:100%;height:'+(document.documentElement.clientHeight-109)+'px;overflow:auto;"></iframe>');
            }
            $('#tab_' + tabId).addClass('active').siblings().removeClass('active');
            $('#' + tabId).addClass('active').siblings().removeClass('active');
        },
        createTabInSub: function (tabId, dir, tabName, params) {
            if ($('#' + tabId, window.parent.document).length > 0) {
                $('#' + tabId, window.parent.document).empty();
            } else {
                var title = $('<a href="#' + tabId + '" role="' + dir + '" data-toggle="tab">' + tabName + '</a>')
                    .append('<i class="close">&times;</i>');
                $('.nav-tabs', window.parent.document).append($('<li id="tab_' + tabId + '"></li>').append(title));
                $('.tab-content', window.parent.document).append('<div id="'+ tabId + '" role="tabpanel" class="tab-pane"></div>');
            }
            $('#' + tabId, window.parent.document).append(
                '<iframe scrolling="auto" frameborder="0" src="./' + dir + '/' + tabId + '.html' + (params ? '?' + params : '') + '" style="width:100%;height:'+document.documentElement.clientHeight+'px;overflow:hidden;"></iframe>'
            );
            $('#tab_' + tabId, window.parent.document).addClass('active').siblings().removeClass('active');
            $('#' + tabId, window.parent.document).addClass('active').siblings().removeClass('active');
        }
    }
});
