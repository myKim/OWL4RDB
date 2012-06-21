package component;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import javax.swing.*;

import dataTpye.*;

import manager.*;

import ui.*;
import eventListener.*;

public class DrawBoardPanel extends JPanel {
	/*******************************
	 * 	��� ����
	 *******************************/
	
	private MindMapUI mindmapUI;
	private ResourceManager rscManager;
	
	// ��� Ʈ��, ���õ� ��� �ڷᱸ��
	private CustomTree nodeTree;
	private Vector<NodeLabel> selectedNodeSet;
	
	// �г� �� �巡�� �÷���
	private boolean dragState;
	private boolean dragRectState;
	
	// �巡�� �簢�� ����
	private int firstX, firstY;
	private int dragRectWidth, dragRectHeight;
	
	
	
	/*******************************
	 * 	������
	 *******************************/
	
	//// ������
	public DrawBoardPanel(MindMapUI mindmapUI, ResourceManager rscManager) {
		super(null);
		
		this.mindmapUI = mindmapUI;
		this.rscManager = rscManager;
		init();
	}

	//// �ʱ�ȭ �޼���
	public void init() {
		rscManager.setMindMapPanel(this);
		nodeTree = rscManager.getDataTree();
		selectedNodeSet = new Vector<NodeLabel>();
		
		dragState = false;
		dragRectState = false;
		dragRectWidth = 0;
		dragRectHeight = 0;
		
		// ���콺 ������ ���
		DrawBoardMouseListener drawBoardMouseListener = new DrawBoardMouseListener(this);
		this.addMouseListener(drawBoardMouseListener);
		this.addMouseMotionListener(drawBoardMouseListener);
		
		this.setBackground(Color.WHITE);
	}
	
	
	
	/*******************************
	 * 	�޼���
	 *******************************/
	
	// �׸��� �޼���
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// ��Ƽ �������
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// ��� ���� �׸���
		nodeTree.drawAllLine(g2);
		
		// �巡�� �簢�� �׸���
		if(dragRectState) {
			g2.setColor(Color.green);
			drawDragRect(g2);
		}
		
