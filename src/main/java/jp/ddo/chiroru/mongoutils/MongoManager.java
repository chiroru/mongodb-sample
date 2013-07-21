package jp.ddo.chiroru.mongoutils;

import java.io.IOException;
import java.util.Properties;

import jp.ddo.chiroru.utils.ClassPathPropertyLoader;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MongoManager {

    private final static String MONGO_PROPERTY_PATH="mongo.properties";

    private final String HOST;
    private final int PORT;
    private final String DB_NAME;
    private final MongoClient client;
    private final DB db;

    private static MongoManager instance = new MongoManager();

    private MongoManager() {
        ClassPathPropertyLoader loader = new ClassPathPropertyLoader();
        try {
            Properties props = loader.load(MONGO_PROPERTY_PATH);
            HOST = props.getProperty("host");
            PORT = Integer.valueOf(props.getProperty("port"));
            DB_NAME = props.getProperty("db_name");
            final MongoClientOptions options = new MongoClientOptions.Builder()
            .connectionsPerHost(Integer.valueOf(props.getProperty("connections_per_host")))
            .threadsAllowedToBlockForConnectionMultiplier(Integer.valueOf(props.getProperty("connections_per_host")))
            .build();
            client = new MongoClient(new ServerAddress(HOST, PORT), options);
            client.setWriteConcern(WriteConcern.SAFE);
            db = client.getDB(DB_NAME);
            dumpMongoClientOption(options);
        } catch (IOException e) {
            throw new RuntimeException("Can't load " + MONGO_PROPERTY_PATH, e);
        }
    }

    public static MongoManager getInstance() {
        return instance;
    }

    public DB getDb() {
        return db;
    }

    protected void dumpMongoClientOption(MongoClientOptions options) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Description : [" + options.getDescription() + "]\n");
        buffer.append("ConnectionPerHost : [" + options.getConnectionsPerHost() + "]\n");
        buffer.append("ConnectTimeout : [" + options.getConnectTimeout() + "]\n");
        buffer.append("MaxAutoConnectRetryTime : [" + options.getMaxAutoConnectRetryTime() + "]\n");
        buffer.append("MaxWaitTime : [" + options.getMaxWaitTime() + "]\n");
        buffer.append("SocketTimeout : [" + options.getSocketTimeout() + "]\n");
        buffer.append("ThreadsAllowedToBlockForConnectionMultiplier : [" + options.getThreadsAllowedToBlockForConnectionMultiplier() + "]\n");
        buffer.append("AlwaysUseMBeans : [" + options.isAlwaysUseMBeans() + "]\n");
        buffer.append("AutoConnectRetry : [" + options.isAutoConnectRetry() + "]\n");
        buffer.append("CursorFinalizerEnabled : [" + options.isCursorFinalizerEnabled() + "]\n");
        buffer.append("SocketKeepAlive : [" + options.isSocketKeepAlive() + "]\n");
        System.out.println(buffer.toString());
    }
}
