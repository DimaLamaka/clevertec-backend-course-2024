package ru.clevertec.app.servlet.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

import java.util.logging.Logger;

@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {
    private static final Logger LOGGER = Logger.getLogger(SessionAttributeListener.class.getName());

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        LOGGER.info("Attribute added: " + event.getName() + " = " + event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        LOGGER.info("Attribute removed: " + event.getName());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        LOGGER.info("Attribute replaced: " + event.getName() + " = " + event.getValue());
    }
}
