# 专栏App

空余时间开发的一个App，非官方知乎专栏App，里面的内容全部来自知乎，内容版权也是知乎的，我挺喜欢知乎的

起初，开始这个项目主要是为了练手，熟悉开发一个完整App的流程。就像造房子，亲手打地基，搬砖头，垒砖头，还自己装修（设计）

后来，这个项目变成了我熟悉Android新技术的工程，比如最近我集成了Retrofit2以及Databinding进这个项目，未来我应该还会使用rxJava以及React-Native等等

http://bxbxbai.gitcafe.io/2015/03/26/zhuanlan/

### 下载使用： [http://fir.im/zhuanlan](http://fir.im/zhuanlan)


##1.1版本
现在App在Android 4.4系统上支持沉浸式状态栏了，个人感觉更加好看了，增加了抽屉菜单功能（部分功能还未完成）。

最新添加了“最近一周文章”功能，这功能会显示所有内置专栏作者最近一周的文章列表，并且所有的文章会按时间排序。还添加了“查看原网页”功能


##看截图：

![闪屏][1]


![专栏列表][2]


![文章列表][3]


![文章内容][4]


## Dependency - 依赖
  - Java Development Kit (JDK) 7 +
  - com.android.tools.build:gradle:2.0.0
  - Android SDK
    - SDK 23
    - build tool 23.0.3


## Build - 构建

    git clone https://github.com/bxbxbai/ZhuanLan.git

用最新的Android Studio导入工程（Import Project），然后等待IDE下载gradle和依赖包即可

This project uses the Gradle build system. To build this project, use the "gradlew build" command or use "Import Project" in Android Studio.


##将要做的

- 添加搜索专栏id 的功能
- 添加关注某一专栏的功能
- 。。。


##关于我

[http://bxbxbai.github.io/about/][5]


  [1]: https://raw.githubusercontent.com/bxbxbai/ZhuanLan/master/images/home.png
  [2]: https://raw.githubusercontent.com/bxbxbai/ZhuanLan/master/images/list.png
  [3]: https://raw.githubusercontent.com/bxbxbai/ZhuanLan/master/images/story.png
  [4]: https://raw.githubusercontent.com/bxbxbai/ZhuanLan/master/images/story2.png
  [5]: http://bxbxbai.github.io/about/

  