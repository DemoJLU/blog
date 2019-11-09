
require(['../config'], function (config) {

    $(document).ready(function(){
        $.post(config.basePath + '/blog/articleList', function (data) {
            if (data!=null){
                var tdHtml = "";
                for(var i=0;i<data.length;i++){

                    var articleSummary = data[i].articleSummary;
                    var articleId = data[i].articleId;
                    var articleTitle = data[i].articleTitle;
                    var articleCreateTime = data[i].articleCreateTime;
                    var author = data[i].userName;

                    tdHtml +=
                        '<div class="grid-sizer"></div>'+
                        '<div class="grid-item">'+
                        '<div class="grid-item__inner">'+
                        '<div class="grid-item__content-wrapper">'+
                        '<div class="post">'+
                        '<div class="post__media"><a href="blog-detail.html"><img src="" alt=""/></a></div>'+
                        '<div class="post__body">'+
                        '<div class="post__meta"><span class="date">'+articleCreateTime+'</span><span class="author"><a href="#">by '+author+'</a></span></div>'+
                        '<h2 class="post__title"><a href="article-detail.html?data='+articleId+'">'+articleTitle+'</a></h2>'+
                        '<p class="post__text" id="sumarry">'+articleSummary+'</p>'+
                        '<a class="md-btn md-btn--outline-primary" href="article-detail.html?data='+articleId+'">read more</a>'+
                        '</div>'+
                        '</div>'+

                        '</div>'+
                        '</div>'+
                        '</div>'
                    // $("fan").html(tdHtml);
                    var testdiv = document.getElementById("fan");
                    testdiv.innerHTML=tdHtml;
                    // document.write(tdHtml);
                }
            }
        });

    })

    // $('#btnMemo').on('click', function (e) {
    //     window.location.href = "../index.html";
    // });

    /**
     * Fullscreen menu
     */
    // $('.fullscreenmenu__module').each(function () {
    //     var self = $(this),
    //         triggerID = self.attr('trigger');
    //
    //     self.on("click", function () {
    //         $(triggerID).toggleClass('open');
    //         $(this).toggleClass('open');
    //     });
    //     $(triggerID).on("click", function () {
    //         $(triggerID).toggleClass('open');
    //         self.toggleClass('open');
    //     });
    // });

    // var articleTitle = document.getElementById();
    // function js_method(articleTitle){
    //
    // };


});


