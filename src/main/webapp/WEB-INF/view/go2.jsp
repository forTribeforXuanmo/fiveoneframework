<%--
  Created by IntelliJ IDEA.
  User: lishengzhu
  email:530735771@qq.com
  Date: 2017/6/10
  Time: 17:35
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title> ***</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>

</head>
<body>
这里是go2.jsp
<script src="/asset/jquery.min.js"></script>
<script type="text/javascript">
    $(function () {
        $.ajax({
            url:'/noParamTest',
            type:'get',
            success:function (data) {
                console.log(data);
                console.log("data.a=="+data.a);

            }
        });
        $.ajax({
            url:'/noParamTest2',
            type:'get',
            success:function (data) {
               console.log(data);
            }
        });
        $.ajax({
            url:'/annotationTest',
            type:'post',
            data:{name:'lsz'},
            success:function (data) {
                console.log(data);
            }
        });
    })
</script>
</body>
</html>
