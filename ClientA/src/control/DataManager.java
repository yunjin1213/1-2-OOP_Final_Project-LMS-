package control;

import java.rmi.RemoteException;
import java.util.List;

import valueObject.VCampus;
import valueObject.VCollege;
import valueObject.VDepartment;
import valueObject.VGangjwa;

public class DataManager {

	private CCampusControl campusControl;
	private CCollegeControl collegeControl;
	private CDepartmentControl departmentControl;
	private CGangjwaControl gangjwaControl;
	private CMiridamgiControl miridamgiControl;
	private CSugangSincheongControl sugangSincheongControl;

	// 생성자
	public DataManager() {
		this.campusControl = new CCampusControl();
		this.collegeControl = new CCollegeControl();
		this.departmentControl = new CDepartmentControl();
		this.gangjwaControl = new CGangjwaControl();
		this.miridamgiControl = new CMiridamgiControl();
		this.sugangSincheongControl = new CSugangSincheongControl();
	}

	// 캠퍼스 데이터 로딩
	public List<VCampus> getCampuses() {
		return campusControl.getCampuses();
	}

	// 단과대 데이터 로딩
	public List<VCollege> getCollegesByCampusId(int campusId) {
		return collegeControl.getCollegesByCampusId(campusId);
	}

	// 학과 데이터 로딩
	public List<VDepartment> getDepartmentsByCollegeId(int collegeId) {
		return departmentControl.getDepartmentsByCollegeId(collegeId);
	}

	// 강좌 데이터 로딩
	public List<VGangjwa> getGangjwasByDepartmentId(int departmentId) {
		return gangjwaControl.getGangjwasByDepartmentId(departmentId);
	}

	// 미리담기 저장
	public void saveMiridamgi(String userId, List<VGangjwa> vGangjwas) {
		miridamgiControl.saveMiridamgi(userId, vGangjwas);
	}

	// 미리담기 로딩
	public List<VGangjwa> getMiridamgi(String userId) {
		return miridamgiControl.getMiridamgi(userId);
	}

	public void deleteMiridamgi(String userId, List<VGangjwa> vGangjwas) {
		miridamgiControl.deleteMiridamgi(userId, vGangjwas); // RMI 호출
	}

	// 수강 신청 저장
	public void saveSugangSincheong(String userId, List<VGangjwa> vGangjwas) {
		sugangSincheongControl.saveSugangSincheong(userId, vGangjwas);
	}

	// 수강 신청 로딩
	public List<VGangjwa> getSugangSincheong(String userId) {
		return sugangSincheongControl.getSugangSincheong(userId);
	}

	// 미리담기 총 학점 계산
	public int getMiridamgiCredits(String userId) throws RemoteException {
        List<VGangjwa> miridamgiList = miridamgiControl.getMiridamgi(userId);
        return miridamgiList.stream().mapToInt(VGangjwa::getCredit).sum();
    }

    // 수강신청 강좌 총 학점 계산
    public int getSugangSincheongCredits(String userId) throws RemoteException {
        List<VGangjwa> sugangList = sugangSincheongControl.getSugangSincheong(userId);
        return sugangList.stream().mapToInt(VGangjwa::getCredit).sum();
    }
}