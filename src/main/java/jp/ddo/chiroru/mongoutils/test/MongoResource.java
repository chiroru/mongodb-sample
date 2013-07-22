package jp.ddo.chiroru.mongoutils.test;

import org.junit.rules.ExternalResource;

import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;

public class MongoResource extends ExternalResource {

    private MongodExecutable mongodExe;
    private MongodProcess mongod;

    @Override
    protected void before() throws Throwable {
        super.before();
        MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();
        mongodExe = runtime.prepare(new MongodConfig(Version.V2_1_2, 12345, false));
        mongod = mongodExe.start();
    }

    @Override
    protected void after() {
        super.after();
        mongod.stop();
        mongodExe.cleanup();
    }
}
