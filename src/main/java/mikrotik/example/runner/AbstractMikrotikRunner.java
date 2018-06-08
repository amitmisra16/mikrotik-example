package mikrotik.example.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

@Component
public abstract class AbstractMikrotikRunner implements CommandLineRunner {

    @Value("${mikrotik.management.ipaddress}")
    private String mikrotikIpAddress;

    @Value("${mikrotik.management.port:8728}")
    private int mikrotikPort;

    @Value("${mikrotik.management.tls.port:8729}")
    private int mikrotikTlsPort;

    @Value("${mikrotik.username}")
    private String mikrotikUsername;

    @Value("${mikrotik.password}")
    private String mikrotikPassword;

    protected ApiConnection connect() throws MikrotikApiException {
        ApiConnection apiConnection = ApiConnection.connect(mikrotikIpAddress);
        apiConnection.login(mikrotikUsername, mikrotikPassword);
        return apiConnection;
    }

}
