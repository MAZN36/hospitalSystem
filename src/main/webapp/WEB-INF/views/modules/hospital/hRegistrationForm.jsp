<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>挂号信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hospital/hRegistration/">挂号信息列表</a></li>
		<li class="active"><a href="${ctx}/hospital/hRegistration/form?id=${hRegistration.id}">挂号信息<shiro:hasPermission name="hospital:hRegistration:edit">${not empty hRegistration.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hospital:hRegistration:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hRegistration" action="${ctx}/hospital/hRegistration/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">挂号价格：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">挂号医师id：</label>
			<div class="controls">
				<form:input path="doctorId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">症状：</label>
			<div class="controls">
				<form:input path="registrationRemarks" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">挂号时间：</label>
			<div class="controls">
				<input name="registrationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${hRegistration.registrationDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预约时间：</label>
			<div class="controls">
				<input name="bookDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${hRegistration.bookDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">挂号科室：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${hRegistration.office.id}" labelName="office.name" labelValue="${hRegistration.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排队号：</label>
			<div class="controls">
				<form:input path="queueNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="sts" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sts')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="hospital:hRegistration:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>