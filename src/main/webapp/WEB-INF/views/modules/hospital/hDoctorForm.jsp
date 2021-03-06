<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>医生信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#no").focus();
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
		<li><a href="${ctx}/hospital/hDoctor/">医生信息列表</a></li>
		<li class="active"><a href="${ctx}/hospital/hDoctor/form?id=${hDoctor.id}">医生信息<shiro:hasPermission name="hospital:hDoctor:edit">${not empty hDoctor.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hospital:hDoctor:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hDoctor" action="${ctx}/hospital/hDoctor/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="user.id"/>
		<form:hidden path="user.oldLoginName"/>
		<input type="hidden">
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">头像:</label>
            <div class="controls">
                <form:hidden id="nameImage" path="user.photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">科室：</label>
            <div class="controls">
                <sys:treeselect id="office" name="user.office.id" value="${hDoctor.user.office.id}" labelName="user.office.name" labelValue="${hDoctor.user.office.name}"
                                title="科室" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">工号：</label>
            <div class="controls">
                <form:input path="jobNumber" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">姓名:</label>
            <div class="controls">
                <form:input path="user.name" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">工龄：</label>
            <div class="controls">
                <form:input path="workYear" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">年龄：</label>
            <div class="controls">
                <form:input path="user.age" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">性别：</label>
            <div class="controls">
                <form:select path="user.sex" class="input-xlarge ">
                    <form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">身份证号：</label>
            <div class="controls">
                <form:input path="user.idCard" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">职称：</label>
            <div class="controls">
                <form:select path="jobName" class="input-xlarge ">
                    <form:options items="${fns:getDictList('job_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">学历：</label>
            <div class="controls">
                <form:select path="education" class="input-xlarge ">
                    <form:options items="${fns:getDictList('education_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">邮箱:</label>
            <div class="controls">
                <form:input path="user.email" htmlEscape="false" maxlength="100" class="input-xlarge email"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">电话:</label>
            <div class="controls">
                <form:input path="user.phone" htmlEscape="false" maxlength="100" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">手机:</label>
            <div class="controls">
                <form:input path="user.mobile" htmlEscape="false" maxlength="100" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">是否允许登录:</label>
            <div class="controls">
                <form:select path="sts">
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
                <span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">介绍：</label>
            <div class="controls">
                <form:textarea path="introduce" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注：</label>
            <div class="controls">
                <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
            </div>
        </div>
		<div class="form-actions">
			<shiro:hasPermission name="hospital:hDoctor:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>