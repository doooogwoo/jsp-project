<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>商品查詢</title>
  <style>
    body{font-family: ui-sans-serif, system-ui; padding: 24px;}
    .form{display:flex; gap:12px; align-items:center; margin-bottom:16px;}
    input[type=text]{padding:8px 12px; width:320px;}
    button{padding:8px 16px; cursor:pointer;}
    table{border-collapse:collapse; width:100%; margin-top:12px;}
    th,td{border:1px solid #ddd; padding:8px; text-align:left;}
    th{background:#f5f5f5}
    .empty{margin-top:12px; color:#666;}
  </style>
</head>
<body>
<h2>多筆資料查詢</h2>

<!-- 用 c:url 會自動把 /jsp-demo 的 context path 帶上去 -->
<form class="form" method="get" action="<c:url value='/search'/>">
  <input type="text" name="q" placeholder="輸入品名關鍵字，如：球鞋" value="${fn:escapeXml(param.q)}"/>
  <button type="submit">搜尋</button>
</form>

<c:choose>
  <c:when test="${not empty results}">
    <table>
      <thead><tr><th>ID</th><th>商品名稱</th><th>價格</th><th>剩餘數量</th></tr></thead>
      <tbody>
      <c:forEach var="p" items="${results}">
        <tr>
          <td>${p.id}</td>
          <td>${p.name}</td>
          <td>$${p.price}</td>
          <td>${p.stock}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:when>
  <c:otherwise>
    <div class="empty">請輸入關鍵字後查詢，或查無資料。</div>
  </c:otherwise>
</c:choose>
</body>
</html>
