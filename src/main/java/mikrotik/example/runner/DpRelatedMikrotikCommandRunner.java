package mikrotik.example.runner;

import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import mikrotik.example.command.MikrotikCommands;

@Component
@Order(7)
@Slf4j
public class DpRelatedMikrotikCommandRunner extends AbstractMikrotikRunner {

    @Override
    public void run(String... args) throws Exception {
        try (ApiConnection apiConnection = connectUsingAnnonTls()) {
            log.info("Print the current DHCP lease table");
            List<Map<String,String>> rs = apiConnection.execute(MikrotikCommands.DHCP_SERVER_LEASE_PRINT.command());
            printResultSet(rs);

            log.info("Create a new sniff target for a source IP address");
            String command = MikrotikCommands.CALEA_ADD_ACTION_SRC_IP.command("1", "172.16.35.131", "22200", "10.10.10.10");
            log.info("Command: {}", command);
            printResultSet(apiConnection.execute(MikrotikCommands.CALEA_ADD_ACTION_SRC_IP.command("1", "172.16.35.131", "22200", "10.10.10.10")));

            log.info("Create a new sniff target for a destination IP address");
            printResultSet(apiConnection.execute(MikrotikCommands.CALEA_ADD_ACTION_DEST_IP.command("2", "172.16.35.131", "22200", "20.20.20.20")));

            log.info("Find all calea record with sniff id");
            List<Map<String, String>> resultSet = apiConnection.execute(MikrotikCommands.CALEA_FIND_ALL.command());
            int caleaRecords = resultSet.size();
            log.info("Number of calea records {}", caleaRecords);
            printResultSet(resultSet);

            log.info("Remove all calea records");
            String cmd = MikrotikCommands.CALEA_REMOVE_SNIFF_ID.command(generateSequence(resultSet.size()));
            log.info("Remove all command: {}", cmd);
            apiConnection.execute(cmd);

            log.info("Find all calea record with sniff id");
            resultSet = apiConnection.execute(MikrotikCommands.CALEA_FIND_ALL.command());
            caleaRecords = resultSet.size();
            log.info("Number of calea records {}", caleaRecords);
            printResultSet(resultSet);
        } finally {
            log.info("Finished executing DpRelatedMikrotikCommandRunner\n=========================================================\n");
        }

    }

    private String generateSequence(int maxNumber) {
        StringBuilder seqBuilder = new StringBuilder();
        for(int i = 0 ; i < maxNumber; i++) {
            seqBuilder.append(i);
            if (i < maxNumber -1)
                seqBuilder.append(",");
        }
        return seqBuilder.toString();
    }
}
