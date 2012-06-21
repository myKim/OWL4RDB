package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class AboutDialog extends JDialog {
	/*******************************
	 * 	상수
	 *******************************/

	private final int DIALOG_WIDTH = 300;
	private final int DIALOG_HEIGHT = 180;
	
	/*******************************
	 * 	멤버 변수
	 *******************************/
	
	private JDialog dialog;
	private JPanel mainPanel, panelBtn, panelInfo;
	private JButton btnExitDialog;
	
	
	
	/*******************************
	 * 	생성자
	 *******************************/
	
	public AboutDialog(JFrame frame) {
		int x, y;
		
		// 다이알로그가 프레임의 중앙에 위치하도록 위치 계산
		if(frame.getWidth() < DIALOG_WIDTH)
			x = frame.getX() - (DIALOG_WIDTH - frame.getWidth()) / 2;
		else
			x = frame.getX() + (frame.getWidth() - DIALOG_WIDTH) / 2;
		if(frame.getHeight() < DIALOG_HEIGHT)
			y = frame.getY() - (DIALOG_HEIGHT - frame.getHeight()) / 2;
		else
			y = frame.getY() + (frame.getHeight() - DIALOG_HEIGHT) / 2;

		// 다이알로그, 컴포넌트 생성
		dialog = new JDialog(frame, "정보");
		mainPanel = new JPanel(new BorderLayout(0, 20));
		panelBtn = new JPanel();
		panelInfo = new JPanel(new GridLayout(3, 1, 10, 10));
		
		btnExitDialog = new JButton("닫기");
		JLabel lbl1 = new JLabel("객체지향 프로그래밍 - 마인드맵 프로그램", JLabel.CENTER);
		JLabel lbl2 = new JLabel("제출일 : 2012.06.14", JLabel.CENTER);
		JLabel lbl3 = new JLabel("작성자 : 20082289 김명유", JLabel.CENTER);
		
		// 컴포넌트 기본 설정
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		panelBtn.add(btnExitDialog);
		btnExitDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		// 다이알로그에 컴포넌트 삽입
		panelInfo.add(lbl1);
		panelInfo.add(lbl2);
		panelInfo.add(lbl3);
		
		mainPanel.add(panelInfo, BorderLayout.CENTER);
		mainPanel.add(panelBtn, BorderLayout.SOUTH);
		
		dialog.getContentPane().add(mainPanel);
		
		// 다이알로그 기본 설정
		dialog.setBounds(x, y, DIALOG_WIDTH, DIALOG_HEIGHT);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}
}