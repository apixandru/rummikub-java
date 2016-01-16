package com.apixandru.utils.swing;

import java.awt.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 16, 2016
 */
public final class SwingUtil {

    /**
     *
     */
    SwingUtil() {
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

}
