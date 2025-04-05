package sugangSincheong;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import control.DataManager;
import valueObject.VGangjwa;

public class PGangjwaSelectionPanel extends JTable {

	private static final long serialVersionUID = 1L;

	private List<VGangjwa> gangjwaList;
	private DefaultTableModel tableModel;
	private DataManager dataManager;

	// 생성자
	public PGangjwaSelectionPanel(ListSelectionListener listSelectionHandler) {
		this.tableModel = new DefaultTableModel(createHeader(), 0);
		this.setModel(tableModel);
		this.getSelectionModel().addListSelectionListener(listSelectionHandler);
		this.gangjwaList = new ArrayList<>();
	}

	// 테이블 헤더 생성
	private String[] createHeader() {
		return new String[] { "강좌번호", "강좌명", "담당교수", "학점", "시간" };
	}

	// 테이블 초기화 메서드
	public void updateTableContents(List<VGangjwa> newGangjwaList) {
		this.gangjwaList = new ArrayList<>(newGangjwaList);  // 목록 업데이트
		refreshTable();
	}

	// 강좌 데이터 초기화 메서드 (RMI 호출 포함)
	public void initialize(int departmentId, PResultPanel pMiridamgiPanel, PResultPanel pSincheongPanel)
	        throws MalformedURLException, NotBoundException, RemoteException {

	    // 데이터 초기화 (RMI 호출 시 데이터 로드)
	    this.gangjwaList = dataManager.getGangjwasByDepartmentId(departmentId);

	    // 중복 강좌 필터링 (미리담기 & 수강신청)
	    removeDuplicatedGangjwas(pMiridamgiPanel.getGangjwas());
	    removeDuplicatedGangjwas(pSincheongPanel.getGangjwas());

	    // 테이블 새로고침
	    refreshTable();
	}


	// 테이블 새로고침 메서드
	private void refreshTable() {
		this.tableModel.setRowCount(0);  // 기존 데이터 초기화
		for (VGangjwa gangjwa : gangjwaList) {
			tableModel.addRow(new Object[] {
				gangjwa.getGangjwaId(),
				gangjwa.getCourseName(),
				gangjwa.getInstructor(),
				gangjwa.getCredit(),
				gangjwa.getScheduleTime()
			});
		}

		if (!gangjwaList.isEmpty()) {
			this.setRowSelectionInterval(0, 0);  // 첫 번째 행 선택
		}
	}

	// 강좌 목록 반환 메서드 (새로 추가된 메서드)
	public List<VGangjwa> getGangjwas() {
		return this.gangjwaList;  // 내부 강좌 리스트 반환
	}

	// 선택된 강좌 반환 (제거하지 않음)
	public List<VGangjwa> getSelectedGangjwas() {
		List<VGangjwa> selectedGangjwas = new ArrayList<>();
		int[] selectedRows = this.getSelectedRows();
		for (int row : selectedRows) {
			selectedGangjwas.add(gangjwaList.get(row));
		}
		return selectedGangjwas;
	}

	// 강좌 추가 메서드 (새로운 강좌 추가)
	public void addGangjwas(List<VGangjwa> selectedGangjwas) {
		this.gangjwaList.addAll(selectedGangjwas);
		refreshTable();
	}

	// 선택된 강좌 제거 메서드
	public List<VGangjwa> removeSelectedGangjwas() {
		List<VGangjwa> removedGangjwas = new ArrayList<>();
		int[] selectedRows = this.getSelectedRows();
		for (int i = selectedRows.length - 1; i >= 0; i--) {
			removedGangjwas.add(gangjwaList.remove(selectedRows[i]));
		}
		refreshTable();
		return removedGangjwas;
	}

	// 중복 강좌 제거 메서드
	private void removeDuplicatedGangjwas(List<VGangjwa> selectedGangjwas) {
	    if (selectedGangjwas == null || selectedGangjwas.isEmpty()) {
	        return;
	    }
	    gangjwaList.removeIf(gangjwa -> selectedGangjwas.stream()
	            .anyMatch(selected -> selected.getGangjwaId() == gangjwa.getGangjwaId()));
	}

}
