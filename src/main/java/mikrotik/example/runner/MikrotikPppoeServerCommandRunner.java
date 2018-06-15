package mikrotik.example.runner;

import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.legrange.mikrotik.ApiConnection;
import mikrotik.example.command.MikrotikCommands;

@Component
@Slf4j
@Order(5)
public class MikrotikPppoeServerCommandRunner extends AbstractMikrotikRunner {
    @Override
    public void run(String... args) throws Exception {
        ApiConnection apiConnection = connect();
        log.info("Get Mikrokit pppoe-server info");
        List<Map<String, String>> rs = apiConnection.execute(MikrotikCommands.INTERFACE_PPPOE_SERVER_PRINT_COMMAND.command());
        for (Map<String,String> r : rs) {
            log.info("{}", r);
        }
        apiConnection.close();
    }
}
