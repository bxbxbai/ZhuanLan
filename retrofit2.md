title: Retrofit2 源码解析
date: 2015-12-13 13:16:29
tags: Android
---

公司里最近做的项目中网络框架用的就是[Retrofit][1]，用的多了以后觉得这个框架真的非常好用，然后抽了点时间debug了一下源码，觉得不光代码写的非常好，而且设计这个框架的思路都非常特别，收获很多，决定记录下来

**本文的源码分析基于Retrofit 2.0，和Retrofit 1.0有较大的不同**， 本文主要分为几部分：0、Retrofi 是什么，1、Retrofi怎么用，2、Retrofit的原理是什么，3、我的心得与看法

**下面说的Retrofit全部指Retrofit 2**

<!--more-->

## 0 Retrofit是什么

来自Retrofit官网的介绍：

> A type-safe HTTP client for Android and Java

简单的说它是一个HTTP请求工具，和Google开发的Volley功能上非常相似，这里有[Volley的源码解析][2]，但是使用上很不相似。Retrofit使用起来更简单，Volley使用上更加原始而且符合使用者的直觉，其实我觉得如果对自己Volley封装一下也可以像Retrofit那样的简单的使用

关于Volley的使用方法，请戳上面的Volley源码解析链接，这里就不赘述了

## 1 Retrofit怎么用

虽然Retrofit官网已经说明了，我还是要按照我的思路说一下它的使用方法

比如你要请求这么一个api：

> https://api.github.com/repos/{owner}/{repo}/contributors

查看github上某个repo的contributors，首先你要这样建一个接口：

    public interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);
    }

然后你还需要创建一个`Retrofit`对象：

    public static final String API_URL = "https://api.github.com";
    
    // Create a very simple REST adapter which points the GitHub API.
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

再用这个`Retrofit`对象创建一个`GitHub`对象：

    // Create an instance of our GitHub API interface.
    GitHub github = retrofit.create(GitHub.class);
    
    // Create a call instance for looking up Retrofit contributors.
    Call<List<Contributor>> call = github.contributors("square", "retrofit");

最后你就可以用这个`Githb`对象获得数据了：

    // Fetch and print a list of the contributors to the library.
    call.enqueue(new Callback<List<Contributor>>() {
        @Override
        public void onResponse(Response<List<Contributor>> response) {
            for (Contributor contributor : response.body()) {
                System.out.println(contributor.login + " (" + contributor.contributions + ")");
            }
        }
        @Override
        public void onFailure(Throwable t) {
        }
    });

这个使用方式看上去和Volley的方式完全不一样，使用Volley时你必须先创建一个`Request`对象，包括这个请求的Method，Url，Url的参数，以及一个请求成功和失败的Listener，然后把这个请求放到`RequestQueue`中，最后NetworkDispatcher会请求服务器获得数据。而`Retrofit`只要创建一个接口就可以了，太不可思议了！！

而我要说，其实这两种方式本质上是一样的，只是这个框架**描述HTTP请求的方式不一样而已**。因此，**你可以发现上面的`Github`接口其实就是`Retrofit`对一个HTTP请求的描述**


# 2 Retrofit的原理

Volley描述一个HTTP请求是需要创建一个`Request`对象，而执行这个请求呢，就是把这个请求对象放到一个队列中，让网络线程去处理。

Retrofit是怎么做的呢？答案就是**Java的动态代理**

## 动态代理

当开始看Retrofit的代码，我对下面这句代码感到很困惑：

    // Create an instance of our GitHub API interface.
    GitHub github = retrofit.create(GitHub.class);

我给Retrofit对象传了一个`Github`接口的Class对象，怎么又返回一个`Github`对象呢？进入`create`方法一看，没几行代码，但是我觉得这几行代码就是Retrofit的精妙的地方：

    /** Create an implementation of the API defined by the {@code service} interface. */
    @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
    public <T> T create(final Class<T> service) {
      Utils.validateServiceInterface(service);
      if (validateEagerly) {
          eagerlyValidateMethods(service);
      }
      return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
        new InvocationHandler() {
          private final Platform platform = Platform.get();
          @Override public Object invoke(Object proxy, Method method, Object... args)
              throws Throwable {
            // If the method is a method from Object then defer to normal invocation.
            if (method.getDeclaringClass() == Object.class) {
              return method.invoke(this, args);
            }
            if (platform.isDefaultMethod(method)) {
              return platform.invokeDefaultMethod(method, service, proxy, args);
            }
            return loadMethodHandler(method).invoke(args);
          }
        });
    }

看，`create`方法重要就是返回了一个动态代理对象。那么问题来了...

**动态代理是个什么东西？**

