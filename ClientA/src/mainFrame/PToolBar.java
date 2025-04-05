package mainFrame;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import sugangSincheong.PSugangSincheongPanel;

public class PToolBar extends JToolBar {
    private static final long serialVersionUID = 1L;

    private PSugangSincheongPanel sugangPanel; // PSugangSincheongPanel 참조
    private Main mainApp; // Main 객체 참조 추가

    // 매개변수 있는 생성자
    public PToolBar(PSugangSincheongPanel sugangPanel, Main mainApp) {
        this.sugangPanel = sugangPanel; // PSugangSincheongPanel 초기화
        this.mainApp = mainApp;

        // 저장 버튼 생성
        JButton saveButton = new JButton("강좌저장");
        saveButton.addActionListener(event -> saveContent());
        this.add(saveButton);

        // 로그아웃 버튼 추가
        JButton logoutButton = new JButton("로그아웃");
        logoutButton.addActionListener(event -> logout());
        this.add(logoutButton);

        // 마이페이지 버튼 추가
        JButton mypageButton = new JButton("마이페이지");
        mypageButton.addActionListener(event -> openMypage());
        this.add(mypageButton);
    }

    // 저장 동작 메서드
    private void saveContent() {
        Object[] options = { "예", "아니오" };

        int confirm = JOptionPane.showOptionDialog(this, "미리담기 및 수강신청 내역을 저장하시겠습니까?", "저장 확인",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // 저장 수행
                if (sugangPanel != null) {
                    sugangPanel.finish(); // 데이터 저장
                    sugangPanel.updateCredits(null); // 학점 갱신
                }
                JOptionPane.showMessageDialog(this, "저장이 완료되었습니다!", "저장 성공", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "저장 중 오류가 발생했습니다.", "저장 실패", JOptionPane.ERROR_MESSAGE);
            }
        } else if (confirm == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "저장을 취소했습니다.", "저장 취소", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 로그아웃 동작 메서드
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "로그아웃 하시겠습니까?", "로그아웃 확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // 현재 창 종료 및 로그인 창 열기
            if (mainApp != null) {
                mainApp.showLoginDialog(); // Main 클래스의 메서드 호출
            }
        }
    }

    // 마이페이지 버튼 동작 메서드
    private void openMypage() {
        // PMypage 창 생성
        PMypage mypageFrame = new PMypage();
        mypageFrame.setVisible(true); // 창 표시
    }
}
