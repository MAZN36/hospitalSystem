<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>诊断信息管理</title>
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
		<li class="active"><a href="${ctx}/hospital/hDiagnoseInfo/">诊断信息列表</a></li>
		<shiro:hasPermission name="hospital:hDiagnoseInfo:edit"><li><a href="${ctx}/hospital/hDiagnoseInfo/form">诊断信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hDiagnoseInfo" action="${ctx}/hospital/hDiagnoseInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<ul class="ul-form">
			<li><label>病人id：</label>
				<form:input path="patientId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>挂号单id：</label>
				<form:input path="registrationId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>医生id：</label>
				<form:input path="doctorId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>科室id：</label>
				<form:input path="deptId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>诊断症状：</label>
				<form:input path="symptom" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>诊断备注：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>病人id</th>
				<th>挂号单id</th>
				<th>医生id</th>
				<th>科室id</th>
				<th>诊断症状</th>
				<th>诊断日期</th>
				<th>诊断备注</th>
				<shiro:hasPermission name="hospital:hDiagnoseInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hDiagnoseInfo">
			<tr>
				<td><a href="${ctx}/hospital/hDiagnoseInfo/form?id=${hDiagnoseInfo.id}">
					${hDiagnoseInfo.patientId}
				</a></td>
				<td>
					${hDiagnoseInfo.registrationId}
				</td>
				<td>
					${hDiagnoseInfo.doctorId}
				</td>
				<td>
					${hDiagnoseInfo.deptId}
				</td>
				<td>
					${hDiagnoseInfo.symptom}
				</td>
				<td>
					<fmt:formatDate value="${hDiagnoseInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hDiagnoseInfo.remarks}
				</td>
				<shiro:hasPermission name="hospital:hDiagnoseInfo:edit"><td>
    				<a href="${ctx}/hospital/hDiagnoseInfo/form?id=${hDiagnoseInfo.id}">修改</a>
					<a href="${ctx}/hospital/hDiagnoseInfo/delete?id=${hDiagnoseInfo.id}" onclick="return confirmx('确认要删除该诊断信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>