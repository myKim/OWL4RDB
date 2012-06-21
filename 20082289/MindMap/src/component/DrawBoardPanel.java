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
	 * 	멤버 변수
	 *******************************/
	
	private MindMapUI mindmapUI;
	private ResourceManager rscManager;
	
	// 노드 트리, 선택된 노드 자료구조
	private CustomTree nodeTree;
	private Vector<NodeLabel> selectedNodeSet;
	
	// 패널 안 드래그 플래그
	private boolean dragState;
	private boolean dragRectState;
	
	// 드래그 사각형 정보
	private int firstX, firstY;
	private int dragRectWidth, dragRectHeight;
	
	
	
	/*******************************
	 * 	생성자
	 *******************************/
	
	//// 생성자
	public DrawBoardPanel(MindMapUI mindmapUI, ResourceManager rscManager) {
		super(null);
		
		this.mindmapUI = mindmapUI;
		this.rscManager = rscManager;
		init();
	}

	//// 초기화 메서드
	public void init() {
		rscManager.setMindMapPanel(this);
		nodeTree = rscManager.getDataTree();
		selectedNodeSet = new Vector<NodeLabel>();
		
		dragState = false;
		dragRectState = false;
		dragRectWidth = 0;
		dragRectHeight = 0;
		
		// 마우스 리스너 등록
		DrawBoardMouseListener drawBoardMouseListener = new DrawBoardMouseListener(this);
		this.addMouseListener(drawBoardMouseListener);
		this.addMouseMotionListener(drawBoardMouseListener);
		
		this.setBackground(Color.WHITE);
	}
	
	
	
	/*******************************
	 * 	메서드
	 *******************************/
	
	// 그리기 메서드
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// 안티 엘리어싱
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// 노드 라인 그리기
		nodeTree.drawAllLine(g2);
		
		// 드래그 사각형 그리기
		if(dragRectState) {
			g2.setColor(Color.green);
			drawDragRect(g2);
		}
		
		// 안티 엘리어싱 끄기
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
	}
	
	
	// 드래그 사각형을 그린다 : paintComponent에서 호출
	private void drawDragRect(Graphics g2) {
		Rectangle dragRect = getDragRect();
		g2.drawRect(dragRect.x, dragRect.y, dragRect.width, dragRect.height);
	}
	
	// 드래그 사각형의 크기를 설정 : DrawBoardMouseListener.mouseDragged에서 호출
	public void setDragRectSize(int width, int height) {
		dragRectWidth = width;
		dragRectHeight = height;
		
		// 드래그 사각형 안의 노드들 선택
		selectNodeInDragRect();
		
		// 드래그 사각형 변한 크기 화면에 갱신해줌
		repaint();
	}
	
	// 드래그 사각형 내부의 노드를 선택
	private void selectNodeInDragRect() {
		nodeTree.selectNodeInDragRect(getDragRect(), selectedNodeSet);
		mindmapUI.updateAttribute(selectedNodeSet);
	}
	
	// 현재의 드래그 사각형 반환
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
	
	// 모든 노드 선택 해제
	public void deselectAllNode() { 
		for(int i=0; i<selectedNodeSet.size(); i++) {
			selectedNodeSet.get(i).deselect();
		}
		selectedNodeSet.clear();
		updateAttribute();
	}
	
	// 노드 선택 리스트에 추가
	public void addSelectNode(NodeLabel selectedNode) {
		selectedNode.select();
		selectedNodeSet.add(selectedNode);
		
		mindmapUI.updateAttribute(selectedNodeSet);
	}
	
	// 트리 노드 생성
	public void createNode() {
		NodeLabel newNode;
		
		// 트리가 비었을 때 : 루트 생성
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
		// 루트가 존재할 때
		else if(selectedNodeSet.size() == 1) {
			if(selectedNodeSet.get(0).getEmptySpaceNumber() != 0) {
				newNode = createNodeLabel(false);
				selectedNodeSet.get(0).addChildNode(newNode);
				
				this.add(newNode);
				repaint();
			}
		}
	}
	
	// 노드 레이블 생성 : createNode()에서 호출
	private NodeLabel createNodeLabel(boolean isRoot) {
		NodeLabel newNode;
		if(isRoot)
			newNode = new NodeLabel("루트");
		else
			newNode = new NodeLabel("노드", 3);
		
		NodeMouseListener nodeMouseListener = new NodeMouseListener(this, newNode);
		newNode.addMouseListener(nodeMouseListener);
		newNode.addMouseMotionListener(nodeMouseListener);
	
		return newNode;
	}
	
	// 속성 패널(Attribute Panel) 업데이트 : MindMapUI
	public void updateAttribute() {
		mindmapUI.updateAttribute(selectedNodeSet);
	}
	
	// 속성 패널(Attribute Panel) 내용 적용 : MindMapUI에서 호출
	public void adaptAttribute(int x, int y, int width, int height, String text) {
		if(selectedNodeSet.size() == 1) {
			NodeLabel node = selectedNodeSet.get(0);
			node.setBounds(x, y, width, height);
			node.setText(text);
			repaint();
		}
	}
	
	// 현재 선택된 노드 삭제 : MindMapUI에서 호출
	public void deleteSelectedNode() {
		if(selectedNodeSet.size() > 0) {
			nodeTree.deleteNode(selectedNodeSet);
			
			mindmapUI.updateAttribute(selectedNodeSet);
			repaint();
		}
	}
	
	// 전체 노드 삭제 : MindMapUI '새파일' 함수에서 호출
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

	// 현재 드래그 사각형 이벤트 플래그
	public boolean isDragRectState() {
		return dragRectState;
	}
	public void setDragRectState(boolean dragRectState) {
		this.dragRectState = dragRectState;
	}

	// 현재 드래그 이벤트 플래그
	public boolean isDragging() {
		return dragState;
	}
	public void setDragging(boolean bool) {
		this.dragState = bool;
	}
}