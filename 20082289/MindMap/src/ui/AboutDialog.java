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
	 * 	���
	 *******************************/

	private final int DIALOG_WIDTH = 300;
	private final int DIALOG_HEIGHT = 180;
	
	/*******************************
	 * 	��� ����
	 *******************************/
	
	private JDialog dialog;
	private JPanel mainPanel, panelBtn, panelInfo;
	private JButton btnExitDialog;
	
	
	
	/*******************************
	 * 	������
	 *******************************/
	
	public AboutDialog(JFrame frame) {
		int x, y;
		
		// ���̾˷αװ� �������� �߾ӿ� ��ġ�ϵ��� ��ġ ���
		if(frame.getWidth() < DIALOG_WIDTH)
			x = frame.getX() - (DIALOG_WIDTH - frame.getWidth()) / 2;
		else
			x = frame.getX() + (frame.getWidth() - DIALOG_WIDTH) / 2;
		if(frame.getHeight() < DIALOG_HEIGHT)
			y = frame.getY() - (DIALOG_HEIGHT - frame.getHeight()) / 2;
		else
			y = frame.getY() + (frame.getHeight() - DIALOG_HEIGHT) / 2;

		// ���̾˷α�, ������Ʈ ����
		dialog = new JDialog(frame, "����");
		mainPanel = new JPanel(new BorderLayout(0, 20));
		panelBtn = new JPanel();
		panelInfo = new JPanel(new GridLayout(3, 1, 10, 10));
		
		btnExitDialog = new JButton("�ݱ�");
		JLabel lbl1 = new JLabel("��ü���� ���α׷��� - ���ε�� ���α׷�", JLabel.CENTER);
		JLabel lbl2 = new JLabel("������ : 2012.06.14", JLabel.CENTER);
		JLabel lbl3 = new JLabel("�ۼ��� : 20082289 �����", JLabel.CENTER);
		
		// ������Ʈ �⺻ ����
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		panelBtn.add(btnExitDialog);
		btnExitDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		// ���̾˷α׿� ������Ʈ ����
		panelInfo.add(lbl1);
		panelInfo.add(lbl2);
		panelInfo.add(lbl3);
		
		mainPanel.add(panelInfo, BorderLayout.CENTER);
		mainPanel.add(panelBtn, BorderLayout.SOUTH);
		
		dialog.getContentPane().add(mainPanel);
		
		// ���̾˷α� �⺻ ����
		dialog.setBounds(x, y, DIALOG_WIDTH, DIALOG_HEIGHT);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}
}