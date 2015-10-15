/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import java.awt.Container;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
final class ComponentDragSource implements DragSource {

	private final Container container;

	/**
	 * @param container
	 */
	public ComponentDragSource(final Container container) {
		this.container = container;
	}

	/* (non-Javadoc)
	 * @see com.github.apixandru.games.rummikub.ui.DragSource#getComponent(java.awt.event.MouseEvent)
	 */
	@Override
	public JComponent getComponent(final MouseEvent e) {
		return (JComponent) this.container.findComponentAt(e.getX(), e.getY());
	}

}
