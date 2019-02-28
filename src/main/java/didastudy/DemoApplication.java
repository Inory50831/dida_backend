package didastudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = "didastudy.dao")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

