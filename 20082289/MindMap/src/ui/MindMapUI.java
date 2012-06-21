package ui;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;

import manager.*;

import component.*;

import eventListener.*;


public class MindMapUI extends JFrame {
	/*******************************
	 * 	��� ����
	 *******************************/
	//// ���ҽ�
	private ResourceManager rscManager;
	
	//// �޴�
	private JMenuBar menuBar;
	private JMenu menu_File, menu_About;
	private JMenuItem file_New, file_Open, file_Save, file_SaveAs, file_Exit;
	private JMenuItem about_About;
	
	//// ����
	private JToolBar toolBar;
	private JButton tbBtnNew, tbBtnOpen, tbBtnSave, tbBtnSaveAs;
	
	//// �г�
	private JSplitPane mainSplitPane;
	private JPanel attributePanel;
	private DrawBoardPanel mindMapPanel;
	
	//// ������Ʈ
	// �Ӽ� �г�(Attribute Panel) ������Ʈ
	private JPanel attributeSubPanel;
	private JLabel lblX, lblY, lblWidth, lblHeight, lblText;
	private JTextField tfX, tfY, tfWidth, tfHeight, tfText;
	
	private JPanel buttonPanel;
	private JButton btnChange, btnCreateNode, btnDelete;
	
	//// ���ڿ�
	private final String programName = "���ε�� ���α׷�";
	private String fileName = "";
	private File file = null;
	
	/*******************************
	 * 	������
	 *******************************/
	
