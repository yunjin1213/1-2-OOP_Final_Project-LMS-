package sugangSincheong;

import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import control.DataManager;
import valueObject.VCampus;
import valueObject.VCollege;
import valueObject.VDepartment;

public class PHakgwaSelectionPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private DataManager dataManager;
    private PDirectory pCampus;
    private PDirectory pCollege;
    private PDirectory pDepartment;

    // 학과 선택 시 외부로 업데이트를 전달하기 위한 인터페이스
    private Runnable onDepartmentSelectionChanged;

    // 생성자
    public PHakgwaSelectionPanel(DataManager dataManager, Runnable onDepartmentSelectionChanged) {
        this.dataManager = dataManager;
        this.onDepartmentSelectionChanged = onDepartmentSelectionChanged;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JScrollPane scrollPane;

        // 캠퍼스 테이블 초기화
        scrollPane = new JScrollPane();
        this.pCampus = new PDirectory("캠퍼스", e -> {
            if (!e.getValueIsAdjusting()) {
                updateColleges(); // 캠퍼스 선택 시 단과대 업데이트
            }
        });
        scrollPane.setViewportView(this.pCampus);
        this.add(scrollPane);

        // 단과대 테이블 초기화
        scrollPane = new JScrollPane();
        this.pCollege = new PDirectory("단과대", e -> {
            if (!e.getValueIsAdjusting()) {
                updateDepartments(); // 단과대 선택 시 학과 업데이트
            }
        });
        scrollPane.setViewportView(this.pCollege);
        this.add(scrollPane);

        scrollPane = new JScrollPane();
        this.pDepartment = new PDirectory("학과", e -> {
            if (!e.getValueIsAdjusting()) {
                handleHakgwaSelection(); // 학과 선택 시 강좌 업데이트 호출
            }
        });
        scrollPane.setViewportView(this.pDepartment);
        this.add(scrollPane);
    }

    // 학과 선택 시 호출되는 메서드
    private void handleHakgwaSelection() {
        int selectedDepartmentId = getSelectedDepartmentId();
        System.out.println("Selected Department ID: " + selectedDepartmentId); // 디버깅 로그
        if (onDepartmentSelectionChanged != null) {
            onDepartmentSelectionChanged.run(); // 외부로 선택 변경 이벤트 전달
        }
    }

    // 초기화 메서드
    public void initialize() {
        List<VCampus> campuses = dataManager.getCampuses();
        System.out.println("Loaded Campuses: " + campuses); // 디버깅 로그
        this.pCampus.updateData(campuses);
    }

    // 캠퍼스 선택 → 단과대 데이터 업데이트
    public void updateColleges() {
        int campusId = this.pCampus.getSelectedId(); // 실제 ID 반환
        System.out.println("Selected Campus ID: " + campusId); // 디버깅 로그
        if (campusId != -1) {
            List<VCollege> colleges = dataManager.getCollegesByCampusId(campusId);
            System.out.println("Loaded Colleges: " + colleges); // 디버깅 로그
            this.pCollege.updateData(colleges);
            this.pDepartment.updateData(null); // 학과 초기화
        }
    }

    // 단과대 선택 → 학과 데이터 업데이트
    public void updateDepartments() {
        int collegeId = this.pCollege.getSelectedId(); // 실제 ID 반환
        System.out.println("Selected College ID: " + collegeId); // 디버깅 로그
        if (collegeId != -1) {
            List<VDepartment> departments = dataManager.getDepartmentsByCollegeId(collegeId);
            System.out.println("Loaded Departments: " + departments); // 디버깅 로그
            this.pDepartment.updateData(departments);
        }
    }

    // 선택된 학과 ID 반환
    public int getSelectedDepartmentId() {
        return this.pDepartment.getSelectedId();
    }

    // 내부 클래스: 계층별 데이터 관리 (캠퍼스, 단과대, 학과)
    private class PDirectory extends JTable {

        private static final long serialVersionUID = 1L;
        private DefaultTableModel tableModel;

        public PDirectory(String title, ListSelectionListener listSelectionHandler) {
            this.tableModel = new DefaultTableModel(new String[] { title, "ID" }, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // 셀 편집 방지
                }
            };
            this.setModel(this.tableModel);
            this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.getSelectionModel().addListSelectionListener(listSelectionHandler);
            this.getColumnModel().getColumn(1).setMinWidth(0); // ID 열 숨기기
            this.getColumnModel().getColumn(1).setMaxWidth(0);
            this.getColumnModel().getColumn(1).setWidth(0);
        }

        // 데이터 업데이트
        public void updateData(List<?> data) {
            this.tableModel.setRowCount(0); // 기존 데이터 초기화
            if (data != null) {
                for (Object item : data) {
                    if (item instanceof VCampus) {
                        VCampus campus = (VCampus) item;
                        this.tableModel.addRow(new Object[] { campus.getName(), campus.getId() });
                    } else if (item instanceof VCollege) {
                        VCollege college = (VCollege) item;
                        this.tableModel.addRow(new Object[] { college.getName(), college.getId() });
                    } else if (item instanceof VDepartment) {
                        VDepartment department = (VDepartment) item;
                        this.tableModel.addRow(new Object[] { department.getName(), department.getId() });
                    }
                }
                if (!data.isEmpty()) {
                    this.setRowSelectionInterval(0, 0); // 첫 번째 행 선택
                }
            }
        }

        // 선택된 ID 반환
        public int getSelectedId() {
            int selectedRow = this.getSelectedRow();
            if (selectedRow != -1) {
                return (int) this.tableModel.getValueAt(selectedRow, 1); // ID 열 값 반환
            }
            return -1; // 선택되지 않음
        }
    }
}