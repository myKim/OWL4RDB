package eventListener;

import java.awt.event.*;

import ui.*;

public class MindMapUIToolBarListener implements ActionListener {
	private MindMapUI mindMapUI;
	
	/*******************************
	 * 	������
	 *******************************/
	
	public MindMapUIToolBarListener(MindMapUI mindMapUI) {
		this.mindMapUI = mindMapUI;
	}
	
	
	
	/*******************************
	 * 	������ �޼���
	 *******************************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// �� ����
		if(source == mindMapUI.getTbBtnNew()) {
			mindMapUI.newFile();
		}
		// ����
		else if(source == mindMapUI.getTbBtnOpen()) {
			mindMapUI.openFile();
		}
		// ����
		else if(source == mindMapUI.getTbBtnSave()) {
			mindMapUI.saveFile();
		}
		// �ٸ� �̸����� ����
		else if(source == mindMapUI.getTbBtnSaveAs()) {
			mindMapUI.saveAsFile();
		}
	}
}
