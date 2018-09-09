<%--
<%@ page import="mapper.globaltest.Say" %>
<%@ page import="service.globaltest.SayHello" %>
&lt;%&ndash;
  User: 王海
  Date: 2018/3/14
  Time: 19:40
&ndash;%&gt;
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    SayHello sayHello =new SayHello("we are ");
    Say say =new Say("global_test");
    // 仅仅在console打印
    sayHello.sayHello(sayHello,say);
    String testName = sayHello.getName();
%>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>测试jsp</title>
    <meta name="author" content="wanghai"/>
    &lt;%&ndash;<link href="" rel="shortcut icon" type="image/x-icon"/>&ndash;%&gt;
    &lt;%&ndash;<link rel="stylesheet" type="text/css" href="">&ndash;%&gt;
</head>
<body>
<h1>
    打印调用自其它地方的方法，测试包，测试tomcat。
</h1>
<h3>
    &lt;%&ndash; 前台打印 &ndash;%&gt;
    <%=testName%>
</h3>

<script type="text/javascript" src=""></script>
<script src="" type="text/javascript" charset="UTF-8"></script>
</body>
</html>--%>
