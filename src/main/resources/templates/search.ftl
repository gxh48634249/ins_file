<a style="margin-left:  -2%">${pageParam.totleInfo}条数据</a>
            <table class="table table-striped table-hover" style="margin-left: -2%;table-layout: fixed">
                <thead>
                <tr>
                    <th>文件名称</th>
                    <th>URL</th>
                    <th>文件描述</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="flush">
                <#list data as item>
                <tr id="${item.fileId}" class="item-info">
                    <td name="fileName">${item.fileName?default("none")}</td>
                    <td name="fileUrl" >${item.fileUrl?default("none")}</td>
                    <td name="fileAuthor" >${item.fileAuthor?default("none")}</td>
                    <td name="fileSize" style="display: none">${item.fileSize?default("none")}kb</td>
                    <td id="${item.fdfsUrl?default("none")}">
                        <a href="#" class="download"><img src="img/下载.png"></a>
                    <#--<a href="#" style="margin-left: 15px" class="modify"><img src="img/编辑.png"></a>-->
                        <#--<a href="#" style="margin-left: 15px" class="delete" id="${item.fileId}"><img src="img/删除.png"></a>-->
                    </td>
                <#--<td name="downloadTime" style="display: none">${item.downloadTime?default("none")}</td>-->
                    <td name="fileSuffix" style="display: none">${item.fileSuffix?default("none")}</td>
                    <td name="times" style="display: none">${item.times?default("none")}</td>
                    <td name="fdfsUrl" style="display: none">${item.fdfsUrl?default("none")}</td>
                    <td name="times" style="display: none">${item.times?default("none")}</td>
                <#--<td name="uploadTime" style="display: none">${item.uploadTime?default("none")}</td>-->
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