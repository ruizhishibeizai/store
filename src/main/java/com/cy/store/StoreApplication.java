package com.cy.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
//@MapperScan指定mapper接口的位置
@MapperScan("com.cy.store.mapper")
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }


    @Bean
    public MultipartConfigElement getMultipartConfigElement(){
        //创建配置类的工厂对象
        MultipartConfigFactory factory =
                new MultipartConfigFactory();
        //设置需要创建对象的相关信息
        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(10,DataUnit.MEGABYTES));

        //通过工厂对象来创建MultipartConfigElement
        return factory.createMultipartConfig();
    }
}
