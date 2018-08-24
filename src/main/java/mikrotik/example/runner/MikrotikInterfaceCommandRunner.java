package mikrotik.example.runner;

import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.legrange.mikrotik.ApiConnection;
import mikrotik.example.command.MikrotikCommands;

@Component
@Order(2)
@Slf4j
public class MikrotikInterfaceCommandRunner extends AbstractMikrotikRunner {

    @Override
    public void run(String... args) throws Exception {
        try (ApiConnection apiConnection = connectUsingAnnonTls()) {


            log.info("Get Mikrokit interface info");
            List<Map<String, String>> rs = apiConnection.execute(MikrotikCommands.INTERFACE_PRINT.command());
            printResultSet(rs);

            log.info("Get Mikrotik ethernet info");
            rs = apiConnection.execute(MikrotikCommands.INTERFACE_ETHERNET_PRINT.command());
            printResultSet(rs);

            log.info("Get Mikrokit ip address");
            rs = apiConnection.execute(MikrotikCommands.IP_ADDRESS_PRINT.command());
            printResultSet(rs);

            log.info("Get Mikrotik pppoe-server print");
            rs = apiConnection.execute(MikrotikCommands.INTERFACE_PPPOE_SERVER_PRINT_COMMAND.command());
            printResultSet(rs);

            log.info("Get Mikrotik pppoe-server server print");
            printResultSet(apiConnection.execute(MikrotikCommands.INTERFACE_PPPOE_SERVER_SERVER_PRINT_COMMAND.command()));

            log.info("Get Mikrotik pppoe-client print");
            printResultSet(apiConnection.execute(MikrotikCommands.INTERFACE_PPPOE_CLIENT_PRINT_COMMAND.command()));

            log.info("Get PPP profile print");
            printResultSet(apiConnection.execute(MikrotikCommands.PPP_PROFILE_PRINT.command()));

            log.info("Get PPP secret print");
            printResultSet(apiConnection.execute(MikrotikCommands.PPP_SECRET_PRINT.command()));
        } finally {
            log.info("Finished executing MikrotikInterfaceCommandRunner\n=========================================================\n");
        }
    }
}
