/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 13, 2015
 */
final class CardDndListener extends MouseAdapter {

	private final JLayeredPane layeredPane;
	private final JPanel panel;

	private CardUi movingPiece;
	private int xOffset;
	private int yOffset;

	CardDndListener(final JLayeredPane layeredPane, final JPanel pane) {
		this.layeredPane = layeredPane;
		this.panel = pane;
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

		final Container parent = (Container) getComponent(e);
		parent.add(this.movingPiece);
		parent.validate();
		this.movingPiece = null;
	}

	/**
	 * @param e
	 * @return
	 */
	private Component getComponent(final MouseEvent e) {
		return this.panel.findComponentAt(e.getX(), e.getY());
	}

	/**
	 * @param e
	 * @return
	 */
	private CardUi getCard(final MouseEvent e) {
		Component c = getComponent(e);
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
