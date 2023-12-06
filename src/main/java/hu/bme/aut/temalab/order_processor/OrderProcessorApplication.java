package hu.bme.aut.temalab.order_processor;

import hu.bme.aut.temalab.order_processor.service.InitDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class OrderProcessorApplication implements CommandLineRunner{

    private final InitDbService initDbService;

    public static void main(String[] args) {
        SpringApplication.run(OrderProcessorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        initDbService.initDb();
    }

}
