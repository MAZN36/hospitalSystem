<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费单信息管理</title>
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
		function chargeInfoList(id) {
			top.$.jBox.open("iframe:${ctx}/hospital/hCharge/chargeInfoList?id="+id, "收费信息",810,$(top.document).height()-240,{
				showClose : false, //移除右上角的关闭按钮
				persistent: true, //点击窗口外不关闭
				buttons:{"收费":"ok", "关闭":false},submit:function(v, h, f){
					if (v=="ok"){
						var submitUrl = h.find("iframe")[0].contentWindow.submitForm();
						$.ajax(submitUrl);
						window.location.reload();
						return true;
					}else {
						return confirm("你确定要取消收费吗？",function () {
							return true;
						});
					}
				}
			});
		}
		function chargeInfo(id) {
			top.$.jBox.open("iframe:${ctx}/hospital/hCharge/chargeInfoList?id="+id, "收费信息",810,$(top.document).height()-240,{
				showClose : false, //移除右上角的关闭按钮
				persistent: true, //点击窗口外不关闭
				buttons:{"关闭":false},submit:function(v, h, f){
					return true;
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hospital/hCharge/">收费单信息列表</a></li>
		<%--<shiro:hasPermission name="hospital:hCharge:edit"><li><a href="${ctx}/hospital/hCharge/form">收费单信息添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="hCharge" action="${ctx}/hospital/hCharge/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<%--	<ul class="ul-form">
			<li><label>病人id：</label>
				<form:input path="patientId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>病例id：</label>
				<form:input path="diagnoseId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>病人姓名</th>
				<th>身份证号</th>
				<th>流水号</th>
				<th>应收费用</th>
				<th>实收费用</th>
				<th>支付方式</th>
				<th>收费日期</th>
				<th>状态</th>
				<th>状态日期</th>
				<th>备注</th>
				<shiro:hasPermission name="hospital:hCharge:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hCharge">
			<tr>
				<td>
					<%--${hCharge.patientId}--%>
					${hCharge.userName}
				</td>
				<td>
					${hCharge.idCard}
				</td>
				<td>
					${hCharge.paymentNo}
				</td>
				<td>
					${hCharge.receivePrice}
				</td>
				<td>
					${hCharge.proceedsPrice}
				</td>
				<td>
					${fns:getDictLabel(hCharge.paymentType, 'payment_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${hCharge.paymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(hCharge.sts, 'pay_sts', '')}
				</td>
				<td>
					<fmt:formatDate value="${hCharge.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hCharge.remarks}
				</td>
				<shiro:hasPermission name="hospital:hCharge:edit"><td>
					<c:choose>
						<c:when test="${hCharge.sts eq 'A'}">
							<a href="javascript:chargeInfoList('${hCharge.id}')">收费</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:chargeInfo('${hCharge.id}')">详情</a>
						</c:otherwise>
					</c:choose>
    				<%--<a href="${ctx}/hospital/hCharge/form?id=${hCharge.id}">修改</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>