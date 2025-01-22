package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.print.StreamPrintService;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {

		lomboktest test = new lomboktest();
		test.setData("test");
		String data = test.getData();
		System.out.println(data);

		SpringApplication.run(JpashopApplication.class, args);
	}

}
