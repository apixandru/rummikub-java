/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
final class ComponentDragSource implements DragSource {

	private final Container parent;
	private final Container container;

	/**
	 * @param container
	 */
	public ComponentDragSource(final Container container) {
		this.parent = container.getParent();
		this.container = container;
	}

	/* (non-Javadoc)
	 * @see com.github.apixandru.games.rummikub.ui.DragSource#getComponentAt(int, int)
	 */
	@Override
	public JComponent getComponentAt(final int x, final int y) {
		return (JComponent) this.container.findComponentAt(x, y);
	}

	/* (non-Javadoc)
	 * @see com.github.apixandru.games.rummikub.ui.DragSource#beginDrag(java.awt.Component)
	 */
	@Override
	public void beginDrag(final Component component) {
		this.parent.add(component, JLayeredPane.DRAG_LAYER);
	}

	/* (non-Javadoc)
	 * @see com.github.apixandru.games.rummikub.ui.DragSource#endDrag(java.awt.Component)
	 */
	@Override
	public void endDrag(final Component component) {
		this.parent.remove(component);
		this.parent.repaint();
	}

}
