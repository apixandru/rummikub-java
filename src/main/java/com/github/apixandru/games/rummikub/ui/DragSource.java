/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public interface DragSource {

	/**
	 * @param e
	 * @return
	 */
	JComponent getComponent(final MouseEvent e);

}
