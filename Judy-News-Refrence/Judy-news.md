Judy-news文档

## 一、项目介绍

### 1.1 技术架构图

<img src="Judy-news.assets/image-20210605193007002.png" alt="image-20210605193007002" style="zoom:80%;" />

### 1.2 功能列表

- 门户端 
- - 首页
  - 搜索
  - 通行证
  - 文章详情
  - 文章列表
  - 文章分类
  - 作家主页
  - 热点新闻
  - 文章评论
  - 友情链接

- 作家中心
- - 发文
  - 内容管理
  - 评论管理
  - 粉丝管理
  - 粉丝画像
  - 账号设置
- admin中心
- - 用户管理
  - 内容审核
  - 友情链接管理
  - 文章分类管理
  - 人脸管理
  - IP黑名单

<img src="Judy-news.assets/image-20210605194159792.png" alt="image-20210605194159792" style="zoom:67%;" />

<img src="Judy-news.assets/5f45cbd6083fe36007090979.jpg" style="zoom:80%;" />

## 二、项目构建

### 2.1 聚合工程

#### 2.1.1 构建顶级工程

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.dexlace</groupId>
    <artifactId>imood-news-dev</artifactId>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>imood-news-dev-common</module>
        <module>imood-news-dev-model</module>
        <module>imood-news-dev-service-api</module>
        <module>imood-news-dev-service-user</module>
        <module>imood-news-dev-service-files</module>
        <module>imood-news-dev-service-admin</module>
        <module>imood-news-dev-service-article</module>

    </modules>


    <packaging>pom</packaging>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.5.RELEASE</version>
        <relativePath />
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>

        <mysql-connector-java.version>5.1.41</mysql-connector-java.version>
        <mybatis-spring-boot-starter.version>2.1.0</mybatis-spring-boot-starter.version>
        <mapper-spring-boot-starter.version>2.1.5</mapper-spring-boot-starter.version>
        <pagehelper-spring-boot-starter.version>1.2.12</pagehelper-spring-boot-starter.version>

        <okhttp.version>4.2.2</okhttp.version>
        <jackson.version>2.10.2</jackson.version>

        <commons-codec.version>1.11</commons-codec.version>
        <commons-lang3.version>3.4</commons-lang3.version>
        <commons-fileupload.version>1.4</commons-fileupload.version>
        <google-guava.version>28.2-jre</google-guava.version>

        <springfox-swagger2.version>2.4.0</springfox-swagger2.version>
        <swagger-bootstrap-ui.version>1.6</swagger-bootstrap-ui.version>
        <fastdfs.version>1.27.2</fastdfs.version>

        <slf4j.version>1.7.21</slf4j.version>
        <joda-time.version>2.10.6</joda-time.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- SpringCloud 依赖 -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR3</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!-- 引入 mongodb 依赖 -->
            <dependency>
                <groupId>org.mongodb</groupId>
                <artifactId>mongodb-driver</artifactId>
                <!--mongodb-driver.version-->
                <version>3.11.1</version>
            </dependency>

            <!-- mysql 驱动 -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql-connector-java.version}</version>
            </dependency>
            <!-- mybatis -->
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${mybatis-spring-boot-starter.version}</version>
            </dependency>
            <!-- 通用mapper逆向工具 -->
            <dependency>
                <groupId>tk.mybatis</groupId>
                <artifactId>mapper-spring-boot-starter</artifactId>
                <version>${mapper-spring-boot-starter.version}</version>
            </dependency>
            <!--pagehelper -->
            <dependency>
                <groupId>com.github.pagehelper</groupId>
                <artifactId>pagehelper-spring-boot-starter</artifactId>
                <version>${pagehelper-spring-boot-starter.version}</version>
            </dependency>

            <dependency>
                <groupId>com.squareup.okhttp3</groupId>
                <artifactId>okhttp</artifactId>
                <version>${okhttp.version}</version>
            </dependency>

            <!-- jackson -->
            <dependency>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-core</artifactId>
                <version>${jackson.version}</version>
            </dependency>
            <dependency>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-annotations</artifactId>
                <version>${jackson.version}</version>
            </dependency>
            <dependency>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-databind</artifactId>
                <version>${jackson.version}</version>
            </dependency>

            <!-- apache 工具类 -->
            <dependency>
                <groupId>commons-codec</groupId>
                <artifactId>commons-codec</artifactId>
                <version>${commons-codec.version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>${commons-lang3.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-fileupload</groupId>
                <artifactId>commons-fileupload</artifactId>
                <version>${commons-fileupload.version}</version>
            </dependency>

            <!-- google 工具类 -->
            <dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>${google-guava.version}</version>
            </dependency>

            <!-- swagger2 配置 -->
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-swagger2</artifactId>
                <version>${springfox-swagger2.version}</version>
            </dependency>
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-swagger-ui</artifactId>
                <version>${springfox-swagger2.version}</version>
            </dependency>
            <dependency>
                <groupId>com.github.xiaoymin</groupId>
                <artifactId>swagger-bootstrap-ui</artifactId>
                <version>${swagger-bootstrap-ui.version}</version>
            </dependency>

            <dependency>
                <groupId>com.github.tobato</groupId>
                <artifactId>fastdfs-client</artifactId>
                <version>${fastdfs.version}</version>
            </dependency>

            <!-- joda-time 时间工具 -->
            <dependency>
                <groupId>joda-time</groupId>
                <artifactId>joda-time</artifactId>
                <version>${joda-time.version}</version>
            </dependency>

        </dependencies>
    </dependencyManagement>

    <build>
        <finalName>${project.artifactId}</finalName>
        <plugins>
            <!-- Java 编译 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>


</project>
```

#### 2.1.2 各个模块介绍

- imood-news-dev-common

```xml
<!--
    imood-news-dev-common:
    通用工程
    包含了一些工具类，枚举类，封装的公共方法
-->
```

- imood-news-dev-model

```xml
<!--
	imood-news-dev-model:
	模型工程，所有子工程以及微服务中所涉及到模型实体都在此管理
	可以包含一些 *Pojo，*Bean 或 *Entity，比如：vo, bo, dto等
-->
		<dependency>
            <groupId>com.dexlace</groupId>
            <artifactId>imood-news-dev-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

- imood-news-dev-service-api

```xml
<!--
	imood-news-dev-service-api:
	接口工程，集中管理所有controller中的接口，为了更好的统一管理微服务
-->
 		<dependency>
            <groupId>com.dexlace</groupId>
            <artifactId>imood-news-dev-model</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

- service服务模块

- - imood-news-dev-service-user

  - ```xml
    <!--
    	imood-news-dev-service-user:
    	用户服务，用户相关业务在此微服务工程中实现
    	包含：用户登录，用户注册，用户信息查询等
    -->
           <dependency>
                <groupId>com.dexlace</groupId>
                <artifactId>imood-news-dev-service-api</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
    ```

  - imood-news-dev-service-admin

  - imood-news-dev-service-article

  - imood-news-dev-service-file

#### 2.1.3 api模块说明

```java

/**
 * api 的作用：
 * api 就相当于企业的领导，老板，部门经理
 * 其他的服务层都是实现，他们就相当于员工，只做事情
 * 老板（开发人员）来看一下每个人（服务）的进度，做什么事。
 * 老板不会去问员工，他只会对接部门经理。
 * 那么这个里的所有的api 接口就是统一在这里进行管理和调度的，微服务也是如此
 */

/**
 * 运作：
 * 现在的所有接口都在此暴露，实现都是在各自的微服务中
 * 本项目只写接口，不写实现，实现在各自的微服务工程中，因为以业务来划分的微服务有很多
 * controller也会分散在各个微服务工程中，一旦多了就很难统一管理和查看
 *
 * 其次，微服务之间的调用都是基于接口的
 * 如果不这么做，微服务之间的调用就需要相互依赖了，
 * 耦合度也就高了，接口的目的为了能够提供解耦。
 *
 * 此外，本工程的接口其实就是一套规范。实现都是由各自的工程去做的处理。
 * 目前我们使用springboot作为接口的实现的。
 * 如果未来以后出现新的java web框架，那么我们不需要修改接口，
 * 只需要去修改对应的实现就可以了。这其实也是解耦的一个体现。
 *
 * Swagger2，基于接口的自动文档生成。
 * 所有的配置文件只需要一份，就能在当前项目中去构建了。
 * 管理起来很方便。
 *
 * 综上所述，如此的做法，可以提高多服务的项目可扩展性。
 */
     

```

### 2.2 通用返回结果封装

- 版本一

```java
package com.dexlace.common.result;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/28
 */


/**
 自定义响应数据结构
 * 				本类可提供给 H5/ios/安卓/公众号/小程序 使用
 * 				前端接受此类数据（json object)后，可自行根据业务去实现相关功能
 *
 * 				200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 * 				556: 用户qq校验异常
 * 			    557: 校验用户是否在CAS登录，用户门票的校验
 */
public class IMOODJSONResult {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据  Object类型可以适配所有数据类型
    private Object data;

    private String ok;	// 不使用

    /**
     * 几个静态方法，对应几个构造函数，这里是为了避免去new 对象
     */
    public static IMOODJSONResult build(Integer status, String msg, Object data) {
        return new IMOODJSONResult(status, msg, data);
    }

    public static IMOODJSONResult build(Integer status, String msg, Object data, String ok) {
        return new IMOODJSONResult(status, msg, data, ok);
    }

    public static IMOODJSONResult ok(Object data) {
        return new IMOODJSONResult(data);
    }

    public static IMOODJSONResult ok() {
        return new IMOODJSONResult(null);
    }

    public static IMOODJSONResult errorMsg(String msg) {
        return new IMOODJSONResult(500, msg, null);
    }

    public static IMOODJSONResult errorUserTicket(String msg) {
        return new IMOODJSONResult(557, msg, null);
    }

    public static IMOODJSONResult errorMap(Object data) {
        return new IMOODJSONResult(501, "error", data);
    }

    public static IMOODJSONResult errorTokenMsg(String msg) {
        return new IMOODJSONResult(502, msg, null);
    }

    public static IMOODJSONResult errorException(String msg) {
        return new IMOODJSONResult(555, msg, null);
    }

    public static IMOODJSONResult errorUserQQ(String msg) {
        return new IMOODJSONResult(556, msg, null);
    }

    public IMOODJSONResult() {

    }


    /**
     * 一系列的构造函数
     * @param status
     * @param msg
     * @param data
     */
    public IMOODJSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 
     * @param status 状态码
     * @param msg 响应消息
     * @param data 额外的数据内容
     * @param ok 
     */
    public IMOODJSONResult(Integer status, String msg, Object data, String ok) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }


    /**
     * 传一个参数的构造函数默认返回结果为200，表示成功
     * @param data 返回的内容  data用Object类型表示可以适配任何类型
     */
    public IMOODJSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }


    /**
     * 以下是get和set方法
     * 
     */

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

}


```

- 版本二

```java
package com.dexlace.common.result;

import java.util.Map;

/**
 * 自定义响应数据类型枚举升级版本
 *
自定义响应数据结构
 * 				本类可提供给 H5/ios/安卓/公众号/小程序 使用
 * 				前端接受此类数据（json object)后，可自行根据业务去实现相关功能

 */
public class GraceIMOODJSONResult {

    // 响应业务状态码
    private Integer status;

    // 响应消息
    private String msg;

    // 是否成功
    private Boolean success;

    // 响应数据，可以是Object，也可以是List或Map等
    private Object data;

    /**
     * 成功返回，带有数据的，直接往OK方法丢data数据即可
     * @param data
     * @return
     */
    public static GraceIMOODJSONResult ok(Object data) {
        return new GraceIMOODJSONResult(data);
    }
    /**
     * 成功返回，不带有数据的，直接调用ok方法，data无须传入（其实就是null）
     * @return
     */
    public static GraceIMOODJSONResult ok() {
        return new GraceIMOODJSONResult(ResponseStatusEnum.SUCCESS);
    }
    public GraceIMOODJSONResult(Object data) {
        this.status = ResponseStatusEnum.SUCCESS.status();
        this.msg = ResponseStatusEnum.SUCCESS.msg();
        this.success = ResponseStatusEnum.SUCCESS.success();
        this.data = data;
    }


    /**
     * 错误返回，直接调用error方法即可，当然也可以在ResponseStatusEnum中自定义错误后再返回也都可以
     * @return
     */
    public static GraceIMOODJSONResult error() {
        return new GraceIMOODJSONResult(ResponseStatusEnum.FAILED);
    }

    /**
     * 错误返回，map中包含了多条错误信息，可以用于表单验证，把错误统一的全部返回出去
     * @param map
     * @return
     */
    public static GraceIMOODJSONResult errorMap(Map map) {
        return new GraceIMOODJSONResult(ResponseStatusEnum.FAILED, map);
    }

    /**
     * 错误返回，直接返回错误的消息
     * @param msg
     * @return
     */
    public static GraceIMOODJSONResult errorMsg(String msg) {
        return new GraceIMOODJSONResult(ResponseStatusEnum.FAILED, msg);
    }

    /**
     * 错误返回，token异常，一些通用的可以在这里统一定义
     * @return
     */
    public static GraceIMOODJSONResult errorTicket() {
        return new GraceIMOODJSONResult(ResponseStatusEnum.TICKET_INVALID);
    }

    /**
     * 自定义错误范围，需要传入一个自定义的枚举，可以到[ResponseStatusEnum.java[中自定义后再传入
     * @param responseStatus
     * @return
     */
    public static GraceIMOODJSONResult errorCustom(ResponseStatusEnum responseStatus) {
        return new GraceIMOODJSONResult(responseStatus);
    }
    public static GraceIMOODJSONResult exception(ResponseStatusEnum responseStatus) {
        return new GraceIMOODJSONResult(responseStatus);
    }

    /**
     *
     *  以下对应三种不同结构的JSONResult
     *  满足多种需求
     */
    public GraceIMOODJSONResult(ResponseStatusEnum responseStatus) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.success = responseStatus.success();
    }
    public GraceIMOODJSONResult(ResponseStatusEnum responseStatus, Object data) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.success = responseStatus.success();
        this.data = data;
    }
    public GraceIMOODJSONResult(ResponseStatusEnum responseStatus, String msg) {
        this.status = responseStatus.status();
        this.msg = msg;
        this.success = responseStatus.success();
    }

    public GraceIMOODJSONResult() {
    }


    /**
     * 一系列的get和set方法
     */

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

```

对应版本二的枚举类，对应具体响应内容

```java
package com.dexlace.common.result;

/**
 * 响应结果枚举，用于提供给GraceJSONResult返回给前端的
 * 本枚举类中包含了很多的不同的状态码供使用，可以自定义
 * 便于更优雅的对状态码进行管理，一目了然
 */
public enum ResponseStatusEnum {

    SUCCESS(200, true, "操作成功！"),
    FAILED(500, false, "操作失败！"),

    // 50x
    UN_LOGIN(501,false,"请登录后再继续操作！"),
    TICKET_INVALID(502,false,"会话失效，请重新登录！"),
    NO_AUTH(503,false,"您的权限不足，无法继续操作！"),
    MOBILE_ERROR(504,false,"短信发送失败，请稍后重试！"),
    SMS_NEED_WAIT_ERROR(505,false,"短信发送太快啦~请稍后再试！"),
    SMS_CODE_ERROR(506,false,"验证码过期或不匹配，请稍后再试！"),
    USER_FROZEN(507,false,"用户已被冻结，请联系管理员！"),
    USER_UPDATE_ERROR(508,false,"用户信息更新失败，请联系管理员！"),
    USER_INACTIVE_ERROR(509,false,"请前往[账号设置]修改信息激活后再进行后续操作！"),
    FILE_UPLOAD_NULL_ERROR(510,false,"文件不能为空，请选择一个文件再上传！"),
    FILE_UPLOAD_FAILD(511,false,"文件上传失败！"),
    FILE_FORMATTER_FAILD(512,false,"文件图片格式不支持！"),
    FILE_MAX_SIZE_ERROR(513,false,"仅支持500kb大小以下的图片上传！"),
    FILE_NOT_EXIST_ERROR(514,false,"你所查看的文件不存在！"),
    USER_STATUS_ERROR(515,false,"用户状态参数出错！"),
    USER_NOT_EXIST_ERROR(516,false,"用户不存在！"),

    // 自定义系统级别异常 54x
    SYSTEM_INDEX_OUT_OF_BOUNDS(541, false, "系统错误，数组越界！"),
    SYSTEM_ARITHMETIC_BY_ZERO(542, false, "系统错误，无法除零！"),
    SYSTEM_NULL_POINTER(543, false, "系统错误，空指针！"),
    SYSTEM_NUMBER_FORMAT(544, false, "系统错误，数字转换异常！"),
    SYSTEM_PARSE(545, false, "系统错误，解析异常！"),
    SYSTEM_IO(546, false, "系统错误，IO输入输出异常！"),
    SYSTEM_FILE_NOT_FOUND(547, false, "系统错误，文件未找到！"),
    SYSTEM_CLASS_CAST(548, false, "系统错误，类型强制转换错误！"),
    SYSTEM_PARSER_ERROR(549, false, "系统错误，解析出错！"),
    SYSTEM_DATE_PARSER_ERROR(550, false, "系统错误，日期解析出错！"),

    // admin 管理系统 56x
    ADMIN_USERNAME_NULL_ERROR(561, false, "管理员登录名不能为空！"),
    ADMIN_USERNAME_EXIST_ERROR(562, false, "管理员登录名已存在！"),
    ADMIN_NAME_NULL_ERROR(563, false, "管理员负责人不能为空！"),
    ADMIN_PASSWORD_ERROR(564, false, "密码不能为空后者两次输入不一致！"),
    ADMIN_CREATE_ERROR(565, false, "添加管理员失败！"),
    ADMIN_PASSWORD_NULL_ERROR(566, false, "密码不能为空！"),
    ADMIN_NOT_EXIT_ERROR(567, false, "管理员不存在或密码错误！"),
    ADMIN_FACE_NULL_ERROR(568, false, "人脸信息不能为空！"),
    ADMIN_FACE_LOGIN_ERROR(569, false, "人脸识别失败，请重试！"),
    CATEGORY_EXIST_ERROR(570, false, "文章分类已存在，请换一个分类名！"),

    // 媒体中心 相关错误 58x
    ARTICLE_COVER_NOT_EXIST_ERROR(580, false, "文章封面不存在，请选择一个！"),
    ARTICLE_CATEGORY_NOT_EXIST_ERROR(581, false, "请选择正确的文章领域！"),
    ARTICLE_CREATE_ERROR(582, false, "创建文章失败，请重试或联系管理员！"),
    ARTICLE_QUERY_PARAMS_ERROR(583, false, "文章列表查询参数错误！"),
    ARTICLE_DELETE_ERROR(584, false, "文章删除失败！"),
    ARTICLE_WITHDRAW_ERROR(585, false, "文章撤回失败！"),
    ARTICLE_REVIEW_ERROR(585, false, "文章审核出错！"),
    ARTICLE_ALREADY_READ_ERROR(586, false, "文章重复阅读！"),

    // 人脸识别错误代码
    FACE_VERIFY_TYPE_ERROR(600, false, "人脸比对验证类型不正确！"),
    FACE_VERIFY_LOGIN_ERROR(601, false, "人脸登录失败！"),

    // 系统错误，未预期的错误 555
    SYSTEM_ERROR(555, false, "系统繁忙，请稍后再试！"),
    SYSTEM_OPERATION_ERROR(556, false, "操作失败，请重试或联系管理员"),
    SYSTEM_RESPONSE_NO_INFO(557, false, "");


    // 响应业务状态
    private Integer status;
    // 调用是否成功
    private Boolean success;
    // 响应消息，可以为成功或者失败的消息
    private String msg;

    ResponseStatusEnum(Integer status, Boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    public Integer status() {
        return status;
    }
    public Boolean success() {
        return success;
    }
    public String msg() {
        return msg;
    }
}

```

### 2.3 配置逆向生成工具

略

### 2.4 Swagger2文档工具

由于所有的service接口都在==imood-news-dev-service-api==中定义，所以，在该模块中配置Swagger2。

> 依赖

```xml
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>swagger-bootstrap-ui</artifactId>
        </dependency>

```

> 配置

```java
package com.dexlace.api.config;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/28
 */
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//    项目部署路径/swagger-ui.html     原路径
//    项目部署路径/doc.html     新路径

    // 配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {

        Predicate<RequestHandler> adminPredicate = RequestHandlerSelectors.basePackage("com.dexlace.admin.controller");
        Predicate<RequestHandler> articlePredicate = RequestHandlerSelectors.basePackage("com.dexlace.article.controller");
        // 注意这里的不是接口controller的路径所在的包  而是实现类的包  表示对该路径下的apd进行监控
        // 注意子模块的启动类不能少了@ComponentScan注解
        Predicate<RequestHandler> userPredicate = RequestHandlerSelectors.basePackage("com.dexlace.user.controller");
        Predicate<RequestHandler> filesPredicate = RequestHandlerSelectors.basePackage("com.dexlace.files.controller");

        return new Docket(DocumentationType.SWAGGER_2)  // 指定api类型为swagger2
                .apiInfo(apiInfo())                 // 用于定义api文档汇总信息
                .select()
                .apis(Predicates.or(adminPredicate,articlePredicate, userPredicate, filesPredicate))
//                .apis(Predicates.or( userPredicate))
                .paths(PathSelectors.any())         // 所有controller
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("牧德新闻·自媒体接口api")                       // 文档页标题
                .contact(new Contact("imood",
                        "https://www.imood.com",
                        "dexlace@imood.com"))                   // 联系人信息
                .description("专为牧德新闻·自媒体平台提供的api文档")      // 详细信息
                .version("1.0.1")                               // 文档版本号
                .termsOfServiceUrl("https://www.imood.com")     // 网站地址
                .build();
    }

}



```

文档访问路径一：醒目部署路径/doc.html

![image-20210606093432009](Judy-news.assets/image-20210606093432009.png)

文档访问路径二：项目部署路径/swagger-ui.html

<img src="Judy-news.assets/image-20210606093615280.png" alt="image-20210606093615280" style="zoom:80%;" />

> 使用示例
>
> @Api、@ApiOperation、 @ApiParam等注解的使用

```java
@Api(value = "用户管理相关的接口定义", tags = {"用户管理相关功能的controller"})
@RequestMapping("appUser")
public interface AppUserMngControllerApi {
    @PostMapping("queryAll")
    @ApiOperation(value = "查询所有网站用户", notes = "查询所有网站用户", httpMethod = "POST")
    GraceIMOODJSONResult queryAll(@RequestParam String nickname,
                                         @RequestParam Integer status,
                                         @RequestParam Date startDate,
                                         @RequestParam Date endDate,
                                         @ApiParam(name = "page", value = "查询下一页的第几页", 
                                                   required = false)
                                         @RequestParam Integer page,
                                         @ApiParam(name = "pageSize", value = "分页的每一页显示的条数",                                            required = false)
                                         @RequestParam Integer pageSize);
}
```

## 三、开发通行证服务

### 3.1 短信登录流程

> 发送短信验证码流程

<img src="Judy-news.assets/image-20210606095026206.png" alt="image-20210606095026206" style="zoom: 50%;" />

> 一键登录/注册流程

<img src="Judy-news.assets/image-20210606095055086.png" alt="image-20210606095055086" style="zoom:67%;" />

### 3.2 配置秘钥和资源文件

落码模块：imood-news-dev-common

> 依赖

```xml
      <dependency>
            <groupId>com.aliyun</groupId>
            <artifactId>aliyun-java-sdk-core</artifactId>
            <version>3.6.2</version>
        </dependency>
```

> 秘钥

```properties
# 注意以下是本人阿里云账号的秘钥，瞎改过，不可用，自己需要自己去申请
aliyun.accessKeyId=LTAI5t8C7ksWByD19Uww453o
aliyun.accessKeySecret=6QUzGVVhz14KPOg50IGBHh54456343
```

> 资源类

```java
package com.dexlace.common.properties;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/29
 */
@Component
@PropertySource("classpath:aliyun.properties")
@ConfigurationProperties(prefix = "aliyun")
public class AliyunResourceProperties {
    private String accessKeyId;
    private String accessKeySecret;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
```

### 3.3 创建短信发送工具类

落码模块：imood-news-dev-common

参考着阿里云官网去改

```java
package com.dexlace.common.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.dexlace.common.properties.AliyunResourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/29
 */
@Component
public class SMSUtils {

    @Autowired
    public AliyunResourceProperties aliyunResource;

    public void sendSMS(String mobile, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                aliyunResource.getAccessKeyId(),
                aliyunResource.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");

        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "dexlacce");
        request.putQueryParameter("TemplateCode", "SMS_183761535");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}


```

### 3.4 发送短信

> 相关controller接口

```java
@Api(value = "用户注册登录", tags = {"用户注册登录的controller"})
@RequestMapping("passport")
public interface PassportControllerApi {
    @ApiOperation(value = "获得短信验证码", notes = "获得短信验证码", httpMethod = "GET")
    @GetMapping("/getSMSCode")
    GraceIMOODJSONResult getSMSCode(@RequestParam String mobile, HttpServletRequest request);
}
```

> 实现落码模块：imood-news-dev-service-user

```java
@Autowired
private SMSUtils smsUtils;

@Override
public GraceJSONResult getSMSCode() {

    String random = "123789";
    smsUtils.sendSMS(你的手机号, random);

    return GraceJSONResult.ok();
}

```

### 3.5 完善短信服务

> 实现落码模块：imood-news-dev-service-user
>
> 完善功能：
>
> ​    1、锁住用户ip 60秒
>
> ​    2、设置验证码有效时间30分钟

```java
@RestController
public class PassportController extends BaseController implements PassportControllerApi {
    @Autowired
    private SMSUtils smsUtils;
    public static final String REDIS_USER_TOKEN = "redis_user_token";

    @Override
    public GraceIMOODJSONResult getSMSCode(String mobile, HttpServletRequest request) {
        // 根据用户的ip来限制用户在60秒内只能获得一次验证吗。
        String userIP = IPUtil.getRequestIp(request);
        // value随意填写即可，因为仅仅只是用来锁住ip的，60秒过后，又能发送了
        // 也就是说，这个key存在，则当前ip用户无法发送验证码，直到key消失
        // 因为存在该用户的key啊，不会创建新的
        // 但是防刷限制还要去结合拦截器
        redis.setnx60s(MOBILE_SMSCODE + userIP, userIP);

        // 哈哈哈  这里只是做了隐私保护，对于前端演示而说，当前端输入123456时会转换为
        // 实际的电话号码15914463559  这里不做脱敏处理
//        if (mobile.equalsIgnoreCase("123456")) {
//            mobile = "15914463559";
//        }
        String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
        // 把验证码放入redis，并且有效时间为30分钟
        redis.set(MOBILE_SMSCODE + mobile, random, 30 * 60);
        // 没有钱买短信服务的注释下面的代码  然后去redis中看信息
        smsUtils.sendSMS(mobile, random);
        return GraceIMOODJSONResult.ok();
    }
}
```

> 自定义拦截器，限制60秒发送
>
> 落码模块：imood-news-dev-service-api

```java
package com.dexlace.api.interceptor;

import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.IPUtil;
import com.dexlace.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/29
 */
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redis;

    public static final String MOBILE_SMSCODE = "mobile:smscode:";

    /**
     * 拦截请求，在访问controller调用之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userIP = IPUtil.getRequestIp(request);

        boolean keyIsExist = redis.keyIsExist(MOBILE_SMSCODE + userIP);

        // 根据去redis查验是否存在这个key来决定是否放行
        if (keyIsExist) {
            GraceException.display(ResponseStatusEnum.SMS_NEED_WAIT_ERROR);
            return false;
        }

        /**
         * false: 请求被拦截，被驳回，验证出现问题
         * true: 请求在经过验证校验以后，是OK的，是可以放行的
         */
        return true;
    }

    /**
     * 请求访问controller之后，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求访问controller之后，渲染视图之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}

```

> 注册拦截器
>
>    落码模块：imood-news-dev-service-api

```java
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public PassportInterceptor passportInterceptor() {
        return new PassportInterceptor();
    }
    
     @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor())
                .addPathPatterns("/passport/getSMSCode");
    }
}
```

以上，是短信发送服务的实现

### 3.6 跨域问题

> 浏览器会限制对域名的调用，不同的域名发起请求到不同的接口会出现跨域问题，为了解决这一问题，可以通过springboot来解决，参考如下代码，主要开放限制域名为*，也就是所有进行调用接口。此配置类为项目配置，可以直接放入任何项目中即可使用
>
> 落码模块：api模块

```java
package com.dexlace.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 解决跨域问题
 *
 */
