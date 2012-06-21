package component;

import java.awt.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.*;

import dataTpye.*;
import dataTpye.LineInfo.*;


public class NodeLabel extends JLabel implements Serializable {
	/*******************************
	 * 	상수, 열거형(enum)
	 *******************************/
	
	public static enum ResizeState {MAINTAIN ,EAST, WEST, SOUTH, NORTH, SOUTH_EAST, SOUTH_WEST, NORTH_EAST, NORTH_WEST}
	// 라인 가능 공간의 개수
	private final int SPACE_NUMBER = 4;
	// 방향
	public static final int EAST = 0;
	public static final int WEST = 1;
	public static final int SOUTH = 2;
	public static final int NORTH = 3;
	
	/*******************************
	 * 	멤버 변수
	 *******************************/
	
	// 참조값
	private NodeLabel leftChild;
	private NodeLabel rightSibling;
	
	// 라인
	private LineInfo[] line;
	private int emptySpaceNumber;
	private int parentsSpace;
	
	//// 노드 크기 변경시 사용되는 임시변수 : NodeMouseListener.mouseDragged()에서 사용
	private int prevX, prevY, firstLocX, firstLocY;
	private int firstWidth, firstHeight;
	
	// 플래그
	private ResizeState resizeState;
	private boolean selectedFlag;
	
	// 기본 배경색, 선택 배경색
	private Color defaultColor, selectedColor;
	
	// 최소 너비, 최소 높이
	private int minimumWidth, minimumHeight;
	
	
	
	/*******************************
	 * 	생성자
	 *******************************/
	
	//// 생성자
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
	
	//// 초기화 메서드
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
	 * 	메서드
	 *******************************/
	
	// setText 오버라이드
	@Override
	public void setText(String text) {
		super.setText(text);
		
		minimumWidth = text.getBytes().length * 8;
		
		// 최소값의 20 이상
		if(minimumWidth < 20)
			minimumWidth = 20;
		
		if(getWidth() < minimumWidth)
			setSize(minimumWidth, getHeight());
	}
	
	// 자식 노드를 추가
	public void addChildNode(NodeLabel data) {
		// child가 한개도 없을 때
		if(leftChild == null) {
			leftChild = data;
			
			addLine(data);
			emptySpaceNumber--;
		}
		// child가 한개이상 존재하고 여유 공간이 남아있을 때
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
		// 여유 공간이 없을 때
		else {
			System.out.println("Error : 여유공간이 없습니다");
		}
	}
	
	//// 노드가 가지고 있는 라인 그리기 : DrawBoardPanel에서 호출
	public void drawLine(Graphics2D g2) {
		for(int i=0; i<SPACE_NUMBER; i++) {
			if(line[i] != null)
				line[i].drawLine(g2);
		}
	}
	
	// 라인을 추가 : addChildNode()에서 호출
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
	
	// 노드가 선택되어 있는지 반환
	public boolean isSelected() {
		return this.selectedFlag;
	}
	
	// 노드 선택
	public void select() {
		this.setBackground(selectedColor);
		this.selectedFlag = true;
	}
	
	// 노드 선택 해제
	public void deselect() {
		this.setBackground(defaultColor);
		this.selectedFlag = false;
	}
	
	// 점 위치(EAST, WEST, SOUTH, NORTH) 반환
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
	
	// 노드를 삭제
	public void delete() {
		// 컨테이너에서 노드 제거
		getParent().remove(this);
		
		// 모든 라인 삭제
		for(int i=0; i<SPACE_NUMBER; i++) {
			if(line[i] != null) {
				line[i].delete();
				line[i] = null;
				emptySpaceNumber++;
			}
		}
		
		// 참조값 삭제
		leftChild = null;
		rightSibling = null;
	}
	
	// 매개변수의 부모 노드인지 반환
	public boolean isParent(NodeLabel child) {
		for(int i=0; i<SPACE_NUMBER; i++) {
			if(line[i] != null) {
				if(line[i].getDestNode() == child)
					return true;
			}
		}
		return false;
	}
	
	// 특정 라인 삭제
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
	// 노드의 중앙값 반환
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
