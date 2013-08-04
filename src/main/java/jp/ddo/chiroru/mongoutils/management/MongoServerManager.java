package jp.ddo.chiroru.mongoutils.management;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;

import com.mongodb.DB;
import com.mongodb.DBTCPConnector;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MongoServerManager {

    private static final String command = "C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/bin/mongod --dbpath=C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/data/node1 --replSet=myrep --port=30000 --logpath C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/log/node1/mongodb.log --logappend";

    public void startServer() {
        CommandLine commandLine = 
                CommandLine.parse(command);
        DefaultExecutor executor = new DefaultExecutor();
        try {
            executor.setExitValue(0);    // 正常終了の場合に返される値
            // 非同期モードで実行
            DefaultExecuteResultHandler resultHandler = 
                    new DefaultExecuteResultHandler();
            executor.execute(commandLine, resultHandler);

            System.out.println("Hello!!!!!");

            resultHandler.waitFor();
            System.out.println("Prcess Exit.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecuteException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void startReplicaSet(ReplicaSetConfig config) {
        List<MongoServerConfig> serverConfigList = config.getMongoServerConfigList();
        List<CommandLine> commandLineList = new ArrayList<>();
        List<DefaultExecuteResultHandler> handlerList = new ArrayList<>();
        for (MongoServerConfig c : serverConfigList) {
            commandLineList.add(CommandLine.parse(c.getCommand()));
        }
        DefaultExecutor executor = new DefaultExecutor();
        try {
            executor.setExitValue(0);
            for (CommandLine cl : commandLineList) {
                DefaultExecuteResultHandler resultHandler = 
                        new DefaultExecuteResultHandler();
                executor.execute(cl, resultHandler);
                handlerList.add(resultHandler);
            }
        } catch (ExecuteException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String ic1 = "\'rs.initiate({" +
"  _id : \"\"myrep\"\"," +
"  members : [" +
"    { _id : 0, host : \"\"127.0.0.1:30000\"\" }," +
"    { _id : 1, host : \"\"127.0.0.1:30001\"\" }," +
"    { _id : 2, host : \"\"127.0.0.1:30002\"\", arbiterOnly : true } ]})\'";
        CommandLine cl = CommandLine.parse("C:/Products/mongodb-win32-x86_64-2008plus-2.4.5/bin/mongo --port=30000 --eval " + ic1);
//"{" +
//"  \"_id\"     : \"myrep\"," +
//"  \"members\" : [" +
//"    {" +
//"      \"_id\"         : 0," +
//"      \"host\"        : \"127.0.0.1:30000\"" +
//"    }," +
//"    {" +
//"      \"_id\"         : 1," +
//"      \"host\"        : \"127.0.0.1:30001\"" +
//"    }," +
//"    {" +
//"      \"_id\"         : 2," +
//"      \"host\"        : \"127.0.0.1:30002\"," +
//"      \"arbiterOnly\" : true" +
//"    } ]" +
//"}\");rs.initiate(config);";
//        String ic2 = "rs.initiate(config)";
        DefaultExecuteResultHandler r = 
                new DefaultExecuteResultHandler();
        BufferedOutputStream bos = new BufferedOutputStream(new ByteArrayOutputStream());
        PumpStreamHandler ioh = new PumpStreamHandler();
        ioh.setProcessInputStream(bos);
        ioh.start();
        executor.setStreamHandler(ioh);
        try {
            executor.execute(cl);
//            try {
//                bos.write(ic1.getBytes());
////                bos.write(ic2.getBytes());
//            } catch (IOException e2) {
//                // TODO Auto-generated catch block
//                e2.printStackTrace();
//            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            r.waitFor();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        for (DefaultExecuteResultHandler h : handlerList) {
            try {
                h.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MongoServerManager manager = new MongoServerManager();
        manager.startServer();
    }
}
