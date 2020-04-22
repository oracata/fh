<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    pageContext.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>部门管理</title>

    <script src="https://cdn.fusioncharts.com/fusioncharts/fusioncharts.powercharts.js"></script>


    <script src="https://cdn.fusioncharts.com/fusioncharts/latest/fusioncharts.js"></script>


    <script src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.theme.fusion.js"></script>





    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>

<!-- 修改 模态框  start-->
<div class="modal fade" id="editDeptModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">修改部门</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="dept_add_form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">部门名称</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="dname"
                                   placeholder="输入部门名称" id="dname">
                        </div>
                        <span class="col-sm-12"
                              style="line-height: 32px; font-size: 16px;"></span>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-6">
                            <textarea class="form-control" rows="3" id="dremark" name="dremark"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="dept_save_edit">更改</button>
            </div>
        </div>
    </div>
</div>
<!-- 修改 模态框  end-->

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <h3>部门管理</h3>
        </div>
    </div>
    <hr>
    <ul id="deptTab" class="nav nav-tabs">
        <li class="active" id="nav_dept_info"><a href="#dept_info" data-toggle="tab">
            部门信息 </a></li>
        <li id="nav_dept_add"><a href="#dept_add" data-toggle="tab">部门添加</a></li>
    </ul>
    <br>
    <div id="deptTab" class="tab-content">
        <!-- 部门信息:start -->
        <div class="tab-pane fade in active" id="dept_info">
            <div class="col-md-12">
                <table class="table table-hover" id="dept_info_table">

                    <tr>
                        <td>
                            <div id="chart-container"></div>
                            <script type="text/javascript">
                                const dataSource = {
                                    chart: {
                                        caption: "Countries With Most Oil Reserves [2017-18]",
                                        subcaption: "In MMbbl = One Million barrels",
                                        xaxisname: "Country",
                                        yaxisname: "Reserves (MMbbl)",
                                        numbersuffix: "K",
                                        theme: "fusion"
                                    },
                                    data: [
                                        {
                                            label: "Venezuela",
                                            value: "290"
                                        },
                                        {
                                            label: "Saudi",
                                            value: "260"
                                        },
                                        {
                                            label: "Canada",
                                            value: "180"
                                        },
                                        {
                                            label: "Iran",
                                            value: "140"
                                        },
                                        {
                                            label: "Russia",
                                            value: "115"
                                        },
                                        {
                                            label: "UAE",
                                            value: "100"
                                        },
                                        {
                                            label: "US",
                                            value: "30"
                                        },
                                        {
                                            label: "China",
                                            value: "30"
                                        }
                                    ]
                                };

                                FusionCharts.ready(function() {
                                    var myChart = new FusionCharts({
                                        type: "column2d",
                                        renderAt: "chart-container",
                                        width: "100%",
                                        height: "100%",
                                        dataFormat: "json",
                                        dataSource
                                    }).render();
                                });

                            </script>


                        </td>
                    </tr>

                </table>
            </div>
        </div>
        <!-- 部门信息:end -->
        <!-- 增加部门：start -->
        <div class="tab-pane fade" id="dept_add">
            <form class="form-horizontal" id="dept_add_form">
                <div class="form-group">
                    <label class="col-sm-1 control-label">部门名称</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="dname"
                               placeholder="输入部门名称" id="dname" required>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-1 control-label">备注</label>
                    <div class="col-sm-4">
                        <textarea class="form-control" rows="3" id="dremark" name="dremark"></textarea>
                    </div>
                </div>

                <div class="form-group col-sm-5" style="text-align: right;">
                    <button type="button" class="btn btn-default"
                            id="dept_add_btn">添加</button>
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
            </form>
        </div>
        <!-- 增加部门：end -->
    </div>
</div>

<script type="text/javascript">
    $(function() {
        getAllDepts();
    });
    /* 部门显示 */
    function getAllDepts(){
        $.ajax({
            url:"${path }/dept/list",
            type:"GET",
            success:function(result){
                console.log(result);
                show_depts_tobody(result);
            }
        });
    }
    /* 解析数据 */
    /*
    function show_depts_tobody(result){
        $("#dept_info_table  tbody").empty();//清空原有的body
        var depts=result.extend.depts;//
        $.each(depts,function(index,item){
            var did=item.did;
            var num=$("<td></td>").append(index+1);//<td>index</td>
            var nameTd=$("<td></td>").append(item.dname);
            var remarkTd=$("<td></td>").append(item.dremark);
            var editBtn = $("<button></button>").addClass(
                "btn btn-primary btn-xs").append("<span></span>")
                .addClass("glyphicon glyphicon-pencil").append("编辑")
                .attr("id", "dept_edit_btn").attr("edit-id", did);
            var delBtn = $("<button></button>").addClass(
                "btn btn-danger btn-xs delBtn").append("<span></span>")
                .addClass("glyphicon glyphicon-trash").append("删除")
                .css("margin-left", "10px").attr("del-id", did).attr(
                    "id", "del_dept_btn");
            var editTD=$("<td></td>").append(editBtn).append(delBtn);
            $("<tr></tr>").append(num).append(nameTd).append(remarkTd).append(editTD)
                .appendTo("#dept_info_table  tbody");
        });
    }
    */
    /* 点击添加按钮 */
    $("#dept_add_btn").click(function(){
        if($("#dept_add #dname").val()==""){
            alert("请输入部门名称");
            return;
        }
        $.ajax({
            url:"${path }/dept/add",
            data:$("#dept_add #dept_add_form").serialize(),
            type:"POST",
            success:function(result){
                if(result.code==100){
                    $("#dept_add_form")[0].reset();
                    getAllDepts();
                    $("#nav_dept_info").addClass("active");
                    $("#dept_info").addClass("active in");
                    $("#nav_dept_add").removeClass("active");
                    $("#dept_add").removeClass("active in");
                }
            }
        });
    });
    /* 点击删除按钮 */
    $(document).on("click","#del_dept_btn",function(){
        var id=$(this).attr("del-id");
        var name=$(this).parents("tr").find("td:eq(1)").text();
        if(!confirm("确认删除【"+name+"】吗?")){
            return;
        }
        $.ajax({
            url:"${path }/dept/delete/"+id,
            type:"DELETE",
            success:function(result){
                if(result.code==100){
                    getAllDepts();
                    alert("处理成功");
                }
            }
        });
    });
    /* 点击编辑按钮 */
    $(document).on("click","#dept_edit_btn",function(){
        $("#editDeptModal form")[0].reset();
        $("#editDeptModal").modal("show");
        var id=$(this).attr("edit-id");
        $("#dept_save_edit").attr("did",id);
        //加载编辑信息
        $.ajax({
            url:"${path }/dept/getById/"+id,
            type:"GET",
            success:function(result){
                if(result.code==100){
                    var dept=result.extend.dept;
                    $("#editDeptModal #dname").val(dept.dname);
                    $("#editDeptModal #dremark").val(dept.dremark);
                }
            }
        });
    });

    /* 保存新增 */
    $("#dept_save_edit").click(function(){
        var id=$(this).attr("did");
        $.ajax({
            url:"${path }/dept/edit/"+id,
            data:$("#editDeptModal form").serialize(),
            type:"PUT",
            success:function(result){
                if(result.code==100){
                    $("#editDeptModal").modal("hide");
                    getAllDepts();
                }
            }
        });
    })
</script>
</body>
</html>