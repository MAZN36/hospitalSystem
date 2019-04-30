<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>挂号信息管理</title>
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
		<li class="active"><a href="${ctx}/hospital/hRegistration/">挂号信息列表</a></li>
		<shiro:hasPermission name="hospital:hRegistration:edit"><li><a href="${ctx}/hospital/hRegistration/form">挂号信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hRegistration" action="${ctx}/hospital/hRegistration/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<ul class="ul-form">
			<li><label>挂号医师id：</label>
				<form:input path="doctorId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>症状：</label>
				<form:input path="registrationRemarks" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>挂号时间：</label>
				<input name="registrationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hRegistration.registrationDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>预约时间：</label>
				<input name="bookDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${hRegistration.bookDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>挂号科室：</label>
				<sys:treeselect id="office" name="office.id" value="${hRegistration.office.id}" labelName="office.name" labelValue="${hRegistration.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>排队号：</label>
				<form:input path="queueNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>挂号价格</th>
				<th>挂号医师id</th>
				<th>症状</th>
				<th>挂号时间</th>
				<th>预约时间</th>
				<th>挂号科室</th>
				<th>排队号</th>
				<th>状态</th>
				<th>状态时间</th>
				<shiro:hasPermission name="hospital:hRegistration:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hRegistration">
			<tr>
				<td><a href="${ctx}/hospital/hRegistration/form?id=${hRegistration.id}">
					${hRegistration.price}
				</a></td>
				<td>
					${hRegistration.doctorId}
				</td>
				<td>
					${hRegistration.registrationRemarks}
				</td>
				<td>
					<fmt:formatDate value="${hRegistration.registrationDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${hRegistration.bookDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hRegistration.office.name}
				</td>
				<td>
					${hRegistration.queueNo}
				</td>
				<td>
					${fns:getDictLabel(hRegistration.sts, 'sts', '')}
				</td>
				<td>
					<fmt:formatDate value="${hRegistration.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="hospital:hRegistration:edit"><td>
    				<a href="${ctx}/hospital/hRegistration/form?id=${hRegistration.id}">修改</a>
					<a href="${ctx}/hospital/hRegistration/delete?id=${hRegistration.id}" onclick="return confirmx('确认要删除该挂号信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>