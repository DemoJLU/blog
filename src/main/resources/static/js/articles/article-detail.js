
require(['../config'], function (config) {

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + 'data' + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r != null) return unescape(r[2]);
        return null;
    }

    var articleId=getQueryString('data');

    $(document).ready(function(){
        var params = {};
        params ={
            articleId:articleId,
        };
        $.post(config.basePath + '/blog/articleDetail',params,function (data) {
            if (data!=null){
                var titleHtml = "";
                var tdHtml = "";
                var articleSummary = data[0].articleSummary;
                var articleTitle = data[0].articleTitle;
                var articleUpdateTime = data[0].articleUpdateTime;
                var author = data[0].userName;
                var articleContent = data[0].articleContent;
                titleHtml +=
                    '<div class="page-title pb-40">'+
                  //  '<span class="post-detail__cat">Free stuff</span>'+
                    '<h2 class="page-title__title">'+articleTitle+'</h2>'+
                    '<div class="post-detail__meta"><span class="date">'+articleUpdateTime+'</span><span class="author"><a href="#">by '+author+'</a></span></div>'+
                    '<div class="page-title__divider"></div>'+
                    '</div>'


                tdHtml +=
                    //'<div class="post-detail__media"><img src="https://images.pexels.com/photos/395132/pexels-photo-395132.jpeg?w=1260&amp;h=750&amp;auto=compress&amp;cs=tinysrgb" alt=""/></div>'+
                    '<div class="post-detail__entry row">'+
                    '<div class="col-md-8">'+
                    //'<h5>Vivamus eget vulputate risus. Aliquam id fringilla lacus, vitae maximus felis. Suspendisse in posuere urna. Ut ipsum nisi, suscipit at nisl nec, pulvinar dapibus risus. Etiam non hendrerit nulla, in volutpat dui.</h5>'+
                    '<p>'+articleContent+'</p>'+

                    // '<p>Donec quis molestie magna. Sed mattis ac nunc sit amet scelerisque. Curabitur a aliquam sem. Suspendisse condimentum elementum eros, a vehicula tortor ornare sit amet. Donec ac commodo enim, eget mattis ipsum.</p>'+
                    // '<p>In vestibulum vestibulum suscipit. Phasellus velit felis, imperdiet quis dolor ut, aliquet iaculis mauris. Pellentesque consectetur placerat suscipit. Donec quis hendrerit eros. Mauris sed leo aliquet, feugiat magna et, feugiat mauris. Integer a nunc eu risus ultrices euismod. Pellentesque at dictum turpis. Fusce sed mauris lorem. Nam condimentum odio diam, vitae congue sapien mollis vitae. Pellentesque nulla est, dapibus finibus sapien in, euismod dapibus diam.</p>'+
                    // '<p>In dictum, magna non suscipit volutpat, ligula ligula scelerisque purus, et dapibus ipsum enim quis nisi. Sed eget faucibus velit. Integer in felis consequat, aliquam neque vitae, pellentesque nibh. Donec id lobortis risus, at finibus tortor. Praesent consequat elementum tristique. Sed dictum quis magna a consequat. Maecenas id malesuada sem. Phasellus pharetra odio purus, sit amet commodo tortor mollis ac. Donec gravida aliquet tellus.</p>'+
                    '</div>'+
                    '</div>'

                var title = document.getElementById("title");
                title.innerHTML=titleHtml;
                var testdiv = document.getElementById("fan");
                testdiv.innerHTML=tdHtml;
            }
        });

    })
});


