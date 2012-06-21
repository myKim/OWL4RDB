package eventListener;

import java.awt.*;
import java.awt.event.*;

import ui.*;

import component.*;


public class DrawBoardMouseListener implements MouseListener, MouseMotionListener {
	private DrawBoardPanel drawBoard;
	
	/*******************************
	 * 	������
	 *******************************/
	
	public DrawBoardMouseListener(DrawBoardPanel drawBoard) {
		this.drawBoard = drawBoard;
	}
	
	
	
	/*******************************
	 * 	������ �޼���
	 *******************************/
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int width = e.getX() - drawBoard.getFirstX();
		int height = e.getY() - drawBoard.getFirstY();
		
		drawBoard.setDragRectSize(width, height);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {
		drawBoard.setFirstPoint(e.getX(), e.getY());
		drawBoard.deselectAllNode();
		
		drawBoard.setDragging(true);
		drawBoard.setDragRectState(true);
		
		drawBoard.requestFocus();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		drawBoard.setDragging(false);
		drawBoard.setDragRectState(false);
		drawBoard.repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
}