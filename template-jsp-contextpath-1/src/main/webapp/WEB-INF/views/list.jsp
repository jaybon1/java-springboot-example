<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
  </head>
  <body>
    <h1>Temp</h1>
    <h1>${pageContext.request.contextPath}</h1>
    <!-- <h1>${img}</h1> -->
    <img
      src="${pageContext.request.contextPath}/img/cat.jpg"
      width="100%"
      alt="고양이"
    />
  </body>
</html>
