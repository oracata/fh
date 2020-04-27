﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<%@ include file="top.jsp"%>

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
									lalala
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

				$(top.hangge());

				//检索
				function search(){
					top.jzts();
					$("#Form").submit();
				}

				//新增
				function add(){
					top.jzts();
					var diag = new top.Dialog();
					diag.Drag=true;
					diag.Title ="新增";
					diag.URL = '<%=basePath%>pictures/goAdd.do';
					diag.Width = 800;
					diag.Height = 490;
					diag.CancelEvent = function(){ //关闭事件
						if('${page.currentPage}' == '0'){
							top.jzts();
							setTimeout("self.location=self.location",100);
						}else{
							nextPage(${page.currentPage});
						}
						diag.close();
					};
					diag.show();
				}

				//删除
				function del(Id,PATH){

					if(confirm("确定要删除?")){
						top.jzts();
						var url = "<%=basePath%>pictures/delete.do?PICTURES_ID="+Id+"&PATH="+PATH+"&tm="+new Date().getTime();
						$.get(url,function(data){
							nextPage(${page.currentPage});
						});
					}
				}

				//修改
				function edit(Id){
					top.jzts();
					var diag = new top.Dialog();
					diag.Drag=true;
					diag.Title ="编辑";
					diag.URL = '<%=basePath%>pictures/goEdit.do?PICTURES_ID='+Id;
					diag.Width = 600;
					diag.Height = 465;
					diag.CancelEvent = function(){ //关闭事件
						if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
							nextPage(${page.currentPage});
						}
						diag.close();
					};
					diag.show();
				}
			</script>

			<script type="text/javascript">

				//全选 （是/否）
				function selectAll(){
					var checklist = document.getElementsByName ("ids");
					if(document.getElementById("zcheckbox").checked){
						for(var i=0;i<checklist.length;i++){
							checklist[i].checked = 1;
						}
					}else{
						for(var j=0;j<checklist.length;j++){
							checklist[j].checked = 0;
						}
					}
				}



				//批量操作
				function makeAll(msg){

					if(confirm(msg)){

						var str = '';
						for(var i=0;i < document.getElementsByName('ids').length;i++)
						{
							if(document.getElementsByName('ids')[i].checked){
								if(str=='') str += document.getElementsByName('ids')[i].value;
								else str += ',' + document.getElementsByName('ids')[i].value;
							}
						}
						if(str==''){
							alert("您没有选择任何内容!");
							return;
						}else{
							if(msg == '确定要删除选中的数据吗?'){
								top.jzts();
								$.ajax({
									type: "POST",
									url: '<%=basePath%>pictures/deleteAll.do?tm='+new Date().getTime(),
									data: {DATA_IDS:str},
									dataType:'json',
									//beforeSend: validateData,
									cache: false,
									success: function(data){
										$.each(data.list, function(i, list){
											nextPage(${page.currentPage});
										});
									}
								});
							}
						}
					}
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
