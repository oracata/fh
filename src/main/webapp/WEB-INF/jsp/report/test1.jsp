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







						<div class="tabbable" id="userTab">
							<ul class="nav nav-tabs">
								<li class="active">
									<a data-toggle="tab"  href="#userchartTab">
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

										<!-- 检索  -->
										<form action="user/listUsers.do" method="post" name="userForm" id="userForm">
											<table>
												<tr>

													<td><input    class="span10 date-picker" name="lastLoginStart" id="lastLoginStart"  value="${lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="最近登录开始"/></td>
													<td><input    class="span10 date-picker" name="lastLoginEnd" name="lastLoginEnd"  value="${lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="最近登录结束"/></td>


												</tr>
											</table>
										</form>




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
						columns: [ {
							checkbox:"true",
							field : "box"
						},  {
							title : "id",
							field : "USER_ID",
							visible: false
						},  {
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
						//search : true,//搜索
						//showToggle:true,
						//showRefresh: true,
						locale:'zh-CN',//中文支持
						//页面需要展示的列，后端交互对象的属性
						pagination: true,  //开启分页
						sidePagination: 'server',
				    	pageNumber: 1,//默认加载页
					 	pageSize: 5,//每页数据
					   // pageList: [5,10,15,20],//可选的每页数据


						url: 'report/listtest1', //服务器数据的加载地址
						queryParams:queryParam,
						responseHandler:function(res){
							console.log(JSON.stringify(res.rows));
							return{                            //return bootstrap-table能处理的数据格式
								"total":res.total,
								"rows":res.rows
							}


						}
					});

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

				});



			</script>

			<script>
			//检索
			function search(){
			top.jzts();
			$("#userForm").submit();
			}


			</script>

			<script type="text/javascript">
					$(function() {
						//日期框
						$('.date-picker').datepicker();
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
