package com.ssafy.devit.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
		basePackages = "com.ssafy.devit.repository"
)
public class DatabaseConfig {}
