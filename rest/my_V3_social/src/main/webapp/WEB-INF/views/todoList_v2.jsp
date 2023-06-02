<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script
            type="text/javascript"
            src="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"
    ></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <link
            href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
            rel="stylesheet"
            id="bootstrap-css"
    />
    <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/css/todoList.css"
    />
    <title>todoList</title>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">
                jaybon's work
            </a>
        </div>
        <button type="button" onclick="javascript:location.replace('/logout')" class="btn btn-default navbar-btn navbar-right">logout</button>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <div class="todolist not-done">
                <h1>TODO LIST</h1>
                <input
                        id="content"
                        type="text"
                        class="form-control add-todo"
                        placeholder="할일을 입력하고 엔터를 치세요"
                        autofocus
                />
                <hr/>
                <ul id="todoList" class="list-unstyled"></ul>
                <div class="todo-footer">
                    <strong>
                        <span id="todoCount" class="count-todos"></span>
                    </strong>
                    항목 남았음
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="todolist">
                <h1>Already DONE</h1>
                <ul id="doneList" class="list-unstyled"></ul>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    console.log("스타일 참고", "https://bootsnipp.com/snippets/QbN51");

    const init = () => {
        $.ajax({
            type: "get",
            url: `/api/v2/todo`,
            contentType: "application/json; charset=utf-8",
        })
            .done((response) => {
                if (response.code === 0) {
                    const todoList = response.data.todoList.filter((todo) => todo.doneYn === "N");
                    const todoSize = todoList.length;
                    const doneList = response.data.todoList.filter((todo) => todo.doneYn === "Y");

                    $("#todoList").empty();

                    for (const todo of todoList) {
                        $("#todoList").append(
                            `
                            <li class="ui-state-default">
                                <div class="checkbox">
                                    <label>
                                        <input
                                            onchange="setDone(` + todo.idx + `)"
                                            type="checkbox"
                                        />
                                        <span>` + todo.content + `</span>
                                    </label>
                                </div>
                            </li>
                            `
                        );
                    }

                    $(`#todoCount`).text(todoSize);

                    $("#doneList").empty();

                    for (const todo of doneList) {
                        $("#doneList").append(
                            `
                            <li>
                                <div class="checkbox">
                                    <label>
                                        <input
                                            onchange="setDone(` + todo.idx + `)"
                                            class="remove-item"
                                            type="checkbox"
                                        />
                                        <span>` + todo.content + `</span>
                                    </label>
                                    <button
                                        onclick="setDelete(` + todo.idx + `)"
                                        class="remove-item btn btn-default btn-xs pull-right"
                                    >
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                </div>
                            </li>
                            `
                        );
                    }
                } else {
                    console.log(result.message);
                }
            })
            .fail((error) => {
                alert(error.message);
            });
    };
    init();

    $("#content").on("keyup", (e) => {
        if (e.which === 13) {
            if ($("#content").val() !== "") {
                const data = {
                    content: $("#content").val(),
                };

                $.ajax({
                    type: "post",
                    url: `/api/v2/todo`,
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                })
                    .done((response) => {
                        $("#content").val("");
                        init();
                    })
                    .fail((error) => {
                        alert(error.message);
                    });
            }
        }
    });

    const setDone = (idx) => {
        $.ajax({
            type: "put",
            url: `/api/v2/todo/` + idx,
            contentType: "application/json; charset=utf-8",
        })
            .done((response) => {
                init();
            })
            .fail((error) => {
                alert(error.message);
            });
    };

    const setDelete = (idx) => {
        $.ajax({
            type: "delete",
            url: `/api/v2/todo/` + idx,
            contentType: "application/json; charset=utf-8",
        })
            .done((response) => {
                init();
            })
            .fail((error) => {
                alert(error.message);
            });
    };
</script>
</html>
