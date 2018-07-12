package com.anthonyzero.seed.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author pingjin create 2018年7月4日
 *
 */
@Configuration
@EnableSwagger2
@ConditionalOnExpression("'${swagger.enable}' == 'true'")
public class Swagger2 {
	
	private static List<Parameter> pars = new ArrayList<>();
	static {
		ParameterBuilder tokenPar = new ParameterBuilder();  
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();  
        pars.add(tokenPar.build());  
	}

	@Bean
    public Docket createApi() {
		
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.anthonyzero.seed.modules"))
                //设置扫描哪些controller，可以匹配正则，这里是全扫描
                .paths(PathSelectors.any())         
                .build()
                .globalOperationParameters(pars);
    }
	
	
	//构建 api文档的详细信息函数
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
        		//页面标题
                .title("API接口文档")
                .description("项目具体描述")
                //创建人
                .contact(new Contact("作者AnthonyZero", "https://github.com/AnthonyZero", ""))
                .version("1.0")
                .build();
    }
}
