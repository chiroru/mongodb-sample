package jp.ddo.chiroru.mongoutils.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReplicaSetConfig
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<MongoServerConfig> MongoServerConfigList = new ArrayList<>();

    public void addMongoServerConfig(MongoServerConfig config) {
        MongoServerConfigList.add(config);
    }

    public List<MongoServerConfig> getMongoServerConfigList() {
        return MongoServerConfigList;
    }
}
