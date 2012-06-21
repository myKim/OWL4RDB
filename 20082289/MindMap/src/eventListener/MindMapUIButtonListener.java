package eventListener;

import java.awt.event.*;

import ui.*;

public class MindMapUIButtonListener implements ActionListener {
	private MindMapUI mindMapUI;
	
	/*******************************
	 * 	생성자
	 *******************************/
	
	public MindMapUIButtonListener(MindMapUI mindMapUI) {
		this.mindMapUI = mindMapUI;
	}
	
	
	
	/*******************************
	 * 	리스너 메서드
	 *******************************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		// 노드 생성
		if(source == mindMapUI.getBtnCreateNode()) {
			mindMapUI.createNode();
		}
		// 속성 변경
		else if(source == mindMapUI.getBtnChange()) {
			mindMapUI.changeAttribute();
		}
		else if(source == mindMapUI.getBtnDelete()) {
			mindMapUI.deleteSelectedNode();
		}
	}
}
