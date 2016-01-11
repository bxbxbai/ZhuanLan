# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/baia/Downloads/adt/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5
-dontusemixedcaseclassnames#混淆时不会产生形形色色的类名
-dontskipnonpubliclibraryclasses #指定不去忽略非公共的库类
-dontpreverify #不预校验
-dontwarn #不警告
-verbose
-dontshrink #不压缩 roboguice 需要设置成不压缩
#-dontoptimize #不优化
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* #优化配置
-ignorewarnings #忽略警告
# 保留签名，解决泛型、类型转换的问题
-keepattributes Signature
# 不混淆带有 annotation 的变量 和 函数
-keepattributes *Annotation*

-keep class * implements java.io.Serializable

# 除了 其他都不混淆
#-keep class !me.ele.** {*;}

# eventbus(只有指定的member不混淆)
-keepclassmembers class * {
    public void onEvent*(**);
}

#scheme
-keep class **$$SchemeDatabase
-keep class me.ele.scheme.SchemeDispatcher

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keep class freemarker.** {*;}

#weixin
-keep class com.tencent.mm.sdk.** {
   *;
}

# WebView
-keepclassmembers class * extends android.webkit.WebChromeClient {
     public void openFileChooser(...);
}

# BuildConfig没必要混淆，可以供 library通过反射判读当前debuggable
#-keep class **BuildConfig {*;}

