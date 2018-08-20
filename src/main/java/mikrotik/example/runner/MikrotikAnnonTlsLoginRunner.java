package mikrotik.example.runner;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.legrange.mikrotik.ApiConnection;

@Component
@Order(6)
@Slf4j
public class MikrotikAnnonTlsLoginRunner extends AbstractMikrotikRunner {

    @Override
    public void run(String... args) throws Exception {

        try (ApiConnection apiConnection = connectUsingAnnonTls()) {
            log.info("Connected using anonymous tls connection");
        } finally {
            log.info("Finished executing MikrotikAnnonTlsLoginRunner\n=========================================================\n");
        }
    }
}
