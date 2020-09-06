<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8' %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<meta lang="ko-KR">

<html>

<head>

    <title>서초 그린안심존 - 로그인</title>
    <meta charset="UTF-8">
    <meta name="description" content="Free Web tutorials">
    <meta name="keywords" content="HTML,CSS,XML,JavaScript">
    <meta name="author" content="KimEunjin">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <![endif]-->
    <!-- CSS -->
    <link href="css/login/login.css" type="text/css" rel="stylesheet">
    <link href="util/micromodal/micromodal.css" type="text/css" rel="stylesheet">

    <!-- JS -->
    <script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
    <script src="util/micromodal/micromodal.js"></script>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"
        integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=" crossorigin="anonymous"></script>

    <%@include file="templates/toastr.jsp" %>
    <script>
        //alert(document.cookie);
        //GROUP_AUTH
        function getCookie(c_name) {
            var re = new RegExp(c_name + "=([^;]+)");
            var value = re.exec(document.cookie);
            return (value != null) ? unescape(value[1]) : null;
        }
        $(document).ready(function () {
            MicroModal.init();
            var loginExist = Boolean("${loginExist}");
            if (loginExist) {
                MicroModal.show('modal-1');
            }
            $('#loginExist').on('click', function () {
                $('input[name=password]').val("auth|auth|" + $('input[name=password]').val())
                $('form').submit();
            })
        })
    </script>
</head>

<body>

    <div id="wrap">
        <form method="post" action="/login" style="min-height:969px;">
            <!--<img class="login_logo" src="/assets/img/logo.PNG" alt="성남도시개발공사">-->
            <div class="login_tltle" style="padding-top:235px;">
                <p>제주 테스트</p>
            </div>

            <div class="container">
                <input type="text" placeholder="ID" name="username" value="${username}" required>
                <input type="password" placeholder="PASSWORD" name="password" value="${password}" required>
                <button type="submit"><a href="#">로그인</a></button>
            </div>
            <img class="login_logo2" src="images/login/footer_logo.png" alt="케이웨더">
        </form>
    </div>
    <div class="modal micromodal-slide" id="modal-1" aria-hidden="true">
        <div class="modal__overlay" tabindex="-1" data-micromodal-close>
            <div class="modal__container" role="dialog" aria-modal="true" aria-labelledby="modal-1-title">
                <main class="modal__content" id="modal-1-content">
                    <p>
                        이미 로그인되어있습니다. 이전 계정을 로그아웃하고 로그인하시겠습니까?
                    </p>
                </main>
                <footer class="modal__footer">
                    <button id="loginExist" class="modal__btn modal__btn-primary">Continue</button>
                    <button class="modal__btn" data-micromodal-close
                        aria-label="Close this dialog window">Close</button>
                </footer>
            </div>
        </div>
    </div>

</html>