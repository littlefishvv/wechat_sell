package com.zzu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;


@SpringBootApplication

public class WechatSellApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatSellApplication.class, args);
		System.out.println(SpringVersion.getVersion());
        System.out.println(SpringBootVersion.getVersion()) ;
	}

}
