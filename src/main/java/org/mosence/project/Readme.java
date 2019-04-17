package org.mosence.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ：msoence
 * @date ：2019/04/09
 */
@SpringBootApplication
@EnableSwagger2
public class Readme {
    public static void main(String[] args){
        SpringApplication.run(Readme.class,args);
    }
}
