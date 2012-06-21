package eventListener;

import java.awt.event.*;

import ui.*;

public class MindMapUIButtonListener implements ActionListener {
	private MindMapUI mindMapUI;
	
	/*******************************
	 * 	������
	 *******************************/
	
	public MindMapUIButtonListener(MindMapUI mindMapUI) {
		this.mindMapUI = mindMapUI;
	}
	
	
	
	/*******************************
	 * 	������ �޼���
	 *******************************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		// ��� ����
		if(source == mindMapUI.getBtnCreateNode()) {
			mindMapUI.createNode();
		}
		// �Ӽ� ����
		else if(source == mindMapUI.getBtnChange()) {
			mindMapUI.changeAttribute();
		}
		else if(source == mindMapUI.getBtnDelete()) {
			mindMapUI.deleteSelectedNode();
		}
	}
}
