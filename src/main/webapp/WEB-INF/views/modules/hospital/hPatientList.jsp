<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>病人信息管理</title>
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
		<li class="active"><a href="${ctx}/hospital/hPatient/">病人信息列表</a></li>
		<shiro:hasPermission name="hospital:hPatient:edit"><li><a href="${ctx}/hospital/hPatient/form">病人信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hPatient" action="${ctx}/hospital/hPatient/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>手机</th>
				<th>身份证号</th>
				<th>年龄</th>
				<th>性别</th>
				<th>血型</th>
                <th>学历</th>
                <th>固定电话</th>
                <th>家庭住址</th>
                <th>邮箱</th>
                <th>是否允许登录</th>
				<th>状态时间</th>
				<th>备注</th>
				<shiro:hasPermission name="hospital:hPatient:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hPatient">
			<tr>
				<td><a href="${ctx}/hospital/hPatient/form?id=${hPatient.id}">
					${hPatient.user.name}
				</a></td>
                <td>
                    ${hPatient.user.mobile}
                </td>
                <td>
                    ${hPatient.user.idCard}
                </td>
                <td>
                    ${hPatient.user.age}
                </td>
                <td>
                    ${fns:getDictLabel(hPatient.user.sex, 'sex', '')}
                </td>
                <td>
                    ${fns:getDictLabel(hPatient.bloodType, 'blood_type', '')}
                </td>
                <td>
                    ${fns:getDictLabel(hPatient.education, 'education_type', '')}
                </td>
                <td>
                    ${hPatient.user.phone}
                </td>
				<td>
					${hPatient.address}
				</td>
                <td>
					${hPatient.user.email}
				</td>
				<td>
                        ${fns:getDictLabel(hPatient.sts, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${hPatient.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hPatient.remarks}
				</td>
				<shiro:hasPermission name="hospital:hPatient:edit"><td>
    				<a href="${ctx}/hospital/hPatient/form?id=${hPatient.id}">修改</a>
					<a href="${ctx}/hospital/hPatient/delete?id=${hPatient.id}" onclick="return confirmx('确认要删除该病人信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>