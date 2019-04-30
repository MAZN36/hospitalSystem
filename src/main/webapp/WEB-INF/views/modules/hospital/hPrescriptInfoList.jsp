<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>处方信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hospital/hPrescriptInfo/">处方信息列表</a></li>
		<shiro:hasPermission name="hospital:hPrescriptInfo:edit"><li><a href="${ctx}/hospital/hPrescriptInfo/form">处方信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hPrescriptInfo" action="${ctx}/hospital/hPrescriptInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<%--	<ul class="ul-form">
			<li><label>诊断信息id：</label>
				<form:input path="diagnoseId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>收费单id：</label>
				<form:input path="chargeId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>诊断信息id</th>
				<th>收费单id</th>
				<th>药品id</th>
				<th>药品数量</th>
				<th>药品用法</th>
				<th>诊断日期</th>
				<th>诊断备注</th>
				<shiro:hasPermission name="hospital:hPrescriptInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hPrescriptInfo">
			<tr>
				<td><a href="${ctx}/hospital/hPrescriptInfo/form?id=${hPrescriptInfo.id}">
					${hPrescriptInfo.diagnoseId}
				</a></td>
				<td>
					${hPrescriptInfo.chargeId}
				</td>
				<td>
					${hPrescriptInfo.durgId}
				</td>
				<td>
					${hPrescriptInfo.durgNum}
				</td>
				<td>
					${hPrescriptInfo.durgUsage}
				</td>
				<td>
					<fmt:formatDate value="${hPrescriptInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hPrescriptInfo.remarks}
				</td>
				<shiro:hasPermission name="hospital:hPrescriptInfo:edit"><td>
    				<a href="${ctx}/hospital/hPrescriptInfo/form?id=${hPrescriptInfo.id}">修改</a>
					<a href="${ctx}/hospital/hPrescriptInfo/delete?id=${hPrescriptInfo.id}" onclick="return confirmx('确认要删除该处方信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>