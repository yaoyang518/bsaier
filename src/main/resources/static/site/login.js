$(function() {
    initLoading();
    function initLoading() {
        $("body").append('<!-- loading -->' +
            '<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="my-loading">' +
            '<div class="am-modal-dialog">' +
            '<div class="am-modal-hd">正在载入...</div>' +
            '<div class="am-modal-bd">' +
            '<span class="am-icon-spinner am-icon-spin"></span>' +
            '</div></div></div>'
        );
    }
    function showLoading(text) {
        $(".am-modal-hd").html(text);
        $("#my-loading").modal('open');
    }
    function hideLoading() {
        $("#my-loading").modal("close");
    }

    function codeWarn(item) {
        $(".lss-password").addClass("ls-border-err");
        $('#codeWarn').html(item);
        $('#phoneErr').html('');
        $(".lss-phone").removeClass("ls-border-err");
        $('#codeWarn').removeClass('am-hide');
    }
    function phoneErr(item) {
        $('#phoneErr').removeClass('am-hide');
        $('#phoneErr').html(item);
        $(".lss-phone").addClass("ls-border-err");
    }
    /*登录事件*/
    $('#lss-login').click(
        function () {
            showLoading("登录中...");
           var username = $('#phone').val();
            var password = $("#password").val();
            if (username != "" && password != "") {
                $.ajax({
                    type: "GET",
                    url: "/api/users/login?loginName="+username+'&password='+ password,
                    dataType: "JSON",
                    contentType: "application/json; charset=utf-8",
                   xhrFields: {
                        withCredentials: true
                    },
                    crossDomain: true,
                   /* data: JSON.stringify({
                        "mobile": username,
                        "password": password,
                    }),*/
                    success : function(result) {
                        console.log(result);
                        if(result.success){
                           $.AMUI.utils.cookie.set("realname", result.data.realname, 8 * 60 * 1000, "/");//存token
                            location.href = "datav.html";
                        } else {
                            if(result.code=='4002'){
                                codeWarn(result.message);
                            } else if (result.code=='0011'){
                                phoneErr(result.message);
                            }

                        }
                        hideLoading();
                    }
                })
            } else {
                alert("请检查您的输入！");
            }
        });
    $(window).resize(function(){
        $(".lss-login-main").css({
            marginTop: ($(window).height() - $(".lss-login-main").outerHeight())/2
        });
    });
    $(window).resize();

});
