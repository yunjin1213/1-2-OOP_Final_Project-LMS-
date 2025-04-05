package mainFrame;

import constants.Constants.EMainFrame;
import valueObject.VGangjwa;

import javax.swing.*;
import java.util.List;

public class PMypage extends JFrame {
    private static final long serialVersionUID = 1L;

    // 생성자
    public PMypage() {
        // 프레임 설정
        this.setSize(EMainFrame.width.getInt(), EMainFrame.height.getInt()); // 크기 설정
        this.setLocationRelativeTo(null); // 화면 중앙에 위치
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 동작 설정
        this.setTitle("마이페이지"); // 창 제목 설정

    }


}
