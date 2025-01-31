package com.green.greengram.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@Component도 가능 하다. Component는 빈등록을 하는 모든 에노테이션의 부모 에노테이션
//Configuration: 메소드를 Bean등록하려면 Configuration을 쓰고 메소드 위에 @Bean 써야함
//메소드를 Bean등록하면 메소드가 반환하는 값을 Bean등록하고 반환값을 싱글톤으로 사용한다.
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final String uploadPath;
    //MyFileUtils에서 했던 것 처럼 생성자로 파일 경로 설정
    public WebMvcConfiguration(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { //이 메소드는 spring이 호출해줌
        registry.addResourceHandler("/pic/**").addResourceLocations("file:" + uploadPath+"/");
    }
    // 서버에 /pic/파일명.jpg 로 요청하면 /${file.directory}/파일명.jpg로 인식하도록 하겠다.
    // /pic은  /${file.directory}(=D:/ksj/download/greengram_ver1) 이다.

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //RestController 에노테이션을 붙인 클래스의 모든 URL에 "/api" prefix(경로)를 설정
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }
}



//Bean 등록을 하면 리턴하는 주소값을 spring이 갖고있음.
//Spring에서는 활용을 하는것이 목적이라 거의 싱글톤을 사용함. 그러나 저장이 필요할 때는 그때마다 객체를 새로 만들어야함.

