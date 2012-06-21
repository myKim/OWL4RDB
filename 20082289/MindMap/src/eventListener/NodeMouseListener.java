package eventListener;


import java.awt.*;
import java.awt.event.*;

import component.*;
import component.NodeLabel.ResizeState;


public class NodeMouseListener implements MouseListener, MouseMotionListener {
	// �巡�� ����Ʈ �ʺ�
	private final int DRAG_POINT_WIDTH = 6;
	
	private DrawBoardPanel panel;
	private NodeLabel node;

	/*******************************
	 * 	������
	 *******************************/
	
	public NodeMouseListener(DrawBoardPanel panel, NodeLabel node) {
		this.panel = panel;
		this.node = node;
	}
	
	
	
	/*******************************
	 * 	�޼���
	 *******************************/
	
	@Override
	public void mouseDragged(MouseEvent e) {
		switch(node.getResizeState()) {
		case EAST:
			// ���ϴ� �ʺ� �ּҰ����� Ŭ ��
			if(node.getWidth() + (e.getX() - node.getPrevX()) > node.getMinimumWidth()) {
				node.setSize(node.getWidth() + (e.getX() - node.getPrevX()), node.getHeight());
				node.setPrevX(e.getX());
			}
			// ���ϴ� �ʺ� �ּҰ����� ���� ��
			else {
				node.setSize(node.getMinimumWidth() , node.getHeight());
				node.setPrevX(node.getMinimumWidth() - 1);
			}
			break;
			
		case WEST:
			// ���ϴ� �ʺ� �ּҰ����� Ŭ ��
			if(	node.getWidth() + (node.getPrevX() - e.getX()) > node.getMinimumWidth()) {
				node.setBounds(node.getX() + (	e.getX() - node.getPrevX()), node.getY(), 
												node.getWidth() + (node.getPrevX() - e.getX()), node.getHeight());
			}
			// ���ϴ� �ʺ� �ּҰ����� ���� ��
			else {
				node.setBounds(	node.getFirstX() + (node.getFirstWidth() - node.getMinimumWidth()), node.getY(), 
								node.getMinimumWidth(), node.getHeight());
			}
			break;
			
		case SOUTH:
			// ���ϴ� ���̰� �ּҰ����� Ŭ ��
			if(	node.getHeight() + (e.getY() - node.getPrevY()) > node.getMinimumHeight()) {
				node.setSize(node.getWidth(), node.getHeight() + (e.getY() - node.getPrevY()));
				node.setPrevY(e.getY());
			}
			// ���ϴ� ���̰� �ּҰ����� ���� ��
			else {
				node.setSize(node.getWidth(), node.getMinimumHeight());
				node.setPrevY(node.getMinimumHeight() - 1);
			}
			break;
			
		case NORTH:
			// ���ϴ� ���̰� �ּҰ����� Ŭ ��
			if(	node.getHeight() + (node.getPrevY() - e.getY()) > node.getMinimumHeight()) {
				node.setBounds(	node.getX(), node.getY() + (e.getY() - node.getPrevY()), 
								node.getWidth(), node.getHeight() + (node.getPrevY() - e.getY()));
			}
			// ���ϴ� ���̰� �ּҰ����� ���� ��
			else {
				node.setBounds(node.getX(), node.getFirstY() + (node.getFirstHeight() - node.getMinimumHeight()), 
												node.getWidth(), node.getMinimumHeight());
			}
			break;
			
		case SOUTH_EAST:
			// �ʺ� �ּҰ� �̻� ���� �ּҰ� �̻�
			if(		(node.getWidth() + (e.getX() - node.getPrevX()) > node.getMinimumWidth()) &&
					(node.getHeight() + (e.getY() - node.getPrevY()) > node.getMinimumHeight())	) {
				node.setSize(node.getWidth() + (e.getX() - node.getPrevX()), node.getHeight() + (e.getY() - node.getPrevY()));
				node.setPrevX(e.getX());
				node.setPrevY(e.getY());
			}
			// �ʺ� �ּҰ� �̻� ���� �ּҰ� ����
			else if(node.getWidth() + (e.getX() - node.getPrevX()) > node.getMinimumWidth()) {
				node.setSize(node.getWidth() + (e.getX() - node.getPrevX()), node.getMinimumHeight());
				node.setPrevX(e.getX());
				node.setPrevY(node.getMinimumHeight() - 1);
			}
			// �ʺ� �ּҰ� ���� ���� �ּҰ� �̻�
			else if(node.getHeight() + (e.getY() - node.getPrevY()) > node.getMinimumHeight()) {
				node.setSize(node.getMinimumWidth(), node.getHeight() + (e.getY() - node.getPrevY()));
				node.setPrevX(node.getMinimumWidth() - 1);
				node.setPrevY(e.getY());
			}
			// �ʺ� �ּҰ� ���� ���� �ּҰ� ����
			else {
				node.setSize(node.getMinimumWidth(), node.getMinimumHeight());
				node.setPrevX(node.getMinimumWidth() - 1);
				node.setPrevY(node.getMinimumHeight() - 1);
			}
			break;
			
		case NORTH_EAST:
			// �ʺ� �ּҰ� �̻� ���� �ּҰ� �̻�
			if(		(node.getWidth() + (e.getX() - node.getPrevX()) > node.getMinimumWidth()) &&
					(node.getHeight() + (node.getPrevY() - e.getY()) > node.getMinimumHeight())) {
				node.setBounds(	node.getX(), node.getY() + (e.getY() - node.getPrevY()), 
								node.getWidth() + (e.getX() - node.getPrevX()), node.getHeight() + (node.getPrevY() - e.getY()));
				node.setPrevX(e.getX());
			}
			// �ʺ� �ּҰ� �̻� ���� �ּҰ� ����
			else if(node.getWidth() + (e.getX() - node.getPrevX()) > node.getMinimumWidth()) {
				node.setBounds(	node.getX(), node.getFirstY() + (node.getFirstHeight() - node.getMinimumHeight()), 
								node.getWidth() + (e.getX() - node.getPrevX()), node.getMinimumHeight());
				node.setPrevX(e.getX());
			}
			// �ʺ� �ּҰ� ���� ���� �ּҰ� �̻�
			else if(node.getHeight() + (node.getPrevY() - e.getY()) > node.getMinimumHeight()) {
				node.setBounds(	node.getX(), node.getY() + (e.getY() - node.getPrevY()), 
						node.getMinimumWidth(), node.getHeight() + (node.getPrevY() - e.getY()));
				node.setPrevX(node.getMinimumWidth() - 1);
			}
			// �ʺ� �ּҰ� ���� ���� �ּҰ� ����
			else {
				node.setBounds(	node.getX(), node.getFirstY() + (node.getFirstHeight() - node.getMinimumHeight()), 
								node.getMinimumWidth(), node.getMinimumHeight());
				node.setPrevX(node.getMinimumWidth() - 1);
			}
			break;
			
		case SOUTH_WEST:
			// �ʺ� �ּҰ� �̻� ���� �ּҰ� �̻�
			if(		(node.getWidth() + (node.getPrevX() - e.getX()) > node.getMinimumWidth()) && 
					(node.getHeight() + (e.getY() - node.getPrevY()) > node.getMinimumHeight())) {
				node.setBounds(node.getX() + (	e.getX() - node.getPrevX()), node.getY(), 
						node.getWidth() + (node.getPrevX() - e.getX()), node.getHeight() + (e.getY() - node.getPrevY()));
				node.setPrevY(e.getY());
			}
			// �ʺ� �ּҰ� �̻� ���� �ּҰ� ����
			else if(node.getWidth() + (node.getPrevX() - e.getX()) > node.getMinimumWidth()) {
				node.setBounds(node.getX() + (	e.getX() - node.getPrevX()), node.getY(), 
						node.getWidth() + (node.getPrevX() - e.getX()), node.getMinimumHeight());
				node.setPrevY(node.getMinimumHeight() - 1);
			}
			// �ʺ� �ּҰ� ���� ���� �ּҰ� �̻�
			else if(node.getHeight() + (e.getY() - node.getPrevY()) > node.getMinimumHeight()) {
				node.setBounds(node.getFirstX() + (node.getFirstWidth() - node.getMinimumWidth()), node.getY(), 
						node.getMinimumWidth(), node.getHeight() + (e.getY() - node.getPrevY()));
				node.setPrevY(e.getY());
			}
			
			// �ʺ� �ּҰ� ���� ���� �ּҰ� ����
			else {
				node.setBounds(node.getFirstX() + (node.getFirstWidth() - node.getMinimumWidth()), node.getY(), 
						node.getMinimumWidth(), node.getMinimumHeight());
				node.setPrevY(node.getMinimumHeight() - 1);
			}
			break;
			
		case NORTH_WEST:
			// �ʺ� �ּҰ� �̻� ���� �ּҰ� �̻�
			if(		(node.getWidth() + (node.getPrevX() - e.getX()) > node.getMinimumWidth()) &&
					(node.getHeight() + (node.getPrevY() - e.getY()) > node.getMinimumHeight())) {
				node.setBounds(node.getX() + (	e.getX() - node.getPrevX()), node.getY() + (e.getY() - node.getPrevY()), 
						node.getWidth() + (node.getPrevX() - e.getX()), node.getHeight() + (node.getPrevY() - e.getY()));
			}
			// �ʺ� �ּҰ� �̻� ���� �ּҰ� ����
			else if(node.getWidth() + (node.getPrevX() - e.getX()) > node.getMinimumWidth()) {
				node.setBounds(node.getX() + (	e.getX() - node.getPrevX()), node.getFirstY() + (node.getFirstHeight() - node.getMinimumHeight()), 
						node.getWidth() + (node.getPrevX() - e.getX()), node.getMinimumHeight());
			}	
			// �ʺ� �ּҰ� ���� ���� �ּҰ� �̻�
			else if(node.getHeight() + (node.getPrevY() - e.getY()) > node.getMinimumHeight()) {
				node.setBounds(node.getFirstX() + (node.getFirstWidth() - node.getMinimumWidth()), node.getY() + (e.getY() - node.getPrevY()), 
						node.getMinimumWidth(), node.getHeight() + (node.getPrevY() - e.getY()));
			}
			// �ʺ� �ּҰ� ���� ���� �ּҰ� ����
			else {
				node.setBounds(node.getFirstX() + (	node.getFirstWidth() - node.getMinimumWidth()), 
													node.getFirstY() + (node.getFirstHeight() - node.getMinimumHeight()), 
													node.getMinimumWidth(), 
													node.getMinimumHeight());
			}
			break;
			
		default: // ��ü �̵�
			if(panel.getMousePosition() != null) {
				NodeLabel tempNode;
				int widthGap = (panel.getMousePosition().x - node.getPrevX()) - node.getX();
				int heightGap = (panel.getMousePosition().y - node.getPrevY()) - node.getY();
				
				
				for(int i=0; i<panel.getSelectedNodeSet().size(); i++) {
					tempNode = panel.getSelectedNodeSet().get(i);	
					tempNode.setLocation(tempNode.getX() + widthGap, tempNode.getY() + heightGap);
				}
			}
			break;
		}
		panel.repaint();
		panel.updateAttribute();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!panel.isDragging()) {
			// EAST NORTH
			if((e.getX() > node.getWidth() - DRAG_POINT_WIDTH) && (e.getY() < DRAG_POINT_WIDTH))
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			// EAST SOUTH
			else if((e.getX() > node.getWidth() - DRAG_POINT_WIDTH) && (e.getY() > node.getHeight() - DRAG_POINT_WIDTH))
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
			// WEST NORTH
			else if((e.getX() < DRAG_POINT_WIDTH) && (e.getY() < DRAG_POINT_WIDTH))
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			// WEST SOUTH
			else if((e.getX() < DRAG_POINT_WIDTH) && (e.getY() > node.getHeight() - DRAG_POINT_WIDTH))
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
			// EAST
			else if(e.getX() > node.getWidth() - DRAG_POINT_WIDTH)
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			// WEST
			else if(e.getX() < DRAG_POINT_WIDTH)
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
			// SOUTH
			else if(e.getY() > node.getHeight() - DRAG_POINT_WIDTH)
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
			// NORTH
			else if(e.getY() < DRAG_POINT_WIDTH)
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			else {
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {
		panel.requestFocus();
		
		// ���� ���� ��� ��� ����
		if(!node.isSelected()) {
			panel.deselectAllNode();
			panel.addSelectNode(node);
		}
		
		// ����(Pressed)�� ��ǥ ����
		node.setPrevX(e.getX());
		node.setPrevY(e.getY());
		node.setFirstX(node.getX());
		node.setFirstY(node.getY());
		node.setFirstWidth(node.getWidth());
		node.setFirstHeight(node.getHeight());
		
		// EAST NORTH
		if((e.getX() > node.getWidth() - DRAG_POINT_WIDTH) && (e.getY() < DRAG_POINT_WIDTH)){
			node.setResizeState(ResizeState.NORTH_EAST);
			panel.setDragging(true);
		}
		// EAST SOUTH
		else if((e.getX() > node.getWidth() - DRAG_POINT_WIDTH) && (e.getY() > node.getHeight() - DRAG_POINT_WIDTH)) {
			node.setResizeState(ResizeState.SOUTH_EAST);
			panel.setDragging(true);
		}
		// WEST NORTH
		else if((e.getX() < DRAG_POINT_WIDTH) && (e.getY() < DRAG_POINT_WIDTH)) {
			node.setResizeState(ResizeState.NORTH_WEST);
			panel.setDragging(true);
		}
		// WEST SOUTH
		else if((e.getX() < DRAG_POINT_WIDTH) && (e.getY() > node.getHeight() - DRAG_POINT_WIDTH)) {
			node.setResizeState(ResizeState.SOUTH_WEST);
			panel.setDragging(true);
		}
		// EAST
		else if(e.getX() > node.getWidth() - DRAG_POINT_WIDTH) {
			node.setResizeState(ResizeState.EAST);
			panel.setDragging(true);
		}
		// WEST
		else if(e.getX() < DRAG_POINT_WIDTH) {
			node.setResizeState(ResizeState.WEST);
			panel.setDragging(true);
		}
		// SOUTH
		else if(e.getY() > node.getHeight() - DRAG_POINT_WIDTH) {
			node.setResizeState(ResizeState.SOUTH);
			panel.setDragging(true);
		}
		// NORTH
		else if(e.getY() < DRAG_POINT_WIDTH) {
			node.setResizeState(ResizeState.NORTH);
			panel.setDragging(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		panel.setDragging(false);
		node.setResizeState(ResizeState.MAINTAIN);
		
		if(e.getX() > node.getWidth() || e.getX() < 0 || e.getY() > node.getHeight() || e.getY() <0)
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {
		if(!panel.isDragging()) {
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
}