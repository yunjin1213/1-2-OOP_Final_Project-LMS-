package sugangSincheong;

import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import control.DataManager;
import valueObject.VUser;

public class PSugangSincheongPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    // 기존 UI 컴포넌트
    private PHeaderPanel pHeaderPanel;
    private PContentPanel pContentPanel;
    private PFooterPanel pFooterPanel;

    // 추가된 학점 표시 컴포넌트
    private JLabel miridamgiCreditsLabel;
    private JLabel sugangSincheongCreditsLabel;
    private JLabel maxCreditsLabel;

    // DataManager 필드 추가
    private DataManager dataManager;
    private String userId;
    private int maxCredits; // 최대 학점 저장

    // 생성자 - DataManager 주입
    public PSugangSincheongPanel(DataManager dataManager, String userId) {
        this.dataManager = dataManager;  // DataManager 초기화
        this.userId = userId;

        // 레이아웃 설정
        this.setLayout(new BorderLayout());

        // UI 패널 초기화
        this.pHeaderPanel = new PHeaderPanel();
        this.add(this.pHeaderPanel, BorderLayout.NORTH);

        this.pContentPanel = new PContentPanel(this.dataManager, this.userId);  // DataManager 및 사용자 ID 전달
        this.add(this.pContentPanel, BorderLayout.CENTER);

        this.pFooterPanel = new PFooterPanel();
        this.add(this.pFooterPanel, BorderLayout.SOUTH);

        // 학점 표시 초기화
        JPanel creditsPanel = new JPanel();
        miridamgiCreditsLabel = new JLabel("미리담기 강좌 총 학점: 0");
        sugangSincheongCreditsLabel = new JLabel("수강신청 강좌 총 학점: 0");
        maxCreditsLabel = new JLabel("최대 수강신청 가능 학점: 0");
        creditsPanel.add(miridamgiCreditsLabel);
        creditsPanel.add(sugangSincheongCreditsLabel);
        creditsPanel.add(maxCreditsLabel);
        this.add(creditsPanel, BorderLayout.NORTH);
    }

    public PContentPanel getPContentPanel() {
        return this.pContentPanel;
    }

    // 초기화 메서드
    public void initialize(VUser vUser) throws MalformedURLException, NotBoundException, RemoteException {
        // 사용자 정보 기반으로 패널 초기화
        this.pHeaderPanel.initialize(vUser);
        this.pContentPanel.initialize(vUser);
        this.pFooterPanel.intialize();  // 푸터 초기화

        // 부모 패널 설정
        this.pContentPanel.setParentPanel(this);

        // 최대 학점 설정
        this.maxCredits = vUser.getMaxCredits();
        updateCredits(vUser);  // 학점 정보 갱신
    }

 // 학점 정보 업데이트
    public void updateCredits(VUser vUser) throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            try {
                int miridamgiCredits = dataManager.getMiridamgiCredits(userId);
                int sugangSincheongCredits = dataManager.getSugangSincheongCredits(userId);

                miridamgiCreditsLabel.setText("미리담기 강좌 총 학점: " + miridamgiCredits);
                sugangSincheongCreditsLabel.setText("수강신청 강좌 총 학점: " + sugangSincheongCredits);
                maxCreditsLabel.setText("최대 수강신청 가능 학점: " + maxCredits);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // 종료 메서드
    public void finish() throws RemoteException {
        this.pContentPanel.finish();  // 수강 신청 데이터 저장
    }
}