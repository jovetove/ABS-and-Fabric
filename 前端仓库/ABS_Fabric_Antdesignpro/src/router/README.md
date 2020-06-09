路由/菜单说明
====


格式和说明
----

```ecmascript 6
const routerObject = {
  redirect: noredirect,
  name: 'router-name',
  hidden: true,
  meta: {
    title: 'title',
    icon: 'a-icon',
    target: '_blank|_self|_top|_parent',
    keepAlive: true,
    hiddenHeaderContent: true,
  }
}
```



`{ Route }` 对象

| 参数     | 说明                                      | 类型    | 默认值 |
| -------- | ----------------------------------------- | ------- | ------ |
| hidden   | 控制路由是否显示在 sidebar                | boolean | false |
| redirect | 重定向地址, 访问这个路由时,自定进行重定向 | string  | -      |
| name     | 路由名称, 必须设置,且不能重名           | string  | -      |
| meta     | 路由元信息（路由附带扩展信息）            | object  | {}     |
| hideChildrenInMenu | 强制菜单显示为Item而不是SubItem(配合 meta.hidden) | boolean  | -   |


`{ Meta }` 路由元信息对象

| 参数                | 说明                                                         | 类型    | 默认值 |
| ------------------- | ------------------------------------------------------------ | ------- | ------ |
| title               | 路由标题, 用于显示面包屑, 页面标题 *推荐设置                 | string  | -      |
| icon                | 路由在 menu 上显示的图标                                     | [string,svg]  | -      |
| keepAlive           | 缓存该路由                                                   | boolean | false  |
| target              | 菜单链接跳转目标（参考 html a 标记）                          | string | -  |
| hidden              | 配合`hideChildrenInMenu`使用，用于隐藏菜单时，提供递归到父菜单显示 选中菜单项_（可参考 个人页 配置方式）_ | boolean | false  |
| hiddenHeaderContent | *特殊 隐藏 [PageHeader](https://github.com/sendya/ant-design-pro-vue/blob/master/src/components/PageHeader/PageHeader.vue#L6) 组件中的页面带的 面包屑和页面标题栏 | boolean | false  |
| permission          | 与项目提供的权限拦截匹配的权限，如果不匹配，则会被禁止访问该路由页面 | array   | []     |

> 路由自定义 `Icon` 请引入自定义 `svg` Icon 文件，然后传递给路由的 `meta.icon` 参数即可

