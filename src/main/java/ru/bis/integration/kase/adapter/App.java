package ru.bis.integration.kase.adapter;

import static ru.bis.integration.kase.util.KaseConstants.CHECK_COMMAND;
import static ru.bis.integration.kase.util.KaseConstants.START_COMMAND;
import static ru.bis.integration.kase.util.KaseConstants.STATUS_COMMAND;
import static ru.bis.integration.kase.util.KaseConstants.STOP_COMMAND;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import ru.bis.integration.kase.configuration.SpringConfig;
import ru.bis.integration.kase.util.CommandExecutor;

/**
 * @author shds
 * 
 */
public class App {

    private static AbstractApplicationContext appCtx;

    public static void main(String... args) {
        parseCommandLine(args);
    }

    private static void parseCommandLine(String... args) {
        CommandExecutor cmndExecutor = loadApplicationContext().getBean(CommandExecutor.class);
        if (args[0].equalsIgnoreCase(START_COMMAND)) {
            cmndExecutor.start();
        } else if (args[0].equalsIgnoreCase(STOP_COMMAND)) {
            cmndExecutor.stop();
        } else if (args[0].equalsIgnoreCase(STATUS_COMMAND)) {
            cmndExecutor.status();
        } else if (args[0].equalsIgnoreCase(CHECK_COMMAND)) {
            cmndExecutor.check();
        } else {
            cmndExecutor.printUsage();
            cmndExecutor.exit();
        }
    }

    public static AbstractApplicationContext loadApplicationContext() {
        if (appCtx == null) {
            appCtx = new AnnotationConfigApplicationContext(SpringConfig.class);
        }
        return appCtx;
    }
}