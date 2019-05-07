<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费单信息详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
        function submitForm() {
			var paymentType = $("#paymentType").val();
			return "${ctx}/hospital/hCharge/saveCharge?id=${hCharge.id}&&paymentType="+paymentType;
		}
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="hCharge" action="${ctx}/hospital/hCharge/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<tbody>
		<tr><strong>病人信息</strong></tr>
		<tr>
			<td>
				<strong>病人姓名：</strong>${hCharge.userName}
			</td>
			<td>
				<strong>病人症状：</strong>${diagnoseInfo.symptom}
			</td>
			<td>
				<strong>确诊病状：</strong>${diagnoseInfo.diagnoses}
			</td>
		</tr>
		</tbody>
	</table>
	<table class="table table-striped table-bordered table-condensed">
		<tbody>
		<tr><strong>挂号信息</strong></tr>
		<tr>
			<td>
				<strong>预约时间：</strong><fmt:formatDate value="${hRegistration.bookDate}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
				<strong>医生名称：</strong>${doctor.user.name}
			</td>
			<td>
				<strong>科室名称：</strong>${hRegistration.office.name}
			</td>
		</tr>
		</tbody>
	</table>
	<table class="table table-striped table-bordered table-condensed">
		<tbody>
		<tr><strong>收费信息</strong></tr>
		<tr>
			<td><strong>预约挂号费用：</strong>${hRegistration.price}元</td>
			<td><strong>处方费用：</strong>${hCharge.receivePrice-hRegistration.price}元</td>
			<td><strong>总费用：</strong>${hCharge.receivePrice}元</td>
		</tr>
		</tbody>
	</table>
	<table class="table table-striped table-bordered table-condensed">
		<tbody>
		<tr><strong>处方详情信息</strong></tr>
		<c:forEach items="${prescriptInfoList}" var="prescriptInfo">
			<tr>
				<td>
					<strong>药品名称：</strong>${prescriptInfo.drugInfo.drugName}
				</td>
				<td>
					<strong>药品单价：</strong>${prescriptInfo.drugInfo.sellPrice}元
				</td>
				<td>
					<strong>药品数量：</strong>${prescriptInfo.durgNum}个
				</td>
				<td>
					<strong>药品总价：</strong><fmt:formatNumber type="number" value="${prescriptInfo.drugInfo.sellPrice*prescriptInfo.durgNum}" pattern="0.00" maxFractionDigits="2"/>元
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table class="table table-striped table-bordered table-condensed">
		<tbody>
			<tr><strong>付款信息</strong></tr>
			<tr>
				<td>
					<strong>支付方式：</strong>
					<c:choose>
						<c:when test="${hCharge.sts eq 'A'}">
							<form:select path="paymentType" id="paymentType" class="input-xlarge ">
								<form:options items="${fns:getDictList('payment_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(hCharge.paymentType, 'payment_type', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<strong>实收费用：</strong>${hCharge.proceedsPrice}元
				</td>
			</tr>
		</tbody>
	</table>
	</form:form>
</body>
</html>