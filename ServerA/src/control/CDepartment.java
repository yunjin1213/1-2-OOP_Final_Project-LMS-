// Server - CDepartment.java
package control;

import dao.DepartmentDAO;
import model.MDepartment;
import valueObject.VDepartment;
import remoteInterface.IDepartment;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CDepartment implements IDepartment {

    private final DepartmentDAO departmentDAO;

    public CDepartment() {
        this.departmentDAO = new DepartmentDAO();
    }

    @Override
    public List<VDepartment> getDepartmentsByCollegeId(int collegeId) throws RemoteException {
        List<MDepartment> mDepartments = departmentDAO.getDepartmentsByCollegeId(collegeId);
        List<VDepartment> vDepartments = new ArrayList<>();
        for (MDepartment mDepartment : mDepartments) {
            VDepartment vDepartment = new VDepartment();
            vDepartment.setId(mDepartment.getId());
            vDepartment.setName(mDepartment.getName());
            vDepartment.setCode(mDepartment.getCode());
            vDepartment.setCollegeId(mDepartment.getCollegeId());
            vDepartments.add(vDepartment);
        }
        return vDepartments;
    }
}