@Configuration
public class CorsConfig {

    public CorsConfig() {
    }

    @Bean
    public CorsFilter corsFilter() {
        // 1. 添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        // 设置是否发送cookie信息
        config.setAllowCredentials(true);
        // 设置允许请求的方式
        config.addAllowedMethod("*");
        // 设置允许的header
        config.addAllowedHeader("*");
        // 2. 为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);
        // 3. 返回重新定义好的corsSource
        return new CorsFilter(corsSource);
    }

}
```

### 3.7 自定义异常

> common模块中
>
> 先定义一个异常类

```java
package com.dexlace.common.exception;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/30
 */


import com.dexlace.common.result.ResponseStatusEnum;

/**
 * 自定义异常
 * 目的：1. 统一异常处理和管理
 *      2. service与controller错误解耦，不会被service返回的类型而限制
 *
 * RuntimeException: 没有侵入性，如果继承Exception，则代码中需要使用try/catch
 */
public class MyCustomException extends RuntimeException {

    private ResponseStatusEnum responseStatus;

    public MyCustomException(ResponseStatusEnum responseStatus) {
        super("异常状态码: " +  responseStatus.status() + "; 异常信息: " + responseStatus.msg());
        this.responseStatus = responseStatus;
    }

    public ResponseStatusEnum getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatusEnum responseStatus) {
        this.responseStatus = responseStatus;
    }
}


```

> @ControllerAdvice增强之
>
> 只要抛出这个MyCustomException异常，就会知悉相应的方法，注意@ResponseBody在这的使用

```java
/**
 * 统一异常拦截处理
 * 可以针对异常类型进行补货处理，然后返回信息到页面
 */
@ControllerAdvice
public class GraceExceptionHandler {

    final static Logger logger = LoggerFactory.getLogger(GraceExceptionHandler.class);

    /**
     * 只要抛出MyCustomException，就会被此方法拦截到，随后可以自定义处理
     * @param e
     * @return
     */
    @ExceptionHandler(MyCustomException.class)
    @ResponseBody
    public GraceIMOODJSONResult returnMyException(MyCustomException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        return GraceIMOODJSONResult.exception(e.getResponseStatus());
    }
}
```

### 3.8 VO,BO,DTO与PO 

> VO(Value Object):
>
> ==后端传给前端展示==
>
> 对应于界面显示的数据对象

> BO(Business Object)
>
> 业务对象
>
> ==前端传给后端用==
>
> 主要作用是把业务逻辑封装为一个对象。这个对象可以包括一个或多个其它的对象。
>
> 形象描述为一个对象的形为和动作，当然也有涉及到基它对象的一些形为和动作。

> DTO(Data Transfer Object)
>
> 数据传输对象
>
> 主要用于==远程调用==等需要大量传输对象的地方

> PO(Persistant Object)
>
> ==持久化对象==

### 3.9 注册登录-验证BO信息

> 如上一节所介绍的，BO对象是一个面向业务封装的对象，所以我们需要封装一个注册/登录的BO对象
>
> 当然，在model中定义：

```java
package com.dexlace.model.bo;

import javax.validation.constraints.NotBlank;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/30
 */
public class RegisterLoginBO {

    @NotBlank(message = "手机号不能为空")
    private String mobile;
    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    @Override
    public String toString() {
        return "RegisterLoginBO{" +
                "mobile='" + mobile + '\'' +
                ", smsCode='" + smsCode + '\'' +
                '}';
    }
}


```

> api模块中注册登录接口的定义

```java
    @ApiOperation(value = "一键注册登录接口", notes = "一键注册登录接口", httpMethod = "POST")
    @PostMapping("/doLogin")
    GraceIMOODJSONResult doLogin(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestBody @Valid RegisterLoginBO registerBO,
                                 BindingResult result);
```

> user模块中关于注册登录接口的实现

```java
public GraceIMOODJSONResult doLogin(HttpServletRequest request,
                                        HttpServletResponse response,
                                        RegisterLoginBO registerLoginBO,
                                        BindingResult result) {
        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return GraceIMOODJSONResult.errorMap(errorMap);
        }

        // 获得前端传来的基本信息
        String smsCode = registerLoginBO.getSmsCode();
        String mobile = registerLoginBO.getMobile();

        // 0. 校验验证码是否匹配
        String redisSMSCode = redis.get(MOBILE_SMSCODE + mobile);
        if (StringUtils.isBlank(redisSMSCode) || !redisSMSCode.equalsIgnoreCase(smsCode)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }

        // 1. 查询数据库，该用户是否注册过
        AppUser user = userService.queryMobileIsExist(mobile);
        if (user != null && user.getActiveStatus() == UserStatus.FROZEN.type) {
            // 2. 如果用户不为空并且状态已经冻结，则直接抛出异常，无法登录
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.USER_FROZEN);
        } else if (user == null) {
            // 3. 如果没有注册过，注册用户信息入库，用户状态未激活
            user = userService.createUser(mobile);
        }


        return GraceIMOODJSONResult.ok(userActiveStatus);
    }
```

> 相应的service层的
>
>    queryMobileIsExist  
>
>    createUser

```java
   @Autowired
    private AppUserMapper appUserMapper;

@Override
    public AppUser queryMobileIsExist(String mobile) {
        Example userExample = new Example(AppUser.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        // 前一个是属性，是AppUser的属性，后一个是我们传进来的值
        userCriteria.andEqualTo("mobile", mobile);
        AppUser user = appUserMapper.selectOneByExample(userExample);
        return user;
    }
```

```java
   
  @Autowired
    private Sid sid;

private static final String USER_FACE0 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
//      private static final String USER_FACE1 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxF6ZUySASMbOAABBAXhjY0Y649.png";
//    private static final String USER_FACE2 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxF6ZUx6ANoEMAABTntpyjOo395.png";

    @Transactional
    @Override
    public AppUser createUser(String mobile) {

        /**
         * 互联网项目都要考虑可扩展性，
         * 如果未来业务发展，需要分库分表，
         * 那么数据库表主键id必须保证全局（全库）唯一，不得重复
         */
        String userId = sid.nextShort();

        AppUser user = new AppUser();
        user.setId(userId);
        user.setMobile(mobile);
        // 脱敏
        user.setNickname("用户" + DesensitizationUtil.commonDisplay(mobile));
        // 默认头像
        user.setFace(USER_FACE0);

        // 默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 默认性别为 保密
        user.setSex(Sex.secret.type);
        // 默认用户状态为未激活
        user.setActiveStatus(UserStatus.INACTIVE.type);
        // 默认用户总收入
        user.setTotalIncome(0);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        appUserMapper.insert(user);

        return user;
    }
```

> 注意，扫描的包,因为用上了别的第三方工具类，且是以源码形式导入的

```java
// 扫描所有包以及相关组件包
@ComponentScan({"com.dexlace","org.n3r.idworker"})
```

### 3.10 注册登录-设置会话与cookie信息

> 续上doLogin之后的逻辑：
>
>  设置会话与cookie信息

```java
      // 注册后或者登录之后的的token设置
        int userActiveStatus = user.getActiveStatus();
// 如果用户状态已经被封了，则不设置会话与cookie信息
        if (userActiveStatus != UserStatus.FROZEN.type) {
            // 3. 用户的分布式会话设置
            String uniqueToken = UUID.randomUUID().toString().trim();
            redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uniqueToken);
            redis.set(REDIS_USER_INFO + ":" + user.getId(), JsonUtils.objectToJson(user));

            // 4. 用户userId与token放入cookie
            setCookie(request, response, "utoken", uniqueToken, COOKIE_MONTH);
            setCookie(request, response, "uid", user.getId(), COOKIE_MONTH);
        }

// 5. 用户登录成功则需要删除已经使用过的短信验证码，该验证码只能匹配使用一次，过后作废
           redis.del(MOBILE_SMSCODE + mobile);

// 6. 用户状态：未激活或者已激活，返回给前端，用于页面跳转  这里与前端的约定为用户的状态码
        return GraceIMOODJSONResult.ok(userActiveStatus);
```

> 涉及到的setCookie如下实现
>
>   注意，这是在BaseController中实现的，每个具体的controller都继承该方法

```java
public void setCookie(HttpServletRequest request,
                          HttpServletResponse response,
                          String cookieName,
                          String cookieValue,
                          Integer MaxAge) {
        try {
            cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            setCookieValue(request, response, cookieName, cookieValue, MaxAge);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setCookieValue(HttpServletRequest request,
                               HttpServletResponse response,
                               String cookieName,
                               String cookieValue,
                               Integer MaxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(MaxAge);
        cookie.setDomain(DOMAIN_NAME);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

```

### 3.11 查询用户账户信息

> 注册登录之后的逻辑必然是显示用户账户信息，所以需要查询用户账户信息

> api模块中定义：

```java
@Api(value = "用户controller", tags = {"用户controller"})
@RequestMapping("user")
public interface UserControllerApi {
    @PostMapping("/getAccountInfo")
    @ApiOperation(value = "获得用户账户信息", notes = "获得用户账户信息", httpMethod = "POST")
    GraceIMOODJSONResult getAccountInfo(@RequestParam String userId);
}
```

> user模块中实现

```java
@RestController
public class UserController extends BaseController implements UserControllerApi {


    @Autowired
    private UserService userService;

    @Override
    public GraceIMOODJSONResult getAccountInfo(String userId) {

        // 0. 判断不能为空
        if (StringUtils.isBlank(userId)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }

        // 1. 查询userId
        AppUser user = getUser(userId);

        // 2. 设置需要展示的信息，不用entity对象，entity有其他不能展示的信息
        UserAccountInfoVO accountVO = new UserAccountInfoVO();
        // 只要属性名一样就可以复制
        BeanUtils.copyProperties(user, accountVO);

        return GraceIMOODJSONResult.ok(accountVO);
    }
}
```

> dao层，比如这里的getUser不再列出，没有必要，还有这里的UserAccountInfoVO信息，只要知道VO对象是什么，前端需要什么就好了，不再提示

> 注意拦截器的问题
>
>   能到达用户展示页面必须有token信息
>
>   对于前端代码如下可知，前端已经获取cookie，并将cookie信息放到了header中，所以我们需要获得的是header信息，而不是cookie信息去拦截

<img src="Judy-news.assets/image-20210607111333132.png" alt="image-20210607111333132" style="zoom:67%;" />

```java
package com.dexlace.api.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/3
 */
public class UserTokenInterceptor extends BaseInterceptor implements HandlerInterceptor{
    final static Logger logger= LoggerFactory.getLogger(UserTokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        logger.info("userId: "+userId+";"+"userToken: "+userToken);

        // 判断是否放行
        // userId:userToken构成了一个唯一的标识
        boolean run = verifyUserIdToken(userId, userToken, REDIS_USER_TOKEN);
        logger.info(String.valueOf(run));
        return run;
    }


}

```

> 对应的校验token的方法

```java
package com.dexlace.api.interceptor;

import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/3
 */
public class BaseInterceptor {

    final static Logger logger= LoggerFactory.getLogger(BaseInterceptor.class);
    @Autowired
    private RedisOperator redis;

    public static final String REDIS_USER_TOKEN = "redis_user_token";
    public static final String REDIS_ADMIN_TOKEN = "redis_admin_token";

    public boolean verifyUserIdToken(String id, String token, String redisKeyPrefix) {
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(token)) {
            String uniqueToken = redis.get(redisKeyPrefix + ":" + id);
            if (StringUtils.isBlank(uniqueToken)) {
                // 这里双重检查，token过期判断
                logger.info("uniqueToken: "+uniqueToken);
                GraceException.display(ResponseStatusEnum.UN_LOGIN);
                return false;
            } else {
                if (!uniqueToken.equals(token)) {
                    // 验证token对不对
                    GraceException.display(ResponseStatusEnum.TICKET_INVALID);
                    return false;
                }
            }
        } else {
            GraceException.display(ResponseStatusEnum.UN_LOGIN);
            return false;
        }

        /**
         * false: 请求被拦截，被驳回，验证出现问题
         * true: 请求在经过验证校验以后，是OK的，是可以放行的
         */

        // 这里双重检查，token过期判断
        logger.info("token验证成功: "+token);
        return true;
    }
}



```

> user/getAccountInfo路径注册拦截器

```java
  
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

 @Bean
    public UserTokenInterceptor userTokenInterceptor() {
        return new UserTokenInterceptor();
    }
     
  @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userTokenInterceptor())
                .addPathPatterns("/user/getAccountInfo")
    }
}
    
```

### 3.12 查询用户账户信息-注意查缓存

> 上一节的查询用户账户信息中，并不是直接去查询数据库，而是直接将用户信息缓存
>
> 我们无论是注册还是登录，已经把用户信息缓存了
>
> 即getUser的实现如下：

```java
 private AppUser getUser(String userId) {
        // 1. 查询redis中是否包含用户信息，如果包含则查询redis返回，如果不包含则查询数据库
        String userJson = redis.get(REDIS_USER_INFO + ":" + userId);
        logger.info("userJson: " + userJson);

        AppUser user = null;
        if (StringUtils.isNotBlank(userJson)) {
            user = JsonUtils.jsonToPojo(userJson, AppUser.class);
        } else {
            user = userService.getUser(userId);
            // 2. 由于用户信息不怎么会变动，对于千万级别的网站，这类信息数据不会去查询数据库，完全可以把用户信息存入redis
            // 哪怕修改信息，也不会立马体现，这也是弱一致性，在这里有过期时间，比如1天以后，用户信息会更新到页面显示，或者缩短到1小时，都可以
            // 基本信息在新闻媒体类网站是属于数据一致性优先级比较低的，用户眼里看的主要以文章为主，至于文章是谁发的，一般来说不会过多关注
            redis.set(REDIS_USER_INFO + ":" + userId, JsonUtils.objectToJson(user));
        }
        return user;
    }
```

### 3.13 用户完善个人信息

> api定义接口

```java
@PostMapping("/updateUserInfo")
@ApiOperation(value = "完善用户信息", notes = "完善用户信息", httpMethod = "POST")
public GraceJSONResult updateUserInfo(@RequestBody @Valid UpdateUserInfoBO updateUserInfoBO,
                                      BindingResult result);

```

> userController中完善个人信息

```java
  @Override
    public GraceIMOODJSONResult updateUserInfo(UpdateUserInfoBO updateUserInfoBO,
                                               BindingResult result) {

        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return GraceIMOODJSONResult.errorMap(errorMap);
        }

        // 执行更新用户信息操作
        userService.updateUserInfo(updateUserInfoBO);
        return GraceIMOODJSONResult.ok();
    }

```

> 这里的BO信息还是提一下吧

```java
package com.dexlace.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.*;
import java.util.Date;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/30
 */
public class UpdateUserInfoBO {
    @NotBlank(message = "用户ID不能为空")
    private String id;

    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 12, message = "用户昵称不能超过12位")
    private String nickname;

    @NotBlank(message = "用户头像不能为空")
    private String face;

    @NotBlank(message = "真实姓名不能为空")
    private String realname;

    @Email
    @NotBlank(message = "邮件不能为空")
    private String email;

    @NotNull(message = "请选择一个性别")
    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 1, message = "性别选择不正确")
    private Integer sex;

    @NotNull(message = "请选择生日日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") // 解决前端日期字符串传到后端后，转换为Date类型
    private Date birthday;

    @NotBlank(message = "请选择所在城市")
    private String province;

    @NotBlank(message = "请选择所在城市")
    private String city;

    @NotBlank(message = "请选择所在城市")
    private String district;

   /*****以下都是get和set方法******/
}
```

### 3.14 用户完善个人信息-注意缓存的更新

> 双写数据不一致问题：
>
>    数据库到缓存之间断了，数据库是新的，redis/es/mongo是老的，显然数据不一致

> >这是正常的读取数据流程，如果redis和数据库中的数据不一致，那么就读不到正确的数据
> >
> ><img src="Judy-news.assets/image-20210607155202299.png" alt="image-20210607155202299" style="zoom: 33%;" />
> >
> >假设redis中没有，有别人在更新数据，而还没等他更新完，你自己去查到的必然是旧数据，那么旧数据将会写入redis，你得到的必然是旧数据，这就是双写不一致
> >
> ><img src="Judy-news.assets/image-20210607160255172.png" alt="image-20210607160255172" style="zoom: 50%;" />

> 如何达成双写一致：
>
>    1、更新操作开始一定从redis中删除缓存（在更新数据库之前删除一次）
>
>    2、更新数据库，并设置缓存
>
>    3、sleep一秒
>
>    4、再次删除缓存并设置（在更新数据库之后再删除一次并设置缓存）
>
>    如此一来，就能保证在sleep以后的缓存数据在再次被读取时会从db获取，如此一来达到一致性。 

> 上述说的问题就是更新的service层具体如何实现

```java
 @Override
    public void updateUserInfo(UpdateUserInfoBO updateUserInfoBO) {

        String userId = updateUserInfoBO.getId();


        // 用户更新数据前，先把缓存数据删除，然后更新数据库，再同步到redis中去
        // 哪怕redis存入不成功，那么后续用户发起的请求还可以先查库存后存缓存，达到一致性
        // 双写一致，先删除redis，在更新，再设置redis
        // 删第一次
        redis.del(REDIS_USER_INFO + ":" + userId);




        AppUser userInfo = new AppUser();
        BeanUtils.copyProperties(updateUserInfoBO, userInfo);

        userInfo.setUpdatedTime(new Date());
        userInfo.setActiveStatus(UserStatus.ACTIVE.type);

        int result = appUserMapper.updateByPrimaryKeySelective(userInfo);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.USER_UPDATE_ERROR);
        }

        // 更新数据库后一个正常的操作是写入redis
        // 再次查询用户最新信息，随后存入redis
        AppUser user = getUser(userId);
//        redis.set(REDIS_USER_INFO + ":" + userId, JsonUtils.objectToJson(user), 7);
        redis.set(REDIS_USER_INFO + ":" + userId, JsonUtils.objectToJson(user));

        // 缓存双删策略
        try {
            Thread.sleep(200);
            // 删第二次
            redis.del(REDIS_USER_INFO + ":" + userId);
            redis.set(REDIS_USER_INFO + ":" + userId, JsonUtils.objectToJson(user), 7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

```

> 同样，更新用户信息也要设置拦截器，否则不该被访问

```java
  @Bean
    public UserTokenInterceptor userTokenInterceptor() {
        return new UserTokenInterceptor();
    }
  

   @Override
    public void addInterceptors(InterceptorRegistry registry) {
  
        registry.addInterceptor(userTokenInterceptor())
                .addPathPatterns("/user/updateUserInfo");
    }
```

### 3.15 用户激活状态拦截器-本文至此还未用到

```java
package com.dexlace.api.interceptor;

import com.dexlace.common.enums.UserStatus;
import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.RedisOperator;
import com.dexlace.model.entity.AppUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/3
 */
/**
 * 用户激活状态检查拦截器
 * 发文章，修改文章等
 * 发评论，查看评论等
 * 查看我的粉丝等，这些媒体中心的功能必须用户激活后，才能进行，
 * 否则提示用户前往[账号设置]去修改信息
 */
public class UserActiveInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redis;

    public static final String REDIS_USER_INFO = "redis_user_info";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("headerUserId");
        // 1. 偷懒，可以从userController中拷贝过来
        String userJson = redis.get(REDIS_USER_INFO + ":" + userId);
        AppUser user = null;
        if (StringUtils.isNotBlank(userJson)) {
            user = JsonUtils.jsonToPojo(userJson, AppUser.class);
        } else {
            GraceException.display(ResponseStatusEnum.UN_LOGIN);
            return false;
        }

        // 如果不是激活状态则不能执行后续操作
        if (user.getActiveStatus() == null || user.getActiveStatus() != UserStatus.ACTIVE.type) {
            GraceException.display(ResponseStatusEnum.USER_INACTIVE_ERROR);
            return false;
        }

        return true;
    }
}

```

> 需要注册的路径对应的业务至此还未涉及

### 3.16 AOP日志监控

> api模块
>
> 注意监控的包的表达式的书写

```java
package com.dexlace.api.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/3
 */
@Aspect
@Component
public class ServiceLogAspect {

    public static final Logger log =
            LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * AOP通知：
     * 1. 前置通知：在方法调用之前执行
     * 2. 后置通知：在方法正常调用之后执行
     * 3. 环绕通知：在方法调用之前和之后，都分别可以执行的通知
     * 4. 异常通知：如果在方法调用过程中发生异常，则通知
     * 5. 最终通知：在方法调用之后执行
     */

