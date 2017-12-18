package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.metrics.export.prometheus.EnablePrometheusMetrics;

@SpringBootApplication
@EnablePrometheusMetrics
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
