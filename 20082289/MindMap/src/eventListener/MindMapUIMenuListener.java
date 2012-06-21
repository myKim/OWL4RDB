package eventListener;

import java.awt.event.*;

import ui.*;

public class MindMapUIMenuListener implements ActionListener {
	private MindMapUI mindMapUI;
	
	/*******************************
	 * 	생성자
	 *******************************/
	
	public MindMapUIMenuListener(MindMapUI mindMapUI) {
		this.mindMapUI = mindMapUI;
	}
	
	
	
	/*******************************
	 * 	리스너 메서드
	 *******************************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// 새 파일
		if(source == mindMapUI.getFile_New()) {
			mindMapUI.newFile();
		}
		// 열기
		else if(source == mindMapUI.getFile_Open()) {
			mindMapUI.openFile();
		}
		// 저장
		else if(source == mindMapUI.getFile_Save()) {
			mindMapUI.saveFile();
		}
		// 다른 이름으로 저장
		else if(source == mindMapUI.getFile_SaveAs()) {
			mindMapUI.saveAsFile();
		}
		// 종료
		else if(source == mindMapUI.getFile_Exit()) {
			mindMapUI.exitProgram();
		}
		// 정보
		else if(source == mindMapUI.getAbout_About()) {
			mindMapUI.showAboutDialog();
		}
	}
}