看Retrofit代码之前我知道Java动态代理是一个很重要的东西，比如在Spring框架里大量的用到，但是它有什么用呢？

**Java动态代理就是Java开发给了开发人员一种可能：当你要调用某个类的方法前，插入你想要执行的代码**

比如你要执行某个操作前，你必须要判断这个用户是否登录，或者你在付款前，你需要判断这个人的账户中存在这么多钱。这么简单的一句话，我相信可以把一个不懂技术的人也讲明白Java动态代理是什么东西了。

## 为什么要使用动态代理

你看上面代码，获取数据的代码就是这句:

    // Create a call instance for looking up Retrofit contributors.
    Call<List<Contributor>> call = github.contributors("square", "retrofit");

上面`github`对象其实是一个动态代理对象，并不是一个真正的`Githb`接口的`implements`对象，当`github`对象调用`contributors`方法时，执行的是动态代理方法（你debug一下就知道了）

此时，动态代理发挥了它的作用，你看上去是调用了`contributors`方法，其实此时Retrofit把`Github`接口翻译成一个HTTP请求，也就是Retrofit中的`MethodHandler`对象，这个对象中包含了：

- OkHttpClient：发送网络请求的工具
- RequestFactory： 类似于Volley中的`Request`，包含了HTTP请求的Url、Header信息，MediaType、Method以及RequestAction数组
- CallAdapter：HTTP请求返回数据的类型
- Converter：数据转换器

嗯，简单来说，Retrofit就是在你调用`Call<List<Contributor>> call = github.contributors("square", "retrofit");`后为你生成了一个Http请求，然后，你调用`call.enqueue`方法时就发送了这个请求，然后你就可以处理Response的数据了，从原理上讲，就是这样的。如果要再往细节处说，就可以再说很多了

# 3 Retrofit的源码分析

想要弄清楚Retrofit的细节，先来看一下Retrofit源码的组成：

1. 一个`retrofit2.http`包，里面全部是定义HTTP请求的注解，比如`GET`、`POST`、`PUT`、`DELETE`、`Headers`、`Path`、`Query`等等
2. 余下的`retrofit2`包中十几个类和接口就是全部retrofit的代码了，代码真的很少，很简单，因为retrofit把网络请求这部分功能全部交给了okHttp了


## Retrofit接口
我觉得你你必须要知道下面接口的含义

### `Callback<T>`
这个接口就是retrofit请求数据返回的接口，只有两个方法

- `void onResponse(Response<T> response);`
- `void onFailure(Throwable t);`

### `Converter<F, T>`
这个接口主要的作用就是将HTTP返回的数据解析成Java对象，主要由Xml、Gson、protobuf等等，你可以在创建`Retrofit`对象时添加你需要使用的`Converter`实现（看上面创建Retrofit对象的代码）

### `Call<T>` 
这个接口主要的作用就是发送一个HTTP请求，Retrofit默认的实现是`OkHttpCall<T>`，你可以根据实际情况实现你自己的Call类，这个设计和Volley的`HttpStack`接口设计的思想非常相似，子类可以实现基于`HttpClient`或`HttpUrlConnetction`的HTTP请求工具，**这种设计非常的插件化，而且灵活**

### `CallAdapter<T>`
上面说到过，`CallAdapter`中属性只有`responseType`一个，还有一个`<R> T adapt(Call<R> call)`方法，这个接口的实现类也只有一个，`DefaultCallAdapter`。这个方法的主要作用就是将`Call`对象转换成另一个对象，可能是为了支持RxJava才设计这个类的吧


## Retrofit的运行

刚才讲到` GitHub github = retrofit.create(GitHub.class);`代码返回了一个动态代理对象，而执行`Call<List<Contributor>> call = github.contributors("square", "retrofit");`代码时返回了一个`OkHttpCall`对象，拿到这个`Call`对象才能执行HTTP请求

其中后一句代码执行了一个非常复杂的过程

当执行了`contributors`方法时，Retrofit其实是执行了动态代理的`InvocationHandler`对象，最后会创建一个`MethodHandler`对象，这个对象很重要

    static MethodHandler<?> create(Retrofit retrofit, Method method) {
        CallAdapter<Object> callAdapter = (CallAdapter<Object>) createCallAdapter(method, retrofit);
        Type responseType = callAdapter.responseType();
        Converter<ResponseBody, Object> responseConverter =
            (Converter<ResponseBody, Object>) createResponseConverter(method, retrofit, responseType);
        RequestFactory requestFactory = RequestFactoryParser.parse(method, responseType, retrofit);
        
        return new MethodHandler<>(retrofit.client(), requestFactory, callAdapter, responseConverter);
    }

上面代码就是创建一个`MethodHandler`对象，一个`MethodHandler`对象中包含了4个对象

