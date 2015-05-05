package nl.radic.swifttest;

import nl.radic.swiftapi.thrift.SwiftApi;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.security.MessageDigest;

/**
 * Created by radic on 5/5/15.
 */
public class TestClient {
    private String username = "admin";
    private String password = "password";
    private String salt = "saltines";

    public TestClient() {
    }

    public void connect()
    {

        try {
            TTransport transport;
            transport = new TSocket("localhost", 21111);
            transport = new TFramedTransport(transport);
            transport.open();


            TProtocol protocol = new TBinaryProtocol(transport);

            TMultiplexedProtocol multiplex;

            multiplex = new TMultiplexedProtocol( protocol, "SwiftApi");
            SwiftApi.Iface swiftApi = new SwiftApi.Client( multiplex);

            swiftApi.announce(getAuthString("announce"), "Heellooo");
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("hai");
        TestClient tc = new TestClient();
        tc.connect();
    }

    protected String getAuthString(String methodName) {
        String text = username + methodName + password + salt;
        return sha256(text);
    }

    protected String byteToString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            result += String.format("%02x", bytes[i]);
        }
        return result;
    }

    protected String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