		// ��Ƽ ������� ����
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
	}
	
	
	// �巡�� �簢���� �׸��� : paintComponent���� ȣ��
	private void drawDragRect(Graphics g2) {
		Rectangle dragRect = getDragRect();
		g2.drawRect(dragRect.x, dragRect.y, dragRect.width, dragRect.height);
	}
	
	// �巡�� �簢���� ũ�⸦ ���� : DrawBoardMouseListener.mouseDragged���� ȣ��
	public void setDragRectSize(int width, int height) {
		dragRectWidth = width;
		dragRectHeight = height;
		
		// �巡�� �簢�� ���� ���� ����
		selectNodeInDragRect();
		
		// �巡�� �簢�� ���� ũ�� ȭ�鿡 ��������
		repaint();
	}
	
	// �巡�� �簢�� ������ ��带 ����
	private void selectNodeInDragRect() {
		nodeTree.selectNodeInDragRect(getDragRect(), selectedNodeSet);
		mindmapUI.updateAttribute(selectedNodeSet);
	}
	
	// ������ �巡�� �簢�� ��ȯ
	private Rectangle getDragRect() {
		int x, y, width, height;
			
		int absWidth = Math.abs(dragRectWidth);
		int absHeight = Math.abs(dragRectHeight);
		
		if(dragRectWidth >= 0 && dragRectHeight >= 0) {
			x = firstX;
			y = firstY;
			width = absWidth;
			height = absHeight;
		}
		else if(dragRectWidth >= 0) {
			x = firstX;
			y = firstY - absHeight;
			width = absWidth;
			height = absHeight;
		}
		else if(dragRectHeight >= 0) {
			x = firstX - absWidth;
			y = firstY;
			width = absWidth;
			height = absHeight;
		}
		else {
			x = firstX - absWidth;
			y = firstY - absHeight;
			width = absWidth;
			height = absHeight;
		}
		
		return new Rectangle(x, y, width, height);
	}
	
	// ��� ��� ���� ����
	public void deselectAllNode() { 
		for(int i=0; i<selectedNodeSet.size(); i++) {
			selectedNodeSet.get(i).deselect();
		}
		selectedNodeSet.clear();
		updateAttribute();
	}
	
	// ��� ���� ����Ʈ�� �߰�
	public void addSelectNode(NodeLabel selectedNode) {
		selectedNode.select();
		selectedNodeSet.add(selectedNode);
		
		mindmapUI.updateAttribute(selectedNodeSet);
	}
	
	// Ʈ�� ��� ����
	public void createNode() {
		NodeLabel newNode;
		
		// Ʈ���� ����� �� : ��Ʈ ����
		if(nodeTree.isEmpty()) {
			newNode = createNodeLabel(true);
			newNode.setLocation(350, 250);
			newNode.setDefaultColor(Color.GREEN);
			newNode.setSelectedColor(Color.YELLOW);
			newNode.setBackground(newNode.getDefaultColor());
			nodeTree.setRoot(newNode);
			
			this.add(newNode);
			repaint();
		}
		// ��Ʈ�� ������ ��
		else if(selectedNodeSet.size() == 1) {
			if(selectedNodeSet.get(0).getEmptySpaceNumber() != 0) {
				newNode = createNodeLabel(false);
				selectedNodeSet.get(0).addChildNode(newNode);
				
				this.add(newNode);
				repaint();
			}
		}
	}
	
	// ��� ���̺� ���� : createNode()���� ȣ��
	private NodeLabel createNodeLabel(boolean isRoot) {
		NodeLabel newNode;
		if(isRoot)
			newNode = new NodeLabel("��Ʈ");
		else
			newNode = new NodeLabel("���", 3);
		
		NodeMouseListener nodeMouseListener = new NodeMouseListener(this, newNode);
		newNode.addMouseListener(nodeMouseListener);
		newNode.addMouseMotionListener(nodeMouseListener);
	
		return newNode;
	}
	
	// �Ӽ� �г�(Attribute Panel) ������Ʈ : MindMapUI
	public void updateAttribute() {
		mindmapUI.updateAttribute(selectedNodeSet);
	}
	
	// �Ӽ� �г�(Attribute Panel) ���� ���� : MindMapUI���� ȣ��
	public void adaptAttribute(int x, int y, int width, int height, String text) {
		if(selectedNodeSet.size() == 1) {
			NodeLabel node = selectedNodeSet.get(0);
			node.setBounds(x, y, width, height);
			node.setText(text);
			repaint();
		}
	}
	
	// ���� ���õ� ��� ���� : MindMapUI���� ȣ��
	public void deleteSelectedNode() {
		if(selectedNodeSet.size() > 0) {
			nodeTree.deleteNode(selectedNodeSet);
			
			mindmapUI.updateAttribute(selectedNodeSet);
			repaint();
		}
	}
	
	// ��ü ��� ���� : MindMapUI '������' �Լ����� ȣ��
	public void clearTree() {
		nodeTree.clearTree(selectedNodeSet);
	}
	
	
	
	/*******************************
	 * 	Getter, Setter
	 *******************************/
	
	//// Getter, Setter
	public Vector<NodeLabel> getSelectedNodeSet() {
		return selectedNodeSet;
	}
	public void setSelectedNodeSet(Vector<NodeLabel> selectedNodeSet) {
		this.selectedNodeSet = selectedNodeSet;
	}

	public void setFirstPoint(int firstX, int firstY) {
		this.firstX = firstX;
		this.firstY = firstY;
	}
	public int getFirstX() {
		return firstX;
	}
	public int getFirstY() {
		return firstY;
	}

	// ���� �巡�� �簢�� �̺�Ʈ �÷���
	public boolean isDragRectState() {
		return dragRectState;
	}
	public void setDragRectState(boolean dragRectState) {
		this.dragRectState = dragRectState;
	}

	// ���� �巡�� �̺�Ʈ �÷���
	public boolean isDragging() {
		return dragState;
	}
	public void setDragging(boolean bool) {
		this.dragState = bool;
	}
}