package mikrotik.example.runner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

@Component
@Slf4j
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
        log.info("Connected using username: {}", mikrotikUsername);
        return apiConnection;
    }

    protected ApiConnection connectUsingAnnonTls() throws MikrotikApiException {
        ApiConnection apiConnection = ApiConnection.connect(AnonymousSocketFactory.getDefault(), mikrotikIpAddress, ApiConnection.DEFAULT_TLS_PORT, ApiConnection.DEFAULT_CONNECTION_TIMEOUT);
        apiConnection.login(mikrotikUsername, mikrotikPassword);
        log.info("=========================================================\nConnected over TLS using username: {}", mikrotikUsername);
        return apiConnection;
    }

    protected void printResultSet(List<Map<String, String>> rs) {
        for (Map<String, String> r : rs) {
            log.info("{}", r);
        }
    }

    static class AnonymousSocketFactory extends SocketFactory {

        @Override
        public Socket createSocket() throws IOException {
            return fixSocket((SSLSocket) SSLSocketFactory.getDefault().createSocket());
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
            return fixSocket((SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
            return fixSocket((SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress address, int port) throws IOException {
            return fixSocket((SSLSocket) SSLSocketFactory.getDefault().createSocket(address, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return fixSocket((SSLSocket) SSLSocketFactory.getDefault().createSocket(address, port, localAddress, localPort));
        }

        private Socket fixSocket(SSLSocket ssl) {
            List<String> cs = new LinkedList<>();
            // not happy with this code. Without it, SSL throws a "Remote host closed connection during handshake" error
            // caused by a "SSL peer shut down incorrectly" error
            for (String s : ssl.getSupportedCipherSuites()) {
                if (s.startsWith("TLS_DH_anon")) {
                    cs.add(s);
                }
            }
            ssl.setEnabledCipherSuites(cs.toArray(new String[]{}));
            return ssl;
        }

        public static SocketFactory getDefault() {
            if (fact == null) {
                fact = new AnonymousSocketFactory();
            }
            return fact;
        }

        private AnonymousSocketFactory() {

        }

        private static AnonymousSocketFactory fact;

    }

}