### 0. OkHttpClient
这个是Retrofit默认生成的

### 1. RequestFactory：
通过`RequestFactoryParser.parse(method, responseType, retrofit);`生成，主要作用就是**解析整个Http请求的所有数据**

主要原理就是解析一个接口，比如上面的`Github`接口，结果就是得到整个Http请求全部的信息，还会通过`@Path`和`@Query`注解拼接Url

不细讲了，全部代码都在`retrofit.RequestFactoryParser`类中

### 2. CallAdapter

获取`CallAdapter`的代码如下：

    private static CallAdapter<?> createCallAdapter(Method method, Retrofit retrofit) {
      Type returnType = method.getGenericReturnType();
      if (Utils.hasUnresolvableType(returnType)) {
        throw Utils.methodError(method,
          "Method return type must not include a type variable or wildcard: %s", returnType);
      }
      if (returnType == void.class) {
        throw Utils.methodError(method, "Service methods cannot return void.");
      }
      Annotation[] annotations = method.getAnnotations();
      try {
        return retrofit.callAdapter(returnType, annotations);
      } catch (RuntimeException e) { // Wide exception range because factories are user code.
        throw Utils.methodError(e, method, "Unable to create call adapter for %s", returnType);
      }
    }

你可以在创建Retrofit对象时，添加你想要的`CallAdapter`，而获取`CallAdapter`的方式也是从`Retrofit`对象中获取

默认的`DefaultCallAdapter`几乎没有上面作用，基本和动画里面的`LinearInterpolator`差不多

### 3. Converter

获得`Converter`对象和上面的原理几乎一样

    private static Converter<ResponseBody, ?> createResponseConverter(Method method,
      Retrofit retrofit, Type responseType) {
      Annotation[] annotations = method.getAnnotations();
      try {
        return retrofit.responseBodyConverter(responseType, annotations);
      } catch (RuntimeException e) { // Wide exception range because factories are user code.
        throw Utils.methodError(e, method, "Unable to create converter for %s", responseType);
      }
    }

创建这4个对象的目的就是为了执行下面这句代码

    Object invoke(Object... args) {
      return callAdapter.adapt(new OkHttpCall<>(client, requestFactory, responseConverter, args));
    }

这个也就是`github.contributors("square", "retrofit");`返回的`Call`对象

最后你调用`Call`对象的`execute()`或`enqueue(Callback<T> callback)`方法，就能发送一个Http请求了

只不过前一种方式是同步的，后一种是异步的，**也就是说Retrofit提供了同步和异步两种HTTP请求方式**

你可能会觉得我只要发送一个HTTP请求，你要做这么多事情不会很“慢”吗？不会很浪费性能吗？

我觉得，首先现在手机处理器主频非常高了，解析这个接口可能就花1ms可能更少的时间（我没有测试过），面对一个HTTP本来就需要几百ms，甚至几千ms来说不值得一提；而且Retrofit会对解析过的请求进行缓存，就在`Map<Method, MethodHandler<?>> methodHandlerCache`这个对象中

## 如何在Retrofit中使用RxJava

由于Retrofit设计的扩张性非常强，你只需要改变一下`CallAdapter`就可以了

	Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("https://api.github.com")
      .addConverterFactory(ProtoConverterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();

上面代码创建了一个`Retrofit`对象，支持Proto和Gson两种数据格式，并且还支持RxJava


# 4 最后
**Retrofit非常巧妙的用注解来描述一个HTTP请求，将一个HTTP请求抽象成一个Java接口，然后用了Java动态代理的方式，动态的将这个接口的注解“翻译”成一个HTTP请求，最后再执行这个HTTP请求**

Retrofit的功能非常多的依赖Java反射，代码中其实还有很多细节，比如异常的捕获、抛出和处理，大量的Factory设计模式（为什么要这么多使用Factory模式？）

Retrofit中接口设计的恰到好处，在你创建`Retrofit`对象时，让你有更多更灵活的方式去处理你的需求，比如使用不同的`Converter`、使用不同的`CallAdapter`，这也就提供了你使用RxJava来调用Retrofit的可能

我也慢慢看了[`Picasso`][3]和`Retrofit`的代码了，收获还是很多的，也更加深入的理解面向接口的编程方法，这个写代码就是**好的代码就是依赖接口而不是实现**最好的例子

好感谢开源的世界，让我能读到大牛的代码。我一直觉得一个人如果没有读过好的代码是不太可能写出好代码的。什么是好的代码？像`Picasso`和`Retrofit`这样的就是好的代码，扩展性强、低耦合、插件化

  [1]: http://square.github.io/retrofit/
  [2]: http://bxbxbai.github.io/2014/12/24/read-volley-source-code/
  [3]: http://square.github.io/picasso/