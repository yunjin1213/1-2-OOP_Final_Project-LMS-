package mainFrame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import control.CLoginControl;
import control.DataManager;
import valueObject.VLogin;
import valueObject.VUser;

public class PRegisterDialog extends JDialog {

    private JTextField userIdField, passwordField, passwordConfirmField, nameField, emailField, addressField;
    private JComboBox<String> campusCombo, collegeCombo, departmentCombo;
    private JLabel maxCreditsLabel; 
    private JButton registerButton, cancelButton, checkIdButton, checkPasswordButton;

    private CLoginControl loginControl;
    private DataManager dataManager;

    private boolean isIdAvailable = false;

    public PRegisterDialog(CLoginControl loginControl, DataManager dataManager) {
        this.loginControl = loginControl;
        this.dataManager = dataManager;

        this.setTitle("회원가입");
        this.setSize(500, 550);
        this.setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 필드 추가
        addLabelField("아이디:", userIdField = new JTextField(), gbc, 0);
        addButton("중복 확인", gbc, 0, 2, e -> checkUserId());

        addLabelField("비밀번호:", passwordField = new JTextField(), gbc, 1);
        addLabelField("비밀번호 확인:", passwordConfirmField = new JTextField(), gbc, 2);
        addButton("확인", gbc, 2, 2, e -> checkPasswordMatch());

        addLabelField("이름:", nameField = new JTextField(), gbc, 3);
        addLabelField("이메일:", emailField = new JTextField(), gbc, 4);
        addLabelField("주소:", addressField = new JTextField(), gbc, 5);

        addLabelField("캠퍼스:", campusCombo = new JComboBox<>(), gbc, 6);
        campusCombo.addActionListener(e -> loadColleges());

        addLabelField("단과대:", collegeCombo = new JComboBox<>(), gbc, 7);
        collegeCombo.addActionListener(e -> {
            loadDepartments();
            updateMaxCredits(); 
        });

        addLabelField("학과:", departmentCombo = new JComboBox<>(), gbc, 8);

        gbc.gridx = 0;
        gbc.gridy = 9;
        this.add(new JLabel("수강 가능 학점:"), gbc);

        gbc.gridx = 1;
        maxCreditsLabel = new JLabel("17");
        this.add(maxCreditsLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        registerButton = new JButton("등록");
        registerButton.addActionListener(e -> registerUser());
        this.add(registerButton, gbc);

        gbc.gridx = 1;
        cancelButton = new JButton("취소");
        cancelButton.addActionListener(e -> dispose());
        this.add(cancelButton, gbc);

        loadCampuses(); 
    }

    private void addLabelField(String labelText, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        this.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        this.add(field, gbc);
    }

    private void addButton(String label, GridBagConstraints gbc, int row, int col, ActionListener action) {
        gbc.gridx = col;
        gbc.gridy = row;
        JButton button = new JButton(label);
        button.addActionListener(action);
        this.add(button, gbc);
    }

    private void loadCampuses() {
        campusCombo.removeAllItems();
        dataManager.getCampuses().forEach(c -> campusCombo.addItem(c.getName()));
    }

    private void loadColleges() {
        collegeCombo.removeAllItems();
        int campusIndex = campusCombo.getSelectedIndex();
        if (campusIndex != -1) {
            dataManager.getCollegesByCampusId(campusIndex + 1).forEach(c -> {
                System.out.println("Loading College: " + c.getName() + " (ID: " + c.getId() + ")");
                collegeCombo.addItem(c.getName());
            });
        }
    }

    private void loadDepartments() {
        departmentCombo.removeAllItems();
        int collegeIndex = collegeCombo.getSelectedIndex();
        int campusIndex = campusCombo.getSelectedIndex();

        if (collegeIndex != -1 && campusIndex != -1) {
            int selectedCollegeId = dataManager.getCollegesByCampusId(campusIndex + 1)
                                               .get(collegeIndex)
                                               .getId();

            dataManager.getDepartmentsByCollegeId(selectedCollegeId)
                       .forEach(d -> {
                           System.out.println("Loading Department: " + d.getName() + " (ID: " + d.getId() + ")");
                           departmentCombo.addItem(d.getName());
                       });
        }
    }

    private void updateMaxCredits() {
        int collegeIndex = collegeCombo.getSelectedIndex();
        int campusIndex = campusCombo.getSelectedIndex();

        if (collegeIndex != -1 && campusIndex != -1) {
            int selectedCollegeId = dataManager.getCollegesByCampusId(campusIndex + 1)
                                               .get(collegeIndex)
                                               .getId();

            int maxCredits = dataManager.getCollegesByCampusId(campusIndex + 1)
                                        .stream()
                                        .filter(c -> c.getId() == selectedCollegeId)
                                        .map(c -> c.getMaxCredits())
                                        .findFirst()
                                        .orElse(17);
            maxCreditsLabel.setText(String.valueOf(maxCredits));
        }
    }

    private void checkUserId() {
        String userId = userIdField.getText().trim();
        if (userId.isEmpty()) {
            showError("아이디를 입력해주세요.");
            return;
        }
        try {
            if (loginControl.checkDuplicateUserId(userId)) {
                showError("이미 존재하는 아이디입니다.");
                isIdAvailable = false;
            } else {
                showMessage("사용 가능한 아이디입니다.");
                isIdAvailable = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("중복 검사 중 오류가 발생했습니다.");
        }
    }

    private void checkPasswordMatch() {
        String password = passwordField.getText().trim();
        String confirmPassword = passwordConfirmField.getText().trim();

        if (password.equals(confirmPassword)) {
            showMessage("비밀번호가 일치합니다.");
        } else {
            showError("비밀번호가 일치하지 않습니다.");
        }
    }

    private void registerUser() {
        String userId = userIdField.getText().trim();
        String password = passwordField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        if (!isIdAvailable || userId.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || address.isEmpty()) {
            showError("모든 필드를 올바르게 입력해주세요.");
            return;
        }

        int campusIndex = campusCombo.getSelectedIndex();
        int collegeIndex = collegeCombo.getSelectedIndex();
        int departmentIndex = departmentCombo.getSelectedIndex();

        int selectedCollegeId = dataManager.getCollegesByCampusId(campusIndex + 1).get(collegeIndex).getId();
        int selectedDepartmentId = dataManager.getDepartmentsByCollegeId(selectedCollegeId).get(departmentIndex).getId();

        int maxCredits = Integer.parseInt(maxCreditsLabel.getText());

        VLogin newLogin = new VLogin(userId, password);
        VUser newUser = new VUser(userId, name, address, email, campusIndex + 1, selectedCollegeId, selectedDepartmentId, maxCredits, 0);

        try {
            if (loginControl.registerUser(newUser, newLogin)) {
                showMessage("회원가입이 완료되었습니다!");
                dispose();
            } else {
                showError("회원가입에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("회원가입 중 오류가 발생했습니다.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "오류", JOptionPane.ERROR_MESSAGE);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "정보", JOptionPane.INFORMATION_MESSAGE);
    }
}