package jp.ddo.chiroru.mongoutils.management;

import org.junit.Test;

public class CLILauncherTest {

    @Test
    public void ヘルプを表示する() {
        String[] args = {"-h"};
        CLILauncher.main(args);
    }
}
