package com.apixandru.utils.swing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 16, 2016
 */
public final class SwingUtil {

    private static final Logger log = LoggerFactory.getLogger(SwingUtil.class);

    /**
     *
     */
    private SwingUtil() {
    }

    /**
     * @param component the component
     * @return the color of the component or null if component is null
     */
    public static Color getBackground(final Component component) {
        if (null == component) {
            return null;
        }
        return component.getBackground();
    }

    /**
     * @param component the component
     * @param color     the color
     */
    public static void setBackground(final Component component, final Color color) {
        if (null != component) {
            component.setBackground(color);
        }
    }

    /**
     * @param component the component
     */
    public static void setChanged(Component component) {
        if (null != component) {
            component.validate();
            component.repaint();
        }
    }

    /**
     * @param component the component
     * @param container the container
     */
    public static void addAndNotify(final Component component, final Container container) {
        addAndNotify(component, container, null);
    }

    /**
     * @param component   the component
     * @param container   the container
     * @param constraints the constraints
     */
    public static void addAndNotify(final Component component, final Container container, Object constraints) {
        log.debug("Add {} to {}", component, container);
        container.add(component, constraints);
        setChanged(container);
    }

    /**
     * @param component the component
     * @param container the container
     */
    public static void removeAndNotify(final Component component, final Container container) {
        log.debug("Remove {} from {}", component, container);
        container.remove(component);
        setChanged(container);
    }

}
