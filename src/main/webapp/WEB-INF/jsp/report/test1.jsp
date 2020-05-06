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

    <!-- 引入 -->
    <script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
    <script src="static/js/bootstrap.min.js"></script>
    <script src="static/js/ace-elements.min.js"></script>
    <script src="static/js/ace.min.js"></script>
    <!-- 引入 -->

</head>






<body>


	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">







						<div class="tabbable" id="userTab">
							<ul class="nav nav-tabs">
								<li class="active">
									<a data-toggle="tab"  href="#userchartTab">
										区域分布
									</a>
								</li>
								<li>
									<a data-toggle="tab" href="#listUserTab">
                                        数据
									</a>
								</li>
							</ul>
							<div class="tab-content">


							     	<div id="userchartTab" class="tab-pane fade in active">

									aaaaaaaaaaa

								</div>



								<div id="listUserTab" class="tab-pane fade" >
                                    <!-- 检索  -->
                                    <form  method="post" name="reportForm" id="reportForm">
                                        <table>
                                            <tr>

                                                <td><input    class="span10 date-picker" name="begin_date" id="begin_date"  value="${reportday_con.begin_date}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="请选择"/></td>
                                                <td><input    class="span10 date-picker" name="end_date" id="end_date"  value="${reportday_con.end_date}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="请选择"/></td>


                                                <td style="vertical-align:top;"><button class="btn btn-mini btn-light"   title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
                                            </tr>
                                        </table>
										<input type="hidden" name="shengfen" id="shengfen" value="${reportday_con.shengfen }"/>
										<input type="hidden" name="chengshi" id="chengshi" value="${reportday_con.chengshi }"/>
										<input type="hidden" name="quyufl" id="quyufl" value="${reportday_con.quyufl }"/>

                                    </form>




                                    <div id="table-responsive-width"  >
                                        <table   id="dataGrid">
                                        </table>
                                    </div>
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
						method:"POST",
						//极为重要，缺失无法执行queryParams，传递page参数
						contentType : "application/x-www-form-urlencoded",
						dataType:"json",
						columns: [   {
							field: 'chengshi',
							title: '地市',
						}, {
							field: 'quyufl',
							title: '区县',
						}
							, {
								field: 'custom_num',
								title: '客户数',
							}
							, {
								field: 'login_rate',
								title: '登录率',
							}
							, {
								field: 'rq',
								title: '日期',
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
					 	pageSize: 20,//每页数据
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
							sortOrder : this.sortOrder,
                            begin_date: $("#begin_date").val(),
                            end_date:$("#end_date").val(),
							shengfen:$("#shengfen").val(),
							chengshi:$("#chengshi").val(),
							quyufl:$("#quyufl").val(),
						};

						return param;
					}

				});



			</script>



    <script type="text/javascript">

        $(function() {

            //日期框
            $('.date-picker').datepicker();


            //复选框
            $('table th input:checkbox').on('click' , function(){
                var that = this;
                $(this).closest('table').find('tr > td:first-child input:checkbox')
                    .each(function(){
                        this.checked = that.checked;
                        $(this).closest('tr').toggleClass('selected');
                    });

            });

        });

        //导出excel
        function toExcel(){
            var USERNAME = $("#nav-search-input").val();
            var lastLoginStart = $("#lastLoginStart").val();
            var lastLoginEnd = $("#lastLoginEnd").val();
            var ROLE_ID = $("#role_id").val();
            window.location.href='<%=basePath%>user/excel.do?USERNAME='+USERNAME+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&ROLE_ID='+ROLE_ID;
        }

        //打开上传excel页面
        function fromExcel(){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="EXCEL 导入到数据库";
            diag.URL = '<%=basePath%>user/goUploadExcel.do';
            diag.Width = 300;
            diag.Height = 150;
            diag.CancelEvent = function(){ //关闭事件
                if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                    if('${page.currentPage}' == '0'){
                        top.jzts();
                        setTimeout("self.location.reload()",100);
                    }else{
                        nextPage(${page.currentPage});
                    }
                }
                diag.close();
            };
            diag.show();
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
