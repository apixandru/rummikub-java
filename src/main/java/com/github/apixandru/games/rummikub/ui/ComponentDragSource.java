/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseEvent;

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
	public Component getComponent(final MouseEvent e) {
		return this.container.findComponentAt(e.getX(), e.getY());

	}

}
