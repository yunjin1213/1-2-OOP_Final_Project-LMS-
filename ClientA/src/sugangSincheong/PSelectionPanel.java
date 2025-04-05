package sugangSincheong;

import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import control.DataManager;
import valueObject.VGangjwa;

public class PSelectionPanel extends PGangjwaContainer {

	private static final long serialVersionUID = 1L;

	private DataManager dataManager;
	private PHakgwaSelectionPanel pHakgwaSelectionPanel;
	private PGangjwaSelectionPanel pGangjwaSelectionPanel;

	// 생성자
	public PSelectionPanel(DataManager dataManager) {
		this.dataManager = dataManager;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// PHakgwaSelectionPanel 초기화 (학과 선택 패널)
		this.pHakgwaSelectionPanel = new PHakgwaSelectionPanel(dataManager, () -> {
			updateGangjwaData(); // 학과 선택 변경 시 강좌 목록 갱신
		});
		this.add(this.pHakgwaSelectionPanel);

		// PGangjwaSelectionPanel 초기화 (강좌 선택 패널)
		JScrollPane scrollPane = new JScrollPane();
		this.pGangjwaSelectionPanel = new PGangjwaSelectionPanel(event -> {
			System.out.println("강좌 선택이 변경되었습니다."); // 디버깅 메시지
		});
		scrollPane.setViewportView(this.pGangjwaSelectionPanel);
		this.add(scrollPane);
	}

	// 초기화 메서드
	public void initialize(PResultPanel pMiridamgiPanel, PResultPanel pSincheongPanel) {
		this.pHakgwaSelectionPanel.initialize(); // 학과 패널 초기화
		updateGangjwaData(pMiridamgiPanel, pSincheongPanel); // 강좌 목록 초기화
	}

	// 학과 선택 변경 시 강좌 목록 갱신 메서드
	private void updateGangjwaData() {
		int departmentId = this.pHakgwaSelectionPanel.getSelectedDepartmentId();
		if (departmentId != -1) {
			try {
				// 학과 ID로 강좌 목록 가져오기
				List<VGangjwa> gangjwas = dataManager.getGangjwasByDepartmentId(departmentId);
				System.out.println("강좌 목록 불러오기: " + gangjwas); // 디버깅 메시지
				this.pGangjwaSelectionPanel.updateTableContents(gangjwas); // 강좌 테이블 업데이트
			} catch (Exception e) {
				e.printStackTrace(); // 예외 처리
			}
		}
	}

	// 강좌 데이터 초기화 메서드 (미리담기 및 수강신청과 연동)
	private void updateGangjwaData(PResultPanel pMiridamgiPanel, PResultPanel pSincheongPanel) {
		int departmentId = this.pHakgwaSelectionPanel.getSelectedDepartmentId();
		if (departmentId != -1) {
			try {
				List<VGangjwa> gangjwas = dataManager.getGangjwasByDepartmentId(departmentId);

				// "미리담기" 및 "수강신청" 상태와 동기화된 필터링
				gangjwas.removeIf(gangjwa -> pMiridamgiPanel.getGangjwas().stream()
						.anyMatch(miridamgi -> miridamgi.getGangjwaId() == gangjwa.getGangjwaId())
						|| pSincheongPanel.getGangjwas().stream()
								.anyMatch(sincheong -> sincheong.getGangjwaId() == gangjwa.getGangjwaId()));

				this.pGangjwaSelectionPanel.updateTableContents(gangjwas); // 강좌 목록 업데이트
			} catch (Exception e) {
				e.printStackTrace(); // 예외 처리
			}
		}
	}

	// 강좌 데이터 갱신 (리스트 기반)
	public void updateGangjwaData(List<VGangjwa> newGangjwas) {
		this.pGangjwaSelectionPanel.updateTableContents(newGangjwas); // 강좌 목록 업데이트
	}

	// 강좌 목록 반환 (새로 추가된 메서드 구현)
	@Override
	public List<VGangjwa> getGangjwas() {
		return this.pGangjwaSelectionPanel.getGangjwas(); // PGangjwaSelectionPanel에서 데이터 반환
	}

	// 강좌 추가 메서드
	@Override
	public void addGangjwas(List<VGangjwa> vSelectedGangjwas) {
		this.pGangjwaSelectionPanel.addGangjwas(vSelectedGangjwas);
	}

	// 선택된 강좌 가져오기
	@Override
	public List<VGangjwa> getSelectedGangjwas() {
		return this.pGangjwaSelectionPanel.getSelectedGangjwas();
	}

	// 선택된 강좌 제거 메서드
	@Override
	public List<VGangjwa> removeSelectedGangjwas() {
		return this.pGangjwaSelectionPanel.removeSelectedGangjwas();
	}
}
