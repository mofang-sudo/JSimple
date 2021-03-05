function post(ip,token) {
    //console.log(ip+"--2--"+token);
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    if(content.length>1024)
        swal({
            title: "回复超过1024个字长!",
            text: "你的回复字数为:"+content.length+"，请精简您的发言!",
            icon: "warning",
            button: "确认",
        });
    else comment2target(questionId, 1, content,ip,token);
}

function comment2target(targetId, type, content,ip,token) {
    if (!content) {
        //alert("不能回复空内容~~~");
        sweetAlert("出错啦...", "不能回复空内容~~~", "error");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/api/comment/insert",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type,
            "ip":ip,
            "token":token
        }),
        success: function (response) {
            if (response.code == 200) {
                swal({
                    title: "回复成功!",
                    text: "点击确认后即可刷新页面!",
                    icon: "success",
                    button: "确认",
                }).then((value) => {
                    window.location.reload();
            });

            } else {
                if (response.code == 2003) {
                    swal({
                        title: "错误："+response.code,
                        text: response.message,
                        icon: "warning",
                        buttons: true,
                        dangerMode: true,
                    })
                        .then((willDelete) => {
                        if (willDelete) {
                            window.open("/sso/login");
                            window.localStorage.setItem("closable", true);

                            var interval = setInterval(function(){
                                var loginState = window.localStorage.getItem("loginState");
                                if (loginState == "true") {
                                    window.localStorage.removeItem("loginState");
                                    clearInterval(interval);
                                    swal({
                                        title: "登陆成功!",
                                        text: "您可以提交回复啦!",
                                        icon: "success",
                                        button: "好的",
                                    });
                                    return;
                                }
                            }, 2000);
                        } else {
                            swal({
                                     title: "已取消登录!",
                                     text: "取消登陆后，无法成功回复!",
                                     icon: "error",
                                     button: "确认",
                                 });
                }
                });
                }
                else {
                    sweetAlert("错误："+response.code, response.message, "error");
                }
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            swal(textStatus, "错误："+XMLHttpRequest.status, "error");
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var btnId = e.getAttribute("id");
    var strs= new Array();
    strs=btnId.split("-");
    var inputId = strs[1];
    var commentType = e.getAttribute("data-type");
    var inputBtn = $("#input-" + inputId);
    var content = inputBtn.attr('placeholder')+inputBtn.val();
    content=filterXSS(content);
    //console.log('inputId:'+inputId+'commentId:'+commentId+'placeholder:'+inputBtn.attr('placeholder')+'c:'+content);
    if(content.length>1024)
        swal({
            title: "回复超过1024个字长!",
            text: "你的回复字数为:"+content.length+"，请精简您的发言!",
            icon: "warning",
            button: "确认",
        });
    else comment2target(commentId, commentType, content,returnCitySN["cip"],"commentType2");


}

function like_comment(e) {
    var commentId = e.getAttribute("data-id");
    var thumbicon = $("#thumbicon-" + commentId);
    if(thumbicon.attr('class').indexOf('zanok')!=-1){
        swal({
            title: "点赞失败!",
            text: "请不要重复点赞哦!",
            icon: "error",
            button: "确认",
        });
        return;
    }
    like2target(commentId, 2);
}

function like_question(e) {
    var questionId = e.getAttribute("data-id");
    var thumbtext = $("#questionLikeText-" + questionId);
    if('已'==thumbtext.text()){
        swal({
            title: "收藏失败!",
            text: "请不要重复收藏哦!您可以在帖子管理页取消收藏",
            icon: "error",
            button: "确认",
        });
        return;
    }
    like2target(questionId, 1);
}

function removeLike(targetId, type){
    layer.confirm('确定取消收藏吗？', function(index){
        $.ajax({
            type: 'DELETE',
            url: '/api/like/remove',
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify({
                "targetId": targetId,
                "type": type
            }),
            success: function(res){
                layer.close(index);
                if(res.code==200) {
                    swal("成功!", ""+res.message, "success");
                }else swal("Oh,no!", ""+res.message, "error");
            },
            error: function(data){
                swal("取消失败!", ""+res.message, "error");
                //alert("删除失败");
            }
        });



    });
}

function like2target(targetId, type){
    $.ajax({
        type: "POST",
        url: "/api/like/insert",
        contentType: 'application/json',
        data: JSON.stringify({
            "targetId": targetId,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {//点赞成功
                swal({
                    title: ""+response.message,
                    text: "感谢您的支持，作者将会收到通知!",
                    icon: "success",
                    button: "确认",
                });
                if(type==2){//点赞评论时
                var thumbicon = $("#thumbicon-" + targetId);
                thumbicon.addClass("zanok");
                var likecount = $("#likecount-" + targetId);
                likecount.html(parseInt(likecount.text())+1);//点赞+1
                }
                if(type==1){//收藏问题时
                    var thumbicon = $("#questionLikeIcon-" + targetId);
                    thumbicon.html('&#xe658;');
                    var thumbtext = $("#questionLikeText-" + targetId);
                    thumbtext.html('已');
                    var likecount = $("#questionlikecount-" + targetId);
                    likecount.html(parseInt(likecount.text())+1);//点赞+1
                }
            } else {
                if (response.code == 2003) {

                    swal({
                        title: "错误："+response.code,
                        text: response.message,
                        icon: "warning",
                        buttons: true,
                        dangerMode: true,
                    })
                        .then((willDelete) => {
                        if (willDelete) {
                            window.open("https://github.com/login/oauth/authorize?client_id=b6ecb208ce93f679a75a&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");
                            window.localStorage.setItem("closable", true);

                            var interval = setInterval(function(){
                                var loginState = window.localStorage.getItem("loginState");
                                if (loginState == "true") {
                                    window.localStorage.removeItem("loginState");
                                    //  console.log("0");
                                    clearInterval(interval);
                                    // location.reload();
                                    // $("#comment_content").val(content);
                                    swal({
                                        title: "登陆成功!",
                                        text: "您可以点赞啦!",
                                        icon: "success",
                                        button: "好的",
                                    });

                                    //$("#navigation").load("#navigation");

                                    return;
                                }
                                // console.log("1");
                                // document.getElementById("comment_content").value=content;
//do whatever here..
                            }, 2000);


                        } else {
                            swal({
                                     title: "已取消登录!",
                                     text: "取消登陆后，无法成功回复!",
                                     icon: "error",
                                     button: "确认",
                                 });
                }
                });
                }
                else if (response.code == 2022) {
                    if(type==2){//点赞评论时
                    var thumbicon = $("#thumbicon-" + targetId);
                    thumbicon.addClass("zanok");
                    }
                    swal({
                        title: "点赞失败!",
                        text: "请不要重复点赞/收藏哦!",
                        icon: "error",
                        button: "确认",
                    });
                }
                else if (response.code == 2023) {
                    swal({
                        title: "收藏失败!",
                        text: "请不要重复收藏哦!您可以在帖子管理页面取消收藏",
                        icon: "error",
                        button: "确认",
                    });
                }
                else {
                    sweetAlert("错误："+response.code, response.message, "error");
                }
            }
        },
        dataType: "json"
    });
}


function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}


