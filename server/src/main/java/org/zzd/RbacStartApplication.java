package org.zzd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author :zzd
 * @date : 2023-02-26 15:37
 */
@SpringBootApplication
@MapperScan("org.zzd.mapper")
@EnableSwagger2WebMvc
public class RbacStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(RbacStartApplication.class,args);
    }
}
