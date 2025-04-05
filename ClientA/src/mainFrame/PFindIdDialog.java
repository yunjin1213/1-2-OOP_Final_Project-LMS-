// PFindIdDialog.java
package mainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import control.CLoginControl;

public class PFindIdDialog extends JDialog {

	private JTextField nameField, emailField;
	private JButton findIdButton;
	private CLoginControl loginControl;

	public PFindIdDialog(CLoginControl loginControl) {
		this.loginControl = loginControl;

		setTitle("ID 찾기");
		setSize(400, 200);
		setLayout(new GridBagLayout());
		setLocationRelativeTo(null);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// 필드 추가
		addLabelField("이름:", nameField = new JTextField(), gbc, 0);
		addLabelField("이메일:", emailField = new JTextField(), gbc, 1);

		findIdButton = new JButton("ID 찾기");
		findIdButton.addActionListener(this::findUserId);
		addButton(findIdButton, gbc, 2);
	}

	private void addLabelField(String labelText, JComponent field, GridBagConstraints gbc, int row) {
		gbc.gridy = row;
		gbc.gridx = 0;
		add(new JLabel(labelText), gbc);

		gbc.gridx = 1;
		add(field, gbc);
	}

	private void addButton(JButton button, GridBagConstraints gbc, int row) {
		gbc.gridx = 1;
		gbc.gridy = row;
		add(button, gbc);
	}

	private void findUserId(ActionEvent e) {
		String name = nameField.getText().trim();
		String email = emailField.getText().trim();

		if (name.isEmpty() || email.isEmpty()) {
			showError("이름과 이메일을 입력하세요.");
			return;
		}

		try {
			String userId = loginControl.findUserId(name, email);
			if (userId != null) {
				showMessage("사용자 ID: " + userId);
			} else {
				showError("일치하는 사용자를 찾을 수 없습니다.");
			}
		} catch (Exception ex) { // RemoteException을 처리
			ex.printStackTrace(); // 디버깅용 로그
			showError("사용자 ID를 찾는 도중 오류가 발생했습니다.");
		}
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "오류", JOptionPane.ERROR_MESSAGE);
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "정보", JOptionPane.INFORMATION_MESSAGE);
	}
}