	public MindMapUI() {
		rscManager = new ResourceManager(this);
				
		
		// �̺�Ʈ ������ ����
		MindMapUIMenuListener menuListener = new MindMapUIMenuListener(this);
		MindMapUIToolBarListener toolBarListener = new MindMapUIToolBarListener(this);
		MindMapUIButtonListener buttonListener = new MindMapUIButtonListener(this);
		MindMapUIKeyListener keyListener = new MindMapUIKeyListener(this);
		
		// ���� ������ ����
		Container framePane = this.getContentPane();
		framePane.setLayout(new BorderLayout());
		
		//// �޴�
		// �޴� ������Ʈ ����
		menuBar = new JMenuBar();
		menu_File = new JMenu("����(F)");
		menu_About = new JMenu("����(B)");
		file_New = new JMenuItem("���� �����(N)", new ImageIcon("./img/new.png"));
		file_Open = new JMenuItem("����(O)", new ImageIcon("./img/open.png"));
		file_Save = new JMenuItem("����(S)", new ImageIcon("./img/save.png"));
		file_SaveAs = new JMenuItem("�ٸ� �̸����� ����(A)", new ImageIcon("./img/saveas.png"));
		file_Exit = new JMenuItem("����(X)");
		about_About = new JMenuItem("����(B)");
		
		// �޴� ������Ʈ ����
		menu_File.setMnemonic('F');
		menu_About.setMnemonic('B');
		
		file_New.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
		file_New.setMnemonic('N');
		file_New.setToolTipText("�� ���ε���� ����ϴ�.");
		file_Open.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
		file_Open.setMnemonic('O');
		file_Open.setToolTipText("���� ���ε���� ���ϴ�.");
		file_Save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		file_Save.setMnemonic('S');
		file_Save.setToolTipText("���� ���ε���� �����մϴ�.");
		file_SaveAs.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
		file_SaveAs.setMnemonic('A');
		file_SaveAs.setToolTipText("�ٸ� �̸��� ���Ϸ� �����մϴ�.");
		file_Exit.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		file_Exit.setMnemonic('X');
		file_Exit.setToolTipText("���α׷��� �����մϴ�.");
		about_About.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_MASK));
		about_About.setMnemonic('B');
		about_About.setToolTipText("���α׷� ������ ���ϴ�.");
		
		// �޴� �̺�Ʈ ������ ���
		file_New.addActionListener(menuListener);
		file_Open.addActionListener(menuListener);
		file_Save.addActionListener(menuListener);
		file_SaveAs.addActionListener(menuListener);
		file_Exit.addActionListener(menuListener);
		about_About.addActionListener(menuListener);
		
		// �޴� �����ͽ� ����
		this.setJMenuBar(menuBar);
		
		menuBar.add(menu_File);
		menuBar.add(menu_About);
				
		menu_File.add(file_New);
		menu_File.add(file_Open);
		menu_File.addSeparator();
		menu_File.add(file_Save);
		menu_File.add(file_SaveAs);
		menu_File.addSeparator();
		menu_File.add(file_Exit);
		menu_About.add(about_About);
		
		
		//// ����
		// ��ü ����
		toolBar = new JToolBar("ToolBar");
		tbBtnNew = new JButton(new ImageIcon("./img/new.png"));
		tbBtnNew.setFocusPainted(false);
		tbBtnNew.setToolTipText("�� ���ε���� ����ϴ�.");
		
		tbBtnOpen = new JButton(new ImageIcon("./img/open.png"));
		tbBtnOpen.setFocusPainted(false);
		tbBtnOpen.setToolTipText("���� ���ε���� ���ϴ�.");
		
		tbBtnSave = new JButton(new ImageIcon("./img/save.png"));
		tbBtnSave.setFocusPainted(false);
		tbBtnSave.setToolTipText("���� ���ε���� �����մϴ�.");
		
		tbBtnSaveAs = new JButton(new ImageIcon("./img/saveas.png"));
		tbBtnSaveAs.setFocusPainted(false);
		tbBtnSaveAs.setToolTipText("�ٸ� �̸��� ���Ϸ� �����մϴ�.");
		
		// ���� �̺�Ʈ ������ ���
		tbBtnNew.addActionListener(toolBarListener);
		tbBtnOpen.addActionListener(toolBarListener);
		tbBtnSave.addActionListener(toolBarListener);
		tbBtnSaveAs.addActionListener(toolBarListener);
		
		// ������ ���ο� ���� ����
		framePane.add(toolBar, BorderLayout.NORTH);
				
		// ���� ������Ʈ ����
		toolBar.add(tbBtnNew);
		toolBar.add(tbBtnOpen);
		toolBar.addSeparator();
		toolBar.add(tbBtnSave);
		toolBar.add(tbBtnSaveAs);
		
		
		//// �г�
		// ���� ���ø� ���� ��ü ����
		mainSplitPane = new JSplitPane();
		
		// ������ ���ο� ���� ���ø� ���� ����
		framePane.add(mainSplitPane, BorderLayout.CENTER);
		
		
		//// ���� �г�
		// ���� �г� ��ü ����
		attributePanel = new JPanel(new GridBagLayout());
		mindMapPanel = new DrawBoardPanel(this, rscManager);
		
		// �����г� �Ӽ� ����
		attributePanel.setOpaque(true);
		attributePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mindMapPanel.setOpaque(true);
		mindMapPanel.addKeyListener(keyListener);
		
		// ���� ���ø� ���ο� ���� �г� ���� 
		mainSplitPane.setLeftComponent(attributePanel);
		mainSplitPane.setRightComponent(mindMapPanel);
		mainSplitPane.setDividerLocation(260);
		
		//// �Ӽ� �г�(Attribute Panel)
		// ������Ʈ ����
		attributeSubPanel = new JPanel(new GridBagLayout());
		attributeSubPanel.setBorder(new TitledBorder(new EtchedBorder(), "�Ӽ�"));
		
		lblX = new JLabel("X");
		lblY = new JLabel("Y");
		lblWidth = new JLabel("�ʺ�");
		lblHeight = new JLabel("����");
		lblText = new JLabel("�ؽ�Ʈ");
		tfX = new JTextField();
		tfY = new JTextField();
		tfWidth = new JTextField();
		tfHeight = new JTextField();
		tfText = new JTextField();
		
		buttonPanel = new JPanel(new GridBagLayout());
		btnChange = new JButton("�Ӽ��� ����");
		btnCreateNode = new JButton("������");
		btnDelete = new JButton("������");
		
		// ������Ʈ ����
		Dimension btnSize = new Dimension(0, 30);
		
		btnChange.setPreferredSize(btnSize);
		btnCreateNode.setPreferredSize(btnSize);
		btnDelete.setPreferredSize(btnSize);
		
		
		// ������Ʈ �̺�Ʈ ������ ���
		btnChange.addActionListener(buttonListener);
		btnCreateNode.addActionListener(buttonListener);
		btnDelete.addActionListener(buttonListener);
		
		// ������Ʈ ����
		GridBagConstraints c = new GridBagConstraints();
		initGridBagConstraints(c);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		insertGridBagComponent(attributeSubPanel, lblX, 			c, 0, 0, 1, 1);
		insertGridBagComponent(attributeSubPanel, lblY, 			c, 2, 0, 1, 1);
		insertGridBagComponent(attributeSubPanel, lblWidth, 		c, 0, 1, 1, 1);
		insertGridBagComponent(attributeSubPanel, lblHeight, 		c, 2, 1, 1, 1);
		insertGridBagComponent(attributeSubPanel, lblText, 		c, 0, 2, 1, 1);
		
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		insertGridBagComponent(attributeSubPanel, tfX, 			c, 1, 0, 1, 1);
		insertGridBagComponent(attributeSubPanel, tfY, 			c, 3, 0, 1, 1);
		insertGridBagComponent(attributeSubPanel, tfWidth, 		c, 1, 1, 1, 1);
		insertGridBagComponent(attributeSubPanel, tfHeight, 		c, 3, 1, 1, 1);
		insertGridBagComponent(attributeSubPanel, tfText, 			c, 1, 2, 3, 1);
	
		insertGridBagComponent(buttonPanel, btnChange, c, 0, 0, 2, 1);
		insertGridBagComponent(buttonPanel, btnCreateNode, c, 0, 1, 1, 1);
		insertGridBagComponent(buttonPanel, btnDelete, c, 1, 1, 1, 1);
		
		insertGridBagComponent(attributeSubPanel, buttonPanel, c, 0, 4, 4, 1);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weighty = 1.0;
		insertGridBagComponent(attributePanel, attributeSubPanel, c, 0, 0, 1, 1);
		
		
		
		//// ������ ����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(getTitleString());
		this.setBounds(200, 100, 1100, 600);		
		this.setVisible(true);
	}
	
	
	
	/*******************************
	 * 	�޼���
	 *******************************/
	
	// title �̸� ��ȯ
	public String getTitleString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(programName).append(" - ");
		
		if(file == null)
			strBuf.append("���� ����");
		else
			strBuf.append(fileName);
		
		return strBuf.toString();
	}
	
	// GridBag ��� �ʱ�ȭ
	public void initGridBagConstraints(GridBagConstraints c) {
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.0;
		c.weighty = 0.0;
	}
	
	// GridBag ������Ʈ �߰�
	public void insertGridBagComponent(Container con, Component comp, GridBagConstraints c, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		
		con.add(comp, c);
	}
	
	//// �Ӽ� �г�(Attribute Panel) ������Ʈ
	public void updateAttribute(Vector<NodeLabel> selectedNodeSet) {
		if(selectedNodeSet.size() == 1) {
			NodeLabel selectedNode = selectedNodeSet.get(0);
			
			tfX.setText(Integer.toString(selectedNode.getX()));
			tfY.setText(Integer.toString(selectedNode.getY()));
			tfWidth.setText(Integer.toString(selectedNode.getWidth()));
			tfHeight.setText(Integer.toString(selectedNode.getHeight()));
			tfText.setText(selectedNode.getText());
		}
		else {
			tfX.setText("");
			tfY.setText("");
			tfWidth.setText("");
			tfHeight.setText("");
			tfText.setText("");
		}
	}
	
	private boolean checkInputError() {
		String x = tfX.getText();
		String y = tfY.getText();
		String width = tfWidth.getText();
		String height = tfHeight.getText();
		
		if(	x.matches("[\\d]+") 	 &&
			y.matches("[\\d]+") 	 &&
			width.matches("[\\d]+") &&
			height.matches("[\\d]+") )
			return false;
		return true;
	}
	
	
	
	/*******************************
	 * 	��� �޼���
	 *******************************/
	
	// ��� ����
	public void createNode() {
		mindMapPanel.createNode();
	}
	// �Ӽ� ����
	public void changeAttribute() {
		if(mindMapPanel.getSelectedNodeSet().size() == 1) {
			// �Է� ����
			if(checkInputError()) {
				JOptionPane.showConfirmDialog(this, "�߸��� �Է��Դϴ�.\n�������� �־��ּ���.", "����", 
												JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			int x = Integer.parseInt(tfX.getText());
			int y = Integer.parseInt(tfY.getText());
			int width = Integer.parseInt(tfWidth.getText());
			int height = Integer.parseInt(tfHeight.getText());
			String text = tfText.getText();
			
			mindMapPanel.adaptAttribute(x, y, width, height, text);
		}
	}
	// ���� ��� ����
	public void deleteSelectedNode() {
		mindMapPanel.deleteSelectedNode();
	}
	// �� ����
	public void newFile() {
		mindMapPanel.clearTree();
		fileName = "";
		file = null;
		setTitle(getTitleString());
		
		repaint();
	}
	// ���� ����
	public void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser = new JFileChooser("..\\MindMap");
		fileChooser.setFileFilter(new FileNameExtensionFilter("���� ����(*.sav)", "sav"));
		fileChooser.setMultiSelectionEnabled(false);
		
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File tempFile = fileChooser.getSelectedFile();
			String tempFileName = tempFile.getName();
			
			if(!tempFile.exists()) {
				JOptionPane.showConfirmDialog(this, tempFileName + "\n������ �������� �ʽ��ϴ�.", "����", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(tempFileName.length() < 5) {
				JOptionPane.showConfirmDialog(this, "�߸��� ���� �����Դϴ�.", "����", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!tempFileName.substring(tempFileName.length()-4).equals(".sav")) {
				JOptionPane.showConfirmDialog(this, "�߸��� ���� �����Դϴ�.", "����", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			file = tempFile;
			fileName = tempFileName;
			setTitle(getTitleString());
			mindMapPanel.clearTree();
			
			rscManager.loadData(file);
		}
	}
	// ���� ����
	public void saveFile() {
		if(file == null) {
			saveAsFile();
		}
		else {
			rscManager.saveData(file);
		}
	}
	// �ٸ��̸����� ����
	public void saveAsFile() {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser = new JFileChooser("..\\MindMap");
		fileChooser.setFileFilter(new FileNameExtensionFilter("�ؽ�Ʈ ����(*.sav)", "sav"));
		fileChooser.setMultiSelectionEnabled(false);
		
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File tempFile = fileChooser.getSelectedFile();
			String tempFileName = tempFile.getName();
			
			if(tempFile.exists()) {
				int choice = JOptionPane.showConfirmDialog(this, tempFileName + "��(��) �̹� �ֽ��ϴ�.\n �ٲٽðڽ��ϱ�?", "�ٸ��̸����� ���� Ȯ��",
												JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				
				if(choice == JOptionPane.NO_OPTION) {
					return;
				}
			}
			else if(!tempFileName.contains(".")) {
				tempFile = new File(tempFile.getAbsolutePath() + ".sav");
				tempFileName = tempFile.getName();
			}
			
			file = tempFile;
			fileName = tempFileName;
			setTitle(getTitleString());
			
			rscManager.saveData(file);
		}
	}
	// ���α׷� ����
	public void exitProgram() {
		System.exit(0);
	}
	// ���� ���̾˷α� ǥ��
	public void showAboutDialog() {
		new AboutDialog(this);
	}
	
	
	
	/*******************************
	 * 	���� �޼���
	 *******************************/
	
	public static void main(String[] args) {
		new MindMapUI();
	}
	
	
	
	/*******************************
	 * 	Getter
	 *******************************/
	
	public JButton getBtnChange() {
		return btnChange;
	}
	public JButton getBtnCreateNode() {
		return btnCreateNode;
	}
	public JButton getBtnDelete() {
		return btnDelete;
	}
	public JMenuItem getFile_New() {
		return file_New;
	}
	public JMenuItem getFile_Open() {
		return file_Open;
	}
	public JMenuItem getFile_Save() {
		return file_Save;
	}
	public JMenuItem getFile_SaveAs() {
		return file_SaveAs;
	}
	public JMenuItem getFile_Exit() {
		return file_Exit;
	}
	public JMenuItem getAbout_About() {
		return about_About;
	}
	public JButton getTbBtnNew() {
		return tbBtnNew;
	}
	public JButton getTbBtnOpen() {
		return tbBtnOpen;
	}
	public JButton getTbBtnSave() {
		return tbBtnSave;
	}
	public JButton getTbBtnSaveAs() {
		return tbBtnSaveAs;
	}
}
