package mainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import control.CLoginControl;

public class PFindPasswordDialog extends JDialog {

    private JTextField idField, emailField;
    private JButton findPasswordButton;
    private CLoginControl loginControl;

    public PFindPasswordDialog(CLoginControl loginControl) {
        this.loginControl = loginControl;

        setTitle("비밀번호 찾기");
        setSize(400, 200);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID 입력 필드
        addLabelField("ID:", idField = new JTextField(), gbc, 0);

        // Email 입력 필드
        addLabelField("Email:", emailField = new JTextField(), gbc, 1);

        // 비밀번호 찾기 버튼
        findPasswordButton = new JButton("Find Password");
        findPasswordButton.addActionListener(this::findPassword);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(findPasswordButton, gbc);
    }

    private void addLabelField(String labelText, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        add(field, gbc);
    }

    private void findPassword(ActionEvent e) {
        String userId = idField.getText().trim();
        String email = emailField.getText().trim();

        if (userId.isEmpty() || email.isEmpty()) {
            showError("ID와 Email을 모두 입력하세요.");
            return;
        }

        try {
            String password = loginControl.findPassword(userId, email);
            if (password != null) {
                showMessage("사용자의 비밀번호는: " + password);
            } else {
                showError("일치하는 사용자를 찾을 수 없습니다.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("비밀번호를 찾는 도중 오류가 발생했습니다.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "오류", JOptionPane.ERROR_MESSAGE);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "정보", JOptionPane.INFORMATION_MESSAGE);
    }
}