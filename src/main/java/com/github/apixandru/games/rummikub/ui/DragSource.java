/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import java.awt.Component;
import java.awt.event.MouseEvent;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public interface DragSource {

	/**
	 * @param e
	 * @return
	 */
	Component getComponent(final MouseEvent e);

}