    /**
     * 切面表达式：
     * execution 代表所要执行的表达式主体
     * 第一处 * 代表方法返回类型 *代表所有类型
     * 第二处 包名层级代表aop监控的类所在的包   这里的包名层级代表具体的业务模块。比如user，admin之类的
     * 第三处 .*. 代表匹配某一个包，因为我们命名的包都是有一定的规则规范的
     * 第四处 .. 代表该包以及其子包下的所有类方法
     * 第五处 * 代表类名，*代表所有类
     * 第六处 *(..) *代表类中的方法名，(..)表示方法中的任何参数
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.dexlace.*.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("====== 开始执行 {}.{} ======",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());

        // 记录开始时间
        long begin = System.currentTimeMillis();

        // 执行目标 service
        Object result = joinPoint.proceed();

        // 记录结束时间
        long end = System.currentTimeMillis();
        long takeTime = end - begin;

        if (takeTime > 3000) {
            log.error("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        } else if (takeTime > 2000) {
            log.warn("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        } else {
            log.info("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        }

        return result;
    }

}

```

> 开启mybatis日志

```yml
# dev 开发环境开启mybatis日志
mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

```

### 3.17 退出登录，注销会话

```java
  @PostMapping("/logout")
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    GraceIMOODJSONResult logout(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam String userId);
```

```java
  @Override
    public GraceIMOODJSONResult logout(HttpServletRequest request, HttpServletResponse response, String userId) {


            // 1. 清除用户已登录的会话信息
            redis.del(REDIS_USER_TOKEN + ":" + userId);

            // 2. 清除用户userId与token的cookie
            setCookie(request, response, "utoken", "", COOKIE_DELETE);
            setCookie(request, response, "uid", "", COOKIE_DELETE);

            return GraceIMOODJSONResult.ok();


    }
```

## 四、文件存储服务

### 4.1 FastDfs架构图与上传下载时序图

> 分布式文件系统
>
> > - 随着文件数据的越来越多，通过==tomcat或nginx虚拟化的静态资源文件在单一的一个服务器节点内==是存不下的，如果用多个节点来存储也可以，但是不利于管理和维护，所以我们需要一个系统来管理多台计算机节点上的文件数据，这就是分布式文件系统。
> > - 分布式文件系统是一个允许文件通过网络在多台节点上分享的文件系统，多台计算机节点共同组成一个整体，为更多的用户提供分享文件和存储空间。比如==常见的网盘，本质就是一个分布式的文件存储系统==。虽然我们是一个分布式的文件系统，但是对用户来说是透明的，用户使用的时候，就像是访问本地磁盘一样。
> > - 分布式文件系统可以提供==冗余备份==，所以容错能力很高。 系统中有某些节点宕机，但是整体文件服务不会停止，还是能够为用户提供服务，整体还是运作的，数据也不会丢失。
> > - 分布式文件系统的==可扩展性强==，增加或减少节点都很简单，不会影响线上服务，增加完毕后会发布到线上，加入到集群中为用户提供服务。
> > - 分布式文件系统可以提供==负载均衡能力==，在读取文件副本的时候可以由多个节点共同提供服务，而且可以通过横向扩展来确保性能的提升与负载。

> 文件存储的形式
>
> > - 传统服务器存储
> > - FastDFS
> > - OSS
> > - GridFS

> 什么是FastDFS
>
> > - FastDFS是一个开源的轻量级分布式文件系统，它对文件进行管理，功能包括：==文件存储、文件同步、文件访问（文件上传、文件下载）==等，解决了大容量存储和负载均衡的问题。特别适合以文件为载体的在线服务，如相册网站、视频网站等等。
> > - FastDFS为互联网量身定制，充分考虑了冗余备份、负载均衡、线性扩容等机制，并注重高可用、高性能等指标，使用FastDFS很容易搭建一套高性能的文件服务器集群提供文件上传、下载等服务。
> > - FastDFS服务端有两个角色：==跟踪器（tracker）和存储节点（storage）==。跟踪器主要做调度工作，在访问上起负载均衡的作用。
> > - 存储节点存储文件，完成文件管理的所有功能：就是这样的存储、同步和提供存取接口，FastDFS同时对文件的metadata进行管理。所谓文件的meta data就是文件的相关属性，以键值对（key value）方式表示，如：width=1024，其中的key为width，value为1024。文件metadata是文件属性列表，可以包含多个键值对。
> > - 跟踪器和存储节点都可以由一台或多台服务器构成。跟踪器和存储节点中的服务器均可以随时增加或下线而不会影响线上服务。其中跟踪器中的所有服务器都是对等的，可以根据服务器的压力情况随时增加或减少。
> > - 为了支持大容量，存储节点（服务器）采用了分卷（或分组）的组织方式。存储系统由一个或多个卷组成，卷与卷之间的文件是相互独立的，所有卷的文件容量累加就是整个存储系统中的文件容量。一个卷可以由一台或多台存储服务器组成，一个卷下的存储服务器中的文件都是相同的，卷中的多台存储服务器起到了冗余备份和负载均衡的作用。
> > - 在卷中增加服务器时，同步已有的文件由系统自动完成，同步完成后，系统自动将新增服务器切换到线上提供服务。
> > - 当存储空间不足或即将耗尽时，可以动态添加卷。只需要增加一台或多台服务器，并将它们配置为一个新的卷，这样就扩大了存储系统的容量。
> > - FastDFS中的文件标识分为两个部分：卷名和文件名，二者缺一不可。
>
> ### 常见术语
>
> - ==tracker==：追踪者服务器，主要用于==协调调度==，可以起到负载均衡的作用，==记录storage的相关状态信息==。
> - ==storage==：==存储服务器==，用于保存文件以及文件的元数据信息。
> - group：组，同组节点提供冗余备份，不同组用于扩容。
> - mata data：文件的元数据信息，比如长宽信息，图片后缀，视频的帧数等。

> FastDFS图示：
>
> > fastdfs架构图：
> >
> > <img src="Judy-news.assets/image-20210607172604638.png" alt="image-20210607172604638" style="zoom:50%;" />
> >
> > fastdfs上传时序图
> >
> > <img src="Judy-news.assets/image-20210607172923277.png" alt="image-20210607172923277" style="zoom: 50%;" />
> >
> > fastdfs下载时序图
> >
> > <img src="Judy-news.assets/image-20210607173024262.png" alt="image-20210607173024262" style="zoom: 50%;" />

### 4.2 FastDFS-环境准备

> ### 参考文献
>
> https://github.com/happyfish100/
> https://github.com/happyfish100/fastdfs/wiki
> https://www.cnblogs.com/leechenxiang/p/5406548.html
> https://www.cnblogs.com/leechenxiang/p/7089778.html
>
> ### 环境准备
>
> - Centos7.x 两台，分别安装tracker与storage
> - 下载安装包：
>   - libfatscommon：FastDFS分离出的一些公用函数包
>   - FastDFS：FastDFS本体
>   - fastdfs-nginx-module：FastDFS和nginx的关联模块
>   - nginx：发布访问服务

> ### 安装基础环境
>
> gcc/g++
>
> > ```bash
> > yum install -y gcc gcc-c++
> > yum -y install libevent
> > ```
>
> 安装libfatscommon函数库
>
> > ```bash
> > > tar -zxvf libfastcommon-1.0.42.tar.gz
> > >
> > > # 编译并安装
> > >
> > > ./make.sh
> > > ./make.sh install
> > 
> > 
> > ```
>
> 安装fastdfs主程序文件(不论是tracker还是storage都要配置)
>
> > ```bash
> > tar -zxvf fastdfs-6.04.tar.gz
> > 
> > # 编译并安装
> > ./make.sh
> > ./make.sh install    
> > 
> > # /usr/bin 中包含了可执行文件；
> > # /etc/fdfs 包含了配置文件；
> > # 将解压包里的conf文件夹下的所有文件拷贝到 /etc/fdfs/
> > 
> > ```

### 4.3 配置tracker服务

> 修改/etc/fdfs下的配置文件tracker.conf
>
> ```bash
> # 配置base_path 该目录为tracker的工作目录，保存数据以及日志
> # 注意该目录不要忘了创建
> base_path=你要配置的目录
> ```

> 启动tracker服务
>
> ```bash
> /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf
> ```
>
> 检查进程
>
> ```bash
> ps -ef|grep tracker
> ```
>
> 停止tracker
>
> ```bash
> /usr/bin/stop.sh /etc/fdfs/tracker.conf
> ```

### 4.4 配置storage服务

> 修改etc/fdfs的配置文件storage.conf
>
> > ```properties
> > # 修改组名  自己定义即可
> > group_name=imood
> > # 修改storage的工作空间 路径自己去定义
> > base_path=/usr/local/fastdfs/storage
> > # 修改storage的存储空间
> > store_path0=/usr/local/fastdfs/storage
> > # 修改tracker的地址和端口号，用于心跳
> > tracker_server=192.168.205.116:22122
> > 
> > # 后续结合nginx的一个对外服务端口号,自己去定义
> > http.server_port=8888
> > 
> > ```

> 启动storage
>
> > ```bash
> > /usr/bin/fdfs_storaged /etc/fdfs/storage.conf
> > ```

> 检查进程
>
> > ```bash
> > ps -ef|grep storage
> > ```

> 测试上传
>
> > 修改client配置文件client.conf
> >
> > 反正要设置路径的地方自己设置就好了
> >
> > ```properties
> > base_path=/usr/local/fastdfs/client
> > tracker_server=192.168.205.116:22122
> > ```
> >
> > 测试上传
> >
> > ```bash
> > 
> > /usr/bin/fdfs_test  /etc/fdfs/client.conf  upload  某图片路径
> > # 会得到远程图片访问路径
> > ```
> >
> > 查看文件存储位置:
> >
> > ```bash
> > strorage存储空间路径/data
> > ```

### 4.5 安装Nginx服务

> 与storage服务安装在同一台机器上

> 1、去[官网http://nginx.org/](http://nginx.org/)下载对应的nginx包，推荐使用稳定版本
>
> 2、安装依赖环境
>
> > ```bash
> > # 安装gcc环境
> > yum install gcc-c++
> > # 安装PCRE库，用于解析正则表达式
> > yum install -y pcre pcre-devel
> > # zlib压缩和解压缩依赖，
> > yum install -y zlib zlib-devel
> > # SSL 安全的加密的套接字协议层，用于HTTP安全传输，也就是https
> > yum install -y openssl openssl-devel
> > ```
>
> 3、安装
>
> > ```bash
> > tar -zxvf nginx-1.16.1.tar.gz
> > # 编译之前，先创建nginx临时目录，如果不创建，在启动nginx的过程中会报错
> > mkdir /var/temp/nginx -p
> > 
> > # 创建makefile文件
> > ./configure \
> > --prefix=/usr/local/nginx \
> > --pid-path=/var/run/nginx/nginx.pid \
> > --lock-path=/var/lock/nginx.lock \
> > --error-log-path=/var/log/nginx/error.log \
> > --http-log-path=/var/log/nginx/access.log \
> > --with-http_gzip_static_module \
> > --http-client-body-temp-path=/var/temp/nginx/client \
> > --http-proxy-temp-path=/var/temp/nginx/proxy \
> > --http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
> > --http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
> > --http-scgi-temp-path=/var/temp/nginx/scgi
> > 
> > make
> > make install
> > ```
>
> 4、启动
>
> > ```bash
> > # 进入/usr/local/nginx/sbin
> > 
> > ./nginx
> > # 停止：./nginx -s stop
> > # 重新加载：./nginx -s reload
> > ```
>
> 打开浏览器，访问虚拟机所处内网ip即可打开nginx默认页面

### 4.6 整合Nginx文件服务

> 安装整合fastdfs + nginx插件
>
> ```bash
> tar -zxvf fastdfs-nginx-module-1.22.tar.gz
> 
> # 复制配置文件到/etc/fdfs
> cp /解压路径/src/mod_fastdfs.conf /etc/fdfs
> 
> # 修改该模块下的conf文件，修改路径，因为fastfds安装时没有修改路径，原路径时/usr
> vim /解压路径/src/conf
> # 删掉/local
> ```
>
> 重新配置nginx
>
> ```bash
> ./configure \
> --prefix=/usr/local/nginx \
> --pid-path=/var/run/nginx/nginx.pid \
> --lock-path=/var/lock/nginx.lock \
> --error-log-path=/var/log/nginx/error.log \
> --http-log-path=/var/log/nginx/access.log \
> --with-http_gzip_static_module \
> --http-client-body-temp-path=/var/temp/nginx/client \
> --http-proxy-temp-path=/var/temp/nginx/proxy \
> --http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
> --http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
> --http-scgi-temp-path=/var/temp/nginx/scgi \
> --add-module=/解压路径/src
> 
> ```
>
> 重新编译安装nginx
>
> ```bash
> make && make install
> ```
>
> 修改复制过去的配置文件
>
>   /etc/fdfs/mod_fastdfs.conf 配置文件
>
> ```properties
> base_path=/usr/local/fastdfs/tmp
> tracker_server=192.168.205.116:22122
> group_name=imood
> url_have_group_name = true
> # 配置storage时的存储空间的路径
> store0_path=/usr/local/fastdfs/storage
> 
> ```
>
> 修改nginx.conf,修改配置如下：
>
> ```properties
> 
> #user  nobody;
> user root;
> worker_processes  1;
> pid /usr/local/nginx/logs/nginx.pid;
> 
> 
> 
> events {
>     worker_connections  1024;
> }
> 
> 
> http {
>     include       mime.types;
>     default_type  application/octet-stream;
> 
> 
> 
>     sendfile        on;
> 
>     keepalive_timeout  65;
> 
> 
>     server {
>         listen       8888;
>         server_name  localhost;
> 
>         location /imood/M00/ {
> 
>             ngx_fastdfs_module;
>         }
> 
> 
> 
>   }
> 
> }
> 
> ```
>
> 重新启动nginx
>
> ```bash
> # cd /usr/local/nginx
> /sbin/nginx -s stop
> /sbin/nginx
> ```

以上，便可以对外提供文件的上传和下载路径

### 4.7 整合fdfs

> 创建文件服务模块
>
> pom.xml
>
> > ```xml
> > <?xml version="1.0" encoding="UTF-8"?>
> > <project xmlns="http://maven.apache.org/POM/4.0.0"
> >          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
> >          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
> >     <parent>
> >         <artifactId>imood-news-dev</artifactId>
> >         <groupId>com.dexlace</groupId>
> >         <version>1.0-SNAPSHOT</version>
> >     </parent>
> >     <modelVersion>4.0.0</modelVersion>
> > 
> >     <!--
> >         imood-news-service-files:
> >         文件服务，文件相关操作都在此服务中进行，比如文件上传，文件读取
> >     -->
> >     <artifactId>imood-news-dev-service-files</artifactId>
> > 
> >     <dependencies>
> > 
> >         <dependency>
> >             <groupId>com.dexlace</groupId>
> >             <artifactId>imood-news-dev-service-api</artifactId>
> >             <version>1.0-SNAPSHOT</version>
> >         </dependency>
> > 
> >         <dependency>
> >             <groupId>com.github.tobato</groupId>
> >             <artifactId>fastdfs-client</artifactId>
> >         </dependency>
> > 
> > 
> > 
> >     </dependencies>
> > 
> > 
> > </project>
> > 
> > ```
>
> application.yml：
>
> 相关fdfs的配置
>
> > ```yml
> > ############################################################
> > #
> > # 文件微服务
> > # web访问端口号  约定：8004
> > #
> > ############################################################
> > 
> > server:
> >   port: 8004
> > 
> > 
> > 
> > server:
> >   tomcat:
> >     uri-encoding: UTF-8
> >     max-swallow-size: -1  # tomcat默认大小2M，超过2M的文件不会被捕获，需要调整此处大小为100MB或者-1即可
> > 
> > ############################################################
> > #
> > # 配置项目信息
> > #
> > ############################################################
> > spring:
> >   application:
> >     name: service-files
> >   jackson:
> >     date-format: yyyy-MM-dd HH:mm:ss
> >     time-zone: GMT+8
> >   servlet:
> >     multipart:
> >       max-file-size: 512000   # 请求的文件大小限制为500kb
> >       max-request-size: 512000
> >   redis:
> >     database: 1
> >     host: 192.168.205.114
> >     port: 6379
> >     password: 123456
> > 
> > 
> > 
> > 
> > ############################################################
> > #
> > # fdfs 配置
> > #
> > ############################################################
> > fdfs:
> >   connect-timeout: 30   # 连接的超时时间
> >   so-timeout: 30        # 读取的超时时间
> >   # TODO 修改生成环境的
> >   tracker-list: 192.168.205.116:22122   # tracker服务所在的ip地址和端口号
> > 
> > 
> > ```
>
> service
>
> > ```java
> > public interface UploaderService {
> > 
> >    String uploadFdfs(MultipartFile file, String fileExtName) throws Exception;
> > 
> > }
> > 
> > ```
> >
> > ```java
> > @Service
> > public class UploaderServiceImpl implements UploaderService {
> > 
> >     @Autowired
> >     private FastFileStorageClient fastFileStorageClient;
> > 
> >     @Override
> >     public String uploadFdfs(MultipartFile file, String fileExtName) throws Exception {
> > 
> >         StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(),
> >                 file.getSize(),
> >                 fileExtName,
> >                 null);
> > 
> >         String path = storePath.getFullPath();
> >         return path;
> >     }
> > }
> > ```

### 4.8 实现fastdfs图片存储

> api模块
>
> > ```java
> > @Api(value = "文件上传controller", tags = {"文件上传controller"})
> > @RequestMapping("fs")
> > public interface FileUploaderControllerApi {
> > 
> >     @ApiOperation(value = "上传用户头像", notes = "上传用户头像", httpMethod = "POST")
> >     @PostMapping("uploadFace")
> >     public GraceJSONResult uploadFace(
> >             @RequestParam String userId,
> >             MultipartFile file) throws Exception;
> > }
> > 
> > ```
>
> file模块
>
> 注意以下的基本逻辑只是完成了文件后缀名的校验和上传时候的基础路径生成
>
> ```java
>   public GraceIMOODJSONResult uploadFace(String userId, MultipartFile file) throws Exception {
> 
>         String path = "";
>         // 开始文件上传，判断不能为空
>         if (file != null) {
>             // 获得文件上传的文件名称
>             String fileName = file.getOriginalFilename();
>             // 判断文件名后缀符合要求
>             if (StringUtils.isNotBlank(fileName)) {
>                 // 文件重命名  imooc-face.png -> ["imooc-face", "png"]
>                 String fileNameArr[] = fileName.split("\\.");
> 
>                 // 获取文件的后缀名
>                 String suffix = fileNameArr[fileNameArr.length - 1];
>                 if (!suffix.equalsIgnoreCase("png") &&
>                         !suffix.equalsIgnoreCase("jpg") &&
>                         !suffix.equalsIgnoreCase("jpeg")) {
>                     return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.FILE_FORMATTER_FAILD);
>                 }
>                 path = uploaderService.uploadFdfs(file, suffix);
>             }
>         } else {
>             return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
>         }
>       /**************************************************/
>       /**********************这里要生成最终的路径*****************************/
>       /***************************************************/
>         return GraceIMOODJSONResult.ok(finalUserFaceUrl);
>     }
> ```
>
> 新建配置文件
>
> file-dev.properties:
>
> > ```properties
> > # fastdfs storage 的节点地址
> > file.host=http://192.168.205.117:8888/
> > 
> > ```
>
> 新建读取该文件的资源类
>
> > ```java
> > @Component
> > //@PropertySource("classpath:file-${spring.profiles.active}.properties")
> > @PropertySource("classpath:file-dev.properties")
> > @ConfigurationProperties(prefix = "file")
> > public class FileResource {
> > 
> >     private String host;
> > 
> >     public String getHost() {
> >         return host;
> >     }
> >     public void setHost(String host) {
> >         this.host = host;
> >     }
> > }
> > ```
>
> file模块上传功能完善：生成最终的路径
>
> > ```java
> > @Autowired
> > private FileResource fileResource;
> >     
> >    String finalUserFaceUrl = "";
> >         if (StringUtils.isNotBlank(path)) {
> >             finalUserFaceUrl = fileResource.getHost() + path;
> >         } else {
> >             return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
> >         }
> > 
> >         logger.info("finalUserFaceUrl = " + finalUserFaceUrl);
> >         return GraceIMOODJSONResult.ok(finalUserFaceUrl);
> > ```
>
> 注意拦截器：
>
> ```java
> registry.addInterceptor(userTokenInterceptor())
>                 .addPathPatterns("/fs/uploadFace")
> 
> ```

> 图片大小控制的统一处理
>
> MaxUploadSizeExceededException异常非自定义异常，在yml文件中配置了文件的大小限制，这里只需增强而已
>
> > ```java
> > /**
> >  * 统一异常拦截处理
> >  * 可以针对异常类型进行补货处理，然后返回信息到页面
> >  * 
> >  */
> > @ControllerAdvice
> > public class GraceExceptionHandler {
> > 
> >     final static Logger logger = LoggerFactory.getLogger(GraceExceptionHandler.class);
> > 
> > 
> >     @ExceptionHandler(MaxUploadSizeExceededException.class)
> >     @ResponseBody
> >     public GraceIMOODJSONResult returnMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
> >         return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.FILE_MAX_SIZE_ERROR);
> >     }
> > }
> > 
> > ```

此外，oss的不再介绍

## 五、Admin平台运营管理中心服务

- 包含分类管理
- 管理员账号管理
- 友情链接管理
- 文章内容人工审核
- 网站用户管理

### 5.1 账号密码登录

> adminBO对象：
>
> > ```java
> > package com.dexlace.model.bo;
> > 
> > /**
> >  * @Author: xiaogongbing
> >  * @Description:
> >  * @Date: 2021/5/4
> >  */
> > public class AdminLoginBO {
> > 
> >     private String username;
> >     private String password;
> >     private String img64;
> > 
> >    /*********get和set************/
> > 
> > }
> > 
> > 
> > ```
>
> api模块：
>
> > ```javascript
> > @Api(value = "管理员维护", tags = {"管理员维护controller"})
> > @RequestMapping("adminMng")
> > public interface AdminMngControllerApi {
> > 
> >     @PostMapping("/adminLogin")
> >     @ApiOperation(value = "管理员登录", notes = "管理员登录", httpMethod = "POST")
> >     GraceIMOODJSONResult adminLogin(@RequestBody AdminLoginBO adminLoginBO,
> >                                            HttpServletRequest request,
> >                                            HttpServletResponse response);
> > }
> > ```

> adminMngController
>
> ```java
> /**
>  * @Author: xiaogongbing
>  * @Description:
>  * @Date: 2021/5/4
>  */
> @RestController
> public class AdminMngController extends BaseController implements AdminMngControllerApi {
>     public static final Logger logger = LoggerFactory.getLogger(AdminMngController.class);
> 
>     @Autowired
>     private AdminUserService adminUserService;
> 
> 
>     @Autowired
>     private RestTemplate restTemplate;
> 
>     @Autowired
>     private FaceVerifyUtils faceVerifyUtils;
> 
> 
>     @Override
>     public GraceIMOODJSONResult adminLogin(AdminLoginBO adminLoginBO,
>                                            HttpServletRequest request,
>                                            HttpServletResponse response) {
> 
>         // 查询用户是否存在
>         AdminUser admin = adminUserService.queryAdminByUsername(adminLoginBO.getUsername());
>         if (admin == null) {
>             return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
>         }
> 
>         // 判断
>         boolean isPwdMatch = BCrypt.checkpw(adminLoginBO.getPassword(), admin.getPassword());
>         if (isPwdMatch) {
>             doLoginSettings(admin, request, response);
>             return GraceIMOODJSONResult.ok();
>         } else {
>             // 密码不匹配
>             return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
>         }
>     }
> 
>     private void doLoginSettings(AdminUser admin,
>                                  HttpServletRequest request,
>                                  HttpServletResponse response) {
>         // 生成token，保存到redis中
>         String uniqueToken = UUID.randomUUID().toString().trim();
>         redis.set(REDIS_ADMIN_TOKEN + ":" + admin.getId(), uniqueToken);
>         // token与用户信息写入到cookie
>         setCookie(request, response, "atoken", uniqueToken, COOKIE_MONTH);
>         setCookie(request, response, "aid", admin.getId(), COOKIE_MONTH);
>         setCookie(request, response, "aname", admin.getAdminName(), COOKIE_MONTH);
>     }
> 
> 
>   
> 
> 
> }
> ```

> 对应的service层
>
> ```java
> @Service
> public class AdminUserServiceImpl  extends BaseService implements AdminUserService {
> 
>     @Autowired
>     public AdminUserMapper adminUserMapper;
> 
> 
>     @Override
>     public AdminUser queryAdminByUsername(String username) {
>         Example adminUserExample = new Example(AdminUser.class);
>         Example.Criteria adminUserCriteria = adminUserExample.createCriteria();
>         adminUserCriteria.andEqualTo("username", username);
>         AdminUser result = adminUserMapper.selectOneByExample(adminUserExample);
>         return result;
>     }
> }
> ```

### 5.2 查询admin用户是否存在

> api
>
> > ```java
> > @PostMapping("/adminIsExist")
> > @ApiOperation(value = "查询管理人员是否存在", notes = "查询管理人员是否存在", httpMethod = "POST")
> > GraceIMOODJSONResult adminIsExist(@RequestParam String username);
> > ```
>
> controller
>
> > ```java
> > @Override
> > public GraceIMOODJSONResult adminIsExist(String username) {
> >     // 验证管理人用户名必须唯一
> >     checkAdminExist(username);
> >     return GraceIMOODJSONResult.ok();
> > }
> > ```
>
> service层不再去介绍

> 注意自定义拦截器
>
> ```java
> package com.dexlace.api.interceptor;
> 
> import org.springframework.web.servlet.HandlerInterceptor;
> import org.springframework.web.servlet.ModelAndView;
> 
> import javax.servlet.http.HttpServletRequest;
> import javax.servlet.http.HttpServletResponse;
> 
> /**
>  * @Author: xiaogongbing
>  * @Description:
>  * @Date: 2021/5/5
>  */
> public class AdminTokenInterceptor extends BaseInterceptor implements HandlerInterceptor {
> 
>     /**
>      * 拦截请求，在访问controller调用之前
>      * @param request
>      * @param response
>      * @param handler
>      * @return
>      * @throws Exception
>      */
>     @Override
>     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
> 
>         String adminUserId = request.getHeader("adminUserId");
>         String adminUserToken = request.getHeader("adminUserToken");
> 
>         System.out.println("=====================================================================");
>         System.out.println("AdminTokenInterceptor - adminUserId = " + adminUserId);
>         System.out.println("AdminTokenInterceptor - adminUserToken = " + adminUserToken);
>         System.out.println("=====================================================================");
> 
>         boolean run = verifyUserIdToken(adminUserId, adminUserToken, REDIS_ADMIN_TOKEN);
>         return run;
>     }
> 
>     /**
>      * 请求访问controller之后，渲染视图之前
>      * @param request
>      * @param response
>      * @param handler
>      * @param modelAndView
>      * @throws Exception
>      */
>     @Override
>     public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
> 
>     }
> 
>     /**
>      * 请求访问controller之后，渲染视图之后
>      * @param request
>      * @param response
>      * @param handler
>      * @param ex
>      * @throws Exception
>      */
>     @Override
>     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
> 
>     }
> }
> ```
>
> ```java
> registry.addInterceptor(adminTokenInterceptor())
>         .addPathPatterns("/adminMng/adminIsExist")
> ```

###  5.3 创建admin账号

>api
>
>```java
>@PostMapping("/addNewAdmin")
>@ApiOperation(value = "添加新的管理人员", notes = "添加新的管理人员", httpMethod = "POST")
>GraceIMOODJSONResult addNewAdmin(HttpServletRequest request,
>                                   HttpServletResponse response,
>                                   @RequestBody NewAdminBO newAdminBO);
>```
>
>controller
>
>```java
>@Override
>public GraceIMOODJSONResult addNewAdmin(HttpServletRequest request, HttpServletResponse response, NewAdminBO newAdminBO) {
>
>
>    // 0. TODO 验证BO中的用户名和密码不为空
>
>
>    // 1. base64不为空，代表人脸登录，否则密码不能为空
>    if (StringUtils.isBlank(newAdminBO.getImg64())) {
>        if (StringUtils.isBlank(newAdminBO.getPassword()) || StringUtils.isBlank(newAdminBO.getConfirmPassword())) {
>            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
>        }
>    }
>
>    // 2. 密码不为空，必须判断两次输入一致
>    if (!StringUtils.isBlank(newAdminBO.getPassword())) {
>        if (!newAdminBO.getPassword().equalsIgnoreCase(newAdminBO.getConfirmPassword())) {
>            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
>        }
>    }
>
>    // 3. 验证管理人用户名必须唯一
>    checkAdminExist(newAdminBO.getUsername());
>
>    // 4. 新增管理员
>    adminUserService.createAdminUser(newAdminBO);
>
>    return GraceIMOODJSONResult.ok();
>
>
>}
>```
>
>service层
>
>> ```java
>> @Transactional
>> @Override
>> public void createAdminUser(NewAdminBO newAdminBO) {
>>     String adminId = sid.nextShort();
>> 
>>     AdminUser adminUser = new AdminUser();
>>     adminUser.setId(adminId);
>>     adminUser.setUsername(newAdminBO.getUsername());
>>     adminUser.setAdminName(newAdminBO.getAdminName());
>> 
>>     // 如果密码不为空，则使用bcrypt加密，比md5安全需更好
>>     if (StringUtils.isNotBlank(newAdminBO.getPassword())) {
>>         String bPwd = BCrypt.hashpw(newAdminBO.getPassword(), BCrypt.gensalt());
>>         adminUser.setPassword(bPwd);
>>     }
>> 
>>     if (StringUtil.isNotEmpty(newAdminBO.getFaceId())) {
>>         adminUser.setFaceId(newAdminBO.getFaceId());
>>     }
>> 
>>     adminUser.setCreatedTime(new Date());
>>     adminUser.setUpdatedTime(new Date());
>> 
>>     int result = adminUserMapper.insert(adminUser);
>>     if (result != 1) {
>>         GraceException.display(ResponseStatusEnum.ADMIN_CREATE_ERROR);
>>     }
>> }
>> ```
>
>同样要注意拦截器，这里不多言

### 5.4 查看admin列表和分页

> api
>
> ```java
> @PostMapping("/getAdminList")
> @ApiOperation(value = "查询管理人员列表", notes = "查询管理人员列表", httpMethod = "POST")
> GraceIMOODJSONResult getAdminList(@ApiParam(name = "page", value = "查询下一页的第几页", required = false)
>                                     @RequestParam Integer page,
>                                     @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
>                                     @RequestParam Integer pageSize);
> ```
>
> controller
>
> ```java
>  @Override
>     public GraceIMOODJSONResult getAdminList(Integer page, Integer pageSize) {
> 
>         if (page == null) {
>             page = COMMON_START_PAGE;
>         }
> 
>         if (pageSize == null) {
>             pageSize = COMMON_PAGE_SIZE;
>         }
> 
>         PagedGridResult gridResult = adminUserService.queryAdminList(page, pageSize);
>         return GraceIMOODJSONResult.ok(gridResult);
>     }
> ```
>
> service
>
> ```java
>  @Override
>     public PagedGridResult queryAdminList(Integer page, Integer pageSize) {
>         Example adminExample = new Example(AdminUser.class);
>         adminExample.orderBy("createdTime").desc();
> 
>         PageHelper.startPage(page, pageSize);
>         List<AdminUser> adminUserList =
>                 adminUserMapper.selectByExample(adminExample);
> 
> //        System.out.println(adminUserList);
>         return setterPagedGrid(adminUserList,page);
>     }
> ```
>
> 分页插件的使用
>
> ```xml
>   <!--pagehelper -->
>         <dependency>
>             <groupId>com.github.pagehelper</groupId>
>             <artifactId>pagehelper-spring-boot-starter</artifactId>
>         </dependency>
> 
> ```
>
> ```java
> package com.dexlace.common.utils;
> 
> import java.util.List;
> 
> /**
>  * 
>  * @Title: PagedGridResult.java
>  * @Package com.imooc.utils
>  * @Description: 用来返回分页Grid的数据格式
>  * Copyright: Copyright (c) 2019
>  */
> public class PagedGridResult {
>    
>    private int page;        // 当前页数
>    private long total;          // 总页数
>    private long records;     // 总记录数
>    private List<?> rows;     // 每行显示的内容
> 
>  /****get set...*/
> }
> ```
>
> ```java
> public PagedGridResult setterPagedGrid(List<?> list,
>                                        Integer page) {
>     PageInfo<?> pageList = new PageInfo<>(list);
>     PagedGridResult gridResult = new PagedGridResult();
>     gridResult.setRows(list);
>     gridResult.setPage(page);
>     gridResult.setRecords(pageList.getTotal());
>     gridResult.setTotal(pageList.getPages());
>     return gridResult;
> }
> ```
>
> 别忘记拦截器的使用
>
> ```java
> registry.addInterceptor(adminTokenInterceptor())
>         .addPathPatterns("/adminMng/getAdminList");
> ```

### 5.5 admin账号注销登录

> ```java
> @PostMapping("/adminLogout")
> @ApiOperation(value = "管理员注销", notes = "管理员注销", httpMethod = "POST")
> GraceIMOODJSONResult adminLogout(@RequestParam String adminId,
>                                    HttpServletRequest request,
>                                    HttpServletResponse response);
> ```

> ```java
> @Override
> public GraceIMOODJSONResult adminLogout(String adminId, HttpServletRequest request, HttpServletResponse response) {
> 
>     redis.del(REDIS_ADMIN_TOKEN + ":" + adminId);
>     deleteCookie(request, response, "atoken");
>     deleteCookie(request, response, "aid");
>     deleteCookie(request, response, "aname");
> 
>     return GraceIMOODJSONResult.ok();
> }
> ```

> ```java
> public void deleteCookie(HttpServletRequest request,
>                          HttpServletResponse response,
>                          String cookieName) {
>     try {
>         String deleteValue = URLEncoder.encode("", "utf-8");
>         setCookieValue(request, response, cookieName, deleteValue, COOKIE_DELETE);
>     } catch (UnsupportedEncodingException e) {
>         e.printStackTrace();
>     }
> }
> ```

> ```java
> public void setCookieValue(HttpServletRequest request,
>                            HttpServletResponse response,
>                            String cookieName,
>                            String cookieValue,
>                            Integer MaxAge) {
>     Cookie cookie = new Cookie(cookieName, cookieValue);
>     cookie.setMaxAge(MaxAge);
>     cookie.setDomain(DOMAIN_NAME);
>     cookie.setPath("/");
>     response.addCookie(cookie);
> }
> ```

### 5.6 人脸业务流程图

人脸入库

<img src="Judy-news.assets/image-20210608141546817.png" alt="image-20210608141546817" style="zoom:50%;" />

人脸登录

<img src="Judy-news.assets/image-20210608141652987.png" alt="image-20210608141652987" style="zoom: 80%;" />

Chrome开启视频调试模式：

禁用浏览器默认安全模式
复制如下命令到浏览器的地址栏：
chrome://flags/#unsafely-treat-insecure-origin-as-secure
找到黄色部分，然后enabled开启，并且把你自己本地模拟的域名放进去，如此就增加了白名单。设置好需要重启浏览器。

### 5.7 MongoDB介绍与安装

MongoDB 是==非关系型数据库==，也就是nosql，存储json数据格式会非常灵活，要比数据库mysql/MariaDB更好，同时也能为mysql/MariaDB分摊一部分的流量压力。

对于经常读写的数据他会存入内存，如此一来对于热数据的并发性能是相当高的，从而提升整体的系统效率。

另外呢，对于非事务的数据完全可以保存到MongoDB中，这些数据往往也是==非核心数据==。

一般来说，我们可以把==一些非重要数据但是读写却很大的数据==存储在MongoDB，比如我们自己的物流运输的车辆==运行轨迹，GPS坐标，以及大气监测的一些动态指标等数据==。

比如本文中的==友情链接==

此外，==mongodb提供的gridfs提供小文件存储==，可以自己把控接口读取的权限，这一点也是有优势的，比如存储一些==身份证信息==，==人脸信息==都是可以的。

> 安装
>
> 1、解压，最好到/usr/local下，命名为mongodb
>
> 2、配置环境变量
>
> 3、mongodb下创建两个目录：data/db存储数据库文件数据；log目录用于存储日志，touch一个mongodb.log文件
>
> 4、mongodb下创建配置文件mongodb.conf
>
> ```yml
> systemLog:
> #MongoDB发送所有日志输出的目标指定为文件
>   destination: file
>   #mongod或mongos应向其发送所有诊断日志记录信息的日志文件的路径
>   path: "/usr/local/mongodb/log/mongod.log"
>   #当mongos或mongod实例重新启动时，mongos或mongod会将新条目附加到现有日志文件的末尾。
>   logAppend: true
> storage: #mongod实例存储其数据的目录。storage.dbPath设置仅适用于mongod。
>   dbPath: "/usr/local/mongodb/data/db"
>   journal:
>     enabled: true
> security:
>   authorization: enabled
> 
> processManagement:
> #启用在后台运行mongos或mongod进程的守护进程模式。 
>   fork: true
>   #指定用于保存mongos或mongod进程的进程ID的文件位置，其中mongos或mongod将写入其PID 
>   pidFilePath: "/usr/local/mongodb/log/mongod.pid"
> net:
> #服务实例绑定所有IP，有副作用，副本集初始化的时候，节点名字会自动设置为本地域名，而不是ip 
> #bindIpAll: true 
> #服务实例绑定的IP 
>   bindIp: 0.0.0.0  #提供外网访问，不对ip进行绑定
>   #绑定的端口 
>   port: 27117
> #replication: 
> #副本集的名称 
>  # replSetName: shards01
> #sharding:
> ## 分片角色
>  # clusterRole: shardsvr
> ~                              
> ```
>
> 启动：
>
> ```bash
> mongod -f mongodb.conf
> 
> ```
>
> 关闭：
>
> ```bash
> kill -2 进程号
> kill -9 进程号
> mongod --shutdown --dbpath db数据存储目录
> ```

具体使用不写教程，可以看我的learning notes

### 5.8 GridFS整合SpringBoot

```xml
       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>


        <!-- 引入 mongodb 依赖 -->
        <dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver</artifactId>
        </dependency>
