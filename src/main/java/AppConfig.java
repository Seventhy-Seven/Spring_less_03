import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;


@Configuration
@EnableAsync
@ComponentScan({})
public class AppConfig {
}

