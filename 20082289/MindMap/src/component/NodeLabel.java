package component;

import java.awt.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.*;

import dataTpye.*;
import dataTpye.LineInfo.*;


public class NodeLabel extends JLabel implements Serializable {
	/*******************************
	 * 	���, ������(enum)
	 *******************************/
	
	public static enum ResizeState {MAINTAIN ,EAST, WEST, SOUTH, NORTH, SOUTH_EAST, SOUTH_WEST, NORTH_EAST, NORTH_WEST}
	// ���� ���� ������ ����
	private final int SPACE_NUMBER = 4;
	// ����
	public static final int EAST = 0;
	public static final int WEST = 1;
	public static final int SOUTH = 2;
	public static final int NORTH = 3;
	
	/*******************************
	 * 	��� ����
	 *******************************/
	
	// ������
	private NodeLabel leftChild;
	private NodeLabel rightSibling;
	
	// ����
	private LineInfo[] line;
	private int emptySpaceNumber;
	private int parentsSpace;
	
	//// ��� ũ�� ����� ���Ǵ� �ӽú��� : NodeMouseListener.mouseDragged()���� ���
	private int prevX, prevY, firstLocX, firstLocY;
	private int firstWidth, firstHeight;
	
	// �÷���
	private ResizeState resizeState;
	private boolean selectedFlag;
	
	// �⺻ ����, ���� ����
	private Color defaultColor, selectedColor;
	
	// �ּ� �ʺ�, �ּ� ����
	private int minimumWidth, minimumHeight;
	
	
	
	/*******************************
	 * 	������
	 *******************************/
	
	//// ������
	public NodeLabel() {
		super();
		init(SPACE_NUMBER);
	}
	public NodeLabel(String title) {
		super(title);
		init(SPACE_NUMBER);
	}
	public NodeLabel(String title, int emptySpaceNumber) {
		super(title);
		init(emptySpaceNumber);
	}
	
	//// �ʱ�ȭ �޼���
	public void init(int emptySpaceNumber) {
		setBorder(new EtchedBorder());
		
		line = new LineInfo[SPACE_NUMBER];
		for(int i=0; i<SPACE_NUMBER; i++) {
			line[i] = null;
		}
		
		this.leftChild = null;
		this.rightSibling = null;
		
		this.emptySpaceNumber = emptySpaceNumber;
		this.parentsSpace = -1;
		
		prevX = 0;
		prevY = 0;
		resizeState = ResizeState.MAINTAIN;
		selectedFlag = false;
		
		defaultColor = new Color(240, 240, 240);
		selectedColor = Color.ORANGE;
		
		minimumWidth = 50;
		minimumHeight = 30;
		
		setHorizontalAlignment(JLabel.CENTER);
		setBackground(defaultColor);
		setSize(100, 40);
		setOpaque(true);
	}
	
	
	
	/*******************************
	 * 	�޼���
	 *******************************/
	
	// setText �������̵�
	@Override
	public void setText(String text) {
		super.setText(text);
		
		minimumWidth = text.getBytes().length * 8;
		
		// �ּҰ��� 20 �̻�
		if(minimumWidth < 20)
			minimumWidth = 20;
		
		if(getWidth() < minimumWidth)
			setSize(minimumWidth, getHeight());
	}
	
	// �ڽ� ��带 �߰�
	public void addChildNode(NodeLabel data) {
		// child�� �Ѱ��� ���� ��
		if(leftChild == null) {
			leftChild = data;
			
			addLine(data);
			emptySpaceNumber--;
		}
		// child�� �Ѱ��̻� �����ϰ� ���� ������ �������� ��
		else if(emptySpaceNumber > 0){
			NodeLabel tempNode = leftChild;
			
			while((tempNode.getRightSibling()) != null) {
				tempNode = tempNode.getRightSibling();
			}
			
			NodeLabel newNode = data;
			tempNode.setRightSibling(newNode);
			
			addLine(data);
			emptySpaceNumber--;
		}
		// ���� ������ ���� ��
		else {
			System.out.println("Error : ���������� �����ϴ�");
		}
	}
	
	//// ��尡 ������ �ִ� ���� �׸��� : DrawBoardPanel���� ȣ��
	public void drawLine(Graphics2D g2) {
		for(int i=0; i<SPACE_NUMBER; i++) {
			if(line[i] != null)
				line[i].drawLine(g2);
		}
	}
	
	// ������ �߰� : addChildNode()���� ȣ��
	private void addLine(NodeLabel data) {
		for(int i=0; i<SPACE_NUMBER; i++) {
			if(i != parentsSpace && line[i] == null) {
				if(i == NORTH) {
					data.setLocation(getX(), getY() - data.getHeight() - 50);
					line[i] = new LineInfo(this, data, Space.NORTH, Space.SOUTH);
					data.setParentsSpace(SOUTH);
				}
				else if(i == SOUTH) {
					data.setLocation(getX(), getY() + getHeight() + 50);
					line[i] = new LineInfo(this, data, Space.SOUTH, Space.NORTH);
					data.setParentsSpace(NORTH);
				}
				else if(i == EAST) {
					data.setLocation(getX() + getWidth() + 50, getY());
					line[i] = new LineInfo(this, data, Space.EAST, Space.WEST);
					data.setParentsSpace(WEST);
				}
				else {
					data.setLocation(getX() - data.getWidth() - 50, getY());
					line[i] = new LineInfo(this, data, Space.WEST, Space.EAST);
					data.setParentsSpace(EAST);
				}
				break;
			}
		}
	}
	
