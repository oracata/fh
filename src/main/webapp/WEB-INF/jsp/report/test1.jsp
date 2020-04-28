<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- jsp文件头和头部 -->
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file=".././system/admin/top.jsp"%>

</head>
<body>


	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">


			<div class="row-fluid">

				<div class="row-fluid">




						<div class="tabbable" id="userTab">
							<ul class="nav nav-tabs">
								<li class="active">
									<a data-toggle="tab" href="#userchartTab">
										用户分布
									</a>
								</li>
								<li>
									<a data-toggle="tab" href="#roleUserTab">
										用户角色分布
									</a>
								</li>
								<li>
									<a data-toggle="tab" href="#listUserTab">
										用户列表
									</a>
								</li>
							</ul>
							<div class="tab-content">
								<div id="userchartTab" class="tab-pane fade in active">
									<table id="dataGrid">
									</table>
								</div>

								<div id="roleUserTab" class="tab-pane fade" >
									aaaaaa
								</div>

								<div id="listUserTab" class="tab-pane fade" >
									lllllllllllllll
								</div>



							</div>
						</div>


				</div>




			</div><!--/.fluid-container#main-container-->


			<!-- 引入 -->
			<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
			<script src="static/js/bootstrap.min.js"></script>
			<script src="static/js/ace-elements.min.js"></script>
			<script src="static/js/ace.min.js"></script>
			<!-- 引入 -->



			<script type="text/javascript">

				$(function() {
					//初始加载
					initDataGrid();
				});
				function initDataGrid() {
					//创建bootstrapTable
					$("#dataGrid").bootstrapTable({
						method:"POST",
						//极为重要，缺失无法执行queryParams，传递page参数
						contentType : "application/x-www-form-urlencoded",
						dataType:"json",
						url:'report/test1',
						queryParams:queryParam,
						pagination:true,//显示分页条：页码，条数等
						striped:true,//隔行变色
						pageNumber:1,//首页页码
						pageSize:10,//分页，页面数据条数
						uniqueId:"id",//Indicate an unique identifier for each row
						sidePagination:"server",//在服务器分页
						height:tableModel.getHeight(),
						sortName:"created_at",
						toolbar:"#toolbar",//工具栏
						columns : [{
							checkbox:"true",
							field : "box"
						}, {
							title : "姓名",
							field : "name"
						}
						],
						search : true,//搜索
						searchOnEnterKey : true,
						showRefresh : true,//刷新
						showToggle : true//

					});
				}
				function queryParam(params){

					var param = {
						limit : this.limit, // 页面大小
						offset : this.offset, // 页码
						pageNumber : this.pageNumber,
						pageSize : this.pageSize,
						sortName : this.sortName,
						sortOrder : this.sortOrder
					};
					return param;
				}
				//点击取消后清空表单中已写信息
				function resetAddModal(){
					document.getElementById("addUserForm").reset();
				}
				//新增用户
				function addUser(){
					var param = $("#addUserForm").serializeArray();

					$("#conf").attr("onclick","addUser()");
					$.ajax({
						url:"/website/user/addUser",
						method:"post",
						data:param,
						dataType:"json",
						success:function(data){
							if(data.state=="success"){
								document.getElementById("al").innerText="新增成功";
								$("#addEnd").modal('show');
								$("#addUserModal").modal('hide');
								$("#dataGrid").bootstrapTable('refresh');
							}
						},
						error:function(){
							document.getElementById("al").innerText="新增失败";
							$("#addEnd").modal('show');
						}
					});
				}
				//修改用户
				function editUser(){
					//获取选中行的数据
					var rows = $("#dataGrid").bootstrapTable('getSelections');
					if(rows.length!=1){
						document.getElementById("tipContent").innerText="请选择一行数据";
						$("#Tip").modal('show');
					}
					else{
						var row = rows[0];
						$('#editId').val(row.id);
						$('#editAccount').val(row.account);
						$('#editPassword').val(row.password);
						$('#editName').val(row.name);
						$('#editSex').val(row.sex);
						$('#editEmail').val(row.email);
						$('#editPhone').val(row.phone);
						$('#editStates').val(row.states);
						$('#editCreated_at').val(row.created_at);
						$("#editModal").modal("show");
					}
				}
				function updateUser(){
					var param = $("#editForm").serializeArray();
					//设为disable则无法获取
					$.ajax({
						url:"/website/user/updateUser",
						method:"post",
						data:param,
						dataType:"json",
						success:function(data){
							if(data.state=="success"){
								$("#editModal").modal("hide");
								document.getElementById("tipContent").innerText="修改成功";
								$("#Tip").modal('show');
								$("#dataGrid").bootstrapTable("refresh");
							}
						},
						error:function(data){
							alert("wrong");
						}
					});
				}
				function deleteUser(){
					var rows = $("#dataGrid").bootstrapTable("getSelections");
					var ids = [];
					var len = rows.length;

					for(var i=0;i<len;i++){
						ids.push(rows[i].id);
					}

					$.ajax({
						url:"/website/user/deleteUser",
						dataType:"json",
						traditional: true,//属性在这里设置
						method:"post",
						data:{
							"ids":ids
						},
						success:function(data){
							document.getElementById("tipContent").innerText="删除成功";
							$("#Tip").modal('show');
							$("#dataGrid").bootstrapTable("refresh");
						},
						error:function(){
							document.getElementById("tipContent").innerText="删除失败";
							$("#Tip").modal('show');
						}
					});
				}

				//导出excel
				function toExcel(){
					window.location.href='<%=basePath%>pictures/excel.do';
				}
			</script>
			<style type="text/css">
				li {list-style-type:none;}
			</style>
			<ul class="navigationTabs">
				<li><a></a></li>
				<li></li>
			</ul>
</body>
</html>
