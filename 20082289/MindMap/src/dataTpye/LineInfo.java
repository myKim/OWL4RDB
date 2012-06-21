package dataTpye;

import java.awt.*;
import java.io.*;

import component.*;

public class LineInfo implements Serializable {
	public static enum Space {NORTH, SOUTH, EAST, WEST}
	
	private Space srcPoint, destPoint;
	private NodeLabel srcNode, destNode;
	
	/*******************************
	 * 	생성자
	 *******************************/
	
	public LineInfo(NodeLabel srcNode, NodeLabel destNode, Space srcPoint, Space destPoint) {
		this.srcPoint = srcPoint;
		this.destPoint = destPoint;
		this.srcNode = srcNode; 
		this.destNode = destNode;
	}
	
	
	
	/*******************************
	 * 	메서드
	 *******************************/
	
	public void drawLine(Graphics2D g2) {
		int srcX, srcY, destX, destY;
		srcX = 0;
		srcY = 0;
		destX = 0;
		destY = 0;
		
		switch(srcPoint) {
		case NORTH:
			srcX = srcNode.getPointNorth().x;
			srcY = srcNode.getPointNorth().y;
			break;
			
		case SOUTH:
			srcX = srcNode.getPointSouth().x;
			srcY = srcNode.getPointSouth().y;
			break;
			
		case EAST:
			srcX = srcNode.getPointEast().x;
			srcY = srcNode.getPointEast().y;
			break;
			
		case WEST:
			srcX = srcNode.getPointWest().x;
			srcY = srcNode.getPointWest().y;
			break;
		}
		
		switch(destPoint) {
		case NORTH:
			destX = destNode.getPointNorth().x;
			destY = destNode.getPointNorth().y;
			break;
			
		case SOUTH:
			destX = destNode.getPointSouth().x;
			destY = destNode.getPointSouth().y;
			break;
			
		case EAST:
			destX = destNode.getPointEast().x;
			destY = destNode.getPointEast().y;
			break;
			
		case WEST:
			destX = destNode.getPointWest().x;
			destY = destNode.getPointWest().y;
			break;
		}
		
		g2.drawLine(srcX, srcY, destX, destY);
	}

	// 라인 내용 삭제
	public void delete() {
		srcPoint = null;
		srcNode = null;
		destPoint = null;
		destNode = null;
	}
	
	
	
	/*******************************
	 *  Getter
	 *******************************/
	
	public NodeLabel getDestNode() {
		return destNode;
	}
}
