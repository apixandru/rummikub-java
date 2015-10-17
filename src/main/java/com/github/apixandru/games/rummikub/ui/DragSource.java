/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import java.awt.Component;

import javax.swing.JComponent;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public interface DragSource {

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	JComponent getComponentAt(final int x, int y);

	/**
	 * @param component
	 */
	void beginDrag(Component component);

	/**
	 * @param component
	 */
	void endDrag(Component component);

}
