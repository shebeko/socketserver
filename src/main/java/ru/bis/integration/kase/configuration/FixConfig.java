package ru.bis.integration.kase.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import quickfix.ConfigError;
import kz.kase.fix.factory.KaseFixMessageFactory;
import quickfix.store.FileStoreFactory;
import quickfix.logging.FileLogFactory;
import quickfix.logging.LogFactory;
import quickfix.MessageFactory;
import quickfix.store.MessageStoreFactory;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import ru.bis.integration.kase.fix.client.ApplicationImpl;
import ru.bis.integration.kase.fix.client.FixClient;
import ru.bis.integration.kase.repository.KaseRepository;
import static ru.bis.integration.kase.util.KaseConstants.FIX_CFG;

/**
 * @author shds
 *
 */
@Configuration
public class FixConfig {

    @Bean
    public FixClient fixClient() throws FileNotFoundException, ConfigError, InterruptedException {
        FixClient client = new FixClient();
        client.setApplication(application());
        client.setSocketInitiator(socketInitiator());
        return client;
    }

    @Bean
    public SessionSettings sessionSettings() throws FileNotFoundException, ConfigError {
        return new SessionSettings(new FileInputStream(FIX_CFG));
    }

    @Bean
    MessageStoreFactory messageStoreFactory() throws FileNotFoundException, ConfigError {
        return new FileStoreFactory(sessionSettings());
    }

    @Bean
    LogFactory logFactory() throws FileNotFoundException, ConfigError {
        return new FileLogFactory(sessionSettings());
    }

    @Bean
    MessageFactory messageFactory() {
        return new KaseFixMessageFactory();
    }

    @Bean
    SocketInitiator socketInitiator() throws FileNotFoundException, ConfigError {
        return new SocketInitiator(application(), messageStoreFactory(), sessionSettings(), logFactory(), messageFactory());
    }

    @Bean
    public ApplicationImpl application() throws FileNotFoundException, ConfigError {
        ApplicationImpl app = new ApplicationImpl(sessionSettings());
        return app;
    }

    @Bean
    public KaseRepository repository() {
        return new KaseRepository();
    }
}