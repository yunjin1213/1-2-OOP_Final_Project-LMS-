package sugangSincheong;

import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants.EPHeaderPanel;
import valueObject.VUser;

public class PHeaderPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel welcomeLabel;

    public PHeaderPanel() {
        this.welcomeLabel = new JLabel();
        this.add(this.welcomeLabel);		
    }

    public void initialize(VUser vUser) {
        // "userId + 님, 명지대학교 수강신청 시스템에 오신 걸 환영합니다!" 메시지 생성
        String message = String.format("%s님, %s", vUser.getName(), EPHeaderPanel.greetings.getText());
        this.welcomeLabel.setText(message);
    }
}