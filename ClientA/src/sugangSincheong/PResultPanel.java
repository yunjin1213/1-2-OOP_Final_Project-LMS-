package sugangSincheong;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import constants.Constants.EPResultPanel;
import control.DataManager;
import valueObject.VGangjwa;

public class PResultPanel extends PGangjwaContainer {
    private static final long serialVersionUID = 1L;

    private List<VGangjwa> vGangjwas;
    private JTable table;
    private DefaultTableModel tableModel;
    private DataManager dataManager;
    private String userId;
    private String panelType;

    // 생성자
    public PResultPanel(DataManager dataManager, String userId, String panelType) {
        this.dataManager = dataManager;
        this.userId = userId;
        this.panelType = panelType;
        this.vGangjwas = new ArrayList<>();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.table = new JTable();
        JScrollPane scrollPane = new JScrollPane(this.table);
        this.add(scrollPane);

        // 테이블 헤더 초기화
        List<String> header = new ArrayList<>();
        for (EPResultPanel ePResultPanel : EPResultPanel.values()) {
            header.add(ePResultPanel.getText());
        }

        this.tableModel = new DefaultTableModel(header.toArray(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 셀 편집 불가
            }
        };
        this.table.setModel(tableModel);
    }

    // 초기화 메서드
    public void initialize() throws RemoteException {
        if (panelType.equals("미리담기")) {
            this.vGangjwas = dataManager.getMiridamgi(userId);
        } else if (panelType.equals("수강신청")) {
            this.vGangjwas = dataManager.getSugangSincheong(userId);
        }
        System.out.println("초기화된 강좌 목록 (" + panelType + "): " + vGangjwas);
        this.updateTableContents();
    }

    // 저장 메서드
    public void save() throws RemoteException {
        if (panelType.equals("미리담기")) {
            dataManager.saveMiridamgi(userId, vGangjwas);
        } else if (panelType.equals("수강신청")) {
            dataManager.saveSugangSincheong(userId, vGangjwas);
        }
        System.out.println("저장된 강좌 목록 (" + panelType + "): " + vGangjwas);
    }

    // 강좌 목록 가져오기
    public List<VGangjwa> getGangjwas() {
        return new ArrayList<>(this.vGangjwas);
    }

    // 강좌 추가 메서드
    @Override
    public void addGangjwas(List<VGangjwa> vSelectedGangjwas) {
        this.vGangjwas.addAll(vSelectedGangjwas);
        System.out.println("추가된 강좌 목록 (" + panelType + "): " + vSelectedGangjwas);
        this.updateTableContents();
    }

    // 선택된 강좌 제거 메서드
    @Override
    public List<VGangjwa> removeSelectedGangjwas() {
        List<VGangjwa> removedGangjwas = new ArrayList<>();
        int[] selectedRows = this.table.getSelectedRows();

        // 선택된 강좌를 뒤에서부터 제거
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            VGangjwa removedGangjwa = this.vGangjwas.remove(selectedRows[i]);
            removedGangjwas.add(removedGangjwa);
        }

        System.out.println("제거된 강좌 목록 (" + panelType + "): " + removedGangjwas);
        this.updateTableContents();
        return removedGangjwas;
    }

    // 선택된 강좌 가져오기 (제거하지 않음)
    @Override
    public List<VGangjwa> getSelectedGangjwas() {
        List<VGangjwa> selectedGangjwas = new ArrayList<>();
        int[] selectedRows = this.table.getSelectedRows();

        for (int row : selectedRows) {
            selectedGangjwas.add(vGangjwas.get(row));
        }
        System.out.println("선택된 강좌 목록 (" + panelType + "): " + selectedGangjwas);
        return selectedGangjwas;
    }

    // 테이블 업데이트 메서드
    private void updateTableContents() {
        this.tableModel.setRowCount(0);  // 테이블 초기화
        for (VGangjwa vGangjwa : this.vGangjwas) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(vGangjwa.getGangjwaId()));   // 강좌 ID
            row.add(vGangjwa.getCourseName());                 // 강좌 이름
            row.add(vGangjwa.getInstructor());                 // 교수 이름
            row.add(String.valueOf(vGangjwa.getCredit()));     // 학점
            row.add(vGangjwa.getScheduleTime());               // 강의 시간
            this.tableModel.addRow(row.toArray());
        }

        if (!this.vGangjwas.isEmpty()) {
            this.table.setRowSelectionInterval(0, 0);  // 첫 번째 행 선택
        }
    }
}