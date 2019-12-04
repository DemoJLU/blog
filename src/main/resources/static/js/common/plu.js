// AMD中引用$插件
;(function (factory) {
    if (typeof define === "function" && define.amd) {
        // AMD模式
        define([ "jquery" ], factory);
    } else {
        // 全局模式
        factory(jQuery);
    }
}(function ($) {
    var plu = {

    }
    // model
    $.fn.plu = function () {
        //插件代码
    };
}));
