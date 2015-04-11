# 知乎专栏App

最近一直在利用空余时间开发一个完整的App，名字就叫“专栏”。开发这个App的目的主要是为了练练手，熟悉开发一个完整App的流程。就像造房子，亲手打地基，搬砖头，垒砖头，还自己装修（虽然装修的不好看）

http://bxbxbai.gitcafe.io/2015/03/26/zhuanlan/

下载使用： http://vdisk.weibo.com/s/GGofvp4vOJl


##1.1版本
现在App在Android 4.4系统上支持沉浸式状态栏了，个人感觉更加好看了，增加了抽屉菜单功能（部分功能还未完成）。

最新添加了“最近一周文章”功能，这功能会显示所有内置专栏作者最近一周的文章列表，并且所有的文章会按时间排序。还添加了“查看原网页”功能



##看截图：


![闪屏][1]


![专栏列表][2]


![文章列表][3]


![文章内容][4]

![网页版][5]





## Dependency - 依赖
  - Java Development Kit (JDK) 7 +
  - com.android.tools.build:gradle:1.0.0
  - Android SDK
    - Android SDK Build-tools 21.1.2


## Build - 构建

    git clone https://github.com/bxbxbai/ZhuanLan.git

用最新的IntelliJ IDE导入工程（Import Project），然后等待IDE下载gradle和依赖包即可

This project uses the Gradle build system. To build this project, use the "gradlew build" command or use "Import Project" in Android Studio.



##将要做的

- 添加搜索专栏id 的功能
- 添加关注某一专栏的功能
- 。。。


##关于我

[http://bxbxbai.gitcafe.io/about/][6]


  [1]: http://i2.tietuku.com/0753db9e1804f6b3.png
  [2]: http://i2.tietuku.com/2970239f71296398.png
  [3]: http://i2.tietuku.com/6fa9fc0c9c2de428.png
  [4]: http://i2.tietuku.com/ace4ca3200ad7a09.png
  [5]: http://i2.tietuku.com/da044bdfabfe3e4f.png
  [6]: http://bxbxbai.gitcafe.io/about/