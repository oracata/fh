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

	<link rel="stylesheet" href="static/css/my-responsive.css" />






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

							         <div id="table-responsive-width"  >
									     <table   id="dataGrid">
									     </table>
								     </div>

								</div>

								<div id="roleUserTab" class="tab-pane fade" >
								aaaaaaaaaaaa
								</div>

								<div id="listUserTab" class="tab-pane fade" >
									aaaaaaaaaaa
								</div>



							</div>
						</div>


				</div>




			</div><!--/.fluid-container#main-container-->



			<script type="text/javascript">
				//加载提示隐藏
				$(top.hangge());

				$(function () {
					$('#dataGrid').bootstrapTable({
						columns: [  {
							field: 'name',
							title: '姓名',
						}, {
							field: 'ip',
							title: 'ip',
						}
							, {
								field: 'last_LOGIN',
								title: 'last_LOGIN',
							}
							, {
								field: 'user_ID',
								title: 'user_ID',
							}
							, {
								field: 'username',
								title: 'username',
							}
						],
						showToggle:true,
						showRefresh: true,
						locale:'zh-CN',//中文支持
						//页面需要展示的列，后端交互对象的属性
						pagination: true,  //开启分页
						sidePagination: 'server',
						pageNumber: 1,//默认加载页
						pageSize: 2,//每页数据
						pageList: [2,10,15,20],//可选的每页数据


						url: 'report/listtest1', //服务器数据的加载地址
						responseHandler:function(res){
							console.log(JSON.stringify(res.rows));
							return{                            //return bootstrap-table能处理的数据格式
								"total":res.total,
								"rows":res.rows
							}


						}
					});

				});



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
