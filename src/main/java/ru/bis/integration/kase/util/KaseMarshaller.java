package ru.bis.integration.kase.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.log4j.Logger;
import ru.bis.integration.kase.domain.Envelope;

/**
 * @author shds
 *
 */
public class KaseMarshaller {

    public static Logger logger = Logger.getLogger(KaseMarshaller.class);

    private static Marshaller marshaller;

    static {
        // create JAXB context and instantiate marshaller
        try {
            JAXBContext context = JAXBContext.newInstance(Envelope.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException ex) {
            logger.error(ex);
            ex.printStackTrace();
        }
    }

    public static void marshal(Envelope env, File file) {
        try {
            marshaller.marshal(env, file);
        } catch (JAXBException ex) {
            logger.error(ex);
        }
    }

    public static String marshal(Envelope env) {
        String result = "";
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            marshaller.marshal(env, baos);
            result = baos.toString();
        } catch (JAXBException|IOException ex) {
            result = "Error occured while marshalling Envelope object";
            logger.error(ex + " : " + result);
        }
        return result;
    }
}