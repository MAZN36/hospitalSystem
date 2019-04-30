<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>药品信息管理</title>
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
		<li class="active"><a href="${ctx}/hospital/hDrugInfo/">药品信息列表</a></li>
		<shiro:hasPermission name="hospital:hDrugInfo:edit"><li><a href="${ctx}/hospital/hDrugInfo/form">药品信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hDrugInfo" action="${ctx}/hospital/hDrugInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<%--	<ul class="ul-form">
			<li><label>药品编号：</label>
				<form:input path="drugNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>药品名称：</label>
				<form:input path="drugName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>药品编号</th>
				<th>药品名称</th>
				<th>计量单位</th>
				<th>条形码</th>
				<th>规格</th>
				<th>生产厂家</th>
				<th>国家地区</th>
				<th>药品描述</th>
				<th>药品进价</th>
				<th>药品售价</th>
				<th>库存数量</th>
				<th>状态</th>
				<th>状态时间</th>
				<th>备注</th>
				<shiro:hasPermission name="hospital:hDrugInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hDrugInfo">
			<tr>
				<td><a href="${ctx}/hospital/hDrugInfo/form?id=${hDrugInfo.id}">
					${hDrugInfo.drugNo}
				</a></td>
				<td>
					${hDrugInfo.drugName}
				</td>
				<td>
					${hDrugInfo.unit}
				</td>
				<td>
					${hDrugInfo.code}
				</td>
				<td>
					${hDrugInfo.spec}
				</td>
				<td>
					${hDrugInfo.produceArea}
				</td>
				<td>
					${hDrugInfo.madeArea}
				</td>
				<td>
					${hDrugInfo.grugRemarks}
				</td>
				<td>
					${hDrugInfo.rentPrice}
				</td>
				<td>
					${hDrugInfo.sellPrice}
				</td>
				<td>
					${hDrugInfo.inventoryNum}
				</td>
				<td>
					${hDrugInfo.sts}
				</td>
				<td>
					<fmt:formatDate value="${hDrugInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hDrugInfo.remarks}
				</td>
				<shiro:hasPermission name="hospital:hDrugInfo:edit"><td>
    				<a href="${ctx}/hospital/hDrugInfo/form?id=${hDrugInfo.id}">修改</a>
					<a href="${ctx}/hospital/hDrugInfo/delete?id=${hDrugInfo.id}" onclick="return confirmx('确认要删除该药品信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>