package com.sd.blueblood;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import com.sd.conexion.Conexion;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"com.sd.blueblood","com.sd.controllers","com.sd.conexion"})
public class BluebloodApplication {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(BluebloodApplication.class, args);
		/*
		try {
			Conexion.autoConnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}

}
