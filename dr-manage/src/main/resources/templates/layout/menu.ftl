<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<div class="navbar-default sidebar" role="navigation">

    <div class="sidebar-nav navbar-collapse slimscrollsidebar">
        <ul class="nav" id="side-menu">
            <#list menus as menu>
                <@security.authorize access="hasRole('${menu.code}')">
           <li>
               <a href="javascript:void(0);" class="waves-effect" menuId="${(menu.id)!''}" style="font-size: 16px;">
                    <i data-icon="${(menu.icon)!''}" class="linea-icon linea-basic fa-fw"></i>
                    <span class="hide-menu">${(menu.name)!''}<span class="fa arrow"></span> </span>
                </a>
                <ul class="nav nav-second-level">
                <#list menu.subMenus as sub>
                    <@security.authorize access="hasRole('${sub.code}')">
                   <li style="margin-left: 6px;"><a href="${ctx.contextPath}${(sub.url)!''}" menuId="${(sub.id)!''}" >${(sub.name)!''}</a></li>
                    </@security.authorize>
                </#list>
                </ul>
           </li>
                </@security.authorize>
            </#list>
        </ul>
    </div>
</div>