```

```yml
spring:  
  data:
    mongodb:
      uri: mongodb://root:root@192.168.205.118:27117
      database: imood-news
```

```java
package com.dexlace.files.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/5
 */
@Configuration
public class GridFSWebConfig {

    @Value("${spring.data.mongodb.database}")
    private String mongodbDatabase;


    @Value("${spring.data.mongodb.uri}")
    private String connectString;




    @Bean
    public MongoClient getMongoClient(){

        return MongoClients.create(connectString);
    }


    @Bean
    public GridFSBucket getGridFSBucket(){
        MongoDatabase database = getMongoClient().getDatabase(mongodbDatabase);
        GridFSBucket bucket = GridFSBuckets.create(database);
        return bucket;
    }

}


```

### 5.9 实现人脸入库

```java
 /**
     * 文件上传到mongodb的gridfs中
     * @param newAdminBO
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadToGridFS")
    GraceIMOODJSONResult uploadToGirdFS(
            @RequestBody NewAdminBO newAdminBO) throws Exception;
```

```java
    @Autowired
    private GridFSBucket gridFSBucket; 

    @Override
    public GraceIMOODJSONResult uploadToGirdFS(NewAdminBO newAdminBO) throws Exception {
            // base64字符串
            String file64 = newAdminBO.getImg64();
            // 将字符串转换为byte数组
            byte[] bytes = new BASE64Decoder().decodeBuffer(file64.trim());
            // 转化为输入流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

            // 上传
            ObjectId fileId = gridFSBucket.uploadFromStream(newAdminBO.getUsername() + ".png", inputStream);

            logger.info("上传完成。 文件ID：{}",fileId);

            // 文件在mongodb中的id
            String fileIDStr = fileId.toString();

            logger.info("fileIDStr={}",fileIDStr);

            return GraceIMOODJSONResult.ok(fileIDStr);


    }

```

### 5.10 查看admin人脸信息

```java
 /**
     * 从gridFS中读取文件
     * @param faceId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("readInGridFS")
     void readInGridFS(
            String faceId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception;
```

```java
  @Override
    public void readInGridFS(String faceId,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        if (StringUtils.isBlank(faceId) || faceId.equalsIgnoreCase("null")) {
            GraceException.display(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
        }

        // 获取内容
        File file = readGridFSByFaceId(faceId);


        // 读取一个文件，并显示在浏览器
        FileUtils.downloadFileByStream(response, file);
    }


    /**
     * 从GridFS中读取文件内容
     * @param faceId
     * @return
     */
    private File readGridFSByFaceId(String faceId) throws Exception {
        // 获取内容
        GridFSFindIterable gridFSFindIterable = null;
        try {
            // 查找并获得gridFS的迭代器
            gridFSFindIterable = gridFSBucket.find(Filters.eq("_id", new ObjectId(faceId)));
        } catch (IllegalArgumentException e) {
            GraceException.display(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
        }
        // 获得第一个gridFSFile值
        GridFSFile gridFSFile = gridFSFindIterable.first();

        if (gridFSFile == null) {
            GraceException.display(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
        }

        System.out.println("filename: " + gridFSFile.getFilename());
        String fileName = gridFSFile.getFilename();

        // 获取文件流，定义存放位置和名称
//        String tempPath = "/workspaces/temp_face";
        File fileTemp = new File(TEMP_PATH);
        if (!fileTemp.exists()) {
            fileTemp.mkdirs();
        }

        File file = new File(TEMP_PATH + "\\" + fileName);

        // 创建输出流
        OutputStream os = new FileOutputStream(file);
        // 执行下载，下载到本地磁盘，管理员头像不多，下载到服务器不会占用大量硬盘空间
        gridFSBucket.downloadToStream(new ObjectId(faceId), os);

        return file;
    }
```

```java
/*************至于FileUtils的工具类见项目代码*****************/

public class FileUtils {

    /**
     * 文件流下载，在浏览器展示
     * @param response
     * @param file	文件从盘符开始的完整路径
     */
    public static void downloadFileByStream(HttpServletResponse response, File file) {
       
    }

    /**
     * 文件转换为base64
     * @param file
     * @return
     */
    public static String fileToBase64(File file) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     
    }

}

```

### 5.11 人脸登录-获取人脸faceId

和人脸入库后查看人脸信息类似

```java
@PostMapping("/adminFaceLogin")
    @ApiOperation(value = "管理员人脸登录", notes = "管理员人脸登录", httpMethod = "POST")
    GraceIMOODJSONResult adminFaceLogin(@RequestBody AdminLoginBO adminLoginBO,
                                          HttpServletRequest request,
                                          HttpServletResponse response);

```

```java
 
    @Override
    public GraceIMOODJSONResult adminFaceLogin(AdminLoginBO adminLoginBO, HttpServletRequest request, HttpServletResponse response) {


        // 0. 判断用户名和人脸信息不能为空
        // 这里的人脸登录不是用1：N的格式，而是1:1的对比模式，所以需要传用户名
        if (StringUtils.isBlank(adminLoginBO.getUsername())) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_USERNAME_NULL_ERROR);
        }
        // 前端会传来照片的base64消息
        String tempFace64 = adminLoginBO.getImg64();
        if (StringUtils.isBlank(tempFace64)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_FACE_NULL_ERROR);
        }

        // 1. 从数据库中查询admin信息，获得人脸faceId
        AdminUser admin = adminUserService.queryAdminByUsername(adminLoginBO.getUsername());
        String adminFaceId = admin.getFaceId();

        // 2. 请求文件服务，获取人脸的base64信息
        // 需要服务调用



        // 3. 调用阿里人脸识别只能AI接口，对比人脸实现登录
       

      // 4. 设置管理员信息到redis与cookie
        doLoginSettings(admin, request, response);


        return GraceIMOODJSONResult.ok();
    }

```

由于face信息存在了mongodb中的gridfs中，所以需要调用文件服务

首先在文件服务模块实现相应的功能，后续只需要admin模块进行restTemplate远程调用即可

```java
 /**
     * 根据faceId获得admin的base64头像信息
     * @param faceId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("readFace64InGridFS")
    GraceIMOODJSONResult readFace64InGridFS(
            @RequestParam String faceId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception;
```

```java
/**
 * 这是查看mongobd中的face
 * @param faceId
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
@Override
public GraceIMOODJSONResult readFace64InGridFS(String faceId,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
    // 1. 判断faceId不能为空
    if (StringUtils.isBlank(faceId)) {
        return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
    }

    // 2. 获得文件
    File file = readGridFSByFaceId(faceId);

    // 3. 把文件转换为base64并返回给调用方
    String base64 = FileUtils.fileToBase64(file);

    return GraceIMOODJSONResult.ok(base64);
}
```

### 5.12 人脸登录-整合Rest服务

api模块中配置

```java
@Configuration
public class CloudConfig {

    /**
     * 基于OkHttp3配置RestTemplate
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

}
```

注意pom.xml文件配置

```xml
 	  <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

```

AdMinMngController.adminFaceLogin的步骤二

```java
      @Autowired
    private RestTemplate restTemplate;

// 2. 请求文件服务，获取人脸的base64信息
        // 需要服务调用

        String fileServerUrlExecute = "http://files.imoocnews.com:8004/fs/readFace64InGridFS?faceId=" + adminFaceId;
        ResponseEntity<GraceIMOODJSONResult> resultEntity = restTemplate.getForEntity(fileServerUrlExecute, GraceIMOODJSONResult.class);
        GraceIMOODJSONResult graceJSONResult = resultEntity.getBody();
        String base64DB = (String) graceJSONResult.getData();
        logger.info("restTemplate远程调用获得的内容为：{}", base64DB);
```

### 5.13 人脸登录-实现人脸1:1对比登录

```java
  @Autowired
    private FaceVerifyUtils faceVerifyUtils; 

// 3. 调用阿里人脸识别只能AI接口，对比人脸实现登录
        boolean result = faceVerifyUtils.faceVerify(FaceVerifyType.BASE64.type,
                tempFace64,
                base64DB,
                60);

        if (!result) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_FACE_LOGIN_ERROR);
        }
```

怎样使用aliyun人脸识别，看代码

### 5.14 MongoDB-使用场景介绍

1. ==历史数据快照==：比如在电商系统里用户购买一个商品，都会涉及到订单，那么在用户下单购买的时候，该订单对应的商品数据就是此时此刻的数据，该商品数据不会随着商户的更改而更改，这就是快照数据。比如今天用户购买价格是100块钱，明天涨价10块钱，那么用户在查看订单的时候，价格就应该是下单的价格，这些都是快照数据。而快照数据对于每个用户来说有很多，所以往往把他们剥离出来放到MongoDB中去。
2. ==用户浏览记录==：用户在电商系统会浏览很多商品，那么如果存到数据库，那么该张表的数据就是指数级增长了，db压力相当大，所以可以剥离放入到mongodb中。
3. ==客服聊天记录==：虽然我们对外称聊天记录不存保存，但是我们还是会存储一下，而聊天记录都是非关键数据，哪怕没有也无所谓，所以完全可以放到mongodb中去。

- 能不能把这些数据都存redis中呢？
  ==不行，因为redis数据保存到内存，内存很贵的==，如果你们公司老板土豪，可以无限购买内存的话，无所谓。但是需要考虑内存成本的时候，这就需要使用mongodb了。
  所以说，Redis主要用来分摊读压力，提供缓存机制。而MongoDB为数据库分摊大数据量的存储压力，此外这些都是非核心业务数据，哪怕全部丢失了，也无所谓，不会造成整个系统崩溃。

### 5.15 友情链接保存和更新

友情链接的其他操作不再介绍

admin模块

```yml
spring:  
  data:
    mongodb:
      uri: mongodb://root:root@192.168.205.118:27117
      database: imood-news
```

```java
@PostMapping("saveOrUpdateFriendLink")
@ApiOperation(value = "新增或修改友情链接", notes = "新增或修改友情链接", httpMethod = "POST")
GraceIMOODJSONResult saveOrUpdateFriendLink(@RequestBody @Valid SaveFriendLinkBO saveFriendLinkBO,
                                                   BindingResult result);
```

```java
@Autowired
private FriendLinkService friendLinkService;


@Override
public GraceIMOODJSONResult saveOrUpdateFriendLink(SaveFriendLinkBO saveFriendLinkBO,
                                                   BindingResult result) {
    // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
    if (result.hasErrors()) {
        Map<String, String> errorMap = getErrors(result);
        return GraceIMOODJSONResult.errorMap(errorMap);
    }



    // 保存到MongoDB
    FriendLinkMO friendLinkMO = new FriendLinkMO();
    BeanUtils.copyProperties(saveFriendLinkBO, friendLinkMO);
    friendLinkMO.setCreateTime(new Date());
    friendLinkMO.setUpdateTime(new Date());

    friendLinkService.saveOrUpdateFriendLink(friendLinkMO);

    return GraceIMOODJSONResult.ok();
}
```

```java

@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    private FriendLinkRepository friendLinkRepository;

    @Override
    public void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO) {
        friendLinkRepository.save(friendLinkMO);
    }

    @Override
    public List<FriendLinkMO> queryFriendLinkList() {
        return friendLinkRepository.findAll();
    }


    @Override
    public void deleteFriendLink(String linkId) {
        friendLinkRepository.deleteById(linkId);
    }


    @Override
    public List<FriendLinkMO> queryPortalFriendLinkList() {
        return friendLinkRepository.getAllByIsDelete(YesOrNo.NO.type);
    }




}

```

```java

public interface FriendLinkRepository extends MongoRepository<FriendLinkMO,String> {
    /**
     * 自定义Dao方法
     * 同Spring Data JPA一样Spring Data mongodb也提供自定义方法的规则，
     * 如下： 按照ﬁndByXXX，ﬁndByXXXAndYYY、countByXXXAndYYY等规则定义方法，实现查询操作。
     */

    public List<FriendLinkMO> getAllByIsDelete(Integer isDelete);

}

```

### 5.16 文章分类管理

### 5.17 用户管理

## 六、文章内容管理

### 6.1 发头条

### 6.2 文章列表展示

### 6.3 文本检测

### 6.4 自动审核

### 6.5 人工审核

## 七、首页与作家页

### 7.1 文章列表

### 7.2 热闻

### 7.3 作者主页

### 7.4 粉丝关注

### 7.5 粉丝管理

### 7.6 粉丝画像

## 八、详情页与评论模块

### 8.1 文章详情页

### 8.2 阅读数redis累加

### 8.3 阅读数防刷

### 8.4 用户发表评论

### 8.5 文章评论关联查询

## 九、Freemarker文章详情页静态化、解耦及文章定时发布

与jsp类似，其入门就不再说明

### 9.1 生成静态html

pom.xml

顶级工程

```java
<properties>
	<springboot-freemarker.version>2.2.5.RELEASE</springboot-freemarker.version>
</properties>
    
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-freemarker</artifactId>
     <version>${springboot-freemarker.version}</version>
 </dependency>
    
```

article模块直接引用

```xml
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
 </dependency>

```

application.yml

```yml
spring:
  freemarker:
    charset: UTF-8
    content-type: text/html
    suffix: .ftl
    template-loader-path: classpath:/templates/


freemarker:  # 这个只是自定义的配置而已，在工程中被引用  这里的配置表示生成的静态页面路径
  html:
    target: E:\\imood_news_htmls
```

创建一个模板，/templates/stu.ftl

```html
<html>
<head>
    <title>Hello Freemarker</title>
</head>
<body>
<#--&lt;#&ndash;-->
<#--    Freemarker的构成语法：-->
<#--    1. 注释-->
<#--    2. 表达式-->
<#--    3. 指令-->
<#--    4. 普通文本-->
<#--&ndash;&gt;-->

<#--&lt;#&ndash; 注释 &ndash;&gt;-->
<#--&lt;#&ndash; ${} 为变量表达式，同jsp &ndash;&gt;-->

<#--&lt;#&ndash; 输出字符串 &ndash;&gt;-->
<#--<div>hello ${there}</div>-->
<#--&lt;#&ndash; 输出对象 &ndash;&gt;-->
<#--<div>-->
<#--    用户id：${stu.uid}<br/>-->
<#--    用户姓名：${stu.username}<br/>-->
<#--    年龄：${stu.age}<br/>-->
<#--    生日：${stu.birthday?string('yyyy-MM-dd HH:mm:ss')}<br/>-->
<#--    余额：${stu.amount}<br/>-->
<#--    已育：${stu.haveChild?string('yes', 'no')}<br/>-->
<#--    伴侣：${stu.spouse.username}, ${stu.spouse.age}岁-->
<#--</div>-->

<#--&lt;#&ndash; 输出list &ndash;&gt;-->
<#--&lt;#&ndash; stu.articleList &ndash;&gt;-->
<#--<div>-->
<#--    <#list stu.articleList as article>-->
<#--        <div>-->
<#--            <span>${article.id}</span>-->
<#--            <span>${article.title}</span>-->
<#--        </div>-->
<#--    </#list>-->
<#--</div>-->


<#--&lt;#&ndash; 输出map &ndash;&gt;-->
<#--&lt;#&ndash; stu.parents &ndash;&gt;-->
<#--<div>-->
<#--    <#list stu.parents?keys as key>-->
<#--        <div>${stu.parents[key]}</div>-->
<#--    </#list>-->
<#--</div>-->
<#--&lt;#&ndash; if 指令判断 &ndash;&gt;-->
<#--<div>-->
<#--    <#if stu.uid == '1001'>-->
<#--    用户id是1001-->
<#--    </#if><br/>-->
<#--    <#if stu.username != 'Jack'>-->
<#--    用户姓名不是Jack-->
<#--    </#if><br/>-->
<#--    <#if (stu.age >= 18)>-->
<#--    成年人-->
<#--    </#if><br/>-->
<#--    <#if (stu.age < 18)>-->
<#--    未成年人-->
<#--    </#if><br/>-->
<#--    <#if stu.haveChild>-->
<#--    已育-->
<#--    </#if><br/>-->
<#--    <#if !stu.haveChild>-->
<#--    未育-->
<#--    </#if><br/>-->


<#--    <#if stu.spouse??>-->
<#--        伴侣：${stu.spouse.username}, ${stu.spouse.age}岁-->
<#--    </#if>-->
<#--    <#if !stu.spouse??>-->
<#--        单身狗-->
<#--    </#if>-->
<#--</div>-->

<div>
    hello ${stranger}
</div>

</body>
</html>

```

写一个controller

```java
package com.dexlace.article.controller;

import com.dexlace.model.entity.Article;
import com.dexlace.model.entity.Spouse;
import com.dexlace.model.entity.Stu;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/8
 */
@Controller
@RequestMapping("free")
public class FreemarkerController {

    @Value("${freemarker.html.target}")
    private String htmlTarget;

    @GetMapping("/createHTML")
    @ResponseBody   // 注意restController和Controller加ResponseBody的返回结果不是模板页的路径
    // 而只是要显示的内容而已，没有跳转
    public String createHTML(Model model) throws Exception {

        // 0. 配置freemarker基本环境
        Configuration cfg = new Configuration(Configuration.getVersion());
        // 声明freemarker模板所需要加载的目录的位置
        String classpath = this.getClass().getResource("/").getPath();
        cfg.setDirectoryForTemplateLoading(new File(classpath + "templates"));

//        System.out.println(htmlTarget);
//        System.out.println(classpath + "templates");

        // 1. 获得现有的模板ftl文件
        Template template = cfg.getTemplate("stu.ftl", "utf-8");

        // 2. 获得动态数据
        String stranger = "judy";
        model.addAttribute("stranger", stranger);


        // 3. 融合动态数据和ftl，生成html
        File tempDic = new File(htmlTarget);
        if (!tempDic.exists()) {
            tempDic.mkdirs();
        }

        Writer out = new FileWriter(htmlTarget + File.separator + "10010" + ".html");
        template.process(model, out);
        out.close();

        return "ok";
    }

/************以上代码将会生成在上述指定路径下生成10010.html*****************/

    
    
    

//    @GetMapping("/hello")
//    public String hello(Model model) {
//        // 输出字符串
//        String stranger = "你好";
//        model.addAttribute("there", stranger);
//
//        // 返回的是freemarker模板所在的目录位置：/templates/；可以再yml中进行配置
//        // 匹配 *.ftl；ftl为freemarker的文件后缀名
//
//
//        makeModel(model);
//        return "stu";
//    }
//
//
//    private Model makeModel(Model model) {
//
//        Stu stu = new Stu();
//        stu.setUid("10010");
//        stu.setUsername("imooc");
//        stu.setAmount(88.86f);
//        stu.setAge(18);
//        stu.setHaveChild(false);
//        stu.setBirthday(new Date());
//
//        Spouse spouse = new Spouse();
//        spouse.setUsername("Lucy");
//        spouse.setAge(25);
//
//        stu.setSpouse(spouse);
//
//        stu.setArticleList(getArticles());
//        stu.setParents(getParents());
//
//        model.addAttribute("stu", stu);
//
//        return model;
//    }
//
//
//    private List<Article> getArticles() {
//        Article article = new Article();
//        article.setId("10001");
//        article.setTitle("今天天气不错噢~");
//
//        Article article2 = new Article();
//        article2.setId("30002");
//        article2.setTitle("今天打雷下雨~");
//
//        Article article3 = new Article();
//        article3.setId("30003");
//        article3.setTitle("今天去野炊~");
//
//        List<Article> list = new ArrayList<>();
//        list.add(article);
//        list.add(article2);
//        list.add(article3);
//
//        return list;
//    }
//
//    private Map<String, String> getParents() {
//        Map<String, String> map = new HashMap<>();
//        map.put("father", "LiLei");
//        map.put("mather", "Hanmeimei");
//        return map;
//    }

}

```

### 9.2 文章详情页静态化

与上一节所示的例子类似，以上的依赖都一样，这里在application.yml中再定义一个路径变量

```yml
freemarker:  # 生成目录
  html:
    article: D:\\softwareOfDevelopment\\apache-tomcat-9.0.37\\webapps\imooc-news\\portal\\static
