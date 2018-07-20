<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <script src="webjars/jquery/3.3.1-1/jquery.js"></script>
    <script src="webjars/bootstrap/4.1.1/js/bootstrap.bundle.js"></script>
    <script src="webjars/bootstrap/4.1.1/js/bootstrap.js"></script>
    <script src="js/jquery.growl.js"></script>
    <link rel="stylesheet" href="css/jquery.growl.css">
    <link rel="stylesheet" href="webjars/bootstrap/4.1.1/css/bootstrap-grid-jsf.css">
    <link rel="stylesheet" href="webjars/bootstrap/4.1.1/css/bootstrap-grid.css">
    <link rel="stylesheet" href="webjars/bootstrap/4.1.1/css/bootstrap-jsf.css">
    <link rel="stylesheet" href="webjars/bootstrap/4.1.1/css/bootstrap-reboot-jsf.css">
    <link rel="stylesheet" href="webjars/bootstrap/4.1.1/css/bootstrap-reboot.css">
    <link rel="stylesheet" href="webjars/bootstrap/4.1.1/css/bootstrap.css">
    <style>
        div{
            border-color: black;
        }
        table{

        }
    </style>
</head>
<body>
<!--导航-->
<div class="container">
    <ul class="nav nav-pills">
        <li class="nav-item">
            <a class="nav-link active" href="#" style="background-color: dimgray">文件列表</a>
        </li>
        <li class="nav-item">
            <a class="nav-link disabled" href="#">统计信息</a>
        </li>
        <li class="nav-item">
            <a class="nav-link disabled" href="#">日志</a>
        </li>
    </ul>
</div>
<!--检索框-->
<nav class="navbar navbar-expand-sm bg-secondary navbar-dark">
    <a class="offset-2">文件类型：</a>
    <div class="type">
        <select class="form-control" id="type">
            <option>ALL</option>
            <option>ZIP</option>
            <option>RAR</option>
            <option>TAR.GZ</option>
            <option>OHTER</option>
        </select>
    </div>
    <a style="margin-left: 15px">文件名称：</a>
    <input type="text" name="name">
    <a style="margin-left: 15px">URL：</a>
    <input type="url" style="width: 22%" name="url">
    <a href="#" style="margin-left: 15px" id="search""><img class="img" src="img/search.png" alt="Chania" style="height: auto"></a>
</nav>
<!--表格-->
<div class="container-fluid">
    <div class="row">
        <div class="col-1">
            <a style="margin-left: 45%" data-toggle="modal" data-target="#myModal"><img src="img/上传.png"></a>
        </div>
        <div class="offset-1 col-7" id="infoList">
            <a style="margin-left:  -2%">${pageParam.totleInfo}条数据</a>
            <table class="table table-striped table-hover row" style="margin-left: -2%;table-layout: fixed">
                <thead class="w-100">
                <tr class="row">
                    <th class="col-4">文件名称</th>
                    <th class="col-5">URL</th>
                    <th class="col-2">文件描述</th>
                    <th class="col-1">操作</th>
                </tr>
                </thead>
                <tbody class="w-100">
                <#list data as item>
                <tr id="${item.fileId}" class="item-info row">
                    <td class="col-4" name="fileName" style="word-wrap:break-word;word-break:break-all;">${item.fileName?default("none")}</td>
                    <td class="col-5" name="fileUrl"  style="word-wrap:break-word;word-break:break-all;">${item.fileUrl?default("none")}</td>
                    <td class="col-2" name="fileAuthor" style="word-wrap:break-word;word-break:break-all;">${item.fileAuthor?default("none")}</td>
                    <td name="fileSize" style="display: none">${item.fileSize?default("none")}kb</td>
                    <td class="col-1" id="${item.fdfsUrl?default("none")}" style="width: 10%;">
                        <a href="#" class="download"><img src="img/下载.png"></a>
                    </td>
                    <td name="fileSuffix" style="display: none">${item.fileSuffix?default("none")}</td>
                    <td name="times" style="display: none">${item.times?default("none")}</td>
                    <td name="fdfsUrl" style="display: none">${item.fdfsUrl?default("none")}</td>
                    <td name="times" style="display: none">${item.times?default("none")}</td>
                </tr>
                </#list>
                </tbody>
            </table>
            <a style="margin-left: -2%">第${pageParam.pageIndex?default("1")+1}页|共${pageParam.totlePage}页</a>
            <ul class="pagination" style="margin-left: -2%">
                <li class="page-item" id="0"><a class="page-link" href="#" >Previous</a></li>
                <#list  (pageParam.pageIndex-3)..(pageParam.pageIndex+3) as i>
                    <#if i==pageParam.pageIndex>
                        <li class="page-item active" id="${i+1}"><a class="page-link" href="#" >${i+1}</a></li>
                    </#if>
                    <#if -1 lt i>
                        <#if i lt pageParam.totlePage>
                            <#if i != pageParam.pageIndex>

                            <li class="page-item" id="${i+1}"><a class="page-link" href="#" >${i+1}</a></li>
                            </#if>
                        </#if>
                    </#if>
                </#list>
                <li class="page-item"  id="${pageParam.totlePage}"><a class="page-link" href="#">Last</a></li>
            </ul>
        </div>
        <div class="col-3  ">
            <h2>文件详细信息</h2>
            <div class="card" id="info">
                <div class="card-header" id="fileName"></div>
                <div class="card-body">
                    <table class="table table-bordered">
                        <tbody id="fileInfo">
                        <tr>
                            <td>文件大小</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>备注</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>使用次数</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>文件类型</td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="card-footer">底部</div>
            </div>
        </div>
    </div>
</div>
<!--分页组件-->
<div class="container">

