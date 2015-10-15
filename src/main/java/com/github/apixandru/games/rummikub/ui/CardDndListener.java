/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 13, 2015
 */
final class CardDndListener extends MouseAdapter {

	private final JLayeredPane layeredPane;

	private JComponent lastHover;

	private CardUi movingPiece;
	private int xOffset;
	private int yOffset;
	private final Color defaultColor;

	private final DragSource dragSource;

	/**
	 * @param layeredPane
	 * @param dragSource
	 */
	CardDndListener(final JLayeredPane layeredPane, final DragSource dragSource) {
		this.defaultColor = layeredPane.getBackground();
		this.layeredPane = layeredPane;
		this.dragSource = dragSource;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(final MouseEvent e) {
		final CardUi card = getCard(e);
		if (null == card) {
			return;
		}
		this.movingPiece = card;

		final Point parentLocation = card.getParent().getLocation();
		this.xOffset = parentLocation.x - e.getX();
		this.yOffset = parentLocation.y - e.getY();

		layeredPane.add(movingPiece, JLayeredPane.DRAG_LAYER);

		updateMovingPieceLocation(e);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(final MouseEvent e) {
		if (this.movingPiece == null) {
			return;
		}
		updateMovingPieceLocation(e);
		final JComponent component = dragSource.getComponent(e);

		if (lastHover != component) {
			UiUtil.setBackground(lastHover, defaultColor);
			UiUtil.setBackground(component, Color.PINK);
			lastHover = component;
		}

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(final MouseEvent e) {
		if (this.movingPiece == null) {
			return;
		}
		this.layeredPane.remove(this.movingPiece);
		this.layeredPane.repaint();

		final Container parent = dragSource.getComponent(e);
		parent.add(this.movingPiece);
		parent.validate();
		this.movingPiece = null;
	}

	/**
	 * @param e
	 * @return
	 */
	private CardUi getCard(final MouseEvent e) {
		Component c = dragSource.getComponent(e);
		while (null != c && !(c instanceof CardUi)) {
			c = c.getParent();
		}
		return (CardUi) c;
	}

	/**
	 * @param e
	 */
	private void updateMovingPieceLocation(final MouseEvent e) {
		this.movingPiece.setLocation(e.getX() + xOffset, e.getY() + yOffset);
	}

}
