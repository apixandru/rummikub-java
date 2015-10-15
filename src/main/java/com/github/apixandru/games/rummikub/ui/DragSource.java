/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public interface DragSource {

	/**
	 * @param event
	 * @return
	 */
	JComponent getComponentAt(final MouseEvent event);

	/**
	 * @param component
	 */
	void beginDrag(Component component);

	/**
	 * @param component
	 */
	void endDrag(Component component);

}
