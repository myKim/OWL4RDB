package eventListener;

import java.awt.event.*;

import ui.*;

public class MindMapUIToolBarListener implements ActionListener {
	private MindMapUI mindMapUI;
	
	/*******************************
	 * 	생성자
	 *******************************/
	
	public MindMapUIToolBarListener(MindMapUI mindMapUI) {
		this.mindMapUI = mindMapUI;
	}
	
	
	
	/*******************************
	 * 	리스너 메서드
	 *******************************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// 새 파일
		if(source == mindMapUI.getTbBtnNew()) {
			mindMapUI.newFile();
		}
		// 열기
		else if(source == mindMapUI.getTbBtnOpen()) {
			mindMapUI.openFile();
		}
		// 저장
		else if(source == mindMapUI.getTbBtnSave()) {
			mindMapUI.saveFile();
		}
		// 다른 이름으로 저장
		else if(source == mindMapUI.getTbBtnSaveAs()) {
			mindMapUI.saveAsFile();
		}
	}
}
