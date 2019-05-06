<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>医生信息管理</title>
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
		<li class="active"><a href="${ctx}/hospital/hDoctor/">医生信息列表</a></li>
		<shiro:hasPermission name="hospital:hDoctor:edit"><li><a href="${ctx}/hospital/hDoctor/form">医生信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hDoctor" action="${ctx}/hospital/hDoctor/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<ul class="ul-form">
			<li><label>工号：</label>
				<form:input path="jobNumber" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>工号</th>
				<th>科室</th>
				<th>工龄</th>
				<th>职称</th>
				<th>学历</th>
				<th>年龄</th>
				<th>性别</th>
				<th>电话</th>
				<th>手机</th>
				<th>邮箱</th>
				<th>是否允许登录</th>
				<th>状态时间</th>
				<th>介绍</th>
				<th>备注</th>
				<shiro:hasPermission name="hospital:hDoctor:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hDoctor">
			<tr>
				<td><a href="${ctx}/hospital/hDoctor/form?id=${hDoctor.id}">
					${hDoctor.user.name}
				</a></td>
				<td>
					${hDoctor.jobNumber}
				</td>
				<td>
					${hDoctor.user.office.name}
				</td>
				<td>
					${hDoctor.workYear}
				</td>
				<td>
					${fns:getDictLabel(hDoctor.jobName, 'job_type', '')}
				</td>
				<td>
					${fns:getDictLabel(hDoctor.education, 'education_type', '')}
				</td>
				<td>
					${hDoctor.user.age}
				</td>
				<td>
					${fns:getDictLabel(hDoctor.user.sex, 'sex', '')}
				</td>
				<td>
					${hDoctor.user.phone}
				</td>
				<td>
					${hDoctor.user.mobile}
				</td>
				<td>
					${hDoctor.user.email}
				</td>
				<td>
					${fns:getDictLabel(hDoctor.sts, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${hDoctor.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hDoctor.introduce}
				</td>
				<td>
					${hDoctor.remarks}
				</td>
				<shiro:hasPermission name="hospital:hDoctor:edit"><td>
    				<a href="${ctx}/hospital/hDoctor/form?id=${hDoctor.id}">修改</a>
					<a href="${ctx}/hospital/hDoctor/delete?id=${hDoctor.id}" onclick="return confirmx('确认要删除该医生信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>