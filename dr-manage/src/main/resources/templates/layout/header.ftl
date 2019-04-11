<nav class="navbar navbar-default navbar-static-top m-b-0">
    <div class="navbar-header"><a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)"
                                  data-toggle="collapse" data-target=".navbar-collapse"><i class="ti-menu"></i></a>
        <div class="top-left-part">
            <a class="logo" href="${ctx.contextPath}/home">
                <b>
                    <img src="${ctx.contextPath}/plugins/images/yaoyilogo1.png" alt="home" class="dark-logo" style="height: 34px;"/>
                    <img src="${ctx.contextPath}/plugins/images/yaoyilogo1.png" alt="home" class="light-logo" style="height: 34px;"/>
                </b>
                <span class="hidden-xs">
                    <img src="${ctx.contextPath}/plugins/images/yaoyilogo.png" alt="home" class="dark-logo" style="height: 34px;"/>
                    <img src="${ctx.contextPath}/plugins/images/yaoyilogo.png" alt="home" class="light-logo" style="height: 34px;"/>
                </span>
            </a>
        </div>
        <ul class="nav navbar-top-links navbar-left hidden-xs">
            <li>
                <a href="javascript:void(0)" class="open-close hidden-xs waves-effect waves-light"><i
                        class="icon-arrow-left-circle ti-menu"></i></a>
            </li>
            <li>
                <a href="${ctx.contextPath}/home">
                    <i class="icon-home"></i>
                    首页
                </a>
            </li>
        </ul>
        <ul class="nav navbar-top-links navbar-right pull-right">
            <!-- /.dropdown -->
            <!-- /.dropdown -->
            <!-- .Megamenu -->
            <li><a href="javascript:void(0)" id="userMSG"><i class="icon-user"></i>
            ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.nickName!'xxx'} </a></li>
            <!-- /.Megamenu -->
            <!--<li class="right-side-toggle"> <a class="waves-effect waves-light" href="javascript:void(0)"><i class="ti-settings"></i></a></li>-->
            <li><a href="${ctx.contextPath}/logout"><i class="fa fa-power-off"></i> 退出登录</a></li>
            <!-- /.dropdown -->
        </ul>
    </div>
    <!-- /.navbar-header -->
    <!-- /.navbar-top-links -->
    <!-- /.navbar-static-side -->
</nav>