package jp.ddo.chiroru.mongoutils.management;

import java.io.Serializable;

public class MongoServerConfig
implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dbpath;

    private String replicaSetName;

    private int port;

    private String logpath;

    private boolean logappend;

    private boolean master;

    private boolean slave;

    private boolean arbiter;

    public MongoServerConfig(String dbpath,
            String replicaSetName,
            int port,
            String logpath,
            boolean logappend,
            boolean master,
            boolean slave,
            boolean arbiter) {
        this.dbpath = dbpath;
        this.replicaSetName = replicaSetName;
        this.port = port;
        this.logpath = logpath;
        this.logappend = logappend;
        this.master = master;
        this.slave = slave;
        this.arbiter = arbiter;
    }
 
    public String getDbpath() {
        return dbpath;
    }

    public void setDbpath(String dbpath) {
        this.dbpath = dbpath;
    }

    public String getReplicaSetName() {
        return replicaSetName;
    }

    public void setReplicaSetName(String replicaSetName) {
        this.replicaSetName = replicaSetName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getLogPath() {
        return logpath;
    }

    public void setLogPath(String logPath) {
        this.logpath = logPath;
    }

    public boolean isLogappend() {
        return logappend;
    }

    public void setLogappend(boolean logappend) {
        this.logappend = logappend;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public boolean isSlave() {
        return slave;
    }

    public void setSlave(boolean slave) {
        this.slave = slave;
    }

    public boolean isArbiter() {
        return arbiter;
    }

    public void setArbiter(boolean arbiter) {
        this.arbiter = arbiter;
    }

    public String getCommand() {
        StringBuilder command = new StringBuilder();
        command.append("C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/bin/mongod")
               .append(" --dbpath=")
               .append(this.dbpath)
               .append(" --replSet=")
               .append(replicaSetName)
               .append(" --port=")
               .append(String.valueOf(port))
               .append(" --logpath=")
               .append(logpath);
        if (logappend) {
            command.append(" --logappend");
        }
        
        return command.toString();
    }
}
