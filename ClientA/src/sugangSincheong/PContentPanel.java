package sugangSincheong;

import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import control.DataManager;
import valueObject.VGangjwa;
import valueObject.VUser;

public class PContentPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private DataManager dataManager;
    private PSelectionPanel pSelectionPanel;
    private PResultPanel pMiridamgiPanel;
    private PResultPanel pSincheongPanel;
    private PControlPanel pControlPanel1;
    private PControlPanel pControlPanel2;
    private PSugangSincheongPanel parentPanel; // 부모 패널 참조 추가
    private String userId; // 사용자 ID 저장

    public PContentPanel(DataManager dataManager, String userId) {
        this.dataManager = dataManager;
        this.userId = userId; // 사용자 ID 저장

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // 패널 초기화
        this.pSelectionPanel = new PSelectionPanel(this.dataManager);
        this.pMiridamgiPanel = new PResultPanel(this.dataManager, userId, "미리담기");
        this.pSincheongPanel = new PResultPanel(this.dataManager, userId, "수강신청");

        // 제어 패널 초기화
        this.pControlPanel1 = new PControlPanel(
                event -> copyGangjwas(this.pSelectionPanel, this.pMiridamgiPanel),
                event -> moveGangjwasAndRemoveFromSelection(this.pMiridamgiPanel, this.pSelectionPanel)
        );

        this.pControlPanel2 = new PControlPanel(
                event -> moveGangjwasAndRemove(this.pMiridamgiPanel, this.pSincheongPanel),
                event -> moveGangjwas(this.pSincheongPanel, this.pMiridamgiPanel)
        );

        // 레이아웃 추가
        this.add(this.pSelectionPanel);
        this.add(this.pControlPanel1);
        this.add(this.pMiridamgiPanel);
        this.add(this.pControlPanel2);
        this.add(this.pSincheongPanel);
    }

    // 초기화 메서드
    public void initialize(VUser vUser) {
        try {
            this.pMiridamgiPanel.initialize();
            this.pSincheongPanel.initialize();
            this.pSelectionPanel.initialize(this.pMiridamgiPanel, this.pSincheongPanel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 종료 메서드 (데이터 저장)
    public void finish() {
        try {
            this.pMiridamgiPanel.save();
            this.pSincheongPanel.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 부모 패널 설정
    public void setParentPanel(PSugangSincheongPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    // 미리담기 → 선택 복원
    private void moveGangjwasAndRemoveFromSelection(PResultPanel sourcePanel, PSelectionPanel targetPanel) {
        List<VGangjwa> selectedGangjwas = sourcePanel.removeSelectedGangjwas();
        if (!selectedGangjwas.isEmpty()) {
            targetPanel.addGangjwas(selectedGangjwas);
            dataManager.deleteMiridamgi(userId, selectedGangjwas);
            showMessage("강좌를 미리담기에서 제거했습니다!", "강좌 제거 완료");
        }
        updateParentCredits();
    }

    // 미리담기 → 수강신청
    private void moveGangjwasAndRemove(PResultPanel sourcePanel, PResultPanel targetPanel) {
        List<VGangjwa> selectedGangjwas = sourcePanel.removeSelectedGangjwas();
        if (!selectedGangjwas.isEmpty()) {
            targetPanel.addGangjwas(selectedGangjwas);
            dataManager.deleteMiridamgi(userId, selectedGangjwas);
            showMessage("수강신청에 성공했습니다!", "수강신청 완료");
        }
        updateParentCredits();
    }

    // 선택 → 미리담기
    private void copyGangjwas(PGangjwaContainer sourcePanel, PResultPanel targetPanel) {
        List<VGangjwa> selectedGangjwas = sourcePanel.getSelectedGangjwas();
        List<VGangjwa> existingGangjwas = targetPanel.getGangjwas();

        List<VGangjwa> nonDuplicateGangjwas = selectedGangjwas.stream()
                .filter(gangjwa -> !existingGangjwas.contains(gangjwa))
                .toList();

        if (!nonDuplicateGangjwas.isEmpty()) {
            targetPanel.addGangjwas(nonDuplicateGangjwas);
            showMessage("미리담기에 성공했습니다!", "미리담기 성공");
        } else {
            showMessage("중복된 강좌입니다!", "중복 경고");
        }
        updateParentCredits();
    }

    // 수강신청 → 미리담기
    private void moveGangjwas(PGangjwaContainer sourcePanel, PGangjwaContainer targetPanel) {
        List<VGangjwa> selectedGangjwas = sourcePanel.removeSelectedGangjwas();
        if (!selectedGangjwas.isEmpty()) {
            targetPanel.addGangjwas(selectedGangjwas);
            showMessage("강좌가 수강신청에서 미리담기로 이동되었습니다!", "수강신청 취소");
        }
        updateParentCredits();
    }

    // 학점 갱신 요청
    private void updateParentCredits() {
        if (parentPanel != null) {
            try {
                parentPanel.updateCredits(null); // 부모 패널에 학점 갱신 요청
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 메시지 표시 헬퍼 메서드
    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}