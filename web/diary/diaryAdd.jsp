<%--
  Created by IntelliJ IDEA.
  User: cf
  Date: 2017/2/22
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
        function checkForm() {
            var title=document.getElementById("title").value;
            var content=CKEDITOR.instances.content.getData();
            var typeId=document.getElementById("typeId").value;
            if(title==null||title==""){
                document.getElementById("error").innerHTML="标题不能为空！";
                return false;
            }
            if(content==null||content==""){
                document.getElementById("error").innerHTML="内容不能为空！";
                return false;
            }
            if(typeId==null||typeId==""){
                document.getElementById("error").innerHTML="请选择内容类别！";
                return false;
            }
        }
</script>
<div class="data_list">
    <div class="data_list_title">
        <c:choose>
            <c:when test="${diary.typeId!=null}">
                <img src="${pageContext.request.contextPath}/images/diary_type_edit_icon.png">
                修改日志
            </c:when>
            <c:otherwise>
                <img src="${pageContext.request.contextPath}/images/diary_add_icon.png"/>
                写日志
            </c:otherwise>
        </c:choose>


    </div>
    <form action="diary?action=save" method="post" onsubmit="return checkForm()">
        <div>
            <div class="diary_title"><input type="text" id="title"  name="title" value="${diary.title}" class="input-xlarge"  style="margin-top:5px;height:30px;"  placeholder="日志标题..."/></div>
            <!--在线文本编辑器-->
            <div>
                <textarea class="ckeditor" id="content" name="content">${diary.content}</textarea>
            </div>
            <div class="diary_type">
                <select id="typeId" name="typeId">
                    <option value="">请选择日志类别...</option>
                    <c:forEach var="typeCountList" items="${typeCountList}">
                        <!--将选中的类别传过来-->
                        <option value="${typeCountList.diaryTypeId}" ${typeCountList.diaryTypeId==diary.typeId?'selected':'' }>${typeCountList.typeName}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <!--隐藏域，提交diaryId的值-->
                <input type="hidden" id="diaryId" name="diaryId" value="${diary.diaryId}">
                <input type="submit" class="btn btn-primary" value="保存"/>
                <button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
                <font id="error" color="red">${error }</font>
            </div>
        </div>
    </form>
</div>
