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
        // 注意这里的不是接口controller的路径所在的包  而是实现类的包  表示对该路径下的api进行监控
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


