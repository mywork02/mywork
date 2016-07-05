package com.suime.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * title
     */
    private String title = "suime api";
    /**
     * description
     */
    private String description = "suime api";
    /**
     * termsOfServiceUrl
     */
    private String termsOfServiceUrl = "开发者: suime developer";
    /**
     * contact
     */
    private String contact = "developer@sui.me";
    /**
     * license
     */
    private String license = "";
    /**
     * licenseUrl
     */
    private String licenseUrl = "";

    @Bean
    public Docket userApi() {
//        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.regex("/api/.*")).build().apiInfo(getApiInfo());//.pathMapping("/rest");
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().contact(contact).title(title).termsOfServiceUrl(termsOfServiceUrl)
                .license(license).licenseUrl(licenseUrl).description(description).build();
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

}
