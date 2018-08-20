package mikrotik.example.runner;

import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.legrange.mikrotik.ApiConnection;
import mikrotik.example.command.MikrotikCommands;

@Component
@Order(4)
@Slf4j
public class MikrotikDhcpServerCommandRunner extends AbstractMikrotikRunner {
    @Override
    public void run(String... args) throws Exception {
        try (ApiConnection apiConnection = connectUsingAnnonTls()) {
            log.info("Get Mikrokit dhcp-server info");
            List<Map<String, String>> rs = apiConnection.execute(MikrotikCommands.DHCP_SERVER_PRINT_COMMAND.command());
            printResultSet(rs);
        } finally {
            log.info("Finished executing MikrotikDhcpServerCommandRunner\n=========================================================\n");
        }
    }
}