```

反观articleController中的doReview，我们需要在对文章审核之后对文章页面进行静态化

```java
 @Override
    public GraceIMOODJSONResult doReview(String articleId, Integer passOrNot) {
       
        /*****************
        /*****审核过程
        /*****************************
        /**
         * 审核完毕就应该文章详情页面静态化
         */
        if (pendingStatus == ArticleReviewStatus.SUCCESS.type) {
            // 审核成功，生成html
            try {
                createArticleHTML(articleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return GraceIMOODJSONResult.ok();
    }

```

文章详情静态页面生成api,和上一节类似

```java
 // 文章详情页生成静态html，也就是入FreemarkerController中的示例所示
    public void createArticleHTML(String articleId) throws Exception {

        Configuration cfg = new Configuration(Configuration.getVersion());
        String classpath = this.getClass().getResource("/").getPath();
        cfg.setDirectoryForTemplateLoading(new File(classpath + "templates"));

        // 文章详情页模板/templates/detail.ftl
        Template template = cfg.getTemplate("detail.ftl", "utf-8");

        // 获得文章的详情数据
        // 这里需要远程调用
        ArticleDetailVO detailVO = getArticleDetail(articleId);
        Map<String, Object> map = new HashMap<>();
        map.put("articleDetail", detailVO);

        File tempDic = new File(articlePath);
        if (!tempDic.exists()) {
            tempDic.mkdirs();
        }

        String path = articlePath + File.separator + detailVO.getId() + ".html";

        Writer out = new FileWriter(path);
        template.process(map, out);
        out.close();
    }
```

如是上闻，我们的模板需要articleDetail对象，我们的VO对象ArticleDetailVO正好符合，为此，我们需要远程调用，得到一个ArticleDetailVO对象（虽然两个controller在一个模块，但是分别是不同的controller，必须远程调用，不然只能是无语吧）

```java
    // 发起远程调用rest，获得文章详情数据
    // 一个controller调用另外一个controller，虽然在同一个controller，是一个重定向可以
    // 此外只能用restTemplate进行远程调用
    public ArticleDetailVO getArticleDetail(String articleId) {
        String url
                = "http://www.imoocnews.com:8001/portal/article/detail?articleId=" + articleId;
        ResponseEntity<GraceIMOODJSONResult> responseEntity
                = restTemplate.getForEntity(url, GraceIMOODJSONResult.class);
        GraceIMOODJSONResult bodyResult = responseEntity.getBody();
        ArticleDetailVO detailVO = null;
        if (bodyResult.getStatus() == 200) {
            String detailJson = JsonUtils.objectToJson(bodyResult.getData());
            detailVO = JsonUtils.jsonToPojo(detailJson, ArticleDetailVO.class);
        }
        return detailVO;
    }
```

此外，记得去改并且会改写一个模板，比如上述的detail.ftl

```html
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">


    <!-- <link rel="stylesheet" type="text/css" href="css/app.css" /> -->
    <!-- <link rel="stylesheet" type="text/css" href="css/index/topHeader.css" /> -->
    <!-- <link rel="stylesheet" type="text/css" href="css/detail/detail.css" />
    <link rel="stylesheet" type="text/css" href="css/detail/comments.css" />
    <link rel="stylesheet" type="text/css" href="css/detail/middleBar.css" /> -->
    <!-- <link rel="stylesheet" type="text/css" href="./libs/JqueryShare/share.min.css" /> -->

    <link rel="stylesheet" type="text/css" href="../css/app.css" />
    <link rel="stylesheet" type="text/css" href="../css/index/topHeader.css" />
    <link rel="stylesheet" type="text/css" href="../css/detail/detail.css" />
    <link rel="stylesheet" type="text/css" href="../css/detail/comments.css" />
    <link rel="stylesheet" type="text/css" href="../css/detail/middleBar.css" />
    <link rel="stylesheet" type="text/css" href="../libs/JqueryShare/share.min.css" />

    <!-- 分页css -->
    <!-- <link href="libs/vuePaging/VuePaging/zpageNav.css" rel="stylesheet" /> -->
    <link href="../libs/vuePaging/VuePaging//zpageNav.css" rel="stylesheet" />

    <!-- <link rel="shortcut icon" href="img/mu-toutiao.ico" /> -->
    <link rel="shortcut icon" href="../img/mu-toutiao.ico" />

    <style type="text/css">
        .social-share .social-share-icon {
            width: 32px;
            height: 32px;
            line-height: 32px;
        }
    </style>

    <title id="pageTitle">${articleDetail.title}</title>
</head>

<body>
<div id="detailContainer">
    <!-- 顶部导航头 header -->
    <div class="white-header">
        <div class="white-bar">
            <div class="left-part">
                <div class="logo-wrapper" @click="goIndex()">
                    <!-- <img src="./img/imooc-news-logo.png" class="big-logo"/> -->
                    <img src="../img/imood-news-logo.png" class="big-logo"/>
                </div>

                <div class="menus">
                    <!-- single-selected -->
                    <div class="single-default" v-bind:class="{'single-selected': (cat.id==selectedCatId)}" v-for="(cat, index) in catList" v-key="index">
                        <!-- menu-selected -->
                        <a href="javascript:void(0);" class="white-header-left" style="text-decoration:none;" v-bind:class="{'menu-selected': (cat.id==selectedCatId)}" @click="queryByCategory(cat.id)">{{cat.name}}</a>
                    </div>
                    <div class="single-default">
                        <a href="https://class.imooc.com/sale/javaarchitect" target="_blank" class="white-header-left">架构师</a>
                    </div>
                </div>
            </div>

            <div class="right-part">
                <!-- 如果登录则不显示 -->
                <block v-if="userInfo == null">
                    <a href="javascript:(0);" @click="doLogin()" target="_blank" class="white-header-right-start">登录 | 注册</a>
                </block>
                <a href="javascript:(0);" @click="goWriterCenter()" target="_blank" class="white-header-right">作家中心</a>
                <a href="javascript:(0);" @click="goAdminCenter()" target="_blank" class="white-header-right" style="margin-right: 20px;">管理平台</a>
            </div>
        </div>
    </div>

    <!-- 中间主体部分 -->
    <div class="container">

        <div class="big-title">
            ${articleDetail.title}
        </div>
        <div class="read-counts">
            阅读量：{{readCounts}}
        </div>

        <div class="detail-really">

            <!-- 左侧分享 -->
            <div class="left-social">

                <div class="date-title">
                    <span class="year">${articleDetail.publishTime?string('yyyy')}</span>
                </div>
                <div class="back-year-line"></div>

                <div class="date-md">${articleDetail.publishTime?string('MM/dd')}</div>

                <div class="date-times">${articleDetail.publishTime?string('HH:mm:ss')}</div>

                <div class="writer-name" @click="showWriter('${articleDetail.publishUserId}')">
                    ${articleDetail.publishUserName}
                </div>

                <div class="imooc-words">
                    <span class="imooc-num">牧德媒体号</span>
                </div>

                <div class="share-title">
                    <span class="share-words">分享</span>
                </div>
                <div class="back-line"></div>

                <div class="social-line" @click="shareWeiBo">
                    <img src="../img/social/weibo.png" class="social-pic"/>
                    <span class="social-words">微博</span>
                </div>
                <div class="social-line" @click="shareDouBan">
                    <img src="../img/social/douban.png" class="social-pic"/>
                    <span class="social-words" >豆瓣</span>
                </div>
                <div class="social-line" @click="shareQQ">
                    <img src="../img/social/qq.png" class="social-pic"/>
                    <span class="social-words">QQ</span>
                </div>
                <div class="social-line" @click="shareQZone">
                    <img src="../img/social/qzone.png" class="social-pic"/>
                    <span class="social-words">QQ空间</span>
                </div>
                <div class="social-line" @click="shareWeChat">
                    <div class="social-share" data-wechat-qrcode-title="请打开微信扫一扫"
                         data-disabled="google,twitter,facebook,douban,qzone,qq,weibo,tencent,linkedin,diandian" >
                        <!-- <img src="./img/social/wechat.png" class="social-pic"/> -->
                        <!-- <span class="social-words">微信</span> -->
                    </div>
                    <span class="social-words">微信</span>
                </div>
            </div>

            <!-- 中间文章主体 -->
            <div class="container-middle">
                <div class="article-wrapper">

                    <div class="content">
                        ${articleDetail.content}
                    </div>

                    <div class="declare">
                        免责声明：本平台所有内容仅供测试，且文章来自互联网，不代表牧德网的观点和立场，如有不妥，请联系后删除。
                    </div>
                </div>

                <div class="comments-wrapper">

                    <div class="comments-words">
                        <div class="comments-words-left">
                            <div class="comments-big">留言评论</div>
                            <div class="comments-warn">文明上网理性发言</div>
                        </div>

                        <!-- <div class="comments-counts">{{articleDetail.commentCounts}}条评论</div> -->
                        <div class="comments-counts">{{commentCounts}}条评论</div>

                    </div>

                    <div id="summernote" class="summernote"></div>
                    <div class="say-sth-wrapper">
                        <button class="say-sth" @click="doComment(0)">发表评论</button>
                    </div>

                    <div class="all-comments-wrapper">
                        <div class="all-comments">所有评论</div>
                    </div>

                    <div class="all-comments-list-wrapper">

                        <div class="all-comments-list" v-for="(comment,index) in commentList" :key="index">
                            <div class="single-comment-wrapper">
                                <!-- <img src="./img/face1.png" class="user-face"/> -->
                                <img :src="comment.commentUserFace" class="user-face"/>
                            </div>

                            <div class="comment-info-wrapper">
                                <div class="publish-info">
                                    <div>{{comment.commentUserNickname}}</div>
                                    <div class="publish-date">{{formatData(comment.createTime)}}</div>
                                </div>

                                <!-- 回复的内容，如果判断是回复别人的留言，则再此引用一下 -->
                                <div class="quote-wrapper" v-show="comment.quoteUserNickname != null">
                                        <span class="quote-content">
                                            <!-- <span>回复“</span> -->
                                            <span><strong>{{comment.quoteUserNickname}}</strong>：</span><span v-html="comment.quoteContent"></span>
                                            <!-- <span>”</span> -->
                                        </span>
                                </div>

                                <div class="blank-wrapper" v-show="comment.quoteUserNickname == null">
                                </div>

                                <div class="publish-content" v-html="comment.content">
                                </div>

                                <div class="reply-wrapper" @click="doReply(comment.commentId)">
                                    <img class="icon-reply"/>
                                    <!-- src="./img/icon-reply.png"  -->
                                    <div class="reply-words">回复</div>
                                </div>

                                <!-- nowReplyingFatherCommentId: 根据当前用户正在回复的父commentId进行页面的留言看展示或隐藏 -->
                                <div class="reply-content-wrapper" v-show="nowReplyingFatherCommentId==comment.commentId">
                                    <textarea :id="'reply-to-' + comment.commentId" class="reply-content"></textarea>
                                    <button class="reply-btn" @click="replyToComment(comment.commentId)">回复</button>
                                </div>
                            </div>
                        </div>

                        <!-- 分页 start-->
                        <div class="wrap" id="wrap">
                            <zpagenav v-bind:page="page" v-bind:page-size="pageSize" v-bind:total="total"
                                      v-bind:max-page="maxPage"  v-on:pagehandler="doPaging">
                            </zpagenav>
                        </div>
                        <!-- 分页 end-->

                    </div>
                </div>
            </div>

            <div class="blank-right"></div>
        </div>
    </div>
</div>

<!-- <script src="libs/vue.min.js"></script>
<script src="libs/axios.min.js"></script> -->
<script src="../libs/vue.min.js"></script>
<script src="../libs/axios.min.js"></script>

<link href="../libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="../libs/jquery-3.4.1.min.js"></script>
<script src="../libs/bootstrap/js/bootstrap.min.js"></script>
<link href="../libs/summernote/dist/summernote.css" rel="stylesheet">
<script src="../libs/summernote/dist/summernote.js"></script>

<!-- <script src="libs/summernote/lang/summernote-zh-CN.js"></script>
<script src="libs/vuePaging/VuePaging/zpageNav.js"></script>
<script src="js/app.js"></script>
<script src="./libs/JqueryShare/jquery.share.min.js"></script>
<script src="libs/moment-with-locales.min.js"></script> -->

<script src="../libs/summernote/lang/summernote-zh-CN.js"></script>
<script src="../libs/vuePaging/VuePaging/zpageNav.js"></script>
<script src="../js/app.js"></script>
<script src="../libs/JqueryShare/jquery.share.min.js"></script>
<script src="../libs/moment-with-locales.min.js"></script>

<script type="text/javascript">

    var articleList = new Vue({
        el: "#detailContainer",
        data: {
            nowReplyingFatherCommentId: 0,  // 根据当前用户正在回复的父commentId进行页面的留言看展示或隐藏
            userInfo: null,

            articleId: "",
            articleDetail: {},
            readCounts: 0,

            // 文章分类列表
            catList: [],
            selectedCatId: "",		// 被选中的当前的分类id
            // isSelected = (cat.id==selectedCatId)

            commentList: [],    // 评论列表
            commentCounts: 0,

            page: 1, // 分页页数
            pageSize: 10, // 分页每页显示数量
            maxPage: 1, // 总页数
            total: 1, // 总记录数
        },
        created() {
            var me = this;
            // 通过cookie判断用户是否登录
            app.judgeUserLoginStatus(me);

            // 查询文章详情
            // var articleId = app.getUrlParam("articleId");
            // console.log(articleId);
            // this.articleId = articleId;
            // this.getArticleDetail(articleId);

            // 根据当前页面的名称，定义为文章的articleId，作为我们的静态化页面名称
            // 比如 1001.html, 200221.html
            var thisPage = app.getPageName();
            console.log(thisPage);
            var articleId = thisPage;
            this.articleId = articleId;

            // 获得文章阅读数
            this.getArticleReadCounts(articleId);

            // 获得分类
            this.getAllCategory();
            // 文章阅读数累加
            this.readArticle(articleId);

            // 获得文章的评论列表
            this.getAllComments(1, 10);
            // 获得文章评论数
            this.getCommentCounts();
        },
        mounted() {
            // 初始化编辑器
            $('#summernote').summernote({
                placeholder: '随便说几句吧...',
                lang: 'zh-CN',
                height: 200,
                width: 700,
                border: 0,
                // disableDragAndDrop: true, // 禁止文件拖放
                toolbar: [
                ],
                // 回调函数
                callbacks: {
                    // summernote 初始化
                    onInit: function() {
                        $(".panel").css({"margin-bottom": "10px"});
                    }
                }
            });
        },
        methods: {
            // 文章阅读数累加
            readArticle(articleId) {
                var me = this;

                var articleServerUrl = app.articleServerUrl;
                axios.defaults.withCredentials = true;
                axios.post(articleServerUrl + "/portal/article/readArticle?articleId=" + articleId)
                    .then(res => {
                        // console.log(JSON.stringify(res.data));

                        if (res.data.status == 200) {

                        } else {
                            console.log(res.data.msg);
                        }
                    });
            },

            // 获得文章阅读数
            getArticleReadCounts(articleId) {
                var me = this;

                var articleServerUrl = app.articleServerUrl;
                axios.defaults.withCredentials = true;
                axios.get(articleServerUrl + "/portal/article/readCounts?articleId=" + articleId)
                    .then(res => {
                        // console.log(JSON.stringify(res.data));
                        this.readCounts = res.data;
                    });
            },


            // 跳转作家页面
            showWriter(writerId) {
                window.open("../writer.html?writerId=" + writerId);
            },
            // 查询文章详情
            getArticleDetail(articleId) {
                var me = this;

                var articleServerUrl = app.articleServerUrl;
                axios.defaults.withCredentials = true;
                axios.get(articleServerUrl + "/portal/article/detail?articleId=" + articleId)
                    .then(res => {
                        // console.log(JSON.stringify(res.data));

                        if (res.data.status == 200) {
                            var result = res.data.data;
                            me.articleDetail = result;
                            console.log(JSON.stringify(me.articleDetail));

                            $("#pageTitle").html(result.title);
                        } else {
                            alert(res.data.msg);
                        }
                    });
            },

            // 获得文章评论数
            getCommentCounts() {
                var me = this;
                var articleServerUrl = app.articleServerUrl;
                axios.defaults.withCredentials = true;
                axios.get(articleServerUrl + "/comment/counts?articleId=" + me.articleId)
                    .then(res => {
                        console.log(JSON.stringify(res.data));

                        if (res.data.status == 200) {
                            var commentCounts = res.data.data;
                            me.commentCounts = commentCounts;
                        } else {
                            console.log(res.data.msg);
                        }
                    });
            },

            // 获得文章的评论列表
            getAllComments(page, pageSize) {
                var me = this;
                var articleServerUrl = app.articleServerUrl;
                axios.defaults.withCredentials = true;
                axios.get(articleServerUrl + "/comment/list?articleId=" + me.articleId +
                    "&page=" + page +
                    "&pageSize=" + pageSize)
                    .then(res => {
                        console.log(JSON.stringify(res.data));

                        if (res.data.status == 200) {
                            var grid = res.data.data;
                            var commentList = grid.rows;
                            me.commentList = commentList;

                            me.page = grid.page;  // 当前页数累加1，用于后续页面滚动分页
                            var maxPage = grid.total; // 获得总页数
                            var total = grid.records; // 获得总记录数

                            this.maxPage = maxPage;
                            this.total = total;

                        } else {
                            console.log(res.data.msg);
                        }
                    });
            },

            // 点击回复出现回复框
            doReply(fatherCommentId) {
                // 如果用户点击的当前的评论回复id和nowReplyingFatherCommentId一致，则隐藏，如果不一致，则显示
                if (fatherCommentId == this.nowReplyingFatherCommentId) {
                    this.nowReplyingFatherCommentId = 0;
                } else {
                    this.nowReplyingFatherCommentId = fatherCommentId;
                }
            },

            // 用户留言或回复
            doComment(fatherCommentId) {
                console.log(fatherCommentId);
                var articleContent = $('#summernote').summernote('code');
                this.commentDisplay(fatherCommentId, articleContent);
            },
            // 调用后端，留言保存
            commentDisplay(fatherCommentId, articleContent) {

                var me = this;
                var utoken = app.getCookie("utoken");
                var uid = app.getCookie("uid");

                var commentReplyBO = {
                    articleId: me.articleId,
                    fatherId: fatherCommentId,
                    commentUserId: uid,
                    content: articleContent
                };

                var articleServerUrl = app.articleServerUrl;
                axios.defaults.withCredentials = true;
                axios.post(articleServerUrl + '/comment/createComment',
                    commentReplyBO,
                    {
                        headers: {
                            'headerUserId': uid,
                            'headerUserToken': utoken
                        }
                    }
                )
                    .then(res => {
                        console.log(JSON.stringify(res.data));

                        if (res.data.status == 200) {
                            // 清空评论框中内容
                            $('#summernote').summernote('reset');
                            $("#reply-to-" + fatherCommentId).val("");

                            // 重新查询评论与评论数
                            me.getAllComments(1, 10);
                            me.getCommentCounts();
                        } else {
                            // alert(res.data.msg);
                            alert(JSON.stringify(res.data.data));
                        }
                    });
            },
            // 用户回复其他用户的评论，点击后保存到后端
            replyToComment(fatherCommentId) {
                // TODO: 通过循环的评论id，根据id获得后再拿到回复的内容
                // console.log(fatherCommentId);

                var content = $("#reply-to-" + fatherCommentId).val();

                // console.log(content);
                this.commentDisplay(fatherCommentId, content);
            },

            // 获得所有文章领域
            getAllCategory() {
                var me = this;
                var adminServerUrl = app.adminServerUrl;
                axios.defaults.withCredentials = true;
                axios.get(adminServerUrl + "/categoryMng/getCats")
                    .then(res => {
                        console.log(JSON.stringify(res.data));

                        if (res.data.status == 200) {
                            var catList = res.data.data;
                            me.catList = catList;
                            // console.log(catList);
                        } else {
                            alert(res.data.msg);
                        }
                    });
            },
            // 根据分类Id获得tag颜色
            getCatTagColor(catId) {
                var catList = this.catList;
                for (var i = 0 ; i < catList.length ; i ++) {
                    if (catId == catList[i].id) {
                        return catList[i].tagColor;
                        break;
                    }
                }
            },
            // 根据分类Id获得名称
            getCatName(catId) {
                var catList = this.catList;
                for (var i = 0 ; i < catList.length ; i ++) {
                    if (catId == catList[i].id) {
                        return catList[i].name;
                        break;
                    }
                }
            },
            // 格式化日期
            formatData(times) {
                var date = moment(times).format('YYYY-MM-DD hh:mm:ss');
                return date;
            },
            formatDataYear(times) {
                var date = moment(times).format('YYYY');
                return date;
            },
            formatDataMonthDay(times) {
                var date = moment(times).format('MM/DD');
                return date;
            },
            formatDataTime(times) {
                var date = moment(times).format('hh:mm:ss');
                return date;
            },

            // 分页实现方法 跳转到page页
            doPaging: function (page) {
                this.page = page;
                this.getAllComments(page, this.pageSize);
            },
            // 跳转登录
            doLogin() {
                window.location = app.writerLoginUrl;
            },
            goWriterCenter() {
                window.open(app.writerIndexUrl);
            },
            goAdminCenter() {
                window.open(app.adminCenterUrl);
            },
            // 以下都是社交分享
            shareWeiBo() {
                var weiBoShareUrl = 'http://v.t.sina.com.cn/share/share.php';
                weiBoShareUrl += '?url='+ encodeURIComponent(document.location);    //参数url设置分享的内容链接|默认当前页location，可选参数
                weiBoShareUrl += '&title=' + encodeURIComponent(document.title);    //参数title设置分享的标题|默认当前页标题，可选参数
                weiBoShareUrl += '&source=' + encodeURIComponent('');
                weiBoShareUrl += '&sourceUrl=' + encodeURIComponent('');
                weiBoShareUrl += '&content=' + 'utf-8';                             //参数content设置页面编码gb2312|utf-8，可选参数
                weiBoShareUrl += '&pic=' + encodeURIComponent('');                  //参数pic设置图片链接|默认为空，可选参数
                window.open(weiBoShareUrl, '_blank');
            },
            shareDouBan() {
                var douBanShareUrl = 'http://shuo.douban.com/!service/share?';
                douBanShareUrl += 'href=' + encodeURIComponent(location.href);      //分享的链接
                douBanShareUrl += '&name=' + encodeURIComponent(document.title);    //分享的标题
                window.open(douBanShareUrl, '_blank');
            },
            shareQQ() {
                var qqShareUrl = 'https://connect.qq.com/widget/shareqq/iframe_index.html?';
                qqShareUrl += 'url=' + encodeURIComponent(location.href);           //分享的链接
                qqShareUrl += '&title=' + encodeURIComponent(document.title);       //分享的标题
                window.open(qqShareUrl, '_blank');
            },
            shareQZone() {
                var qzoneShareUrl = 'https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?';
                qzoneShareUrl += 'url=' + encodeURIComponent(document.location);    // 参数url设置分享的内容链接|默认当前页location
                qzoneShareUrl += '&title=' + encodeURIComponent(document.title);    // 参数title设置分享标题，可选参数
                window.open(qzoneShareUrl, '_blank');
            },
            shareWeChat2() {
                var target_url = 'http://zixuephp.net/inc/qrcode_img.php?url=' + document.location.href;
                target_url = 'http://zixuephp.net/inc/qrcode_img.php?url=http://www.itzixi.com';
                window.open(target_url, 'weixin', 'height=320, width=320');
            },
            shareWeChat() {
            },
            // 前往首页
            goIndex() {
                window.location = app.portalIndexUrl;
            }
        },

    });
</script>
</body>

</html>
```

注意，在序列化的过程中在序列化的过程中，遇到一个字符串和date类型转换，这里需要修改`ArticleDetailVO`中代码增加如下的格式化：

```java
@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
private Date publishTime;

```

### 9.3 文章阅读量异步获取

ArticlePortalControllerApi

```java
   @GetMapping("readCounts")
    @ApiOperation(value = "获得文章阅读量", notes = "获得文章阅读量", httpMethod = "GET")
    Integer readCounts(@RequestParam String articleId);
```

ArticlePortalController

```java
 @Override
    public Integer readCounts(String articleId) {
        return getCountsFromRedis(REDIS_ARTICLE_READ_COUNTS + ":" + articleId);
    }
```

在ftl中加入异步请求的js代码

```javascript
   // 获得文章阅读数
            getArticleReadCounts(articleId) {
                var me = this;

                var articleServerUrl = app.articleServerUrl;
                axios.defaults.withCredentials = true;
                axios.get(articleServerUrl + "/portal/article/readCounts?articleId=" + articleId)
                    .then(res => {
                        // console.log(JSON.stringify(res.data));
                        this.readCounts = res.data;
                    });
            },
```

声明周期函数中加入：

```javascript
created() {
    // 获得文章评论数
    this.getReadCounts(articleId);
}

```

修改表达式：

```javascript
<div class="read-counts">
    阅读量：{{readCounts}}
</div>

```

data中新增属性

```javascript
data: {
    readCounts: 0,
}

```

### 9.4  静态化解耦-介绍

==现在的静态化过程其实都是在同一台电脑上执行的==，这样做的话前端后端必须同时部署在同一个服务器，耦合度很大，而生成部署，前后端是分开的，那么这是怎么办？

<img src="Judy-news.assets/image-20210610135154541.png" alt="image-20210610135154541" style="zoom:50%;" />

我们可以接触gridfs作为中间存储介质，把生成的html上传，然后通知前端，让前端去gridfs下载，前后端的程序就能在不同的服务器进行部署了。大大降低耦合度。

<img src="Judy-news.assets/image-20210610135247628.png" alt="image-20210610135247628" style="zoom:50%;" />

解耦后的静态化步骤

1. 生成html，并且上传到gridfs中
2. 获得mongoFileId，关联保存到文章表中
3. 把mongoFileId发送至消费端，让其下载gridfs中的html到节点服务器进行发布

### 9.5 静态化解耦-生产者

主要是生成HTML并且上传到GridFS

在==article 模块中的doReview中==，审核通过后==需要将页面静态化，将会生成静态页面==

类似于creatArticleHTML，写一个createArticleHTMLToGridFS，==只不过不是将生成的html存到本地，而是到gridfs==

==createArticleHTMLToGridFS==:注意mongodb的配置和GridFSBucket的配置和注入

```java
    @Autowired
    private GridFSBucket gridFSBucket;  

/**
    // 文章详情页生成静态html，也就是入FreemarkerController中的示例所示
    // 为了解耦，这里上传到mongobd的gridfs
     **/
    public String createArticleHTMLToGridFs(String articleId) throws Exception {

        Configuration cfg = new Configuration(Configuration.getVersion());
        String classpath = this.getClass().getResource("/").getPath();
        cfg.setDirectoryForTemplateLoading(new File(classpath + "templates"));

        Template template = cfg.getTemplate("detail.ftl", "utf-8");

        // 获得文章的详情数据
        // 这里需要远程调用
        ArticleDetailVO detailVO = getArticleDetail(articleId);
        Map<String, Object> map = new HashMap<>();
        map.put("articleDetail", detailVO);


        /**
         * 将一个html模板转换成String类型
         * 传入一个freemarker的模板对象
         * 和要传入模板对象的动态数据
         */
        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        /**
         *
         * 将一个string内容转换成输入流
         */
        InputStream inputStream = IOUtils.toInputStream(htmlContent);


        /**
         * * @param filename the filename for the stream
         *      * @param source the Stream providing the file data
         *      * @return the ObjectId of the uploaded file.
         */
        ObjectId fileId = gridFSBucket.uploadFromStream(detailVO.getId() + ".html", inputStream);


        return fileId.toString();

    }
```

doReview的改造

```java


 @Override
    public GraceIMOODJSONResult doReview(String articleId, Integer passOrNot) {
        
        //...
      /**
         * 审核完毕就应该文章详情页面静态化
         */
        if (pendingStatus == ArticleReviewStatus.SUCCESS.type) {
            // 审核成功，生成html
            try {
//                createArticleHTML(articleId);
                // 关联文章的id到存储到mongodb gridfs中的articleMongId，
                // articleMongId是到mongodb中的查找依据
                // 这里要做好映射关系的保存，是一个更新操作
                // 在service层里写好对应的关联关系保存的更新操作即可
                String articleMongId = createArticleHTMLToGridFs(articleId);
				 articleService.updateArticleMongodb(articleId,articleMongId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
        }
    }
```

注意需要更新article的持久化属性，它其中是有一个属性记录存储gridfs位置的

```java
    @Transactional
    @Override
    public void updateArticleMongodb(String articleId, String mongoId) {

        Example articleExample = new Example(Article.class);
        Example.Criteria criteria = articleExample.createCriteria();
        criteria.andEqualTo("id", articleId);

        Article pending = new Article();
        pending.setMongoFileId(mongoId);
        articleMapper.updateByExampleSelective(pending, articleExample);

    }

```

### 9.6 静态化解耦-消费端从gridfs下载

消费端目的主要接受请求后，==从grid下载html到制定的路径，用于发布==。（我们这里采用tomcat，如果你使用nginx的话也可以）

```java
@Api(value = "文章html controller", tags = {"文章html controller"})
@RequestMapping("article/html")
public interface ArticleHtmlControllerApi {


    @ApiOperation(value = "下载文章html", notes = "下载文章html", httpMethod = "GET")
    @GetMapping("/download")
    Integer download(String articleId, String articleMongoId) throws Exception;


}

```

```java
jacv@RestController
public class ArticleHtmlController implements ArticleHtmlControllerApi {


        final static Logger logger = LoggerFactory.getLogger(ArticleHtmlController.class);

        @Value("${freemarker.html.article}")
        private String articlePath;

        @Autowired
        private GridFSBucket gridFSBucket;

        @Override
        public Integer download(String articleId, String articleMongoId) throws Exception {
            // 拼接最终文件的保存地址
            String path = articlePath + File.separator + articleId + ".html";
            // 获取文件流，定义存放位置和名称
            File file = new File(path);
            // 创建输出流
            OutputStream os = new FileOutputStream(file);
            // 执行下载
            gridFSBucket.downloadToStream(new ObjectId(articleMongoId), os);

            return HttpStatus.OK.value();
        }



}
```

生产端调用一下，下载html

在审查之后的那一段，直接消费一下

```java
...  
               // createArticleHTML(articleId);
                // 关联文章的id到存储到mongodb gridfs中的articleMongId，
                // articleMongId是到mongodb中的查找依据
                // 这里要做好映射关系的保存，是一个更新操作
                // 在service层里写好对应的关联关系保存的更新操作即可
                String articleMongoId = createArticleHTMLToGridFs(articleId);
                articleService.updateArticleMongodb(articleId,articleMongoId);

                // 调用消费端（前端服务器所部署的后端程序来下载html）
                doDownloadArticleHTML(articleId, articleMongoId);
```

```java
 private void doDownloadArticleHTML(String articleId, String articleMongoId) {
        String htmlUrl = "http://html.imoocnews.com:8010/article/html/download?articleId=" + articleId + "&articleMongoId=" + articleMongoId;
        ResponseEntity<Integer> response = restTemplate.getForEntity(htmlUrl, Integer.class);
        Integer status = response.getBody();
        if (status != HttpStatus.OK.value()) {
            GraceException.display(ResponseStatusEnum.ARTICLE_REVIEW_ERROR);
        }
    }

```

撤回和删除文章需要删除gridfs及html，与上述过程类似，不再提及

### 9.7 静态化解耦-下载-使用RabbitMQ

在api模块中引入rabbitmq依赖

```xml
      <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
```

在生产者和消费者模块中添加rabbitmq配置:注意在rabbitmq管理界面对应配置下述内容

```yml
spring:  
  rabbitmq:
    host: 192.168.205.107
    port: 5672
    username: judy
    password: 123456
    virtual-host: imood-news
```

在api模块中添加rabbitmq的配置类

```java
package com.dexlace.api.config;

import org.springframework.amqp.core.*;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/11
 */
@Configuration
public class RabbitMqConfig {
    // 交换机的名字
    public static final String EXCHANGE_ARTICLE="exchange_article";
    // 队列的没名字
    public static final String QUEUE_DOWNLOAD_HTML="queue_download_html";


    // 创建交换机，放入springboot容器
    @Bean(EXCHANGE_ARTICLE)
    public Exchange exchange() {
        return ExchangeBuilder                      // 构建交换机
                .topicExchange(EXCHANGE_ARTICLE)    // 使用topic类型，并定义交换机的名称。https://www.rabbitmq.com/getstarted.html
                .durable(true)                      // 设置持久化，重启MQ后依然存在
                .build();
    }

    // 创建队列
    @Bean(QUEUE_DOWNLOAD_HTML)
    public Queue queue() {
        return new Queue(QUEUE_DOWNLOAD_HTML);
    }

    // 队列绑定交换机
    @Bean
    public Binding binding(
            @Qualifier(QUEUE_DOWNLOAD_HTML) Queue queue, //@Qualifier保证了对象从容器中拿来
            @Qualifier(EXCHANGE_ARTICLE) Exchange exchange) {
        return BindingBuilder               // 定义绑定关系
                .bind(queue)                // 绑定队列
                .to(exchange)               // 到交换机
                .with("article.*")   // 定义路由规则（requestMapping映射）
                .noargs();                  // 执行绑定
    }
}

```

article模块创建生产者:==将articleId及其对应的articleMongoId投放出去==

```java
   @Autowired
    private RabbitTemplate rabbitTemplate;


    private void doDownloadArticleHTMLByMQ(String articleId, String articleMongoId) {
       rabbitTemplate.convertAndSend(
               RabbitMqConfig.EXCHANGE_ARTICLE,
               "article.download",
               articleId+"-"+articleMongoId);
    }
```

整个生产流程过程如下:==即换掉远程调用==

```java
 /**
         * 审核完毕就应该文章详情页面静态化
         */
        if (pendingStatus == ArticleReviewStatus.SUCCESS.type) {
            // 审核成功，生成html
            try {
//                createArticleHTML(articleId);
                // 关联文章的id到存储到mongodb gridfs中的articleMongId，
                // articleMongId是到mongodb中的查找依据
                // 这里要做好映射关系的保存，是一个更新操作
                // 在service层里写好对应的关联关系保存的更新操作即可
                String articleMongoId = createArticleHTMLToGridFs(articleId);
                articleService.updateArticleMongodb(articleId, articleMongoId);

//                // 调用消费端（前端服务器所部署的后端程序来下载html）
//                doDownloadArticleHTML(articleId, articleMongoId);
                // 发送消息到mq队列，让消费者监听并且执行下载html
                doDownloadArticleHTMLByMQ(articleId,articleMongoId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
```

html模块：消费者监听

```java
@Component
public class RabbitMQConsumer {

    @Autowired
    private ArticleHtmlProcessor articleHtmlProcessor;

    @RabbitListener(queues = {RabbitMqConfig.QUEUE_DOWNLOAD_HTML})
    public void watchQueue(String payload, Message message) throws Exception {

        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        // 找到对应的routingKey，然后获取articleId和对应的mongoId
        if (routingKey.equalsIgnoreCase("article.download")){
            String[] strings = payload.split("-");
            articleHtmlProcessor.download(strings[0],strings[1]);
        }

    }
}
```

ArticleHtmlProcessor提供与以前同样的下载功能而已

```java
package com.dexlace.html.consumer;

import com.mongodb.client.gridfs.GridFSBucket;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/11
 */
@Component
public class ArticleHtmlProcessor {
    final static Logger logger = LoggerFactory.getLogger(ArticleHtmlProcessor.class);

    @Value("${freemarker.html.article}")
    private String articlePath;

    @Autowired
    private GridFSBucket gridFSBucket;


    public Integer download(String articleId, String articleMongoId) throws Exception {
        // 拼接最终文件的保存地址
        String path = articlePath + File.separator + articleId + ".html";
        // 获取文件流，定义存放位置和名称
        File file = new File(path);
        // 创建输出流
        OutputStream os = new FileOutputStream(file);
        // 执行下载
        gridFSBucket.downloadToStream(new ObjectId(articleMongoId), os);

        os.close();

        return HttpStatus.OK.value();
    }

    public Integer delete(String articleId, String articleMongoId) throws Exception {
        gridFSBucket.delete(new ObjectId(articleMongoId));
        // 拼接最终文件的保存地址,这是要删除的路径
        String path = articlePath + File.separator + articleId + ".html";

        logger.info("path: {}",path);
        // 获取文件流，定义删除文件的位置和名称
        File file = new File(path);

        if (!file.exists()){

            return HttpStatus.NOT_FOUND.value();

        }

        // 注意 上个download方法需要关闭流，不然这里删除不了文件
        boolean delete = file.delete();


        return HttpStatus.OK.value();



    }
}

```

### 9.8 静态化解耦-删除/撤回-使用RabbitMQ

逻辑很简单，就是替代远程调用而已

```java
 @Autowired
    private RabbitTemplate rabbitTemplate;
private void deleteArticleHTMLByMQ(String articleId, String articleMongoId) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE_ARTICLE,
                "article.delete",
                articleId+"-"+articleMongoId);
    }
```

然后删除和撤回逻辑里使用上述方法，在不同的routingkey下投递articleId和articleMongoId

```java
   
    @Override
    public GraceIMOODJSONResult delete(String userId, String articleId) {
        String mongoFileId = articleService.deleteArticle(userId, articleId);
//        deleteArticleHTML(articleId, mongoFileId);
        deleteArticleHTMLByMQ(articleId,mongoFileId);
        return GraceIMOODJSONResult.ok();
    }


@Override
    public GraceIMOODJSONResult withdraw(String userId, String articleId) {
        String mongoFileId = articleService.withdrawArticle(userId, articleId);
//        deleteArticleHTML(articleId, mongoFileId);
        deleteArticleHTMLByMQ(articleId,mongoFileId);
        return GraceIMOODJSONResult.ok();
    }

```

消费端监听

```java
...  
if (routingKey.equalsIgnoreCase("article.delete")){
            String[] strings = payload.split("-");
            articleHtmlProcessor.delete(strings[0],strings[1]);
        }
...
```

delete的方法不变

### 9.9 延迟队列需求-定时发布

<img src="Judy-news.assets/image-20210612151549220.png" alt="image-20210612151549220" style="zoom: 50%;" />

目前文章的定时发布其实是到==某个时间点以后，全表扫描数据库，把所有标记为定时发布的更改为即时发布状态（is_appoint）==，这么做虽然能实现文章定时发布，但是会有问题：

- 数据量庞大的时候，==全表扫描==，对数据库有巨大冲击，而且性能十分低下
- ==时间精度不够==，因为发布时间和当前时间相比较，实时性不够好

那么其实消息队列有延时的这个功能，当我要定时发布一个文章的时候，设定好时间，那这个作为一个消息放入RabbitMQ，虽然放入了消息，但是消费者监听到这个队列有消息以后，不会立马去处理消费，而是会等到这个时间点才回触发，这就是延迟队列。常见的场景：

- ==自动发布文章==
- ==自动取消订单==
- ==自动确认收货==

使用RabbitMQ的延迟队列需要下载插件才能使用：https://github.com/rabbitmq/rabbitmq-delayed-message-exchange

或者在此页面查看下载也行：https://www.rabbitmq.com/community-plugins.html

上传至`/usr/lib/rabbitmq/lib/rabbitmq_server-3.7.18/plugins`

![image-20210612153314554](Judy-news.assets/image-20210612153314554.png)

> 开启延时队列（rabbitmq-server启动着）
>
> ```bash
> rabbitmq-serverrabbitmq-plugins enable rabbitmq_delayed_message_exchange
> 
> ```
>
> 运行如下命令检查现有的插件列表：
>
> ```bash
> rabbitmq-plugins list
> ```
>
> 重启rabbitmq：
>
> ```bash
> service rabbitmq-server restart
> 
> 
> ```

### 9.10 定时发布实现

先配置一个==rabbitmq的配置类==，本文所有配置类在api模块中实现

```java
package com.dexlace.api.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/11
 */
@Configuration
public class RabbitMqDelayConfig {
    // 交换机的名字
    public static final String EXCHANGE_DELAY="exchange_delay";
    // 队列的没名字
    public static final String QUEUE_DELAY="queue_delay";


    // 创建交换机，放入springboot容器
    @Bean(EXCHANGE_DELAY)
    public Exchange exchange() {
        return ExchangeBuilder                      // 构建交换机
                .topicExchange(EXCHANGE_DELAY)    // 使用topic类型，并定义交换机的名称。https://www.rabbitmq.com/getstarted.html
                .delayed()    //开启支持延时
                .durable(true)                      // 设置持久化，重启MQ后依然存在
                .build();
    }

    // 创建队列
    @Bean(QUEUE_DELAY)
    public Queue queue() {
        return new Queue(QUEUE_DELAY);
    }

    // 队列绑定交换机
    @Bean
    public Binding delayBinding(
            @Qualifier(QUEUE_DELAY) Queue queue, //@Qualifier保证了对象从容器中拿来
            @Qualifier(EXCHANGE_DELAY) Exchange exchange) {
        return BindingBuilder               // 定义绑定关系
                .bind(queue)                // 绑定队列
                .to(exchange)               // 到交换机
                .with("article.delay.*")   // 定义路由规则（requestMapping映射）
                .noargs();                  // 执行绑定
    }
}

```

==生产者==：发布文章的service的改造，即createArticle的改造

```java
           
...

// 得到毫秒数
            final Long delay = newArticleBO.getPublishTime().getTime() - new Date().getTime();

            MessagePostProcessor messagePostProcessor= message -> {
                message.getMessageProperties()
                        // 设置消息持久化
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                // 设置发布的时间延时
                message.getMessageProperties().setDelay(delay.intValue());
                return message;
            };


           
            rabbitTemplate.convertAndSend(
                    RabbitMqDelayConfig.EXCHANGE_DELAY,
                    "article.delay.publish",
                    articleId,
                    messagePostProcessor
                    );
```

消费者：

```java
@Component
public class RabbitMQDelayConsumer {

    @Autowired
    private ArticleService articleService;


    @RabbitListener(queues={RabbitMqDelayConfig.QUEUE_DELAY})
    public void watchQueue(String payload, Message message){
        String routingKey=message.getMessageProperties().getReceivedRoutingKey();
        if(routingKey.equalsIgnoreCase("article.delay.publish")){
            String articleId=payload;
            articleService.updateAppointToPublishRabbitmq(articleId);
        }

    }
}
```

```java
    @Transactional
    @Override
    public void updateAppointToPublishRabbitmq(String articleId) {

        Article article=new Article();
        article.setId(articleId);
        article.setIsAppoint(ArticleAppointType.IMMEDIATELY.type);
        articleMapper.updateByPrimaryKeySelective(article);
    }

```

## 十、微服务化

注册中心：Eureka

服务调用：Feign

负载均衡：Ribbon

熔断降级：Hystrix

微服务网关：Zuul

分布式配置中心：SpringCloud Config

消息总线：SpringCloud Bus

消息驱动：Stream

链路追踪：Sleuth

挑出几点来阐述（所有配置都不讲，已经学过）Zuul在ip黑名单上的应用：

```java


/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/13
 */
public class BlackIpFilter extends ZuulFilter {

    /**
     * ip连续请求的次数
     */
    @Value("${blackIp.continueCounts}")
    private Integer continueCounts;

    /**
     * ip判断的时间间隔，单位：秒
     */
    @Value("${blackIp.timeInterval}")
    private Integer timeInterval;

    /**
     * 限制的时间，单位：秒
     */
    @Value("${blackIp.limitTimes}")
    private Integer limitTimes;




    @Autowired
    private RedisOperator redis;



    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("执行【IP黑名单】Zuul过滤器...");

        // 获得上下文对象requestContext
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // 获得ip
        String ip = IPUtil.getRequestIp(request);

        /**
         * 需求：
         * 判断ip在10秒内请求的次数是否超过10次，
         * 如果超过，则限制访问15秒，15秒过后再放行
         */
        final String ipRedisKey = "zuul-ip:" + ip;
        final String ipRedisLimitKey = "zuul-ip-limit:" + ip;

        // 获得剩余的限制时间
        long limitLeftTime = redis.ttl(ipRedisLimitKey);
        // 如果剩余时间还存在，说明这个ip不能访问，继续等待
        if (limitLeftTime > 0) {
            stopRequest(requestContext);
            return null;
        }

        // 在redis中累加ip的请求访问次数
        long requestCounts = redis.increment(ipRedisKey, 1);

        // 从0开始计算请求次数，初期访问为1，则设置过期时间，也就是连续请求的间隔时间
        if (requestCounts == 1) {
            redis.expire(ipRedisKey, timeInterval);
        }

        // 如果还能取得到请求次数，说明用户连续请求的次数落在10秒内
        // 一旦请求次数超过了连续访问的次数，则需要限制这个ip了
        if (requestCounts > continueCounts) {
            // 限制ip访问一段时间
            redis.set(ipRedisLimitKey, ipRedisLimitKey, limitTimes);

            stopRequest(requestContext);
        }

        return null;
    }

    private void stopRequest(RequestContext requestContext){
        // 停止继续向下路由，禁止请求通信
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(200);
        String result = JsonUtils.objectToJson(
                GraceIMOODJSONResult.errorCustom(
                        ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP));
        requestContext.setResponseBody(result);
        requestContext.getResponse().setCharacterEncoding("utf-8");
        requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

}

```

其余略

## 十一、分布式搜索引擎与ELK

### 11.1 ElasticSearch概述

- ES 是一个**开源的高扩展的分布式全文搜索引擎**
- 实时的存储、检索数据
- 本身扩展性很好，可以扩展到上百台服务器，处理 PB 级别的数据。

#### 11.1.1 全文搜索引擎

对于非结构化的数据文本，关系型数据库搜索不是能很好的支持。进行全文检索需要扫描整个表，如果数据量大的话即使对 SQL 的语法优化，也收效甚微。建立了索引，但是维护起来也很麻烦，对于 insert 和 update 操作都会重新构建索引。

基于以上原因可以分析得出，在一些生产环境中，使用常规的搜索方式，性能是非常差的：

- 搜索的数据对象是大量的==非结构化的文本数据==。
- ==文件记录量==达到数十万或数百万个甚至更多。
- 支持==大量基于交互式文本的查询==。
- 需求非常灵活的==全文搜索查询==。
- 对高度相关的搜索结果的有特殊需求，但是==没有可用的关系数据库==可以满足。
-  对不同记录类型、非文本数据操作或==安全事务处理的需求相对较少==的情况。

这里说到的全文搜索引擎指的是目前广泛应用的主流搜索引擎。它的工作原理是计算机索引程序通过扫描文章中的每一个词，==对每一个词建立一个索引，指明该词在文章中出现的次数和位置==，当用户查询时，检索程序就根据事先建立的索引进行查找，并将查找的结果反馈给用户的检索方式。这个过程类似于通过字典中的检索字表查字的过程。

#### 11.1.2 Elasticsearch And Solr

Lucene 是 Apache 软件基金会 Jakarta 项目组的一个子项目，提供了一个简单却强大的应用程式接口，能够做全文索引和搜寻。在 Java 开发环境里==Lucene 是一个成熟的免费开源工具==。就其本身而言，Lucene 是当前以及最近几年最受欢迎的==免费 Java 信息检索程序库==。但 Lucene 只是一个提供全文搜索功能类库的核心工具包，而真正使用它还==需要一个完善的服务框架搭建起来进行应用==。

**Elasticsearch** 和 **Solr**,==这两款都是基于 Lucene 搭建的==，可以独立部署启动的搜索引擎服务软件

Elasticsearch 和 Solr 都是开源搜索引擎，那么我们在使用时该如何选择呢？

- Google 搜索趋势结果表明，与 Solr 相比，Elasticsearch 具有很大的吸引力，但这并不意味着 Apache Solr 已经死亡。虽然有些人可能不这么认为，但 Solr 仍然是最受欢迎的搜索引擎之一，拥有强大的社区和开源支持。

- 与 Solr 相比，==Elasticsearch 易于安装且非常轻巧==。此外，你可以在几分钟内安装并运行Elasticsearch。但是，如果 Elasticsearch 管理不当，这种易于部署和使用可能会成为一个问题。基于 JSON 的配置很简单，但如果要为文件中的每个配置指定注释，那么它不适合您。总的来说，如果你的应用使用的是 JSON，那么 Elasticsearch 是一个更好的选择。否则，请使用 Solr，因为它的 schema.xml 和 solrconfig.xml 都有很好的文档记录。

- Solr 拥有更大，更成熟的用户，开发者和贡献者社区。ES 虽拥有的规模较小但活跃的用户社区以及不断增长的贡献者社区。==Solr 贡献者和提交者来自许多不同的组织，而 Elasticsearch 提交者来自单个公司==。

- ==Solr 更成熟，但 ES 增长迅速，更稳定==。

- Solr 是一个非常有据可查的产品，具有清晰的示例和 API 用例场景。==Elasticsearch 的 文档组织良好，但它缺乏好的示例和清晰的配置说明==

**那么，到底是** **Solr** **还是** **Elasticsearch**？

有时很难找到明确的答案。无论您选择 Solr 还是 Elasticsearch，首先需要了解正确的用例和未来需求。总结他们的每个属性。

由于易于使用，Elasticsearch 在新开发者中更受欢迎。一个下载和一个命令就可以启动一切。

如果除了搜索文本之外还需要它来==处理分析查询==，Elasticsearch 是更好的选择

如果需要==分布式索引==，则需要选择 Elasticsearch。对于需要良好可伸缩性和以及性能分布式环境，Elasticsearch 是更好的选择。

Elasticsearch 在==开源日志管理==用例中占据主导地位，许多组织在 Elasticsearch 中索引它们的日志以使其可搜索。

如果你喜欢监控和指标，那么请使用 Elasticsearch，因为相对于 Solr，Elasticsearch 暴露了更多的关键指标

#### 11.1.3 **Elasticsearch** **应用案例**

- **GitHub**: 2013 年初，抛弃了 Solr，采取 Elasticsearch 来做 ==PB 级的搜索==。“GitHub 使用Elasticsearch 搜索 20TB 的数据，包括 13 亿文件和 1300 亿行代码”。 

- **维基百科**：启动以 Elasticsearch 为基础的核心搜索架构

- **SoundCloud**：“SoundCloud 使用 Elasticsearch 为 1.8 亿用户提供即时而精准的音乐搜索服务”。 

- **百度**：目前广泛使用 Elasticsearch 作为文本数据分析，采集百度所有服务器上的各类指标数据及用户自定义数据，通过对各种数据进行多维分析展示，辅助定位分析实例异常或业务层面异常。目前覆盖百度内部 20 多个业务线（包括云分析、网盟、预测、文库、直达号、钱包、风控等），单集群最大 100 台机器，200 个 ES 节点，每天导入 30TB+数据。

- **新浪**：使用 Elasticsearch 分析处理 32 亿条实时日志。

- **阿里**：使用 Elasticsearch 构建日志采集和分析体系。

- **Stack Overflow**：解决 Bug 问题的网站，全英文，编程人员交流的网站。

#### 11.1.4 正排索引和倒排索引

在**搜索引擎**中每个文件都对应==一个文件ID==，文件内容被表示为==一系列关键词（关键词ID）==的集合，如果把ID比作key，那么关键词就是value。

==正排索引==：通过文件ID找关键词，即通过key找value

==倒排索引==：通过关键词找文件ID，即通过value找key

### 11.2 Elasticsearch下载与安装

下载：https://www.elastic.co/cn/downloads/elasticsearch

> es要使用jdk，所以需要先安装jdk
>
> 解压后移动到/usr/local
>
> 修改config/elasticsearc.yml
>
> ```yml
> # ======================== Elasticsearch Configuration =========================
> #
> # NOTE: Elasticsearch comes with reasonable defaults for most settings.
> #       Before you set out to tweak and tune the configuration, make sure you
> #       understand what are you trying to accomplish and the consequences.
> #
> # The primary way of configuring a node is via this file. This template lists
> # the most important settings you may want to configure for a production cluster.
> #
> # Please consult the documentation for further information on configuration options:
> # https://www.elastic.co/guide/en/elasticsearch/reference/index.html
> #
> # ---------------------------------- Cluster -----------------------------------
> #
> # Use a descriptive name for your cluster:
> #
> cluster.name: imood-elasticsearch
> #
> # ------------------------------------ Node ------------------------------------
> #
> # Use a descriptive name for the node:
> #
> node.name: node-1
> #
> # Add custom attributes to the node:
> #
> #node.attr.rack: r1
> #
> # ----------------------------------- Paths ------------------------------------
> #
> # Path to directory where to store the data (separate multiple locations by comma):
> #
> path.data: /usr/local/elasticsearch/data
> #
> # Path to log files:
> #
> path.logs: /usr/local/elasticsearch/data/logs
> #
> # ----------------------------------- Memory -----------------------------------
> #
> # Lock the memory on startup:
> #
> #bootstrap.memory_lock: true
> #
> # Make sure that the heap size is set to about half the memory available
> # on the system and that the owner of the process is allowed to use this
> # limit.
> #
> # Elasticsearch performs poorly when the system is swapping the memory.
> #
> # ---------------------------------- Network -----------------------------------
> #
> # Set the bind address to a specific IP (IPv4 or IPv6):
> #
> network.host: 0.0.0.0  # 如此允许外部客户端访问
> #
> # Set a custom port for HTTP:
> #
> #http.port: 9200
> #
> # For more information, consult the network module documentation.
> #
> # --------------------------------- Discovery ----------------------------------
> #
> # Pass an initial list of hosts to perform discovery when new node is started:
> # The default list of hosts is ["127.0.0.1", "[::1]"]
> #
> #discovery.zen.ping.unicast.hosts: ["host1", "host2"]
> #
> # Prevent the "split brain" by configuring the majority of nodes (total number of master-eligible nodes / 2 + 1):
> #
> #discovery.zen.minimum_master_nodes: 
> #
> # For more information, consult the zen discovery module documentation.
> #
> # ---------------------------------- Gateway -----------------------------------
> #
> # Block initial recovery after a full cluster restart until N nodes are started:
> #
> #gateway.recover_after_nodes: 3
> #
> # For more information, consult the gateway module documentation.
> #
> # ---------------------------------- Various -----------------------------------
> #
> # Require explicit names when deleting indices:
> #
> #action.destructive_requires_name: true
> 
> ```
>
> 修改config/jvm.options,默认的xms和xmx都是1g,根据内存可以更改
>
> ```bash
> -Xms512m
> -Xms512m
> ```
>
> 使用普通用户操作es
>
> ```bash
> chown -R doglast:doglast /usr/local/elasticsearch
> ```
>
> 启动
>
> - 登录普通用户
>
> - ```bash
>   ./bin/elasticsearch
>   ```
>
> - 如果出现以下错误
>
> <img src="Judy-news.assets/image-20210621195844606.png" alt="image-20210621195844606" style="zoom:80%;" />
>
> - 切换到root用户下修改
>
> ```bash
> vim /etc/security/limits.conf
> 
> ```
>
> ```xml
> * soft nofile 65536
> * hard nofile 131072
> * soft nproc 2048
> * hard nproc 4096
> ```
>
> 且：
>
> ```bash
> vim /etc/sysctl.conf
> ```
>
> ```properties
> vm.max_map_count=262145
> ```
>
> 并且`sysctl -p`刷新一下
>
> 重新启动
>
> ```bash
> ./bin/elasticsearch
> # 或者
> ./bin/elasticsearch -d  # 后台启动
> ```
>
> 测试
>
> 虚拟机ip:9200

端口号意义：

- 9200：Http协议，用于==外部通讯==
- 9300：==Tcp协议，ES集群之间是通过9300通讯==

可视化管理：安装ES Header插件，使用谷歌市场的head插件就好

![image-20210621201034078](Judy-news.assets/image-20210621201034078.png)

### 11.3 Elasticsearch数据模型和内置分词器

#### 11.3.1 数据模型

<img src="Judy-news.assets/image-20210621202818035.png" alt="image-20210621202818035" style="zoom:80%;" />

#### 11.3.2 分词与内置分词器

把==文本转换为一个个的单词==，分词称之为==analysis==。es默认只对英文语句做分词，中文不支持，==每个中文字都会被拆分为独立的个体。==

使用postman分析

分词请求路径：http://192.168.205.114:9200/_analyze

- standard:默认分词，==单词会被拆分(各个单词之间需要有空格才能)，大小会转换为小写==

> ```json
> 
> {
>     "analyzer":"standard",
>     "text":"My name is Peter Parker, I am a Super Hero. I don't like the Criminals."
> }
> 
> ```
>
> 以上analyzer为分词器名称，text为测试的文本
>
> 其分析结果如下：
>
> ```json
> {
>     "tokens": [
>         {
>             "token": "my",
>             "start_offset": 0,
>             "end_offset": 2,
>             "type": "<ALPHANUM>",
>             "position": 0
>         },
>         {
>             "token": "name",
>             "start_offset": 3,
>             "end_offset": 7,
>             "type": "<ALPHANUM>",
>             "position": 1
>         },
>         {
>             "token": "is",
>             "start_offset": 8,
>             "end_offset": 10,
>             "type": "<ALPHANUM>",
>             "position": 2
>         },
>         {
>             "token": "peter",
>             "start_offset": 11,
>             "end_offset": 16,
>             "type": "<ALPHANUM>",
>             "position": 3
>         },
>         {
>             "token": "parker",
>             "start_offset": 17,
>             "end_offset": 23,
>             "type": "<ALPHANUM>",
>             "position": 4
>         },
>         {
>             "token": "i",
>             "start_offset": 25,
>             "end_offset": 26,
>             "type": "<ALPHANUM>",
>             "position": 5
>         },
>         {
>             "token": "am",
>             "start_offset": 27,
>             "end_offset": 29,
>             "type": "<ALPHANUM>",
>             "position": 6
>         },
>         {
>             "token": "a",
>             "start_offset": 30,
>             "end_offset": 31,
>             "type": "<ALPHANUM>",
>             "position": 7
>         },
>         {
>             "token": "super",
>             "start_offset": 32,
>             "end_offset": 37,
>             "type": "<ALPHANUM>",
>             "position": 8
>         },
>         {
>             "token": "hero",
>             "start_offset": 38,
>             "end_offset": 42,
>             "type": "<ALPHANUM>",
>             "position": 9
>         },
>         {
>             "token": "i",
>             "start_offset": 44,
>             "end_offset": 45,
>             "type": "<ALPHANUM>",
>             "position": 10
>         },
>         {
>             "token": "don't",
>             "start_offset": 46,
>             "end_offset": 51,
>             "type": "<ALPHANUM>",
>             "position": 11
>         },
>         {
>             "token": "like",
>             "start_offset": 52,
>             "end_offset": 56,
>             "type": "<ALPHANUM>",
>             "position": 12
>         },
>         {
>             "token": "the",
>             "start_offset": 57,
>             "end_offset": 60,
>             "type": "<ALPHANUM>",
>             "position": 13
>         },
>         {
>             "token": "criminals",
>             "start_offset": 61,
>             "end_offset": 70,
>             "type": "<ALPHANUM>",
>             "position": 14
>         }
>     ]
> }
> ```

- simple：==按照<font color=red>非字母</font>分词，大写转为小写。==

> ```json
> {
>     "analyzer":"simple",
>     "text":"My name is Peter Parker, I am a Super Hero. I don't like the Criminals."
> }
> ```
>
> ```json
> {
>     "tokens": [
>         {
>             "token": "my",
>             "start_offset": 0,
>             "end_offset": 2,
>             "type": "word",
>             "position": 0
>         },
>         {
>             "token": "name",
>             "start_offset": 3,
>             "end_offset": 7,
>             "type": "word",
>             "position": 1
>         },
>         {
>             "token": "is",
>             "start_offset": 8,
>             "end_offset": 10,
>             "type": "word",
>             "position": 2
>         },
>         {
>             "token": "peter",
>             "start_offset": 11,
>             "end_offset": 16,
>             "type": "word",
>             "position": 3
>         },
>         {
>             "token": "parker",
>             "start_offset": 17,
>             "end_offset": 23,
>             "type": "word",
>             "position": 4
>         },
>         {
>             "token": "i",
>             "start_offset": 25,
>             "end_offset": 26,
>             "type": "word",
>             "position": 5
>         },
>         {
>             "token": "am",
>             "start_offset": 27,
>             "end_offset": 29,
>             "type": "word",
>             "position": 6
>         },
>         {
>             "token": "a",
>             "start_offset": 30,
>             "end_offset": 31,
>             "type": "word",
>             "position": 7
>         },
>         {
>             "token": "super",
>             "start_offset": 32,
>             "end_offset": 37,
>             "type": "word",
>             "position": 8
>         },
>         {
>             "token": "hero",
>             "start_offset": 38,
>             "end_offset": 42,
>             "type": "word",
>             "position": 9
>         },
>         {
>             "token": "i",
>             "start_offset": 44,
>             "end_offset": 45,
>             "type": "word",
>             "position": 10
>         },
>         {
>             "token": "don",
>             "start_offset": 46,
>             "end_offset": 49,
>             "type": "word",
>             "position": 11
>         },
>         {
>             "token": "t",
>             "start_offset": 50,
>             "end_offset": 51,
>             "type": "word",
>             "position": 12
>         },
>         {
>             "token": "like",
>             "start_offset": 52,
>             "end_offset": 56,
>             "type": "word",
>             "position": 13
>         },
>         {
>             "token": "the",
>             "start_offset": 57,
>             "end_offset": 60,
>             "type": "word",
>             "position": 14
>         },
>         {
>             "token": "criminals",
>             "start_offset": 61,
>             "end_offset": 70,
>             "type": "word",
>             "position": 15
>         }
>     ]
> }
> ```
>
> 

- whitespace:按照==空格分词==，忽略==大小写==

> ```json
> {
>     "analyzer":"whitespace",
>     "text":"My name is Peter Parker, I am a Super Hero. I don't like the Criminals."
> }
> ```
>
> ```json
> {
>     "tokens": [
>         {
>             "token": "My",
>             "start_offset": 0,
>             "end_offset": 2,
>             "type": "word",
>             "position": 0
>         },
>         {
>             "token": "name",
>             "start_offset": 3,
>             "end_offset": 7,
>             "type": "word",
>             "position": 1
>         },
>         {
>             "token": "is",
>             "start_offset": 8,
>             "end_offset": 10,
>             "type": "word",
>             "position": 2
>         },
>         {
>             "token": "Peter",
>             "start_offset": 11,
>             "end_offset": 16,
>             "type": "word",
>             "position": 3
>         },
>         {
>             "token": "Parker,",
>             "start_offset": 17,
>             "end_offset": 24,
>             "type": "word",
>             "position": 4
>         },
>         {
>             "token": "I",
>             "start_offset": 25,
>             "end_offset": 26,
>             "type": "word",
>             "position": 5
>         },
>         {
>             "token": "am",
>             "start_offset": 27,
>             "end_offset": 29,
>             "type": "word",
>             "position": 6
>         },
>         {
>             "token": "a",
>             "start_offset": 30,
>             "end_offset": 31,
>             "type": "word",
>             "position": 7
>         },
>         {
>             "token": "Super",
>             "start_offset": 32,
>             "end_offset": 37,
>             "type": "word",
>             "position": 8
>         },
>         {
>             "token": "Hero.",
>             "start_offset": 38,
>             "end_offset": 43,
>             "type": "word",
>             "position": 9
>         },
>         {
>             "token": "I",
>             "start_offset": 44,
>             "end_offset": 45,
>             "type": "word",
>             "position": 10
>         },
>         {
>             "token": "don't",
>             "start_offset": 46,
>             "end_offset": 51,
>             "type": "word",
>             "position": 11
>         },
>         {
>             "token": "like",
>             "start_offset": 52,
>             "end_offset": 56,
>             "type": "word",
>             "position": 12
>         },
>         {
>             "token": "the",
>             "start_offset": 57,
>             "end_offset": 60,
>             "type": "word",
>             "position": 13
>         },
>         {
>             "token": "Criminals.",
>             "start_offset": 61,
>             "end_offset": 71,
>             "type": "word",
>             "position": 14
>         }
>     ]
> }
> ```

- stop:==去除无意义单词==，比如the/a/an/is

> ```json
> {
>     "analyzer":"stop",
>     "text":"My name is Peter Parker, I am a Super Hero. I don't like the Criminals."
> }
> ```
>
> ```json
> {
>     "tokens": [
>         {
>             "token": "my",
>             "start_offset": 0,
>             "end_offset": 2,
>             "type": "word",
>             "position": 0
>         },
>         {
>             "token": "name",
>             "start_offset": 3,
>             "end_offset": 7,
>             "type": "word",
>             "position": 1
>         },
>         {
>             "token": "peter",
>             "start_offset": 11,
>             "end_offset": 16,
>             "type": "word",
>             "position": 3
>         },
>         {
>             "token": "parker",
>             "start_offset": 17,
>             "end_offset": 23,
>             "type": "word",
>             "position": 4
>         },
>         {
>             "token": "i",
>             "start_offset": 25,
>             "end_offset": 26,
>             "type": "word",
>             "position": 5
>         },
>         {
>             "token": "am",
>             "start_offset": 27,
>             "end_offset": 29,
>             "type": "word",
>             "position": 6
>         },
>         {
>             "token": "super",
>             "start_offset": 32,
>             "end_offset": 37,
>             "type": "word",
>             "position": 8
>         },
>         {
>             "token": "hero",
>             "start_offset": 38,
>             "end_offset": 42,
>             "type": "word",
>             "position": 9
>         },
>         {
>             "token": "i",
>             "start_offset": 44,
>             "end_offset": 45,
>             "type": "word",
>             "position": 10
>         },
>         {
>             "token": "don",
>             "start_offset": 46,
>             "end_offset": 49,
>             "type": "word",
>             "position": 11
>         },
>         {
>             "token": "t",
>             "start_offset": 50,
>             "end_offset": 51,
>             "type": "word",
>             "position": 12
>         },
>         {
>             "token": "like",
>             "start_offset": 52,
>             "end_offset": 56,
>             "type": "word",
>             "position": 13
>         },
>         {
>             "token": "criminals",
>             "start_offset": 61,
>             "end_offset": 70,
>             "type": "word",
>             "position": 15
>         }
>     ]
> }
> ```

- keyword:不做分词。==把整个文本作为一个单独的关键词==

> ```json
> {
>     "analyzer":"keyword",
>     "text":"My name is Peter Parker, I am a Super Hero. I don't like the Criminals."
> }
> ```
>
> ```json
> {
>     "tokens": [
>         {
>             "token": "My name is Peter Parker, I am a Super Hero. I don't like the Criminals.",
>             "start_offset": 0,
>             "end_offset": 71,
>             "type": "word",
>             "position": 0
>         }
>     ]
> }
> ```
>
> 

#### 11.3.3 IK中文分词器

Github：https://github.com/medcl/elasticsearch-analysis-ik

解压该文件到`plugins`文件夹内

重启es

```json


{
    "analyzer": "ik_max_word",
    "text": "今天上下班车流量很大很拥堵"
}

```

```json
{
    "tokens": [
        {
            "token": "今天",
            "start_offset": 0,
            "end_offset": 2,
            "type": "CN_WORD",
            "position": 0
        },
        {
            "token": "今",
            "start_offset": 0,
            "end_offset": 1,
            "type": "CN_CHAR",
            "position": 1
        },
        {
            "token": "天上",
            "start_offset": 1,
            "end_offset": 3,
            "type": "CN_WORD",
            "position": 2
        },
        {
            "token": "上下班",
            "start_offset": 2,
            "end_offset": 5,
            "type": "CN_WORD",
            "position": 3
        },
        {
            "token": "上下",
            "start_offset": 2,
            "end_offset": 4,
            "type": "CN_WORD",
            "position": 4
        },
        {
            "token": "下班",
            "start_offset": 3,
            "end_offset": 5,
            "type": "CN_WORD",
            "position": 5
        },
        {
            "token": "班车",
            "start_offset": 4,
            "end_offset": 6,
            "type": "CN_WORD",
            "position": 6
        },
        {
            "token": "车流量",
            "start_offset": 5,
            "end_offset": 8,
            "type": "CN_WORD",
            "position": 7
        },
        {
            "token": "车流",
            "start_offset": 5,
            "end_offset": 7,
            "type": "CN_WORD",
            "position": 8
        },
        {
            "token": "流量",
            "start_offset": 6,
            "end_offset": 8,
            "type": "CN_WORD",
            "position": 9
        },
        {
            "token": "很大",
            "start_offset": 8,
            "end_offset": 10,
            "type": "CN_WORD",
            "position": 10
        },
        {
            "token": "很",
            "start_offset": 10,
            "end_offset": 11,
            "type": "CN_CHAR",
            "position": 11
        },
        {
            "token": "拥堵",
            "start_offset": 11,
            "end_offset": 13,
            "type": "CN_WORD",
            "position": 12
        }
    ]
}
```

#### 11.3.4 映射mapping

有了索引库，等于有了数据库中的 database。

接下来就需要建索引库(index)中的映射了，类似于数据库(database)中的表结构(table)。

创建数据库表需要设置==字段名称，类型，长度，约束==等；索引库也一样，需要知道这个==类型下有哪些字段，每个字段有哪些约束信息，这就叫做映射(mapping)。==

**创建映射**

如下：

在 Postman 中，向 ES 服务器发 **PUT** 请求 ：http://192.168.205.114:9200/student(索引名)/_mapping

```json
{
 "properties": {
 	"name":{
 		"type": "text",
 		"index": true
	 },
 	"sex":{
 		"type": "text",
 		"index": false
     },
 	"age":{
 		"type": "long",
 		"index": false
		 }
 	} 
}
```

映射数据说明：

- ==字段名：任意填写==，下面指定许多属性，例如：title、subtitle、images、price

- ==type：类型==，Elasticsearch 中支持的数据类型非常丰富，说几个关键的： 
  - String 类型，又分两种：
    - ==text：可分词==
    - ==keyword：不可分词，数据会作为完整字段进行匹配==
  - Numerical：数值类型，分两类
    - 基本数据类型：long、integer、short、byte、double、float、half_float
    - 浮点数的高精度类型：scaled_float
  -  Date：日期类型
  - Array：数组类型
  - Object：对象
-  ==index：是否索引，默认为 true==，也就是说你不进行任何配置，所有字段都会被索引。
  - true：字段会被索引，则可以用来进行搜索
  - false：字段不会被索引，不能用来搜索
- store：==是否将数据进行独立存储，默认为 false==
- ==analyzer：分词器，这里的 ik_max_word 即使用 ik 分词器==

### 11.4  Elasticsearch与Springboot整合

#### 11.4.1 依赖

```yml
spring:
   data:
     elasticsearch:
       cluster-name: imood-elasticsearch
       cluster-nodes: 192.168.205.114:9300
   elasticsearch:
     rest:
       uris:
         - 192.168.205.114:9200
```

```xml
    <!-- 引入 elasticsearch 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>
```

#### 11.4.2 实体类

构建索引：

```java
@Document(indexName = "stu",type="_doc")
public class Stu {

    @Id
    private Long stuId;


    @Field
    private String name;

    @Field
    private Integer age;

    @Field
    private float money;

    @Field
    private String desc;

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public String toString() {
        return "Stu{" +
                "stuId=" + stuId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", money=" + money +
                ", desc='" + desc + '\'' +
                '}';
    }
}

```

#### 11.4.3 创建mapping

为索引创建mapping:

```json
POST        /stu/_doc/_mapping
{
    "properties": {
        "stuId": {
            "type": "long"
        },
        "name": {
            "type": "text",
            "analyzer": "ik_max_word"
        },
        "age": {
            "type": "integer"
        },
        "money": {
            "type": "float"
        },
        "desc": {
            "type": "text",
            "analyzer": "ik_max_word"
        }
    }
}
```

#### 11.4.4 测试

```java
package com.dexlace.search.controller;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.search.pojo.Stu;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/13
 */
@RestController
public class HelloController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @GetMapping("createIndex")
    public Object createIndex(){


        elasticsearchTemplate.createIndex(Stu.class);
        return GraceIMOODJSONResult.ok();
    }

    @GetMapping("deleteIndex")
    public Object deleteIndex(){


        elasticsearchTemplate.deleteIndex(Stu.class);
        return GraceIMOODJSONResult.ok();
    }


    @GetMapping("addDoc")
    public Object addDoc() {
        Stu stu = new Stu();
        stu.setStuId(1001L);
        stu.setName("imood");
        stu.setAge(21);
        stu.setDesc("这世界很荒唐");
        stu.setMoney(100.2f);

        IndexQuery iq = new IndexQueryBuilder().withObject(stu).build();
        elasticsearchTemplate.index(iq);
        return GraceIMOODJSONResult.ok();
    }


    @GetMapping("updateDoc")
    public Object updateDoc() {

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("desc", "hello world");
        updateMap.put("age", 12);

        IndexRequest ir = new IndexRequest();
        ir.source(updateMap);

        UpdateQuery uq = new UpdateQueryBuilder()
                .withClass(Stu.class)
                .withId("1001")
                .withIndexRequest(ir)
                .build();

        elasticsearchTemplate.update(uq);
        return GraceIMOODJSONResult.ok();
    }

    @GetMapping("getDoc")
    public Object getDoc(String id) {
        GetQuery gq = new GetQuery();
        gq.setId(id);
        Stu stu = elasticsearchTemplate.queryForObject(gq, Stu.class);
        return GraceIMOODJSONResult.ok(stu);
    }

    @GetMapping("detDoc")
    public Object detDoc(String id) {
        elasticsearchTemplate.delete(Stu.class, id);
        return GraceIMOODJSONResult.ok();
    }
}

```

### 11.5 Elasticsearch实战：梳理文章索引

依赖不再提

#### 11.5.1 创建索引，添加mapping

<img src="Judy-news.assets/image-20210621225857834.png" alt="image-20210621225857834" style="zoom: 50%;" />

添加映射，使用ik中文分词

```json
POST     /articles/_doc/_mapping
{
    "properties": {
        "id": {
            "type": "text"
        },
        "title": {
            "type": "text",
            "analyzer": "ik_max_word"
        },
        "categoryId": {
            "type": "integer"
        },
        "articleType": {
            "type": "integer"
        },
        "articleCover": {
            "type": "keyword"
        },
        "publishUserId": {
            "type": "text"
        },
        "publishTime": {
            "type": "date"
        }
    }
}

```

#### 11.5.2 创建文档对象articleEO

```java
@Document(indexName = "articles", type = "_doc")
public class ArticleEO {
    @Id
    private String id;
    @Field
    private String title;
    @Field
    private Integer categoryId;
    @Field
    private Integer articleType;
    @Field
    private String articleCover;
    @Field
    private String publishUserId;
    @Field
    private Date publishTime;
}
```

#### 11.5.3 文章审核通过后创建ES文档

`ArticleServiceImpl.updateArticleStatus()`

```java
// 如果审核通过，则查询article，存入es中
if (pendingStatus == ArticleReviewStatus.SUCCESS.type) {
    Article result = articleMapper.selectByPrimaryKey(articleId);
    // 如果是即时发布的文章，审核通过后则可以直接存入es中
    if(result.getIsAppoint() == ArticleAppointType.IMMEDIATELY.type) {
        ArticleEO articleEO = new ArticleEO();
        articleEO.setId(articleId);
        articleEO.setTitle(result.getTitle());
        articleEO.setCategoryId(result.getCategoryId());
        articleEO.setArticleType(result.getArticleType());
        articleEO.setArticleCover(result.getArticleCover());
        articleEO.setPublishTime(result.getPublishTime());
        IndexQuery iq = new IndexQueryBuilder().withObject(articleEO).build();
        esTemplate.index(iq);
    }

}

```

#### 11.5.4 文章撤回/删除删除ES文档

```java
@Transactional
@Override
public void deleteArticle(String userId, String articleId) {
    ......
    esTemplate.delete(ArticleEO.class, articleId);
}

```

```java
@Transactional
@Override
public void withdrawArticle(String userId, String articleId) {
    ......
    esTemplate.delete(ArticleEO.class, articleId);
}

```

#### 11.5.5 Http操作之文档查询操作

##### 1. 查询所有文档match_all

```json
# 查询的url get
http://192.168.205.114:9200/student(索引名)/search
# 查询的body
{
  "query": {
 	 "match_all": {}
   } 
}
#查询结果
{
 "took【查询花费时间，单位毫秒】" : 1116,
 "timed_out【是否超时】" : false,
 "_shards【分片信息】" : {
	 "total【总数】" : 1,
	 "successful【成功】" : 1,
	 "skipped【忽略】" : 0,
	 "failed【失败】" : 0
	 },
 "hits【搜索命中结果】" : {
 	"total"【搜索条件匹配的文档总数】: {
 	"value"【总命中计数的值】: 3,
	"relation"【计数规则】: "eq" # eq 表示计数准确， gte 表示计数不准确
 	},
 "max_score【匹配度分值】" : 1.0,
 "hits【命中结果集合】" : [
 # 匹配的结果
 	]
 } 
}
```

##### 2. 匹配查询match

match 匹配类型查询，==会把查询条件进行分词==，然后进行查询，==多个词条之间是 or 的关系==

```json
# 查询的url  get
http://192.168.205.114:9200/student(索引名)/search
# 查询的body
{
  "query": {
 	 "match": {
        "字段名":"字段值" 
     }
   } 
}
```

##### 3. 字段匹配查询

multi_match 与 match 类似，不同的是它可以在多个字段中查询。

##### 4. 关键字精准查询

term 查询，精确的关键词匹配查询，不对查询条件进行分词。

在 Postman 中，向 ES 服务器发 GET 请求 ：http://192.168.205.114:9200/student/_search

```json
{
 "query": {
 	"term": {
		 "name": {
			 "value": "zhangsan"
 				}
 			}
		 }
}
```

##### 5. 多关键字精准查询

terms 查询和 term 查询一样，但==它允许你指定多值进行匹配==。

如果这个字段==包含了指定值中的任何一个值==，那么这个文档满足条件，==类似于 mysql 的 in==

在 Postman 中，向 ES 服务器发 GET 请求 ：http://192.168.205.114:9200/student/_search

```json
{
 "query": {
 	"terms": {
 		"name": ["zhangsan","lisi"]
			 }
 		} 
}
```

### 11.6 Elasticsearch实战：文章检索

#### 11.6.1 实现match_all

```java
@GetMapping("es/list")
@ApiOperation(value = "通过elasticsearch来进行首页查询文章列表", notes = "首页查询文章列表", httpMethod = "GET")
public GraceJSONResult eslist(@RequestParam String keyword,
                            @RequestParam Integer category,
                            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                            @RequestParam Integer page,
                            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                            @RequestParam Integer pageSize);


```

```java
@Autowired
private ElasticsearchTemplate esTemplate;

@Override
public GraceJSONResult eslist(String keyword,
                              Integer category,
                              Integer page,
                              Integer pageSize) {

    /**
     * es查询：
     *      1. 首页默认查询，不带参数
     *      2. 按照文章分类查询
     *      3. 按照关键字查询
     */

    // es的页面是从0开始计算的，所以在这里page需要-1
    if (page<1) return null;
    page--;
    Pageable pageable = PageRequest.of(page, pageSize);

    SearchQuery query = null;
    // 符合第1种情况  match_all
    if (StringUtils.isBlank(keyword) && category == null) {
        query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())  
                .withPageable(pageable)
                .build();
    }
    
     // 符合第2种情况
    if (StringUtils.isBlank(keyword) && category != null) {
    }

    // 符合第3种情况
    if (StringUtils.isNotBlank(keyword) && category == null) {
    }

    
     AggregatedPage<ArticleEO> pagedArticle = esTemplate.queryForPage(query, ArticleEO.class);
    List<ArticleEO> articleList = pagedArticle.getContent();
    for (ArticleEO a : articleList) {
        System.out.println(a);
    }

    return GraceJSONResult.ok(articleList);
}

```

#### 11.6.2 实现term和match

```java
   SearchQuery query = null;
   
    // 符合第2种情况  term
    if (StringUtils.isBlank(keyword) && category != null) {
        query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("categoryId", category))
                .withPageable(pageable)
                .build();
    }

    // 符合第3种情况  match
    if (StringUtils.isNotBlank(keyword) && category == null) {
        query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", keyword))
                .withPageable(pageable)
                .build();
    }
```

#### 11.6.3 关键字高亮

```java
  @Override
    public GraceIMOODJSONResult eslist(String keyword,
                                  Integer category,
                                  Integer page,
                                  Integer pageSize) {

        /**
         * es查询：
         *      1. 首页默认查询，不带参数
         *      2. 按照文章分类查询
         *      3. 按照关键字查询
         */

        // es的页面是从0开始计算的，所以在这里page需要-1
        if (page<1) return null;
        page--;
        Pageable pageable = PageRequest.of(page, pageSize);

        SearchQuery query = null;
        AggregatedPage<ArticleEO> pagedArticle =null;
        // 符合第1种情况
        if (StringUtils.isBlank(keyword) && category == null) {
            // query对象
            query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchAllQuery())
                    .withPageable(pageable)
                    .build();
            // 结果
            pagedArticle = esTemplate.queryForPage(query, ArticleEO.class);
        }

        // 符合第2种情况
        if (StringUtils.isBlank(keyword) && category != null) {
            // query对象
            query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.termQuery("categoryId", category))
                    .withPageable(pageable)
                    .build();
            // 结果
            pagedArticle = esTemplate.queryForPage(query, ArticleEO.class);
        }

//        // 符合第3种情况
//        if (StringUtils.isNotBlank(keyword) && category == null) {
//            query = new NativeSearchQueryBuilder()
//                    .withQuery(QueryBuilders.matchQuery("title", keyword))
//                    .withPageable(pageable)
//                    .build();
//        }


        
        if (StringUtils.isNotBlank(keyword) && category == null) {
            query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchQuery("title", keyword))
                    .withPageable(pageable)
                    .build();
            pagedArticle = esTemplate.queryForPage(query, ArticleEO.class);
        }

        // 符合第3种情况 高亮
        String searchTitleFiled = "title";
        if (StringUtils.isNotBlank(keyword) && category == null) {
            String preTag = "<font color='red'>";
            String postTag = "</font>";
            query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchQuery(searchTitleFiled, keyword))  // 查找的关键字字段，及对应的关键字
                    .withHighlightFields(new HighlightBuilder.Field(searchTitleFiled)
                            .preTags(preTag)
                            .postTags(postTag))
                    .withPageable(pageable)
                    .build();


            // 最终从es中获取的文章
            pagedArticle = esTemplate.queryForPage(query, ArticleEO.class, new SearchResultMapper() {
                // 获得封装的结果集
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                    List<ArticleEO> articleHighLightList = new ArrayList<>();
                    // 获取命中的结果集
                    SearchHits hits = searchResponse.getHits();
                    for (SearchHit h : hits) {
                        HighlightField highlightField = h.getHighlightFields().get(searchTitleFiled);
                        String title = highlightField.getFragments()[0].toString();

                        // 获得所有数据，并重新封装
                        String articleId = (String)h.getSourceAsMap().get("id");
                        Integer categoryId = (Integer)h.getSourceAsMap().get("categoryId");
                        Integer articleType = (Integer)h.getSourceAsMap().get("articleType");
                        String articleCover = (String)h.getSourceAsMap().get("articleCover");
                        String publishUserId = (String)h.getSourceAsMap().get("publishUserId");
                        Long dateLong = (Long)h.getSourceAsMap().get("publishTime");
                        Date publishTime = new Date(dateLong);

                        ArticleEO articleEO = new ArticleEO();
                        articleEO.setId(articleId);
                        articleEO.setTitle(title);
                        articleEO.setCategoryId(categoryId);
                        articleEO.setArticleType(articleType);
                        articleEO.setArticleCover(articleCover);
                        articleEO.setPublishUserId(publishUserId);
                        articleEO.setPublishTime(publishTime);

                        articleHighLightList.add(articleEO);
                    }

                    return new AggregatedPageImpl<>((List<T>)articleHighLightList,
                            pageable,
                            searchResponse.getHits().totalHits);
                }

                @Override
                public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                    return null;
                }
            });


        }

        // 重组文章列表
        List<ArticleEO> articleEOList = pagedArticle.getContent();
        List<Article> articleList = new ArrayList<>();
        for (ArticleEO a : articleEOList) {
            Article article = new Article();
            System.out.println(a.getPublishUserId());
            BeanUtils.copyProperties(a, article);
            articleList.add(article);
        }

        // 重新封装成之前的grid格式
        PagedGridResult gridResult = new PagedGridResult();
        gridResult.setRows(articleList);
        gridResult.setPage(page + 1);
        gridResult.setTotal(pagedArticle.getTotalPages());
        gridResult.setRecords(pagedArticle.getTotalElements());

        gridResult = rebuildArticleGrid(gridResult);


        return GraceIMOODJSONResult.ok(gridResult);
    }
