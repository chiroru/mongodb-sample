package jp.ddo.chiroru.mongoutils.management;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CLILauncher {

    private final static Options opts = new Options();
    private final static BasicParser parser = new BasicParser();

    static {
        opts.addOption("h", false, "show help.");
    }

    public static void main(String[] args) {
        try {
            CommandLine cl = parser.parse(opts, args);
            if ( cl.hasOption('h') ) {
                showHelp();
            }
        } catch (ParseException e) {
            showHelp();
        }
    }

    private static void showHelp() {
        HelpFormatter help = new HelpFormatter();
        help.printHelp("Mongo DB RepicaSet Manager", opts, true);
        return;
    }
}
