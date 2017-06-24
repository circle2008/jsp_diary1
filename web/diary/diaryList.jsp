<%--
  Created by IntelliJ IDEA.
  User: cf
  Date: 2017/2/20
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="data_list">
    <div class="data_list_title">
        <img src="${pageContext.request.contextPath}/images/list_icon.png"/>
        日记列表
        <div class="diary_datas">
            <ul>
               <c:forEach items="${diaryList}" var="diary">
                    <li>『<fmt:formatDate value="${diary.releasedate}" type="date" pattern="yyyy-MM-dd"></fmt:formatDate>』<span>&nbsp;</span><a href="diary?action=show&diaryId=${diary.diaryId}">${diary.title}</a></li>
               </c:forEach>
            </ul>
        </div>
        <div class="pagination pagination-centered">
            <ul>
                ${pageCode}
            </ul>
        </div>
    </div>
</div>
