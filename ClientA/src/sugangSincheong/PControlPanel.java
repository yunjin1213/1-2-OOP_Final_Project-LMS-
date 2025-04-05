package sugangSincheong;

import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PControlPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton moveRightButton;
    private JButton moveLeftButton;

    public PControlPanel(ActionListener moveRightHandler, ActionListener moveLeftHandler) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 오른쪽으로 이동 버튼
        ImageIcon rightIcon = resizeIcon(new ImageIcon(getClass().getResource("/img/right.png")), 20, 20);
        this.moveRightButton = new JButton(rightIcon);
        this.moveRightButton.addActionListener(moveRightHandler); // 오른쪽 이동 핸들러
        this.add(this.moveRightButton);

        // 왼쪽으로 이동 버튼
        ImageIcon leftIcon = resizeIcon(new ImageIcon(getClass().getResource("/img/left.png")), 20, 20);
        this.moveLeftButton = new JButton(leftIcon);
        this.moveLeftButton.addActionListener(moveLeftHandler); // 왼쪽 이동 핸들러
        this.add(this.moveLeftButton);
    }

    public void initialize() {
        // 필요한 경우 초기화 작업 추가
    }

    public JButton getMoveRightButton() {
        return this.moveRightButton;
    }

    public JButton getMoveLeftButton() {
        return this.moveLeftButton;
    }

    // 이미지 크기 조정 메서드
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}