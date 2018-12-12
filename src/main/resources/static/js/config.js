/**
 * Created by yangfan on 2018/12/7.
 * 说明：
 * （1）require加载的模块，必须是按照AMD规范，例如用define()函数定义的模块（Jquery），对于一些非AMD规范的模块，要采用shim的方式定义他们的特征
 * （2）符合AMD写法的define与require都能加载对应的依赖，define与require不同的就是，如果想要被别的module引用则要用define，写法上其多出来一个接口的返回，
 *     如果把define写成require后，则别的module引用不到本次定义的module。
 */

define(function () {
    // 获取当前网址，如：http://localhost:8080/TSIP/portal.html
    var currentPath = window.document.location.href;
    // 获取主机地址之后的目录，如：/TSIP/portal.html
    var pathName = window.document.location.pathname;
    var pos = currentPath.indexOf(pathName);
    // 获取主机地址，如：http://localhost:8080
    var basePath = currentPath.substring(0, pos);
    if (pathName.toUpperCase().indexOf('BLOG') != -1) {
        basePath += pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    }
    require.config({
        baseUrl: basePath + '/js/lib',
        paths: {
            'jquery': 'jquery.min',
            'bootstrap': 'bootstrap.min',
            'datatables.net': 'jquery.dataTables.min',
            'datatables': 'dataTables.bootstrap.min',
            'jquery-mousewheel': 'jquery.mousewheel.min',
            'datetimepicker': 'jquery.datetimepicker.full.min',
            'jqplot': 'jqplotlib/jquery.jqplot.min',
            'jqplotplugin': 'jqplotlib/jqplotPlugin',
            'zTree': 'jquery.ztree.all.min',
            'pxloader': 'PxLoader',
            'common': '../common',
            'vehicle': '../vehicle',
            'device': '../device',
            'system': '../system',
            'shunting':'../shunting',
            'raphael':'raphael-min',
            'smartMenu':'jquery-smartMenu',
            'bootstrap-switch':'bootstrap-switch.min',
            'echarts':'echarts',
        },
        shim: {
            'bootstrap': ['jquery'],
            'jqplot': ['jquery'],
            'jqplotplugin': ['jqplot'],
            'zTree': ['jquery'],
            'raphael': {
                exports: 'Raphael'
            }
        }
    });

    require(['jquery'], function ($) {
        $(document).ajaxError(function (event, xhr, options, thrownError) {
            console.log('ajax error url: ' + options.url.substr(0, options.url.indexOf('?')) + '; status code: ' + xhr.status);
            if (xhr) {
                if (xhr.status === 403) {
                    top.location.href = basePath + '/error/unauth.html';
                } else if (xhr.status === 404) {
                    top.location.href = basePath + '/error/404.html';
                } else {
                    top.location.href = basePath + '/logout';
                }
            }
        });
    });

    return {
        basePath: basePath,
        constant: {
            DEV_TYPE: { H: '温度检测', A: '声学检测', P: '力学检测', E: '图像检测', W: '轮对尺寸检测' },
            TRAIN_TYPE: { '1':'客车', '2':'货车', '3':'动车' },
            POS_ARR: ['轴位', '左轴位1', '左轴位2', '右轴位1', '右轴位2', '电机', '齿轮箱', '电机端万向轴', '齿轮箱端万向轴'],
            RJ_ARR: ['正常', '微热', '微热', '抱闸', '强热', '激热'],
            TA_FT: { n : '内圈', p: '外圈', r: '滚子' }
        },
        getUrlParam: function () {
            var search = window.location.search;
            var params = new Object();
            if (search.indexOf('?') != -1) {
                var arr = search.substr(1).split('&');
                for (var i = 0; i < arr.length; i++) {
                    var index = arr[i].indexOf('=');
                    params[arr[i].substring(0, index)] = decodeURIComponent(arr[i].substring(index + 1));
                }
            }
            return params;
        },
        buildConstSelect: function (dom, name, value, text) {
            dom.empty();
            for (var key in this.constant[name]) {
                dom.append('<option value="' + key + '">' + this.constant[name][key] + '</option>');
            }
            dom.prepend('<option value="' + value + '" selected>' + text + '</option>');
        }
    }
});