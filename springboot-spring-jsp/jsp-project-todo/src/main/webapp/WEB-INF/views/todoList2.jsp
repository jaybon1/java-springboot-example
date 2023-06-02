<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
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
      type="text/css"
      href="${pageContext.request.contextPath}/static/css/todoList.css"
    />
    <title>Title</title>
  </head>
  <body>
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
            <hr />
            <ul id="sortable" class="list-unstyled">
              <c:forEach items="${todoList}" var="todo">
                <c:if test="${todo.doneYn eq 'N'}">
                  <li class="ui-state-default">
                    <div class="checkbox">
                      <label>
                        <input
                          onchange="setDone(${todo.idx})"
                          type="checkbox"
                          value=""
                        />
                        <span><c:out value="${todo.content}" /></span>
                      </label>
                    </div>
                  </li>
                </c:if>
              </c:forEach>
            </ul>
            <div class="todo-footer">
              <strong>
                <span class="count-todos"><c:out value="${todoSize}" /></span>
              </strong>
              항목 남았음
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="todolist">
            <h1>Already DONE</h1>
            <ul id="done-items" class="list-unstyled">
              <c:forEach items="${todoList}" var="todo">
                <c:if test="${todo.doneYn eq 'Y'}">
                  <li>
                    <div class="checkbox">
                      <label>
                        <input
                          onchange="setDone(${todo.idx})"
                          class="remove-item"
                          type="checkbox"
                          value=""
                        />
                        <span><c:out value="${todo.content}" /></span>
                      </label>
                      <button
                        onclick="setDelete(${todo.idx})"
                        class="remove-item btn btn-default btn-xs pull-right"
                      >
                        <span class="glyphicon glyphicon-remove"></span>
                      </button>
                    </div>
                  </li>
                </c:if>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </body>
  <script>
    console.log("스타일 참고", "https://bootsnipp.com/snippets/QbN51");

    $("#content").on("keyup", (e) => {
      if (e.which === 13) {
        if ($("#content").val() !== "") {
          const data = {
            content: $("#content").val(),
          };

          $.ajax({
            type: "post",
            url: `/api/v1/todoList`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "text",
          })
            .done((result) => {
              // location.href = location.href.slice(location.href.indexOf("/",10),location.href.indexOf("todoList")) +'todoList';
              location.href = "/todoList";
            })
            .fail((error) => {
              alert(error.message);
            });
        } else {
          // some validation
        }
      }
    });

    const setDone = (idx) => {
      $.ajax({
        type: "put",
        url: `/api/v1/todoList`,
        data: JSON.stringify({ idx }),
        contentType: "application/json; charset=utf-8",
        dataType: "text",
      })
        .done((result) => {
          location.href = "/todoList";
        })
        .fail((error) => {
          alert(error.message);
        });
    };

    const setDelete = (idx) => {
      $.ajax({
        type: "delete",
        url: `/api/v1/todoList`,
        data: JSON.stringify({ idx }),
        contentType: "application/json; charset=utf-8",
        dataType: "text",
      })
        .done((result) => {
          location.href = "/todoList";
        })
        .fail((error) => {
          alert(error.message);
        });
    };
  </script>
</html>
