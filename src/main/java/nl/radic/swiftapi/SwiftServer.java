package nl.radic.swiftapi;

import nl.radic.swiftapi.thrift.EAuthException;
import nl.radic.swiftapi.thrift.SwiftApi;
import org.apache.thrift.server.TServer;

import org.apache.thrift.TException;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by radic on 5/5/15.
 */
public class SwiftServer {

    private int port;
    private Game game;
    private TServer server;
    private SwiftApiPlugin plugin;
    protected HashMap<String,TProcessor> apiProcessors = new HashMap<>();

    public SwiftServer(SwiftApiPlugin plugin) throws InterruptedException {
        this.plugin = plugin;
        this.port = plugin.getConfig().getNode("port").getInt();
        this.game = plugin.getGame();
    }

    public void addApiProcessor(String apiHandlerName, TProcessor apiProcessor){
        apiProcessors.put(apiHandlerName, apiProcessor);
    }

    public HashMap<String,TProcessor> getApiProcessors(){
        return apiProcessors;
    }

    public boolean hasApiProcessors(){
        return apiProcessors.isEmpty() == false;
    }

    public void startServer(){
        start(this);
    }

    public void stopServer(){
        //stop();
    }

    private void stop() {
        try {
            plugin.getLogger().info("Stopping server...");
            server.stop();
            plugin.getLogger().info("Server stopped successfully");
        } catch (Exception e) {
            plugin.getLogger().error("Error while stopping server: " + e.getMessage());
        }
    }

    private void start(SwiftServer swiftServerInstance) {
        final SwiftServer swiftServer = swiftServerInstance;
        (new Thread(new Runnable() {

            public void run() {
                try {
                    plugin.getLogger().info( "Sleeping for 2 seconds before starting up...");
                    Thread.sleep(2000);

                    TMultiplexedProcessor processor = new TMultiplexedProcessor();
                    processor.registerProcessor("SwiftApi", new SwiftApi.Processor(new ApiHandler()));


                    /*
                    if (plugin.getExtensions().count() > 0) {
                        for (String extensionClassName : plugin.getExtensions().all().keySet()) {
                            SwiftExtension extension = plugin.getExtensions().get(extensionClassName);
                            if (extension.hasApiHandlers()) {
                                for (String fullClassName : extension.getApiHandlers()) {
                                    String[] splitted = fullClassName.split("\\.");
                                    String className = splitted[splitted.length - 1];
                                    TProcessor tp = extension.getApiProcessor(className);
                                    processor.registerProcessor(className, tp);
                                    Api.debug("Registered extension", className);
                                }
                            }
                        }
                    }

                    if(swiftServer.hasApiProcessors()) {
                        for (Map.Entry<String, TProcessor> entry : swiftServer.getApiProcessors().entrySet()) {
                            processor.registerProcessor(entry.getKey(), entry.getValue());
                        }
                    }
                    */

                    // create the transport
                    TNonblockingServerTransport tst = null;
                    tst = new TNonblockingServerSocket(port);

                    // set up the server arguments
                    TThreadedSelectorServer.Args a = null;
                    a = new TThreadedSelectorServer.Args(tst);

                    // allocate the server
                    server = new TThreadedSelectorServer(a.processor(processor));

                    plugin.getLogger().info("Started up and listening on port " + port);

                    // start up the server
                    server.serve();
                } catch (Exception e) {
                    plugin.getLogger().error("SwiftApi:SwiftServer:Run:Exception > " + e.getMessage());
                    plugin.getLogger().error("SwiftApi:SwiftServer:Run:Exception > In: " + e.getClass().getName());
                    for (StackTraceElement el : e.getStackTrace()) {

                        plugin.getLogger().error("trace: file:[" + el.getFileName() + "]@" + el.getLineNumber() + " - " + el.getMethodName() + " - " + el.toString());
                    }

                    plugin.getLogger().error(e.getMessage());
                }
            }

        })).start();

    }

    class ApiHandler extends SwiftApiHandler implements SwiftApi.Iface {

        @Override
        public boolean announce(String authString, String message) throws EAuthException, TException {
            authenticateAndLog(authString, "announce");
            plugin.getLogger().info("ANNOUNCE: " + message);
            game.getServer().broadcastMessage(Texts.of(message));
            return true;
        }
    }

}