	// ��尡 ���õǾ� �ִ��� ��ȯ
	public boolean isSelected() {
		return this.selectedFlag;
	}
	
	// ��� ����
	public void select() {
		this.setBackground(selectedColor);
		this.selectedFlag = true;
	}
	
	// ��� ���� ����
	public void deselect() {
		this.setBackground(defaultColor);
		this.selectedFlag = false;
	}
	
	// �� ��ġ(EAST, WEST, SOUTH, NORTH) ��ȯ
	public Point getPointNorth() {
		int x = (int) getLocation().getX() + (getWidth() / 2);
		int y = (int) getLocation().getY();
		
		return new Point(x, y);
	}
	public Point getPointSouth() {
		int x = (int) getLocation().getX() + (getWidth() / 2);
		int y = (int) getLocation().getY() + getHeight();
		
		return new Point(x, y);
	}
	public Point getPointWest() {
		int x = (int) getLocation().getX();
		int y = (int) getLocation().getY() + (getHeight() / 2);
		
		return new Point(x, y);
	}
	public Point getPointEast() {
		int x = (int) getLocation().getX() + getWidth();
		int y = (int) getLocation().getY() + (getHeight() / 2);
		
		return new Point(x, y);
	}
	
	// ��带 ����
	public void delete() {
		// �����̳ʿ��� ��� ����
		getParent().remove(this);
		
		// ��� ���� ����
		for(int i=0; i<SPACE_NUMBER; i++) {
			if(line[i] != null) {
				line[i].delete();
				line[i] = null;
				emptySpaceNumber++;
			}
		}
		
		// ������ ����
		leftChild = null;
		rightSibling = null;
	}
	
	// �Ű������� �θ� ������� ��ȯ
	public boolean isParent(NodeLabel child) {
		for(int i=0; i<SPACE_NUMBER; i++) {
			if(line[i] != null) {
				if(line[i].getDestNode() == child)
					return true;
			}
		}
		return false;
	}
	
	// Ư�� ���� ����
	public void removeLine(NodeLabel node) {
		for(int i=0; i<SPACE_NUMBER; i++) {
			if(line[i] != null) { 
				if(line[i].getDestNode() == node) {
					line[i].delete();
					line[i] = null;
					emptySpaceNumber++;
					return;
				}
			}
		}
	}
	
	
	
	/*******************************
	 * 	Getter, Setter
	 *******************************/

	public int getPrevX() {
		return prevX;
	}
	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}
	
	public int getPrevY() {
		return prevY;
	}
	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}
	
	public ResizeState getResizeState() {
		return resizeState;
	}
	public void setResizeState(ResizeState resizeState) {
		this.resizeState = resizeState;
	}
	
	public int getMinimumWidth() {
		return minimumWidth;
	}
	public void setMinimumWidth(int minimumWidth) {
		this.minimumWidth = minimumWidth;
	}
	
	public int getMinimumHeight() {
		return minimumHeight;
	}
	public void setMinimumHeight(int minimumHeight) {
		this.minimumHeight = minimumHeight;
	}
	
	public int getFirstX() {
		return firstLocX;
	}
	public void setFirstX(int firstX) {
		this.firstLocX = firstX;
	}
	
	public int getFirstY() {
		return firstLocY;
	}
	public void setFirstY(int firstY) {
		this.firstLocY = firstY;
	}
	
	public int getFirstWidth() {
		return firstWidth;
	}
	public void setFirstWidth(int firstWidth) {
		this.firstWidth = firstWidth;
	}
	
	public int getFirstHeight() {
		return firstHeight;
	}
	public void setFirstHeight(int firstHeight) {
		this.firstHeight = firstHeight;
	}
	
	public int getEmptySpaceNumber() {
		return emptySpaceNumber;
	}
	public void setEmptySpaceNumber(int emptySpaceNumber) {
		this.emptySpaceNumber = emptySpaceNumber;
	}
	
	public NodeLabel getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(NodeLabel leftChild) {
		this.leftChild = leftChild;
	}
	
	public NodeLabel getRightSibling() {
		return rightSibling;
	}
	public void setRightSibling(NodeLabel rightSibling) {
		this.rightSibling = rightSibling;
	}
	
	public int getParentsSpace() {
		return parentsSpace;
	}
	public void setParentsSpace(int parentsSpace) {
		this.parentsSpace = parentsSpace;
	}
	// ����� �߾Ӱ� ��ȯ
	public Point getCenterPoint() {
		return new Point(getX() + (getWidth() / 2)  , getY() + (getHeight() / 2));
	}
	
	public Color getDefaultColor() {
		return defaultColor;
	}
	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}
	
	public Color getSelectedColor() {
		return selectedColor;
	}
	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}
}