```

#### 11.6.4 整合 es到前端

修改前端页面 index.html：
修改请求方法 `getAllArticleList`中的url

```java
// var queryUrl = articleServerUrl + "/portal/article/list?keyword=" + keyword + 
// 											"&category=" + category + 
// 											"&page=" + page + 
// 											"&pageSize=" + pageSize;
var queryUrl = articleServerUrl + "/portal/article/es/list?keyword=" + keyword + 
											"&category=" + category + 
											"&page=" + page + 
											"&pageSize=" + pageSize;
axios.get(queryUrl)

```

修改html，目的为了高亮：

```html
	<!-- TODO: 后期改为静态页面跳转 -->
								<!--<a :href="'detail.html?articleId='+article.id" target="_blank" class="link-article-title">{{article.title}}</a>-->
								<!--<a :href="'detail.html?articleId='+article.id" target="_blank" class="link-article-title" v-html="article.title"></a> -->
							

<a :href="'./static/'+article.id+'.html'" target="_blank" class="link-article-title" v-html="article.title" ></a>
```

### 11.7 Elasticsearch集群

#### 11.7.1 集群节点

ELasticsearch的集群是由多个节点组成的，通过==cluster.name设置集群名称==，并且用于区分其它的集群，==每个节点通过node.name(<font color=red>注意node.name代表了该节点所在的机器的hostname</font>)指定节点的名称==。

在Elasticsearch中，节点的类型主要有4种：

- ==master节点(<font color=red>操作索引</font>)==
  - 配置文件中==node.master属性为true(默认为true)==，就==有资格==被选为master节点。master节点用于控制整个集群的操作。比如创建或删除索引，管理其它非master节点等。
- ==data节点（<font color=red>操作文档</font>）==
  - 配置文件中node.data属性为true(默认为true)，就有资格被设置成data节点。data节点==主要用于执行数据相关的操作。比如文档的CRUD==。
- ==客户端节点（<font color=red>转发请求</font>）==
  - 配置文件中node.master属性和node.data属性均为false。
  - 该节点不能作为master节点，也不能作为data节点。
  - ==可以作为客户端节点，用于响应用户的请求，把请求转发到其他节点==
- ==部落节点（<font color=red>搜索</font>）==
  - 当一个节点配置tribe.*的时候，它是一个特殊的客户端，它可以连接多个集群，在所有连接的集群上执行
    ==搜索和其他操作。==

#### 11.7.2 集群配置

```yml


