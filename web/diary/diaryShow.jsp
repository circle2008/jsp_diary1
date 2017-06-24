<%--
  Created by IntelliJ IDEA.
  User: cf
  Date: 2017/2/22
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    function diaryDelete(diaryId) {
        if(confirm("确定要删除这篇日志吗"))
        {
            window.location="diary?action=delete&diaryId="+diaryId;
        }
    }
</script>
<div class="data_list">
    <div class="data_list_title">
        <img src="${pageContext.request.contextPath}/images/diary_show_icon.png"/>
        日记信息</div>
    <div>
        <div class="diary_title"><h3>${diary.title}</h3></div>
        <div class="diary_info">
            发布时间：『<fmt:formatDate value="${diary.releasedate}" type="date" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>』
            &nbsp;&nbsp;日志类别：${diary.typeName}
        </div>
        <div class="diary_content">${diary.content}</div>
        <div class="diary_action">
            <button class="btn btn-primary" type="button" onclick="javascript:window.location='diary?action=preSave&diaryId=${diary.diaryId}'">修改日志</button>
            <button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
            <button class="btn btn-danger" type="button" onclick="diaryDelete(${diary.diaryId})">删除日志</button>
        </div>
    </div>
</div>
