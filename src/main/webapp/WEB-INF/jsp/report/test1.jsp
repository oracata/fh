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



			<script type="text/javascript">


				$(function () {
					$('#dataGrid').bootstrapTable({
						columns: [  {
							field: 'name',
							title: '姓名',
						}
						],
						showToggle:true,
						showRefresh: true,
						locale:'zh-CN',//中文支持
						//页面需要展示的列，后端交互对象的属性
						pagination: true,  //开启分页
						sidePagination: 'server',
						pageNumber: 1,//默认加载页
						pageSize: 10,//每页数据
						pageList: [5,10,15,20],//可选的每页数据
						queryParamsType:'',//queryParamsType的默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
						//设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber */
						queryParams: function (params) {
							var param = {
								limit : this.limit, // 页面大小
								offset : this.offset, // 页码
								pageNumber : this.pageNumber,
								pageSize : this.pageSize,
								sortName : this.sortName,
								sortOrder : this.sortOrder
							};
							return param;
						},//请求服务器数据时的参数
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
