<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8' %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<!-- CSS -->
<link href="util/toastr/toastr.min.css" type="text/css" rel="stylesheet">
<style>
    #toast-container>div {
        box-shadow: none !important;
    }

    #toast-container {
        top: 25px;
    }
</style>
<!-- JS -->
<script src="util/toastr/toastr.min.js"></script>

<script>
    let error = "${fn:contains(param, 'error')}";
    let type = "${param.error}";
    console.log(type)
    console.log("${loginFailMsg}")
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": true,
        "positionClass": "toast-top-center",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    //동작 순서로 타이머 걸어줌
    setTimeout(function () {
        let msg, title;
        switch (type) {
            case 'incorrect':
                msg = "사용자의 정보가 맞지 않습니다";
                title = "로그인 실패";
                break;
        }
        error === "true" && toastr.error("${loginFailMsg}", "${title}");
    }, 200)
</script>