#node01的配置：
cluster.name: mycluster
node.name: node01
node.master: true
node.data: true
network.host: 0.0.0.0
http.port: 9200
discovery.zen.ping.unicast.hosts: ["192.168.205.123","192.168.205.124","192.168.205.125"]
# 最小节点数
discovery.zen.minimum_master_nodes: 2
# 跨域专用
http.cors.enabled: true
http.cors.allow-origin: "*"

#node02的配置：
cluster.name: mycluster
node.name: node02
node.master: true
node.data: true
network.host: 0.0.0.0
http.port: 9200
discovery.zen.ping.unicast.hosts: ["192.168.205.123","192.168.205.124","192.168.205.125"]
discovery.zen.minimum_master_nodes: 2
http.cors.enabled: true
http.cors.allow-origin: "*"

#node03的配置：
cluster.name: mycluster
node.name: node03
node.master: true
node.data: true
network.host: 0.0.0.0
http.port: 9200
discovery.zen.ping.unicast.hosts: ["192.168.205.123","192.168.205.124","192.168.205.125"]
discovery.zen.minimum_master_nodes: 2
http.cors.enabled: true
http.cors.allow-origin: "*"
```

#### 11.7.3 分片和副本

**<font color=red>master节点只是起一个管理作用而已，和分片是不是主分片没有关系，一个节点可以有很多分片</font>**

为了将数据添加到Elasticsearch，我们需要索引(index)——一个存储关联数据的地方。实际上，索引只是一个用来指向一个或多个分片(shards)的“==逻辑命名空间(logical namespace)==”.

- ==一个分片(shard)==是一个最小级别“工作单元(worker unit)”,它只是保存了索引中所有==数据的一部分==。
- 我们需要知道是分片就是一个Lucene实例，并且它==本身就是一个完整的搜索引擎==。应用程序不会和它直接通
  信。
- 分片可以是==主分片(primary shard)==或者是==复制分片(replica shard)==。
- 索引中的每个文档属于一个单独的主分片，所以主分片的数量决定了索引最多能存储多少数据。
- ==复制分片只是主分片的一个副本==，它可以防止硬件故障导致的数据丢失，同时可以提供读请求，比如搜索或者从别的shard取回文档。
- 当索引创建完成的时候，主分片的数量就固定了，但是复制分片的数量可以随时调整。
- ==<font color=red>节点数和分片数没有必然联系</font>==，如果要说的话，==<font color=red>副本分片数最大为节点数-1</font>==

![image-20210628104724653](Judy-news.assets/image-20210628104724653.png)

以上创建了==5个分片，1个副本==，意思是上面的索引数据有==五个分片==，并且每个分片==只有一个副本==，==分布在3个节点上==，但每个节点==不能同时存主分片和对应的副本==

==主节点会显示在第一列==

集群中有三种颜色

| 颜色   | 意义                                 |
| ------ | ------------------------------------ |
| green  | 所有主分片和复制分片都可用           |
| yellow | 所有主分片可用，但不是复制分片都可用 |
| red    | 不是所有主要分片都可用               |

#### 11.7.4 故障说明

- ==停止data节点，下面为node-1节点==

![image-20210628110102376](Judy-news.assets/image-20210628110102376.png)

当前集群状态为黄色，表示主节点可用，副本节点不完全可用，==过一段时间==观察，发现节点列表中看不到node-1，==集群状态恢复到绿色。==

![image-20210628110159526](Judy-news.assets/image-20210628110159526.png)

- ==data节点node-1重新加入，仍然是副分片==

![image-20210628110318653](Judy-news.assets/image-20210628110318653.png)

- ==master节点node-2下线，显示yellow状态，重新选举，从黄变绿==

![image-20210628110624702](Judy-news.assets/image-20210628110624702.png)

![image-20210628110722609](Judy-news.assets/image-20210628110722609.png)

- ==master节点node-2归来，只能当小弟==

![image-20210628110852450](Judy-news.assets/image-20210628110852450.png)

特别说明：

如果在配置文件中`discovery.zen.minimum_master_nodes`设置的不是==N/2+1时==，==会出现脑裂问题==，==之前宕机
的主节点恢复后不会加入到集群。==

==即会出现双主==

#### 11.7.5 文档路由、读写原理

- <font color=red>一、路由原理</font>

```bash
shard = hash(routing) % number of_primary_shards
```

其中：

- 
  - ==routing值是一个任意字符串，它默认是_id但也可以自定义。==
  - 这个routing字符串通过哈希函数生成一个数字，然后除以主切片的数量得到一个余数(remainder)，余数
    的范围永远是0到number_of_primary_shards - 1，这个数字就是特定文档所在的分片

这就是为什么创建了主分片后，不能修改的原因。

- <font color=red>二、文档写操作</font>

新建、索引和删除请求都是写（write）操作，它们必须在==主分片上成功完成才能复制分片上==

**==<font color=red>再次注意，master节点和上面的分片是不是主分片没有任何关系</font>==**

master节点**只是管理作用**

请求到master，master确认==文档所属的主分片==，并==找到对应的节点==

该节点执行请求，成功则转发请求到==该主分片对应的复制分片所属的节点上==，所有复制分片执行成功，则报告给客户端

- <font color=red>三、检索文档</font>

有负载均衡机制，并不是一定要到主分片上查询

### 11.8 Java客户端

Elasticsearch提供了2种REST客户端，一种是==低级客户端==，一种是==高级客户端==。

- Java Low Level REST Client：官方提供的低级客户端。该客户端通过http来连接Elasticsearch集群。用户在使
  用该客户端时需要==将请求数据手动拼接成Elasticsearch所需JSON格式==进行发送，收到响应时同样也需要将返回的JSON数据手动封装成对象。虽然麻烦，不过该客户端兼容所有的Elasticsearch版本。
- Java High Level REST Client：官方提供的高级客户端。该客户端基于低级客户端实现，它提供了很多便捷的
  API来解决低级客户端需要手动转换数据格式的问题。

见全栈相关

### 11.9 Filebeat

Filebeat将为你提供一种轻量型方法，用于==转发和汇总日志与文件==，让简单的事情不再繁华，关于Filebeat的记住以下两点：

- ==轻量级日志采集器==
- ==输送至ElasticSearch或者Logstash，在Kibana中实现可视化==

#### 11.9.1 架构图

<img src="Judy-news.assets/image-20210628144651665.png" alt="image-20210628144651665" style="zoom:50%;" />

流程如下：

- 首先是input输入，我们可以指定多个数据输入源，然后==通过通配符进行日志文件的匹配==
- 匹配到日志后，就会使用==Harvester（收割机）==，将日志源源不断的读取到来
- 然后收割机收割到的日志，就传递到==Spooler（卷轴）==，然后卷轴就在将他们传到对应的地方

#### 11.9.2 使用示例

官网地址：https://www.elastic.co/cn/downloads/beats/filebeat

这里使用版本6.8.6

- ==<font color=red>使用示例一：</font>==

在解压目录下touch一个yml文件：myconfig1.yml

```yml
filebeat.inputs: # filebeat input输入
- type: stdin    # 标准输入
  enabled: true  # 启用标准输入
