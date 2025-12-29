package com.hdu.edu.codeexplorer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.hdu.edu.codeexplorer.mapper"})
public class CodeExplorerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeExplorerApplication.class, args);
    }

}
