<#list (page-3)..(page+3) as i>
    <#if i==page>
        ${i} 就算是我
    </#if>
    <#if 0 lt i >
       <#if i lt (page+3)> OK</#if>
    </#if>
    <!--ceshi -->
</#list>