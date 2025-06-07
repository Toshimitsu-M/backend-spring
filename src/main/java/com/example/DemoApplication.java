package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.mapper")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// try (BufferedReader br = new BufferedReader( new InputStreamReader(System.in))){
		// 	System.out.println("in：");
		// 	String input = br.readLine();
		// 	System.out.println("out：" + input);
		// } catch(IOException e) {
		// 	throw new RuntimeException(e);
		// }


		SpringApplication.run(DemoApplication.class, args);
	}

}
