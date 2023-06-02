<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="kr" id="loginPage">
<head>
    <meta charset="UTF-8"/>
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>

    <!-- 부트스트랩 링크 -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
    />
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"
    ></script>

    <!-- Font Awesome -->
    <link
            rel="stylesheet"
            href="https://use.fontawesome.com/releases/v5.15.2/css/all.css"
    />

    <script src="https://accounts.google.com/gsi/client" async defer></script>

    <title>로그인</title>
</head>

<body>
<!-- Start your project here-->
<section style="background-color: #508bfc; min-height: 100vh">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card shadow-2-strong" style="border-radius: 1rem">
                    <div class="card-body p-5 text-center">
                        <h3 class="mb-3">
                            로그인
                        </h3>
                        <div class="input-group mb-3">
                  <span id="idAddOn" class="input-group-text">
                    &nbsp;아이디 &nbsp;
                  </span>
                            <input
                                    type="text"
                                    id="id"
                                    class="form-control"
                                    aria-describedby="idAddOn"
                            />
                        </div>

                        <div class="input-group mb-3">
                            <span id="pwAddOn" class="input-group-text">비밀번호</span>
                            <input
                                    type="password"
                                    id="pw"
                                    class="form-control"
                                    aria-describedby="pwAddOn"
                            />
                        </div>

                        <!-- Checkbox -->
                        <div class="form-check d-flex justify-content-start mb-4">
                            <input
                                    class="form-check-input"
                                    type="checkbox"
                                    id="rememberMe"
                            />
                            <label class="form-check-label" for="rememberMe">
                                아이디 기억하기
                            </label>
                        </div>

                        <button
                                class="btn btn-primary"
                                type="button"
                                style="width: 100%"
                                onclick="requestLogin()"
                        >
                            로그인
                        </button>

                        <hr class="my-4"/>

                        <a href="/oauth2/authorization/google">구글 아이디로 로그인</a>
                        <a href="/oauth2/authorization/kakao">카카오 아이디로 로그인</a>

<%--                        <div id="g_id_onload"--%>
<%--                             data-client_id="356603993924-prvil0ld1rmoggvjf01up5a8vl1jdf88.apps.googleusercontent.com"--%>
<%--                        &lt;%&ndash;                             data-login_uri="https://your.domain/your_login_endpoint"&ndash;%&gt;--%>
<%--                             data-auto_prompt="false">--%>
<%--                        </div>--%>
<%--                        <div class="g_id_signin"--%>
<%--                             data-type="standard"--%>
<%--                             data-size="large"--%>
<%--                             data-theme="outline"--%>
<%--                             data-text="sign_in_with"--%>
<%--                             data-shape="rectangular"--%>
<%--                             data-logo_alignment="left">--%>
<%--                        </div>--%>

<%--                        &lt;%&ndash;                        <button><img src="/img/google.png"></button>&ndash;%&gt;--%>
<%--                        <button onclick="kakaoLogin()"><img src="${pageContext.request.contextPath}/img/kakao.png"></button>--%>
                        <hr class="my-4"/>

                        <a href="/auth/join">아이디가 없으신가요? 회원가입</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- End your project here-->
</body>
<!-- Custom scripts -->
<script type="text/javascript">

    const kakaoLogin = () => {
        // 여러명이 같은 컴퓨터 사용 시 소셜
        // https://rootjs.tistory.com/73
        // https://accounts.kakao.com/login?continue=
        // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=42940caedbd4745dfcba33f777935238&redirect_uri=http://localhost:8070/api/v1/oauth2/kakao
        window.open(
            "https://accounts.kakao.com/login?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fresponse_type%3Dcode%26client_id%3D42940caedbd4745dfcba33f777935238%26redirect_uri%3Dhttp%3A%2F%2Flocalhost%3A8070%2Fapi%2Fv1%2Foauth2%2Fkakao",
            "카카오 로그인",
            "width = 500, height = 700"
        );
    }


    // 비밀번호 입력창에서 엔터키를 치면 로그인 요청하는 함수
    document.querySelector("#pw").addEventListener("keyup", (event) => {
        if (event.keyCode === 13) {
            requestLogin();
        }
    });

    // 로그인 요청하는 함수
    // 로그인 버튼을 누르면 실행됨
    const requestLogin = () => {
        // 서버와 통신하기 전에 입력값 검증
        if (!validateFields()) {
            return;
        }

        // id속성을 이용해서 태그를 가져옴
        const idElement = document.getElementById("id");
        const pwElement = document.getElementById("pw");
        const rememberMeElement = document.getElementById("rememberMe");

        if (rememberMeElement.checked) {
            localStorage.setItem("rememberId", idElement.value);
        }

        const formData = new FormData();
        formData.append("id", idElement.value);
        formData.append("pw", pwElement.value);

        const form = document.createElement('form');
        form.action = '/login-process';
        form.method = 'POST';

        for (const [key, value] of formData.entries()) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = key;
            input.value = value;
            form.appendChild(input);
        }

        document.body.appendChild(form);
        form.submit();


    };

    // 아이디와 비밀번호 입력창이 비어있는지 검사하는 함수
    const validateFields = () => {
        // id속성으로 요소를 가져옴
        const idElement = document.getElementById("id");
        const pwElement = document.getElementById("pw");

        if (idElement.value === "") {
            alert("아이디를 입력해주세요.");
            idElement.focus();
            return false;
        }

        if (pwElement.value === "") {
            alert("비밀번호를 입력해주세요.");
            pwElement.focus();
            return false;
        }

        return true;
    };

    // 페이지가 로드되면 실행되는 함수
    const setLoginPage = () => {
        // 창이 켜지면 아이디 입력창에 포커스가 가도록 설정
        const idElement = document.getElementById("id");
        idElement.focus();

        // 아이디 기억하기 체크박스가 체크되어있으면 아이디 입력창에 아이디를 넣어줌
        const rememberId = localStorage.getItem("rememberId");
        if (rememberId !== null) {
            const rememberMeElement = document.getElementById("rememberMe");

            idElement.value = rememberId;
            rememberMeElement.checked = true;
        }

        // 에러 메시지 확인하기
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const message = urlParams.get('message');
        if (message != null && message != "") {
            alert(message);
        }
    };
</script>
<script defer>
    setLoginPage();
</script>
</html>
