<#import "../../layout/layout.ftl" as layout>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@layout.layout title = "菜单管理"; section>
    <#if section = "css">
        <!--分页的CSS-->
        <link rel="stylesheet" href="${ctx.contextPath}/css/pagination.css">
        <!--alerts CSS -->
        <link href="${ctx.contextPath}/plugins/bower_components/sweetalert/sweetalert.css" rel="stylesheet">
    </#if>
    <#if section = "content">
        <input type="hidden" id="g_menuId" value="3">
        <div id="app-1">
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">菜单列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <ol class="breadcrumb">
                        <li><a href="#">系统管理</a></li>
                        <li class="active">菜单管理</li>
                    </ol>
                </div>
            </div>
            <!--搜索添加-->
            <div class="white-box white-box1" style="padding: 15px 18px 16px 18px;">
                <div class="row">
                    <div class="col-md-3">
                        <span class="m-r-5">关键字: </span>
                        <input @keyup.enter="searchMenus()" v-model="searchKey" type="text"
                               maxlength="30" class="form-control input-sm"
                               style="width:50%; display: inline-block" placeholder="请输入菜单名称">
                    </div>
                    <div class="col-md-3">
                        <span class="m-r-5">上级菜单: </span>
                        <select id="yy_search_parentId" v-model="searchParentId" value="" class="form-control input-sm"
                                style="width: 25%; display: inline-block;">
                            <option value="">无</option>
                            <option v-for="menu in upperLevelMenus" v-bind:value="menu.id">{{ menu.name
                                }}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-3"></div>
                    <div class="col-md-3">
                        <button @click="addMenu()" class="btn btn-success pull-right m-l-20" style="font-size: 1.1em;">
                            添加
                        </button>
                        <button @click="searchMenus()" class="btn btn-info pull-right" style="font-size: 1.1em;">
                            搜索
                        </button>

                    </div>
                </div>
            </div>
            <!--展示-->
            <div class="white-box white-box1">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table color-bordered-table muted-bordered-table table-hover">
                                <thead>
                                <tr style="font-size: 1.11em;">
                                    <th style="width: 20%;">菜单名称</th>
                                    <th>菜单编码</th>
                                    <th>链接路径</th>
                                    <th><span class="pull-right">操&nbsp;&nbsp;&nbsp;作</span></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="menu in menus" :key="menu.id" style="font-size: 0.9em;">
                                    <td>{{ menu.name }}
                                    <td>{{ menu.code }}</td>
                                    <td>{{ menu.url }}</td>
                                    <td class="text-nowrap">
                                     <span class="pull-right">
        <@security.authorize access="hasRole('MENU:MENUMANAGE')">
                                           <a class="yy_btn1 m-r-5" :href="menu.id+'/operation/index'" style="cursor:pointer;
                                            font-size: 1.25em;" title="查看功能">
                                             <i class="fa ti-zoom-in"></i>
                                         </a>
        </@security.authorize>
            <@security.authorize access="hasRole('MENU:EDITMENU')">
                                         <a class="yy_btn1" v-on:click="Vue_edit(menu.id)" style="cursor:pointer;
                                            font-size: 1.25em;" title="编辑">
                                             <i class="fa fa-edit"></i>
                                         </a>
            </@security.authorize>
                                         <#--<a class="yy_btn2" v-on:click="Vue_delete(menu.id)" href="#"
                                            data-toggle="tooltip"
                                            data-original-title="删除" style="font-size: 1.25em;" title="删除">
                                         <i class="fa fa-close text-danger"></i>-->
                                        </a>
                                     </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div v-show="dataIsNull" style="text-align: center;display: none;" class="m-b-10 m-t-10">
                                <span style="color: #f75b36;;">未检索到数据</span>
                            </div>
                        </div>
                    </div>
                </div>
                <!--分页-->
                <div class="row m-t-20">
                    <div class="col-md-12">
                        <div id="pagination" class="pagination"></div>
                    </div>
                </div>
            </div>
            <!--编辑修改窗口-->
            <div id="yy_edit_1" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true" style="padding-right: 17px; ">
                <div class="modal-dialog" style="top: 20%">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="editCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">编辑菜单</h4>
                        </div>
                        <div class="modal-body">
                            <form id="edit_form_1" role="form" class="form-horizontal">
                                <input type="text" class="form-control input-sm" id="yy_edit_id" value=""
                                       maxlength="30"  style="display: none">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">上级菜单: </label>
                                    <div class="col-sm-8">
                                        <select id="yy_edit_parentId" value="" disabled="disabled" class="form-control input-sm"
                                                style="width: 25%; display: inline-block; padding-left: 4px;">
                                            <option value="0">无</option>
                                            <option v-for="menu in upperLevelMenus" v-bind:value="menu.id">{{ menu.name
                                                }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">菜单名称<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_edit_name" value="" type="text" class="form-control input-sm"
                                               maxlength="30" placeholder="菜单名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">菜单编号<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_edit_code" value="" type="text" disabled="disabled" class="form-control input-sm"
                                               maxlength="30" placeholder="菜单编号">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">链接路径: </label>
                                    <div class="col-sm-8">
                                        <input id="yy_edit_url" value="" type="text" disabled="disabled" class="form-control input-sm"
                                               maxlength="30" placeholder="菜单链接路径">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">菜单顺序<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_edit_num" value="" type="text" class="form-control input-sm"
                                               maxlength="30" placeholder="菜单编号">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">图标: </label>
                                    <div class="col-sm-8">
                                        <input id="yy_edit_icon" value="" disabled="disabled" @click="changeIcon()"
                                               type="text" maxlength="30"  class="form-control input-sm"
                                               placeholder="点击选择图标" readonly>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="editCancel()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button @click="editConfirm()" type="button"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!--增加窗口-->
            <div id="yy_add_2" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true" style="padding-right: 17px; ">
                <div class="modal-dialog" style="top: 10%">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click="addCancel()" type="button" class="close">×</button>
                            <h4 class="modal-title">新增菜单</h4>
                        </div>
                        <div class="modal-body">
                            <form role="form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">上级菜单: </label>
                                    <div class="col-sm-8">
                                        <select id="yy_add_parentId" value="" class="form-control input-sm"
                                                style="width: auto; display: inline-block;">
                                            <option value="0">无</option>
                                            <option v-for="menu in upperLevelMenus" v-bind:value="menu.id">{{ menu.name
                                                }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">菜单名称<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_name" value="" type="text" class="form-control input-sm"
                                               maxlength="30" placeholder="菜单名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">菜单编号<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_code" value="" type="text" class="form-control input-sm"
                                               maxlength="30" placeholder="菜单编号">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">链接路径: </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_url" value="" type="text" class="form-control input-sm"
                                               maxlength="100" placeholder="菜单链接路径">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">图标: </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_icon" value="" type="text" class="form-control input-sm"
                                               maxlength="30" placeholder="图标">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">菜单顺序<span style="color: #F87B5E;">*</span>:
                                    </label>
                                    <div class="col-sm-8">
                                        <input id="yy_add_num" value="" type="text" class="form-control input-sm"
                                               maxlength="30" placeholder="菜单顺序">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button @click="addCancel()" type="button" class="btn btn-default btn-sm waves-effect"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button @click="addConfirm()" type="button"
                                    class="btn btn-info btn-sm waves-effect waves-light">
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!--选择图标-->
            <div id="selectIcon" style="position: absolute; z-index: 99999; top: 8%; margin-bottom: 100px; margin-right: 40px; display: none;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3 class="modal-title" style="display: inline-block">选择图标: </h3>
                            <#--<button @click="selectIconCancel()" type="button" class="btn btn-default waves-effect pull-right"
                                    data-dismiss="modal">
                                关闭
                            </button>-->
                            <button @click="selectIconCancel()" type="button" class="close">×</button>
                        </div>
                        <div class="modal-body">
                            <div class="col-md-12">
                                <div class="white-box">
                                    <ul id="qwer" class="glyphs character-mapping">
                                        <li>
                                            <div data-icon="a" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="a">
                                        </li>
                                        <li>
                                            <div data-icon="b" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="b">
                                        </li>
                                        <li>
                                            <div data-icon="c" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="c">
                                        </li>
                                        <li>
                                            <div data-icon="d" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="d">
                                        </li>
                                        <li>
                                            <div data-icon="e" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="e">
                                        </li>
                                        <li>
                                            <div data-icon="f" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="f">
                                        </li>
                                        <li>
                                            <div data-icon="g" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="g">
                                        </li>
                                        <li>
                                            <div data-icon="h" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="h">
                                        </li>
                                        <li>
                                            <div data-icon="i" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="i">
                                        </li>
                                        <li>
                                            <div data-icon="j" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="j">
                                        </li>
                                        <li>
                                            <div data-icon="k" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="k">
                                        </li>
                                        <li>
                                            <div data-icon="l" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="l">
                                        </li>
                                        <li>
                                            <div data-icon="m" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="m">
                                        </li>
                                        <li>
                                            <div data-icon="n" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="n">
                                        </li>
                                        <li>
                                            <div data-icon="o" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="o">
                                        </li>
                                        <li>
                                            <div data-icon="p" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="p">
                                        </li>
                                        <li>
                                            <div data-icon="q" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="q">
                                        </li>
                                        <li>
                                            <div data-icon="r" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="r">
                                        </li>
                                        <li>
                                            <div data-icon="s" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="s">
                                        </li>
                                        <li>
                                            <div data-icon="t" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="t">
                                        </li>
                                        <li>
                                            <div data-icon="u" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="u">
                                        </li>
                                        <li>
                                            <div data-icon="v" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="v">
                                        </li>
                                        <li>
                                            <div data-icon="w" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="w">
                                        </li>
                                        <li>
                                            <div data-icon="x" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="x">
                                        </li>
                                        <li>
                                            <div data-icon="y" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="y">
                                        </li>
                                        <li>
                                            <div data-icon="z" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="z">
                                        </li>
                                        <li>
                                            <div data-icon="A" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="A">
                                        </li>
                                        <li>
                                            <div data-icon="B" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="B">
                                        </li>
                                        <li>
                                            <div data-icon="C" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="C">
                                        </li>
                                        <li>
                                            <div data-icon="D" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="D">
                                        </li>
                                        <li>
                                            <div data-icon="E" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="E">
                                        </li>
                                        <li>
                                            <div data-icon="F" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="F">
                                        </li>
                                        <li>
                                            <div data-icon="G" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="G">
                                        </li>
                                        <li>
                                            <div data-icon="H" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="H">
                                        </li>
                                        <li>
                                            <div data-icon="I" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="I">
                                        </li>
                                        <li>
                                            <div data-icon="J" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="J">
                                        </li>
                                        <li>
                                            <div data-icon="K" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="K">
                                        </li>
                                        <li>
                                            <div data-icon="L" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="L">
                                        </li>
                                        <li>
                                            <div data-icon="M" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="M">
                                        </li>
                                        <li>
                                            <div data-icon="N" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="N">
                                        </li>
                                        <li>
                                            <div data-icon="O" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="O">
                                        </li>
                                        <li>
                                            <div data-icon="P" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="P">
                                        </li>
                                        <li>
                                            <div data-icon="Q" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="Q">
                                        </li>
                                        <li>
                                            <div data-icon="R" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="R">
                                        </li>
                                        <li>
                                            <div data-icon="S" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="S">
                                        </li>
                                        <li>
                                            <div data-icon="T" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="T">
                                        </li>
                                        <li>
                                            <div data-icon="U" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="U">
                                        </li>
                                        <li>
                                            <div data-icon="V" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="V">
                                        </li>
                                        <li>
                                            <div data-icon="W" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="W">
                                        </li>
                                        <li>
                                            <div data-icon="X" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="X">
                                        </li>
                                        <li>
                                            <div data-icon="Y" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="Y">
                                        </li>
                                        <li>
                                            <div data-icon="Z" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="Z">
                                        </li>
                                        <li>
                                            <div data-icon="0" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="0">
                                        </li>
                                        <li>
                                            <div data-icon="1" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="1">
                                        </li>
                                        <li>
                                            <div data-icon="2" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="2">
                                        </li>
                                        <li>
                                            <div data-icon="3" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="3">
                                        </li>
                                        <li>
                                            <div data-icon="4" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="4">
                                        </li>
                                        <li>
                                            <div data-icon="5" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="5">
                                        </li>
                                        <li>
                                            <div data-icon="6" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="6">
                                        </li>
                                        <li>
                                            <div data-icon="7" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="7">
                                        </li>
                                        <li>
                                            <div data-icon="8" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="8">
                                        </li>
                                        <li>
                                            <div data-icon="9" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="9">
                                        </li>
                                        <li>
                                            <div data-icon="!" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="!">
                                        </li>
                                        <li>
                                            <div data-icon="&#34;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&quot;">
                                        </li>
                                        <li>
                                            <div data-icon="#" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="#">
                                        </li>
                                        <li>
                                            <div data-icon="$" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="$">
                                        </li>
                                        <li>
                                            <div data-icon="%" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="%">
                                        </li>
                                        <li>
                                            <div data-icon="&" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;">
                                        </li>
                                        <li>
                                            <div data-icon="'" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&#39;">
                                        </li>
                                        <li>
                                            <div data-icon="(" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="(">
                                        </li>
                                        <li>
                                            <div data-icon=")" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value=")">
                                        </li>
                                        <li>
                                            <div data-icon="*" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="*">
                                        </li>
                                        <li>
                                            <div data-icon="+" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="+">
                                        </li>
                                        <li>
                                            <div data-icon="," class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value=",">
                                        </li>
                                        <li>
                                            <div data-icon="-" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="-">
                                        </li>
                                        <li>
                                            <div data-icon="." class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value=".">
                                        </li>
                                        <li>
                                            <div data-icon="/" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="/">
                                        </li>
                                        <li>
                                            <div data-icon=":" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value=":">
                                        </li>
                                        <li>
                                            <div data-icon=";" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value=";">
                                        </li>
                                        <li>
                                            <div data-icon="<" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&lt;">
                                        </li>
                                        <li>
                                            <div data-icon="=" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="=">
                                        </li>
                                        <li>
                                            <div data-icon=">" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&gt;">
                                        </li>
                                        <li>
                                            <div data-icon="?" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="?">
                                        </li>
                                        <li>
                                            <div data-icon="@" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="@">
                                        </li>
                                        <li>
                                            <div data-icon="[" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="[">
                                        </li>
                                        <li>
                                            <div data-icon="]" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="]">
                                        </li>
                                        <li>
                                            <div data-icon="^" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="^">
                                        </li>
                                        <li>
                                            <div data-icon="_" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="_">
                                        </li>
                                        <li>
                                            <div data-icon="`" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="`">
                                        </li>
                                        <li>
                                            <div data-icon="{" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="{">
                                        </li>
                                        <li>
                                            <div data-icon="|" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="|">
                                        </li>
                                        <li>
                                            <div data-icon="}" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="}">
                                        </li>
                                        <li>
                                            <div data-icon="~" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="~">
                                        </li>
                                        <li>
                                            <div data-icon="\" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="\">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe000;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe000;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe001;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe001;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe002;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe002;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe003;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe003;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe004;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe004;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe005;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe005;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe006;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe006;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe007;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe007;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe008;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe008;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe009;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe009;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe00a;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe00a;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe00b;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe00b;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe00c;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe00c;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe00d;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe00d;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe00e;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe00e;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe00f;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe00f;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe010;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe010;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe011;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe011;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe012;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe012;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe013;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe013;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe014;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe014;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe015;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe015;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe016;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe016;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe017;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe017;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe018;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe018;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe019;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe019;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe01a;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe01a;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe01b;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe01b;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe01c;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe01c;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe01d;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe01d;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe01e;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe01e;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe01f;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe01f;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe020;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe020;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe021;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe021;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe022;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe022;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe023;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe023;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe024;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe024;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe025;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe025;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe026;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe026;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe027;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe027;">
                                        </li>
                                        <li>
                                            <div data-icon="&#xe028;" class="linea-icon linea-basic"></div>
                                            <input type="text" readonly="readonly" value="&amp;#xe028;">
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                        </div>
                    </div>
                </div>
        </div>
    </#if>

    <#if section = "js">
        <!--Vue-->
        <script src="${ctx.contextPath}/js/vue.js"></script>
        <!--分页-->
        <script src="${ctx.contextPath}/js/pagination.js"></script>
        <!-- Sweet-Alert  -->
        <script src="${ctx.contextPath}/plugins/bower_components/sweetalert/sweetalert.min.js"></script>
        <script>
            var baseURL = '${ctx.contextPath}';
        </script>
        <script src="${ctx.contextPath}/js/sys/menu.js"></script>
    </#if>
</@layout.layout>
