package manager;

import java.io.*;

import ui.*;

import component.*;

import dataTpye.*;


public class ResourceManager {
	private MindMapUI mindMapUI;
	private DrawBoardPanel mindMapPanel;
	
	//// ȭ���� ����
	private CustomTree dataTree;
	
	/*******************************
	 * 	������
	 *******************************/
	
	public ResourceManager(MindMapUI mindMapUI) {
		this.mindMapUI = mindMapUI;
		init();
	}
	// �ʱ�ȭ �޼���
	public void init() {
		this.dataTree = new CustomTree();
	}
	
	
	
	/*******************************
	 * 	�޼���
	 *******************************/
	
	// �ε�
	public void loadData(File file) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			dataTree.setRoot((NodeLabel) obj);
			ois.close();
			
			dataTree.addAllNodeToPanel(mindMapPanel);
			mindMapUI.repaint();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// ����
	public void saveData(File file) {
		FileOutputStream fos;
		try {
			mindMapPanel.deselectAllNode();
			
			fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(dataTree.getRoot());
			oos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*******************************
	 * 	Getter, Setter
	 *******************************/

	public CustomTree getDataTree() {
		return dataTree;
	}
	public void setDataTree(CustomTree dataTree) {
		this.dataTree = dataTree;
	}
	public DrawBoardPanel getMindMapPanel() {
		return mindMapPanel;
	}
	public void setMindMapPanel(DrawBoardPanel mindMapPanel) {
		this.mindMapPanel = mindMapPanel;
	}
}