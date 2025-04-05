package mainFrame;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import control.CLoginControl;
import control.DataManager;
import valueObject.VLogin;
import valueObject.VUser;

public class PLoginDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JTextField nameText;
    private JPasswordField passwordText;
    private JButton okButton, cancelButton, registerButton, findIdButton, findPasswordButton;
    private JLabel imageLabel, welcomeLabel;

    private CLoginControl loginControl;
    private DataManager dataManager;

    // 다이얼로그 변수 선언 (중복 방지)
    private PFindIdDialog findIdDialog;
    private PFindPasswordDialog findPasswordDialog;
    private PRegisterDialog registerDialog;

    public PLoginDialog(ActionListener actionHandler, CLoginControl loginControl, DataManager dataManager) {
        // 기본 설정
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new GridBagLayout());

        // 프로그램 종료 동작 설정
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    PLoginDialog.this,
                    "프로그램을 종료하시겠습니까?",
                    "종료 확인",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // 자바 애플리케이션 완전 종료
                }
            }
        });

        // 객체 초기화
        this.loginControl = loginControl;
        this.dataManager = dataManager;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // 환영 메시지
        welcomeLabel = new JLabel("안녕하세요! 명지대학교 수강신청 시스템에 오신걸 환영합니다!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(welcomeLabel, gbc);

        // 이미지 추가
        imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/수강신청메인.png"));
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        this.add(imageLabel, gbc);

        // 입력 필드 패널
        JPanel inputPanel = createInputPanel();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        this.add(inputPanel, gbc);

        // 버튼 패널
        JPanel buttonPanel = createButtonPanel(actionHandler);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        this.add(buttonPanel, gbc);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints inputGbc = new GridBagConstraints();
        inputGbc.insets = new Insets(5, 5, 5, 5);
        inputGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("ID:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        inputGbc.gridx = 0;
        inputGbc.gridy = 0;
        inputPanel.add(nameLabel, inputGbc);

        nameText = new JTextField(15);
        inputGbc.gridx = 1;
        inputGbc.gridy = 0;
        inputPanel.add(nameText, inputGbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        inputGbc.gridx = 0;
        inputGbc.gridy = 1;
        inputPanel.add(passwordLabel, inputGbc);

        passwordText = new JPasswordField(15);
        inputGbc.gridx = 1;
        inputGbc.gridy = 1;
        inputPanel.add(passwordText, inputGbc);

        return inputPanel;
    }

    private JPanel createButtonPanel(ActionListener actionHandler) {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        okButton = new JButton("Login");
        okButton.addActionListener(actionHandler);
        this.getRootPane().setDefaultButton(okButton);
        buttonPanel.add(okButton);

        cancelButton = new JButton("Close");
        cancelButton.addActionListener(e -> this.dispose());
        buttonPanel.add(cancelButton);

        registerButton = new JButton("Sign Up");
        registerButton.addActionListener(e -> openRegisterDialog());
        buttonPanel.add(registerButton);

        findIdButton = new JButton("Find ID");
        findIdButton.addActionListener(e -> openFindIdDialog());
        buttonPanel.add(findIdButton);

        findPasswordButton = new JButton("Find Password");
        findPasswordButton.addActionListener(e -> openFindPasswordDialog());
        buttonPanel.add(findPasswordButton);

        return buttonPanel;
    }

    public void initialize() {
        this.setVisible(true);
    }

    public VUser validateUser(Object eventSource) {
        if (eventSource.equals(okButton)) {
            String userId = nameText.getText().trim();
            String password = new String(passwordText.getPassword()).trim();

            if (userId.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 모두 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            VLogin vLogin = new VLogin();
            vLogin.setUserId(userId);
            vLogin.setPassword(password);

            try {
                if (loginControl.login(vLogin) != null) {
                    return loginControl.getUser(userId);
                } else {
                    JOptionPane.showMessageDialog(this, "로그인 실패: 아이디 또는 비밀번호를 확인하세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "로그인 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        } else if (eventSource.equals(cancelButton)) {
            this.dispose();
        }
        return null;
    }

    private void openRegisterDialog() {
        if (registerDialog == null || !registerDialog.isShowing()) {
            registerDialog = new PRegisterDialog(this.loginControl, this.dataManager);
            registerDialog.setVisible(true);
        }
    }

    private void openFindIdDialog() {
        if (findIdDialog == null || !findIdDialog.isShowing()) {
            findIdDialog = new PFindIdDialog(this.loginControl);
            findIdDialog.setVisible(true);
        }
    }

    private void openFindPasswordDialog() {
        if (findPasswordDialog == null || !findPasswordDialog.isShowing()) {
            findPasswordDialog = new PFindPasswordDialog(this.loginControl);
            findPasswordDialog.setVisible(true);
        }
    }
}