setup.template.settings: 
  index.number_of_shards: 3 # 指定下载数
output.console:  # 控制台输出
  pretty: true   # 启用美化功能
  enable: true
```

并启动：

```bash
./filebeat -e -c myconfig1.yml
```

然后我们在控制台输入hello world，就能看到我们会有一个json的输出，是通过读取到我们控制台的内容后输出的

<img src="Judy-news.assets/image-20210628193713054.png" alt="image-20210628193713054" style="zoom:67%;" />

- ==<font color=red>使用示例二</font>==

touch一个yml文件：myconfig2.yml

```yml
filebeat.inputs:
- type: log   # 输入filebeat的类型，
              # 包括log(具体路径的日志),
              # stdin(键盘输入),redis,udp,docker,tcp,syslog,可以同时配置多个(包括相同类型的)
  enabled: true
  paths:
    - /home/doglast/software/*.log
setup.template.settings:
  index.number_of_shards: 3
output.console:
  pretty: true
  enable: true
```

```bash
cd /home/doglast/software/a.log
echo "hello" >>a.log
```

并启动：

```bash
./filebeat -e -c myconfig2.yml
```

能够发现成功加载a.log,也会有类似的元数据信息

- ==<font color=red>使用实例三:输出到elasticsearch</font>==

```yml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /home/doglast/software/*.log
  tags: ["web", "test"]
  fields:
    from: test-web   #  这个的意思就是会在es中多添加一个字段
  fields_under_root: false 
setup.template.settings:
  index.number_of_shards: 1
output.elasticsearch:
  hosts: ["192.168.205.125:9200"]
```

![image-20210628205314498](Judy-news.assets/image-20210628205314498.png)

#### 11.9.3 filebeat的module：以读取nginx日志为例

```yml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /usr/local/nginx/logs/*.log
  tags: ["nginx"]
  fields_under_root: false 
setup.template.settings:
  index.number_of_shards: 1
output.elasticsearch:
  hosts: ["127.0.0.1:9200"]
```

启动后可以去elasticsearch中查看到nginx的日志

前面要想实现日志数据的读取以及处理都是自己手动配置的，其实，在Filebeat中，有大量的Module，可以简化我们的配置，直接就可以使用，如下：

```bash
./filebeat modules list
```

得到的列表如下所示

```bash
Disabled:
activemq
apache
auditd
aws
azure
barracuda
bluecoat
cef
checkpoint
cisco
coredns
crowdstrike
cylance
elasticsearch
envoyproxy
f5
fortinet
googlecloud
gsuite
haproxy
ibmmq
icinga
iis
imperva
infoblox
iptables
juniper
kafka
kibana
logstash
microsoft
misp
mongodb
mssql
mysql
nats
netflow
netscout
nginx
o365
okta
osquery
panw
postgresql
rabbitmq
radware
redis
santa
sonicwall
sophos
squid
suricata
system
tomcat
traefik
zeek
zscaler
```

可以看到，内置了很多的module，但是都没有启用，如果需要启用需要进行enable操作：

```bash
#启动
./filebeat modules enable nginx 
#禁用
./filebeat modules disable nginx 
```

如上启用nginx，==则到modules.d下可以看到nginx.yml==,而没有启用的为为.disabled结尾

配置nginx.yml

```yml

- module: nginx
  # Access logs
  access:
    enabled: true
    # 添加日志文件
    var.paths: ["/usr/local/nginx/access.log*"]

  

  # Error logs
  error:
    enabled: true
    var.paths: ["/usr/local/nginx/error.log*"]
```

继续配置一个filebeat的启动文件

```yml
setup.template.settings:
  index.number_of_shards: 1
output.elasticsearch:
  hosts: ["127.0.0.1:9200"]
filebeat.config.modules:
  path: ${path.config}/modules.d/*.yml
  reload.enabled: false
```

```bash
./filebeat -e -c mynginx.yml
```

### 11.10 Metricbeat

轻量型的==指标采集器==

<img src="Judy-news.assets/image-20210629102430014.png" alt="image-20210629102430014" style="zoom: 67%;" />

- 定期收集操作系统或应用服务的指标数据
- 存储到Elasticsearch中，进行实时分析

Metricbeat有2部分组成，一部分是Module，另一个部分为Metricset

- Module
  - 收集的对象：如 MySQL、Redis、Nginx、操作系统等
- Metricset
  - 收集指标的集合：如 cpu、memory，network等

<img src="Judy-news.assets/image-20210629103147539.png" alt="image-20210629103147539" style="zoom:67%;" />

下载：https://www.elastic.co/cn/downloads/beats/metricbeat

解压，编辑metricbeat.yml文件

```yml
metricbeat.config.modules:
  path: ${path.config}/modules.d/*.yml
  reload.enabled: false
setup.template.settings:
  index.number_of_shards: 1
  index.codec: best_compression
setup.kibana:
output.elasticsearch:
  hosts: [""127.0.0.1:9200"]
processors:
  - add_host_metadata: ~
  - add_cloud_metadata: ~
```

默认监控的是

```bash
${path.config}/modules.d/*.yml
```

即modules.d下的yml文件对应的模块，默认system是打开的，和filebeat很类似，有很多模块可以用来监控

以上默认监听system模块的指标数据

启动：

```bash
./metricbeat -e
```



==这里还是以nginx模块为例，监控nginx模块的指标数据==

- ==开启Nginx Module模块==

```bash
#重新编译nginx
./configure --prefix=/usr/local/nginx --with-http_stub_status_module
make
make install

./nginx -V #查询版本信息
nginx version: nginx/1.11.6
built by gcc 4.4.7 20120313 (Red Hat 4.4.7-23) (GCC)
configure arguments: --prefix=/usr/local/nginx --with-http_stub_status_module

#配置nginx
vim nginx.conf
location /nginx-status {
    stub_status on;
    access_log off;
}

# 重启nginx
./nginx -s reload
```

测试

<img src="Judy-news.assets/image-20210629180819075.png" alt="image-20210629180819075" style="zoom:67%;" />

结果说明：

Active connections：正在处理的活动连接数

server accepts handled requests

- 第一个 server 表示Nginx启动到现在共处理了203个连接
- 第二个 accepts 表示Nginx启动到现在共成功创建 203次握手
- 第三个 handled requests 表示总共处理了 207次请求
- 请求丢失数 = 握手数 - 连接数 ，可以看出目前为止没有丢失请求

- Reading: 0 Writing: 1 Waiting: 0
  - Reading：Nginx 读取到客户端的 Header 信息数
  - Writing：Nginx 返回给客户端 Header 信息数
  - Waiting：Nginx 已经处理完正在等候下一次请求指令的驻留链接（开启keep-alive的情况下，这个值等于
    Active - (Reading+Writing)）

==配置nginx module==

```bash

./metricbeat modules enable nginx

#修改nginx module配置
vim modules.d/nginx.yml
```

然后修改下面的信息

```yml
# Module: nginx
# Docs: https://www.elastic.co/guide/en/beats/metricbeat/6.5/metricbeat-modulenginx.
html
  - module: nginx
#metricsets:
# - stubstatus
  period: 10s
# Nginx hosts
  hosts: ["http://127.0.0.1"]
# Path to server status. Default server-status
  server_status_path: "nginx-status"
#username: "user"
#password: "secret"
```

==修改完成后，启动nginx==

```bash
#启动
./metricbeat -e
```

去es中可以看到数据

更多的Module使用参见官方文档：

https://www.elastic.co/guide/en/beats/metricbeat/current/metricbeat-modules.html

### 11.11 Kibana

Kibana 是一款开源的数据分析和可视化平台，它是 Elastic Stack 成员之一，设计用于和 Elasticsearch 协作。您可以使用 Kibana 对 Elasticsearch 索引中的数据进行搜索、查看、交互操作。您可以很方便的利用图表、表格及地图对数据进行多元化的分析和呈现。

#### 11.11.1 配置和安装

到下载地址，选择对应的版本：https://www.elastic.co/cn/downloads/kibana

下载解压

找到配置文件kibana.yml,编辑一下

```yml
#对外暴露服务的地址
server.host: "0.0.0.0" 

#配置Elasticsearch
elasticsearch.url: "http://127.0.0.1:9200" 
```

注意启动不能以root用户启动

```bash
./bin/kibana
```

然后打开下面的地址，即可访问我们的kibana了

```bash
http://192.168.205.123:5601/
```

- Discover：数据探索
- Visualize：可视化
- Dashboard：仪表盘
- Timelion：时序控件
- Canvas：画布
- Machine Learning：机器学习
- Infrastructure：基本信息
- Logs：数据日志展示
- APM：性能监控
- Dev Tools：开发者工具
- Monitoring：监控
- Management：管理

以上是kibana的菜单栏的所有功能

#### 11.1.2 数据探索

就是去选择==监听的日志（不论是filebeat或者metricbeat产生的指标性日志）所对应的索引==，可以以正则表达式去检索

然后自己去摸索。比如显示哪些字段之类的，比较容易

#### 11.1.3 Metricbeat指标仪表盘

修改metricbeat的配置

```yml
#修改metricbeat配置
setup.kibana:
  host: "192.168.205.123:5601"
```

```bash
#安装仪表盘到Kibana【需要确保Kibana在正常运行，这个过程可能会有些耗时】
./metricbeat setup --dashboards
```

然后启动metricbeat

```bash
./metricbeat -e
```

至于怎么看，自己摸索

#### 11.1.4 Nginx日志仪表盘

修改filebeat中关于nginx module启动文件配置

```yml
filebeat.inputs:
setup.template.settings:
  index.number_of_shards: 1
output.elasticsearch:
  hosts: ["127.0.0.1:9200"]
filebeat.config.modules:
  path: ${path.config}/modules.d/*.yml
  reload.enabled: false
setup.kibana:
  host: "127.0.0.1:5601"
```

安装仪表盘

```bash
./filebeat -c readnginx.yml setup
```

等待安装成功后，启动filebeat

```bash
./filebeat -e -c readnginx.yml
```

即可看到仪表盘了

### 11.12 Logstash

#### 11.12.1 简介与安装

Logstash是一个开源的服务器端数据处理管道，能够同时从多个来源采集数据，==转换数据==，然后将数据发送到最喜欢的存储库中（我们的存储库当然是ElasticSearch）

==相比于filebeat，logstash能够预处理数据，但是logstash慢，消耗资源大==

<img src="Judy-news.assets/image-20210629210339015.png" alt="image-20210629210339015" style="zoom:50%;" />

Logstash可以处理==各种各样的输入，文档，图表，数据库==

Logstash主要是将==数据源的数据进行一行一行的处理==，同时还直接==过滤切割等功能==。

<img src="Judy-news.assets/image-20210629210514134.png" alt="image-20210629210514134" style="zoom:67%;" />

首先到官网下载logstash：https://www.elastic.co/cn/downloads/logstash

解压

一个使用小case：

```bash
#第一个logstash示例
bin/logstash -e 'input { stdin { } } output { stdout {} }'
```

其实原来的logstash的作用，就是为了做数据的采集，但是==因为logstash的速度比较慢，所以后面使用beats来代替了Logstash==

我们在控制台输入 hello，马上就能看到它的输出信息

![image-20210629210807696](Judy-news.assets/image-20210629210807696.png)

#### 11.12.2 配置详解

Logstash的配置有三部分，如下所示

```bash
input { #输入
	stdin { ... } #标准输入
}

filter { #过滤，对数据进行分割、截取等处理

}

output { #输出	
	stdout { ... } #标准输出
}
```

##### 1.输入

- 采集各种样式、大小和来源的数据，数据往往以各种各样的形式，或分散或集中地存在于很多系统中。

- Logstash 支持各种输入选择 ，可以在同一时间从众多常用来源捕捉事件。能够以连续的流式传输方式，轻松地从您的日志、指标、Web 应用、数据存储以及各种 AWS 服务采集数据。

- 支持==stdin,file,http,tcp,beats,jdbc,log4和各种中间件等==

- ```json
  input { 
      stdin { } 
  } 
  ```

- ```json
  input {
      file {
  
          //    /var/log/*.log：匹配/var/log目录下以.log结尾的所有文件
  		//   /var/log/**/*.log：匹配/var/log所有子目录下以.log结尾的文件
  		//   /var/log/{app1,app2,app3}/*.log：
          path => ["文件路径"]
  
         
          start_position => "beginning"  // 可以配置为beginning/end，是否从头读取文件
          sincedb_path => "/dev/null"  // sincedb的独立文件中来跟踪记录每个文件中的当前位置。
                                      //这使得停止和重新启动Logstash成为可能    
                                       // 默认在home目录下
      }
  }
  ```

- ```json
  input {
  	beats {
  		port => "5044"
  	}
  }
  ```

- ```json
  input {
    http {
      port => 端口号
    }
  }
  ```

- ```json
  input {
    tcp {
      mode => "server"
      host => "0.0.0.0"
      port => 端口号
      codec => json_lines 
      // Codec Plugin作用于Input和Output Plugin，
      // 负责将数据在原始与Logstash Event之间转换，常见的codec有：
      // plain：读取原始内容
  //dots：将内容简化为点进行输出
  //rubydebug：将Logstash Events按照ruby格式输出，方便调试
  //line：处理带有换行符的内容
  //json：处理json格式的内容
  //multiline：处理多行数据的内容
    }
  }
  ```

  

##### 2. 过滤

- ==实时解析和转换数据==
- 数据从源传输到存储库的过程中，Logstash 过滤器能够解析各个事件，识别已命名的字段以构建结构，并将它们转换成通用格式，以便更轻松、更快速地分析和实现商业价值。

Filter是Logstash功能强大的主要原因，它可以对Logstash Event进行丰富的处理，==比如解析数据、删除字段、类型转换等等==，常见的有如下几个：

| 插件名称    | 说明                                   |
| ----------- | -------------------------------------- |
| date        | 日期解析                               |
| grok        | 正则匹配解析                           |
| ==dissect== | 分隔符解析                             |
| ==mutate==  | 对字段进行处理，比如重命名、删除、替换 |
| json        | 按照json解析字段内容到指定字段中       |
| geoip       | 增加地理位置数据                       |
| ruby        | 利用ruby代码来动态修改Logstash Event   |

mutate是使用最频繁的插件，可以对字段进行各种操作，比如重命名、删除、替换、更新等，主要操作如下：

- convert类型转换
- gsub字符串替换
- split、join、merge字符串切割、数组合并为字符串、数组合并为数组
- rename字段重命名
- update、replace字段内容更新或替换。它们都可以更新字段的内容，区别在于update只在字段存在时生效，
- replace在字段不存在时会执行新增字段的操作
- remove_field删除字段

```json
mutate {
    convert => {"age" => "integer"}
    gsub => [
        "birthday", "-", "/",
        "image", "\/", "_"
    ]
    split => {"hobities" => ","}
    rename => {"hobities" => "hobityList"}
    replace => {"icon" => "%{image}"}
    remove_field => ["message"]
}

```



##### 3. 输出

Logstash 提供众多输出选择，您可以将数据发送到您要指定的地方，并且能够灵活地解锁众多下游用例。

```json
output {
  elasticsearch {
    hosts => ["http://192.168.3.12:9200"]
    index => "logstash-%{+YYYY.MM.dd}"
    document_type => "_doc"
    user => "用户名"
    password => "密码"
  }
}
```

#### 11.12.3 读取自定义日志

##### 1. 输出到标准输出

```json
input {
    file {
        path => "/home/doglast/software/app.log"
        start_position => "beginning"
    }
}
filter {
    mutate {
    	split => {"message"=>"|"}
    }
}
output {
	stdout { codec => rubydebug }
}
```

启动

```bash
#启动
./bin/logstash -f ./myconfig.conf
```

然后我们就插入我们的测试数据

```bash
echo "2019-03-15 21:21:21|ERROR|读取数据出错|参数：id=1002" >> app.log
```

然后我们就可以看到==logstash就会捕获到刚刚我们插入的数据，同时我们的数据也被分割了==

<img src="Judy-news.assets/image-20210629214621772.png" alt="image-20210629214621772" style="zoom:80%;" />

##### 2. 输出到Elasticsearch

```json
input {
    file {
        path => "/home/doglast/software/app.log"
        start_position => "beginning"
    }
}
filter {
    mutate {
    	split => {"message"=>"|"}
    }
}
output {
	elasticsearch {
		hosts => ["127.0.0.1:9200"]
	}
}
```

启动

```bash
#启动
./bin/logstash -f ./myconfig2.conf
```

然后我们就插入我们的测试数据

```bash
echo "2019-03-15 21:21:21|ERROR|读取数据出错|参数：id=1002" >> app.log
```

![image-20210629215233953](Judy-news.assets/image-20210629215233953.png)

### 11.13 ELK综合案例

<img src="Judy-news.assets/image-20210630094427418.png" alt="image-20210630094427418" style="zoom:80%;" />

- 应用APP生产日志，用来记录用户的操作
  - [INFO] 2019-03-15 22:55:20 [Main] - DAU|5206|使用优惠券|2019-03-15 03:37:20
  - [INFO] 2019-03-15 22:55:21 [Main] - DAU|3880|浏览页面|2019-03-15 07:25:09
- 通过Filebeat读取日志文件中的内容，并且将内容发送给Logstash，原因是需要对内容做处理
- ==Logstash接收到内容后，进行处理，如分割操作==，然后将内容发送到Elasticsearch中
- ==Kibana会读取Elasticsearch中的数据，并且在Kibana中进行设计Dashboard==，最后进行展示

> 说明：日志格式、图表、Dashboard都是自定义的

#### 11.13.1 模拟生产环境日志生成程序

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.dexlace</groupId>
    <artifactId>elk-doglast</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.2.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.18</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>
        <dependency>
            <groupId>joda-time</groupId>
            <artifactId>joda-time</artifactId>
            <version>2.10.10</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

```java
package com.dexlace;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/29
 */
@Slf4j
@SpringBootApplication
public class ELKApplication {
    public static final String[] VISIT = new String[]{"浏览页面", "评论商品", "加入收藏", "加入购物车", "提交订单", "使用优惠券", "领取优惠券", "搜索", "查看订单"};

    public static void main(String[] args) throws Exception {
        while(true){
            Long sleep = RandomUtils.nextLong(200, 1000 * 5);
            Thread.sleep(sleep);
            Long maxUserId = 9999L;
            // 产生从1到9999的userId
            Long userId = RandomUtils.nextLong(1, maxUserId);
            // 表示随机访问
            String visit = VISIT[RandomUtils.nextInt(0, VISIT.length)];

            // 日期
            DateTime now = new DateTime();
            int maxHour = now.getHourOfDay();
            int maxMillis = now.getMinuteOfHour();
            int maxSeconds = now.getSecondOfMinute();
            String date = now.plusHours(-(RandomUtils.nextInt(0, maxHour)))
                    .plusMinutes(-(RandomUtils.nextInt(0, maxMillis)))
                    .plusSeconds(-(RandomUtils.nextInt(0, maxSeconds)))
                    .toString("yyyy-MM-dd HH:mm:ss");

            String result = "DAU|" + userId + "|" + visit + "|" + date;
            log.error(result);
        }
    }

}

```

```bash
10:04:08.549 [main] ERROR com.dexlace.ELKApplication - DAU|3834|加入购物车|2021-06-30 06:02:06
10:04:12.279 [main] ERROR com.dexlace.ELKApplication - DAU|3041|加入购物车|2021-06-30 06:01:11
10:04:14.879 [main] ERROR com.dexlace.ELKApplication - DAU|6537|评论商品|2021-06-30 05:04:02
10:04:17.090 [main] ERROR com.dexlace.ELKApplication - DAU|1149|提交订单|2021-06-30 07:01:03
10:04:17.773 [main] ERROR com.dexlace.ELKApplication - DAU|5196|加入购物车|2021-06-30 04:01:10
10:04:17.978 [main] ERROR com.dexlace.ELKApplication - DAU|6306|提交订单|2021-06-30 02:01:12
10:04:22.272 [main] ERROR com.dexlace.ELKApplication - DAU|5772|加入购物车|2021-06-30 05:03:07
```

我们将项目打包

```bash
mvn clean
mvn install
```

拷贝jar包，复制到服务器上，创建一个脚本,比如命名为start.sh

```bash
#!/bin/bash     
nohup java  -Xms256m -Xmx512m -jar  elk-0.0.1-SNAPSHOT.jar  > app.log 
```

启动后，程序的输出会到app.log上

#### 11.13.2 配置filebeat

```yml
# 写入数据
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /home/doglast/software/*.log
setup.template.settings:
  index.number_of_shards: 1
output.logstash:
  hosts: ["127.0.0.1:5044"]
```

然后我们就可以启动了==【需要我们把Logstash启动起来】==

```bash
./filebeat -e -c elk-beat.yml
```

#### 11.13.3 配置logstash

##### 1. 简单输出版

```json

input {
	beats {
		port => "5044"
	}
}
output {
	stdout { codec => rubydebug }
}
```

启动

```bash
./bin/logstash -f elk-stash-simple.conf
```

##### 2. 实用输出到es版

```json
input {
        beats {
                port => "5044"   // 表示输入用beats,端口号为5044
        }
}
filter {
        mutate {
                split => {"message"=>"|"}    
        }
        mutate {
                add_field => {
                "userId" => "%{[message][1]}"
                "visit" => "%{[message][2]}"
                "date" => "%{[message][3]}"
                }
        }
        mutate {
                convert => {
                "userId" => "integer"
                "visit" => "string"
                "date" => "string"
                }
        }
        mutate {
           remove_field => [ "host" ]
        }
}
#output {
# stdout { codec => rubydebug }
#}

output {
  elasticsearch {
    hosts => [ "127.0.0.1:9200"]
  }
}
```

启动

```bash
./bin/logstash -f elk-stash-es.conf
```

#### 11.13.4 配置kibana

就如11.11的配置就好，主要是配置elk的地址

通过浏览器，访问地址:5601即可，具体创建索引见kibana的部分