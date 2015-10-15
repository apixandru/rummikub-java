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

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 13, 2015
 */
final class CardDndListener extends MouseAdapter {

	private Container draggablePieceParent;

	private JComponent lastHover;

	private CardUi movingPiece;
	private int xOffset;
	private int yOffset;
	private Color originalColor;

	private final DragSource dragSource;

	/**
	 * @param dragSource
	 */
	CardDndListener(final DragSource dragSource) {
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

		this.draggablePieceParent = card.getParent();

		final Point parentLocation = this.draggablePieceParent.getLocation();
		this.xOffset = parentLocation.x - e.getX();
		this.yOffset = parentLocation.y - e.getY();

		this.dragSource.beginDrag(this.movingPiece);

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
		final JComponent component = dragSource.getComponentAt(e);

		if (lastHover != component) {
			UiUtil.setBackground(lastHover, this.originalColor);

			this.originalColor = UiUtil.getBackground(component);
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
		this.dragSource.endDrag(this.movingPiece);

		final Container destination = dragSource.getComponentAt(e);
		destination.add(this.movingPiece);
		destination.validate();
		this.movingPiece = null;
	}

	/**
	 * @param e
	 * @return
	 */
	private CardUi getCard(final MouseEvent e) {
		Component c = dragSource.getComponentAt(e);
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
