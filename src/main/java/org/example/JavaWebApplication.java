package org.example;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("org.example.mapper")
@Slf4j
@SpringBootApplication
public class JavaWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaWebApplication.class, args);
	}

}
