package ru.bis.integration.kase.configuration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ru.bis.integration.kase.domain.AdapterInfo;
import ru.bis.integration.kase.domain.Mode;
import ru.bis.integration.kase.domain.ServerType;
import ru.bis.integration.kase.transport.AbstractSocketListener;
import ru.bis.integration.kase.transport.CommandSocketListener;
import ru.bis.integration.kase.transport.DocumentSocketListener;
import ru.bis.integration.kase.util.CommandExecutor;
import static ru.bis.integration.kase.util.KaseConstants.*;

/**
 * @author shds
 *
 */
@Configuration
@Import(FixConfig.class)
@ComponentScan(basePackages={"ru.bis.integration.kase.service"})
@ImportResource({"classpath:/spring/spring-integration-context.xml"})
public class SpringConfig {

    private final static Logger logger = Logger.getLogger(SpringConfig.class);
    
    @Value("${adapter.transport.command.socket.port}") private int commandSocketPort;
    @Value("${adapter.transport.document.socket.port}") private int documentSocketPort;
    @Value("${adapter.mode}") private String mode;
    @Value("${server.type}") private String serverType;
    @Value("${adapter.test.execution_reports.responseFile}") private String erResponseFile;

    @Bean
    public AdapterInfo info() {
        AdapterInfo adapterInfo = new AdapterInfo(mode.equals("PROD") ? Mode.PROD : Mode.TEST);
        if (serverType.equals("CUR")) {
            adapterInfo.setServerType(ServerType.CUR);
        } else if (serverType.equals("REPO")) {
            adapterInfo.setServerType(ServerType.REPO);
        } else {
            IllegalStateException ex = new IllegalStateException("server.type property must be set either to CUR or REPO");
            logger.error(ex);
            throw ex;
        }
        if (adapterInfo.mode() ==  Mode.TEST) {
            adapterInfo.setERResponseFile(erResponseFile);
        }
        return adapterInfo;
    }

    @Bean
    public CommandExecutor commandExecutor() {
        CommandExecutor cmndExecutor  = new CommandExecutor();
        return cmndExecutor;
    }

    @Bean
    public AbstractSocketListener documentSocketListener() {
        AbstractSocketListener documentSocketListener = new DocumentSocketListener(documentSocketPort);
        return documentSocketListener;
    }

    @Bean
    public AbstractSocketListener commandSocketListener() {
        AbstractSocketListener commandSocketListener = new CommandSocketListener(commandSocketPort);
        return commandSocketListener;
    }

    static @Bean public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
        Resource[] resourceLocations = new Resource[] {
                new ClassPathResource(PATH_TO_ADAPTER_CONFIG)
        };
        p.setLocations(resourceLocations);
        return p;
    }
}