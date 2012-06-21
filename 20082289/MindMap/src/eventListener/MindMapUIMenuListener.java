package eventListener;

import java.awt.event.*;

import ui.*;

public class MindMapUIMenuListener implements ActionListener {
	private MindMapUI mindMapUI;
	
	/*******************************
	 * 	������
	 *******************************/
	
	public MindMapUIMenuListener(MindMapUI mindMapUI) {
		this.mindMapUI = mindMapUI;
	}
	
	
	
	/*******************************
	 * 	������ �޼���
	 *******************************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// �� ����
		if(source == mindMapUI.getFile_New()) {
			mindMapUI.newFile();
		}
		// ����
		else if(source == mindMapUI.getFile_Open()) {
			mindMapUI.openFile();
		}
		// ����
		else if(source == mindMapUI.getFile_Save()) {
			mindMapUI.saveFile();
		}
		// �ٸ� �̸����� ����
		else if(source == mindMapUI.getFile_SaveAs()) {
			mindMapUI.saveAsFile();
		}
		// ����
		else if(source == mindMapUI.getFile_Exit()) {
			mindMapUI.exitProgram();
		}
		// ����
		else if(source == mindMapUI.getAbout_About()) {
			mindMapUI.showAboutDialog();
		}
	}
}
