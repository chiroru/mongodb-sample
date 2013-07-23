package jp.ddo.chiroru.mongoutils.test;

import org.junit.rules.ExternalResource;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;

public class MongoResource extends ExternalResource {

    int port = 12345;
    MongodConfig mongodConfig = new MongodConfig(Version.Main.PRODUCTION, port, false);
    MongodStarter runtime = MongodStarter.getDefaultInstance();
    MongodExecutable mongodExecutable = null;

    @Override
    protected void before() throws Throwable {
        super.before();
        mongodExecutable = runtime.prepare(mongodConfig);
        mongodExecutable.start();
    }

    @Override
    protected void after() {
        super.after();
        if (mongodExecutable != null)
            mongodExecutable.stop();
    }
}
