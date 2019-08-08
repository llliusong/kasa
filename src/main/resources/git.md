@[TOC]
# Git 提交代码时添加 emoji 图标

使用git的开发者都知道提交代码的最简单命令： ```git commit -m '此次提交的内容说明'```。
我们在github发现了这样一张视图：

![](1-git-emoji.png)

这是在commit时，添加了emoji表情说明，我们来看看其命令语法：


## 在commit时添加一个emoji表情图标
```git
git commit -m ':emoji: 此次提交的内容说明'
```

## 添加多个emoji表情图标

```git
git commit -m ':emoji1: :emoji2: :emoji3: 此次提交的内容说明'
```

在提交内容的前面增加了emoji标签：  **:emoji:**，其中emoji是表情图标的标签，列表见下面的附录表格。

|emoji|	emoji代码	|commit 说明
|----|----|----|
|:art: (调色板)|	```:art:```	|改进代码结构/代码格式
|:zap: (闪电):racehorse: (赛马)|	```:zap:“:racehorse:```	|提升性能
|:fire: (火焰)|	```:fire:```|	移除代码或文件
|:bug: (bug)|	```:bug:```	|修复 bug
|:ambulance: (急救车)|	```:ambulance:```|	重要补丁
|:sparkles: (火花)|	```:sparkles:```	|引入新功能
|:memo: (备忘录)	|```:memo:```	|撰写文档
|:rocket: (火箭)	|```:rocket:```	|部署功能
|:lipstick: (口红)	|```:lipstick:```	|更新 UI 和样式文件
|:tada: (庆祝)	|```:tada:```	|初次提交
|:white_check_mark: (白色复选框)	|```:white_check_mark:```	|增加测试
|:lock: (锁)	|```:lock:```	|修复安全问题
|:apple: (苹果)	|```:apple:```|	修复 macOS 下的问题
|:penguin: (企鹅)|	```:penguin:```|	修复 Linux 下的问题
|:checkered_flag: (旗帜)|	```:checked_flag:```	|修复 Windows 下的问题
|:bookmark: (书签)|	```:bookmark:```	|发行/版本标签
|:rotating_light: (警车灯)|	```:rotating_light:```	|移除 linter 警告
|:construction: (施工)|	```:construction:```	|工作进行中
|:green_heart: (绿心)	|```:green_heart:```	|修复 CI 构建问题
|:arrow_down: (下降箭头)	|```:arrow_down:```	|降级依赖
|:arrow_up: (上升箭头)|	```:arrow_up:```	|升级依赖
|:construction_worker: (工人)|	```:construction_worker:```	|添加 CI 构建系统
|:chart_with_upwards_trend: (上升趋势图)|	```:ch。art_with_upwards_trend:```	|添加分析或跟踪代码
|:hammer: (锤子)|	```:hammer:```	|重大重构
|:heavy_minus_sign: (减号)|	```:heavy_minus_sign:```|	减少一个依赖
|:whale: (鲸鱼)	|```:whale:```	|Docker 相关工作
|:heavy_plus_sign: (加号)|	```:heavy_plug_sign:```	|增加一个依赖
|:wrench: (扳手)	|```:wrench:```	|修改配置文件
|:globe_with_meridians: (地球)|	```:globe_with_meridians:```|	国际化与本地化
|:pencil2: (铅笔)|	```:pencil2:```|	修复 typo


参考资料 :

- https://gitmoji.carloscuesta.me/

代码格式,format of the code-🎨;
提升性能,Improving performance-⚡️;
移除,移除代码,Removing code or files-🔥;
Fixing a bug,修复bug-🐛;
撰写文档,文档,Writing docs-📝;
重构,重大重构-🔨;
Downgrading dependencies,降低依赖-⬇️;
Upgrading dependencies,更新依赖-⬆️;
Docker相关工作,docker-🐳;
initial,initial commit-🎉;
Critical hotfix,紧急修复,重要补丁-🚑;
Introducing new features,新功能-✨;
Deploying stuff,配置东西-🚀;
Updating the UI and style files,更改UI设计-💄;
添加测试,Adding tests-✅;
Fixing security issues,修复安全issues-🔒;
Fixing something on macOS,修复macOS bug-🍎;
Fixing something on Linux,修复Linux bug-🐧;
Fixing something on Windows,修复Windows bug-🏁;
Fixing something on Android,修复Android bug-🤖;
Fixing something on iOS,修复Ios bug-🍏;
Releasing / Version tags,发布版本,发布tag-🔖;
Removing linter warnings,删除警告-🚨;
Work in progress,工作过程中-🚧;
Fixing CI Build,修复ci构建-💚;
Pinning dependencies to specific versions,将依赖关系绑定到特定版本-📌;
Adding CI build system,添加CI生成系统-👷;
Adding analytics or tracking code,添加分析或跟踪代码-📈;
Refactoring code,重构代码-♻️;
Removing a dependency,减少依赖-➖;
Adding a dependency,增加依赖-➕;
Changing configuration files,改变配置程序-🔧;
Internationalization and localization,国际化和本土化-🌐;
Fixing typos,修改错别字-✏️;
Writing bad code that needs to be improved,需要重构的代码-💩;
Reverting changes,回滚代码-⏪;
Merging branches,合并分支-🔀;
Moving or renaming files,删除文件,重命名文件-🚚;
Adding or updating license,修改license,添加license-📄;
Adding or updating assets,增加断言,修改断言-🍱;
Updating code due to code review changes,当code review时修改代码-👌;