package apx.inc.assignments_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
//@EnableFeignClients
public class AssigmentsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssigmentsServiceApplication.class, args);
    }

}