</div>
<div class="container">
    <!-- 模态框 -->
    <div class="modal fade" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- 模态框头部 -->
                <div class="modal-header">
                    <h4 class="modal-title">文件上传</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <!-- 模态框主体 -->
                <div class="modal-body">

                    <form enctype="multipart/form-data" id="fileForm">
                        <div class="form-group">
                            <label for="file">选择文件:</label>
                            <input type="file" name="file" class="form-control" id="file">
                        </div>
                        <div class="form-group">
                            <label for="url">URL:</label>
                            <input type="url" class="form-control" id="url" name="url">
                        </div>
                        <div class="form-group">
                            <label for="remarks">备注:</label>
                            <input type="text" class="form-control" id="remarks" name="author">
                        </div>
                        <input type="button" class="btn btn-primary" value="上传" id="upload">
                        <#--<button class="btn btn-primary" id="upload">Submit</button>-->
                    </form>
                </div>

                <!-- 模态框底部 -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                </div>

            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function() {
        $("body").on('mouseenter','.item-info',function () {
            var fileName = $(this).children("td[name='fileName']").html();
            var fileSize = $(this).children("td[name='fileSize']").html();
            var fileAuthor = $(this).children("td[name='fileAuthor']").html();
            var times = $(this).children("td[name='times']").html();
            var fileSuffix = $(this).children("td[name='fileSuffix']").html();
            document.getElementById("fileName").innerHTML=fileName;
            document.getElementById("fileInfo").innerHTML="<tr><td>文件大小</td><td>"+fileSize+"</td></tr>" +
                    "<tr><td style='width: 50%'>备注</td><td style='width: 50%'>"+fileAuthor+"</td></tr>" +
                    "<tr><td style='width: 50%'>使用次数</td><td style='width: 50%'>"+times+"</td></tr>" +
                    "<tr><td style='width: 50%'>文件类型</td><td style='width: 50%'>"+fileSuffix+"</td></tr>";
        })
        $("body").on('click','#upload',function () {
            $.growl.notice({title:"操作提示",message:"开始上传"})
            var data = new FormData($("#file")[0]);
            var url = $("input[id='url']").val();
            var author = $("input[name='author']").val();
            var datas = new FormData(document.getElementById("fileForm"));
            // data.append('file',$("#file")[0].files[0]);
            // data.append('url',url);
            // data.append('author',author);
            $.ajax({
                type: 'POST',
                url: '/ap-system/uploadFilesOnFDFSAjax',
                data: datas,
                dataType: "json",
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    if(data.status==1) {
                        $.growl.notice({title:"操作提示",message:'上传成功'});
                        $("#close").click();
                        $("#search").click();
                    }else {
                        $.growl.error({title:"操作提示",message:data.message})
                    }
                },
                error: function () {
                    $.growl.error({title:"操作提示",message:"文件上传失败"})
                }
            })
        })
        $("body").on('click','.delete',function () {
            var url = $(this).parent().attr("id");
            var id = $(this).attr("id");
            var xmlhttp;
            if (window.XMLHttpRequest) {
                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
                xmlhttp = new XMLHttpRequest();
            }
            else {
                // IE6, IE5 浏览器执行代码
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    $("tbody tr[id="+id+"]").remove();
                    $.growl.notice({title:"操作提示",message:"删除成功"})
                }
            }
            xmlhttp.open("POST", "/deleteFile?id="+url, true);
            xmlhttp.send("id="+url);
        });
        $("body").on('click','.download',function () {
            var url = $(this).parent().attr("id");
            var elemIF = document.createElement("iframe");
            elemIF.src = "/ap-system/downLoadFile?URL="+url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
        });
        $("body").on('click','#search',function () {
            var url = $(this).siblings("input[name='url']").val();
            var name = $(this).siblings("input[name='name']").val();
            var type = $("select option:selected").text();
            var xmlhttp;
            if (window.XMLHttpRequest) {
                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
                xmlhttp = new XMLHttpRequest();
            }
            else {
                // IE6, IE5 浏览器执行代码
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    document.getElementById("infoList").innerHTML=xmlhttp.responseText;
                    $.growl.notice({title:"操作提示",message:"查询成功"})
                }
            }
            xmlhttp.open("POST", "/search?fileName="+name+"&fileURL="+url+"&suffix="+type+"&pageIndex=0"+"&pageSize=20", true);
            // xmlhttp.setRequestHeader('Access-Control-Allow-Origin','*')
            xmlhttp.send("id="+url);
        })
        $("body").on('click','.page-item',function () {
            var pageIndex = $(this).attr("id");
            var url = $(this).siblings("input[name='url']").val();
            var name = $(this).siblings("input[name='name']").val();
            var type = $("select option:selected").text();
            if (type=="ALL"){
                type = "undefined";
            }
            var xmlhttp;
            if (window.XMLHttpRequest) {
                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
                xmlhttp = new XMLHttpRequest();
            }
            else {
                // IE6, IE5 浏览器执行代码
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    document.getElementById("infoList").innerHTML=xmlhttp.responseText;
                    $.growl.notice({title:"操作提示",message:"查询成功"})
                }
            }
            xmlhttp.open("POST", "/search?fileName="+name+"&fileURL="+url+"&suffix="+type+"&pageIndex="+(pageIndex-1)+"&pageSize=20", true);
            xmlhttp.send("id="+url);
        })
        function loadXMLDoc() {
            var xmlhttp;
            if (window.XMLHttpRequest) {
                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
                xmlhttp = new XMLHttpRequest();
            }
            else {
                // IE6, IE5 浏览器执行代码
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    document.getElementById("infoList").innerHTML = xmlhttp.responseText;
                }
            }
            xmlhttp.open("GET", "/table", true);
            xmlhttp.send();
        }


    });
</script>
</body>
</html>

