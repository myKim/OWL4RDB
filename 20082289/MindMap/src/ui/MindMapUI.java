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
	 * 	멤버 변수
	 *******************************/
	//// 리소스
	private ResourceManager rscManager;
	
	//// 메뉴
	private JMenuBar menuBar;
	private JMenu menu_File, menu_About;
	private JMenuItem file_New, file_Open, file_Save, file_SaveAs, file_Exit;
	private JMenuItem about_About;
	
	//// 툴바
	private JToolBar toolBar;
	private JButton tbBtnNew, tbBtnOpen, tbBtnSave, tbBtnSaveAs;
	
	//// 패널
	private JSplitPane mainSplitPane;
	private JPanel attributePanel;
	private DrawBoardPanel mindMapPanel;
	
	//// 컴포넌트
	// 속성 패널(Attribute Panel) 컴포넌트
	private JPanel attributeSubPanel;
	private JLabel lblX, lblY, lblWidth, lblHeight, lblText;
	private JTextField tfX, tfY, tfWidth, tfHeight, tfText;
	
	private JPanel buttonPanel;
	private JButton btnChange, btnCreateNode, btnDelete;
	
	//// 문자열
	private final String programName = "마인드맵 프로그램";
	private String fileName = "";
	private File file = null;
	
	/*******************************
	 * 	생성자
	 *******************************/
	
	public MindMapUI() {
		rscManager = new ResourceManager(this);
				
		
		// 이벤트 리스너 생성
		MindMapUIMenuListener menuListener = new MindMapUIMenuListener(this);
		MindMapUIToolBarListener toolBarListener = new MindMapUIToolBarListener(this);
		MindMapUIButtonListener buttonListener = new MindMapUIButtonListener(this);
		MindMapUIKeyListener keyListener = new MindMapUIKeyListener(this);
		
		// 메인 프레임 페인
		Container framePane = this.getContentPane();
		framePane.setLayout(new BorderLayout());
		
		//// 메뉴
		// 메뉴 컴포넌트 생성
		menuBar = new JMenuBar();
		menu_File = new JMenu("파일(F)");
		menu_About = new JMenu("정보(B)");
		file_New = new JMenuItem("새로 만들기(N)", new ImageIcon("./img/new.png"));
		file_Open = new JMenuItem("열기(O)", new ImageIcon("./img/open.png"));
		file_Save = new JMenuItem("저장(S)", new ImageIcon("./img/save.png"));
		file_SaveAs = new JMenuItem("다른 이름으로 저장(A)", new ImageIcon("./img/saveas.png"));
		file_Exit = new JMenuItem("종료(X)");
		about_About = new JMenuItem("정보(B)");
		
		// 메뉴 컴포넌트 설정
		menu_File.setMnemonic('F');
		menu_About.setMnemonic('B');
		
		file_New.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
		file_New.setMnemonic('N');
		file_New.setToolTipText("새 마인드맵을 만듭니다.");
		file_Open.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
		file_Open.setMnemonic('O');
		file_Open.setToolTipText("기존 마인드맵을 엽니다.");
		file_Save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		file_Save.setMnemonic('S');
		file_Save.setToolTipText("현재 마인드맵을 저장합니다.");
		file_SaveAs.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
		file_SaveAs.setMnemonic('A');
		file_SaveAs.setToolTipText("다른 이름의 파일로 저장합니다.");
		file_Exit.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		file_Exit.setMnemonic('X');
		file_Exit.setToolTipText("프로그램을 종료합니다.");
		about_About.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_MASK));
		about_About.setMnemonic('B');
		about_About.setToolTipText("프로그램 정보를 봅니다.");
		
		// 메뉴 이벤트 리스너 등록
		file_New.addActionListener(menuListener);
		file_Open.addActionListener(menuListener);
		file_Save.addActionListener(menuListener);
		file_SaveAs.addActionListener(menuListener);
		file_Exit.addActionListener(menuListener);
		about_About.addActionListener(menuListener);
		
		// 메뉴 컴포넌스 삽입
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
		
		
		//// 툴바
		// 객체 생성
		toolBar = new JToolBar("ToolBar");
		tbBtnNew = new JButton(new ImageIcon("./img/new.png"));
		tbBtnNew.setFocusPainted(false);
		tbBtnNew.setToolTipText("새 마인드맵을 만듭니다.");
		
		tbBtnOpen = new JButton(new ImageIcon("./img/open.png"));
		tbBtnOpen.setFocusPainted(false);
		tbBtnOpen.setToolTipText("기존 마인드맵을 엽니다.");
		
		tbBtnSave = new JButton(new ImageIcon("./img/save.png"));
		tbBtnSave.setFocusPainted(false);
		tbBtnSave.setToolTipText("현재 마인드맵을 저장합니다.");
		
		tbBtnSaveAs = new JButton(new ImageIcon("./img/saveas.png"));
		tbBtnSaveAs.setFocusPainted(false);
		tbBtnSaveAs.setToolTipText("다른 이름의 파일로 저장합니다.");
		
		// 툴바 이벤트 리스너 등록
		tbBtnNew.addActionListener(toolBarListener);
		tbBtnOpen.addActionListener(toolBarListener);
		tbBtnSave.addActionListener(toolBarListener);
		tbBtnSaveAs.addActionListener(toolBarListener);
		
		// 프레임 패인에 툴바 삽입
		framePane.add(toolBar, BorderLayout.NORTH);
				
		// 툴바 컴포넌트 삽입
		toolBar.add(tbBtnNew);
		toolBar.add(tbBtnOpen);
		toolBar.addSeparator();
		toolBar.add(tbBtnSave);
		toolBar.add(tbBtnSaveAs);
		
		
		//// 패널
		// 메인 스플릿 패인 객체 생성
		mainSplitPane = new JSplitPane();
		
		// 프레임 페인에 메인 스플릿 패인 삽입
		framePane.add(mainSplitPane, BorderLayout.CENTER);
		
		
		//// 서브 패널
		// 서브 패널 객체 생성
		attributePanel = new JPanel(new GridBagLayout());
		mindMapPanel = new DrawBoardPanel(this, rscManager);
		
		// 서브패널 속성 설정
		attributePanel.setOpaque(true);
		attributePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mindMapPanel.setOpaque(true);
		mindMapPanel.addKeyListener(keyListener);
		
		// 메인 스플릿 패인에 서브 패널 삽입 
		mainSplitPane.setLeftComponent(attributePanel);
		mainSplitPane.setRightComponent(mindMapPanel);
		mainSplitPane.setDividerLocation(260);
		
		//// 속성 패널(Attribute Panel)
		// 컴포넌트 생성
		attributeSubPanel = new JPanel(new GridBagLayout());
		attributeSubPanel.setBorder(new TitledBorder(new EtchedBorder(), "속성"));
		
		lblX = new JLabel("X");
		lblY = new JLabel("Y");
		lblWidth = new JLabel("너비");
		lblHeight = new JLabel("높이");
		lblText = new JLabel("텍스트");
		tfX = new JTextField();
		tfY = new JTextField();
		tfWidth = new JTextField();
		tfHeight = new JTextField();
		tfText = new JTextField();
		
		buttonPanel = new JPanel(new GridBagLayout());
		btnChange = new JButton("속성값 변경");
		btnCreateNode = new JButton("노드생성");
		btnDelete = new JButton("노드삭제");
		
		// 컴포넌트 설정
		Dimension btnSize = new Dimension(0, 30);
		
		btnChange.setPreferredSize(btnSize);
		btnCreateNode.setPreferredSize(btnSize);
		btnDelete.setPreferredSize(btnSize);
		
		
		// 컴포넌트 이벤트 리스너 등록
		btnChange.addActionListener(buttonListener);
		btnCreateNode.addActionListener(buttonListener);
		btnDelete.addActionListener(buttonListener);
		
		// 컴포넌트 삽입
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
		
		
		
		//// 프레임 설정
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(getTitleString());
		this.setBounds(200, 100, 1100, 600);		
		this.setVisible(true);
	}
	
	
	
	/*******************************
	 * 	메서드
	 *******************************/
	
	// title 이름 반환
	public String getTitleString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(programName).append(" - ");
		
		if(file == null)
			strBuf.append("제목 없음");
		else
			strBuf.append(fileName);
		
		return strBuf.toString();
	}
	
	// GridBag 상수 초기화
	public void initGridBagConstraints(GridBagConstraints c) {
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.0;
		c.weighty = 0.0;
	}
	
	// GridBag 컴포넌트 추가
	public void insertGridBagComponent(Container con, Component comp, GridBagConstraints c, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		
		con.add(comp, c);
	}
	
	//// 속성 패널(Attribute Panel) 업데이트
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
	 * 	기능 메서드
	 *******************************/
	
	// 노드 생성
	public void createNode() {
		mindMapPanel.createNode();
	}
	// 속성 변경
	public void changeAttribute() {
		if(mindMapPanel.getSelectedNodeSet().size() == 1) {
			// 입력 오류
			if(checkInputError()) {
				JOptionPane.showConfirmDialog(this, "잘못된 입력입니다.\n정수값을 넣어주세요.", "오류", 
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
	// 선택 노드 삭제
	public void deleteSelectedNode() {
		mindMapPanel.deleteSelectedNode();
	}
	// 새 파일
	public void newFile() {
		mindMapPanel.clearTree();
		fileName = "";
		file = null;
		setTitle(getTitleString());
		
		repaint();
	}
	// 파일 열기
	public void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser = new JFileChooser("..\\MindMap");
		fileChooser.setFileFilter(new FileNameExtensionFilter("저장 파일(*.sav)", "sav"));
		fileChooser.setMultiSelectionEnabled(false);
		
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File tempFile = fileChooser.getSelectedFile();
			String tempFileName = tempFile.getName();
			
			if(!tempFile.exists()) {
				JOptionPane.showConfirmDialog(this, tempFileName + "\n파일이 존재하지 않습니다.", "오류", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(tempFileName.length() < 5) {
				JOptionPane.showConfirmDialog(this, "잘못된 저장 파일입니다.", "오류", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!tempFileName.substring(tempFileName.length()-4).equals(".sav")) {
				JOptionPane.showConfirmDialog(this, "잘못된 저장 파일입니다.", "오류", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			file = tempFile;
			fileName = tempFileName;
			setTitle(getTitleString());
			mindMapPanel.clearTree();
			
			rscManager.loadData(file);
		}
	}
	// 파일 저장
	public void saveFile() {
		if(file == null) {
			saveAsFile();
		}
		else {
			rscManager.saveData(file);
		}
	}
	// 다른이름으로 저장
	public void saveAsFile() {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser = new JFileChooser("..\\MindMap");
		fileChooser.setFileFilter(new FileNameExtensionFilter("텍스트 문서(*.sav)", "sav"));
		fileChooser.setMultiSelectionEnabled(false);
		
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File tempFile = fileChooser.getSelectedFile();
			String tempFileName = tempFile.getName();
			
			if(tempFile.exists()) {
				int choice = JOptionPane.showConfirmDialog(this, tempFileName + "이(가) 이미 있습니다.\n 바꾸시겠습니까?", "다른이름으로 저장 확인",
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
	// 프로그램 종료
	public void exitProgram() {
		System.exit(0);
	}
	// 정보 다이알로그 표시
	public void showAboutDialog() {
		new AboutDialog(this);
	}
	
	
	
	/*******************************
	 * 	메인 메서드
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
