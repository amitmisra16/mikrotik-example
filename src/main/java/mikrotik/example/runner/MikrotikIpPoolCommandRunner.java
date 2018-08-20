package mikrotik.example.runner;

import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.legrange.mikrotik.ApiConnection;
import mikrotik.example.command.MikrotikCommands;

@Component
@Order(3)
@Slf4j
public class MikrotikIpPoolCommandRunner extends AbstractMikrotikRunner {

    @Override
    public void run(String... args) throws Exception {
        try (ApiConnection apiConnection = connectUsingAnnonTls()) {
            log.info("Get Mikrokit ip pool info");
            List<Map<String, String>> rs = apiConnection.execute(MikrotikCommands.IP_POOL_PRINT_COMMAND.command());
            printResultSet(rs);

            log.info("Get Mikrokit used ip pool info");
            rs = apiConnection.execute(MikrotikCommands.IP_POOL_USER_PRINT_COMMAND.command());
            printResultSet(rs);
        } finally {
            log.info("Finished executing MikrotikIpPoolCommandRunner\n=========================================================\n");
        }
    }
}
