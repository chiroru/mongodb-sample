package jp.ddo.chiroru.mongoutils.management;

import org.junit.Test;


public class MongoServerManagerTest {

    @Test
    public void レプリカセットを構成できる() {
        ReplicaSetConfig c = new ReplicaSetConfig();
        MongoServerConfig c1 = new MongoServerConfig("C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/data/node1",
                "myrep",
                30000,
                "C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/log/node1/mongodb.log",
                true,
                true,
                false,
                false);
        MongoServerConfig c2 = new MongoServerConfig("C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/data/node2",
                "myrep",
                30001,
                "C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/log/node2/mongodb.log",
                true,
                true,
                false,
                false);
        MongoServerConfig c3 = new MongoServerConfig("C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/data/node3",
                "myrep",
                30002,
                "C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/log/node3/mongodb.log",
                true,
                true,
                false,
                false);
        c.addMongoServerConfig(c1);
        c.addMongoServerConfig(c2);
        c.addMongoServerConfig(c3);
        MongoServerManager m = new MongoServerManager();
        m.startReplicaSet(c);
    }
}
