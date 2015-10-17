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

	private final DragSource dragSource;

	private Container draggablePieceParent;
	private CardUi draggablePiece;

	private Container dropTarget;
	private Color dropTargetOriginalColor;

	private int xOffset;
	private int yOffset;


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
		final Container component = getComponentOrInitialLocation(event);

		if (this.dropTarget != component) {
			UiUtil.setBackground(this.dropTarget, this.dropTargetOriginalColor);

			this.dropTargetOriginalColor = UiUtil.getBackground(component);
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

		final Container destination = getComponentOrInitialLocation(e);
		destination.add(this.draggablePiece);
		destination.validate();
		this.draggablePiece = null;
	}

	/**
	 * @param event
	 * @return
	 */
	private Container getComponentOrInitialLocation(final MouseEvent event) {
		final JComponent component = getComponentAt(event);
		if (null == component) {
			return this.draggablePieceParent;
		}
		return component;
	}

	/**
	 * @param event
	 * @return
	 */
	private CardUi getCard(final MouseEvent event) {
		Component c = getComponentAt(event);
		while (null != c && !(c instanceof CardUi)) {
			c = c.getParent();
		}
		return (CardUi) c;
	}

	/**
	 * @param event
	 * @return
	 */
	private JComponent getComponentAt(final MouseEvent event) {
		return this.dragSource.getComponentAt(event.getX(), event.getY());
	}

	/**
	 * @param event
	 */
	private void updateMovingPieceLocation(final MouseEvent event) {
		this.draggablePiece.setLocation(event.getX() + xOffset, event.getY() + yOffset);
	}

}
