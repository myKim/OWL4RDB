package dataTpye;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import component.*;
import eventListener.*;

public class CustomTree {
	private NodeLabel root;
	
	/*******************************
	 * 	������
	 *******************************/
	
	public CustomTree() {
		root = null;
	}
	

	
	/*******************************
	 * 	�޼���
	 *******************************/
	
	// Ʈ���� ����ִ��� ��ȯ
	public boolean isEmpty() {
		if(root == null)
			return true;
		
		return false;
	}
	
	// Ʈ���� ���
	public void clearTree(Vector<NodeLabel> selectedNodeSet) {
		deleteAllNode(root, selectedNodeSet);
		root = null;
	}
	
	// Ʈ������ ��带 ����
	public void deleteNode(Vector<NodeLabel> selectedNodeSet) {
		NodeLabel node, parentNode;
		
		while(selectedNodeSet.size() > 0) {
			node = selectedNodeSet.remove(0);
			parentNode = findParentNode(root, node);
			
			// ��尡 ��Ʈ �϶�
			if(parentNode == null) {
				deleteAllNode(node, selectedNodeSet);
				root = null;
			}
			// ��尡 ��Ʈ�� �ƴ� ��
			else {
				// �θ𿡼� ���� ����
				parentNode.removeLine(node);
				 
				// ��ũ ����
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
	
	// ���� ��忡�� �ڽĳ�带 ���� ��� ����
	private void deleteAllNode(NodeLabel node, Vector<NodeLabel> selectedNodeSet) {
		if(node == null)
			return;
		
		deleteAllNode(node.getLeftChild(), selectedNodeSet);
		deleteAllNode(node.getRightSibling(), selectedNodeSet);
		
		// ���õ� ��� ��Ͽ��� ����
		if(selectedNodeSet.contains(node)) {
			selectedNodeSet.remove(node);
		}
		node.delete();
		
	}
	
	
	// �θ� ��� ã��
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
	
	// ��Ʈ ���� ��� ���� �׸���
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
	
	// �巡�� �簢�� ���� ������ ����
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
	
	// ��� �гο� �߰� : �ε�
	public void addAllNodeToPanel(DrawBoardPanel panel) {
		addAllNodeToPanel(root, panel);
	}
	private void addAllNodeToPanel(NodeLabel node, DrawBoardPanel panel) {
		if(node == null)
			return;
		
		// �̺�Ʈ ������ ���
		NodeMouseListener nodeMouseListener = new NodeMouseListener(panel, node);
		node.addMouseListener(nodeMouseListener);
		node.addMouseMotionListener(nodeMouseListener);
		// �гο� �߰�
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
