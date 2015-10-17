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
	private CardUi draggablePiece;

	private Container dropTarget;

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
		this.draggablePiece = card;

		this.draggablePieceParent = card.getParent();

		final Point parentLocation = this.draggablePieceParent.getLocation();
		this.xOffset = parentLocation.x - e.getX();
		this.yOffset = parentLocation.y - e.getY();

		this.dragSource.beginDrag(this.draggablePiece);

		updateMovingPieceLocation(e);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(final MouseEvent e) {
		if (this.draggablePiece == null) {
			return;
		}
		updateMovingPieceLocation(e);
		updateDropIndicator(e);
	}

	/**
	 * @param event
	 */
	private void updateDropIndicator(final MouseEvent event) {
		final Container component = getComponentUnder(event);

		if (this.dropTarget != component) {
			UiUtil.setBackground(this.dropTarget, this.originalColor);

			this.originalColor = UiUtil.getBackground(component);
			UiUtil.setBackground(component, Color.PINK);
			this.dropTarget = component;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(final MouseEvent e) {
		if (this.draggablePiece == null) {
			return;
		}
		this.dragSource.endDrag(this.draggablePiece);

		final Container destination = getComponentUnder(e);
		destination.add(this.draggablePiece);
		destination.validate();
		this.draggablePiece = null;
	}

	/**
	 * @param event
	 * @return
	 */
	private Container getComponentUnder(final MouseEvent event) {
		final JComponent component = dragSource.getComponentAt(event);
		if (null == component) {
			return this.draggablePieceParent;
		}
		return component;
	}

	/**
	 * @param e
	 * @return
	 */
	private CardUi getCard(final MouseEvent e) {
		Component c = this.dragSource.getComponentAt(e);
		while (null != c && !(c instanceof CardUi)) {
			c = c.getParent();
		}
		return (CardUi) c;
	}

	/**
	 * @param e
	 */
	private void updateMovingPieceLocation(final MouseEvent e) {
		this.draggablePiece.setLocation(e.getX() + xOffset, e.getY() + yOffset);
	}

}
