package eventListener;

import java.awt.event.*;

import ui.*;

public class MindMapUIKeyListener implements KeyListener {
	private MindMapUI mindMapUI;
	
	/*******************************
	 * 	������
	 *******************************/
	
	public MindMapUIKeyListener(MindMapUI mindMapUI) {
		this.mindMapUI = mindMapUI;
	}
	
	
	
	/*******************************
	 * 	������ �޼���
	 *******************************/
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DELETE) {
			mindMapUI.deleteSelectedNode();
		}
		else if(e.getKeyCode() == KeyEvent.VK_N) {
			mindMapUI.createNode();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
}
