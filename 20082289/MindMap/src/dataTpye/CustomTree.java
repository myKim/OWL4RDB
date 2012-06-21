package dataTpye;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import component.*;
import eventListener.*;

public class CustomTree {
	private NodeLabel root;
	
	/*******************************
	 * 	생성자
	 *******************************/
	
	public CustomTree() {
		root = null;
	}
	

	
	/*******************************
	 * 	메서드
	 *******************************/
	
	// 트리가 비어있는지 반환
	public boolean isEmpty() {
		if(root == null)
			return true;
		
		return false;
	}
	
	// 트리를 비움
	public void clearTree(Vector<NodeLabel> selectedNodeSet) {
		deleteAllNode(root, selectedNodeSet);
		root = null;
	}
	
	// 트리에서 노드를 삭제
	public void deleteNode(Vector<NodeLabel> selectedNodeSet) {
		NodeLabel node, parentNode;
		
		while(selectedNodeSet.size() > 0) {
			node = selectedNodeSet.remove(0);
			parentNode = findParentNode(root, node);
			
			// 노드가 루트 일때
			if(parentNode == null) {
				deleteAllNode(node, selectedNodeSet);
				root = null;
			}
			// 노드가 루트가 아닐 때
			else {
				// 부모에서 라인 제거
				parentNode.removeLine(node);
				 
				// 링크 조정
				if(node == parentNode.getLeftChild()) {
					parentNode.setLeftChild(node.getRightSibling());
				}
				else {
					NodeLabel tempNode = parentNode.getLeftChild();
					while(tempNode.getRightSibling() != node) {
						tempNode = tempNode.getRightSibling();
					}
					tempNode.setRightSibling(node.getRightSibling());
				}
				node.setRightSibling(null);
				deleteAllNode(node, selectedNodeSet);
			}
		}
	}
	
	// 현재 노드에서 자식노드를 포함 모두 삭제
	private void deleteAllNode(NodeLabel node, Vector<NodeLabel> selectedNodeSet) {
		if(node == null)
			return;
		
		deleteAllNode(node.getLeftChild(), selectedNodeSet);
		deleteAllNode(node.getRightSibling(), selectedNodeSet);
		
		// 선택된 노드 목록에서 삭제
		if(selectedNodeSet.contains(node)) {
			selectedNodeSet.remove(node);
		}
		node.delete();
		
	}
	
	
	// 부모 노드 찾기
	private NodeLabel findParentNode(NodeLabel node, NodeLabel findNode) {
		if(node == null)
			return null;
		if(node.isParent(findNode)) 
			return node;
		
		NodeLabel tempNode = null;
		
		if((tempNode = findParentNode(node.getLeftChild(), findNode)) != null) {
			if(tempNode.isParent(findNode))
				return tempNode;
		}
		if((tempNode = findParentNode(node.getRightSibling(), findNode)) != null) {
			if(tempNode.isParent(findNode))
				return tempNode;
		}
		
		return null;
	}
	
	// 루트 내의 모든 라인 그리기
	public void drawAllLine(Graphics2D g2) {
		drawAllLine(root, g2);
	}
	private void drawAllLine(NodeLabel node, Graphics2D g2) {
		if(node == null)
			return;
		
		node.drawLine(g2);
		
		drawAllLine(node.getLeftChild(), g2);
		drawAllLine(node.getRightSibling(), g2);
	}
	
	// 드래그 사각형 안의 노드들을 선택
	public void selectNodeInDragRect(Rectangle dragRect, Vector<NodeLabel> selectedNodeSet) {
		selectNodeInDragRect(root, dragRect, selectedNodeSet);
	}
	private void selectNodeInDragRect(NodeLabel node, Rectangle dragRect, Vector<NodeLabel> selectedNodeSet) {
		if(node == null)
			return;
		
		if(dragRect.contains(node.getCenterPoint()) && !node.isSelected()) {
			node.select();
			selectedNodeSet.add(node);
		}
		else if(!dragRect.contains(node.getCenterPoint()) && node.isSelected()) {
			node.deselect();
			selectedNodeSet.remove(node);
		}
		
		if(node.getLeftChild() != null) {
			selectNodeInDragRect(node.getLeftChild(), dragRect, selectedNodeSet);
		}
		
		if(node.getRightSibling() != null) {
			selectNodeInDragRect(node.getRightSibling(), dragRect, selectedNodeSet);
		}
	}
	
	// 노드 패널에 추가 : 로드
	public void addAllNodeToPanel(DrawBoardPanel panel) {
		addAllNodeToPanel(root, panel);
	}
	private void addAllNodeToPanel(NodeLabel node, DrawBoardPanel panel) {
		if(node == null)
			return;
		
		// 이벤트 리스너 등록
		NodeMouseListener nodeMouseListener = new NodeMouseListener(panel, node);
		node.addMouseListener(nodeMouseListener);
		node.addMouseMotionListener(nodeMouseListener);
		// 패널에 추가
		panel.add(node);
		
		addAllNodeToPanel(node.getLeftChild(), panel);
		addAllNodeToPanel(node.getRightSibling(), panel);
	}


	
	/*******************************
	 * 	Getter, Setter
	 *******************************/
	
	public void setRoot(NodeLabel data) {
		root = data;
	}
	public NodeLabel getRoot() {
		return root;
	}
}
