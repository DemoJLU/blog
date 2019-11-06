
require(['../config'], function (config) {

    var editor = new E( document.getElementById('editor') )
    var content = editor.txt.html();
    document.getElementById('btnGenCode').addEventListener('click', function () {
        // 读取 html
        alert(content)
    }, false)
    $('#btnMemo').on('click', function (e) {
        window.location.href = "../index.html";
    });




});


