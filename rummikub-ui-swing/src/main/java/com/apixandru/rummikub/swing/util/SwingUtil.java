package com.apixandru.rummikub.swing.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 16, 2016
 */
public final class SwingUtil {

    private static final Logger log = LoggerFactory.getLogger(SwingUtil.class);

    private SwingUtil() {
    }

    public static Color getBackground(final Component component) {
        if (null == component) {
            return null;
        }
        return component.getBackground();
    }

    public static void setBackground(final Component component, final Color color) {
        if (null != component) {
            component.setBackground(color);
        }
    }

    public static void setChanged(Component component) {
        if (null != component) {
            component.validate();
            component.repaint();
        }
    }

    public static void addAndNotify(final Component component, final Container container) {
        addAndNotify(component, container, null);
    }

    public static void addAndNotify(final Component component, final Container container, Object constraints) {
        log.debug("Add {} to {}", component, container);
        container.add(component, constraints);
        setChanged(container);
    }

    public static void removeAndNotify(final Component component, final Container container) {
        log.debug("Remove {} from {}", component, container);
        container.remove(component);
        setChanged(container);
    }

}
