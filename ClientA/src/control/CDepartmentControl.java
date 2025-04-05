// Client - CDepartmentControl.java
package control;

import remoteInterface.IDepartment;
import valueObject.VDepartment;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class CDepartmentControl {
    private IDepartment department;

    public CDepartmentControl() {
        try {
            this.department = (IDepartment) Naming.lookup(IDepartment.OBJECT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VDepartment> getDepartmentsByCollegeId(int collegeId) {
        try {
            return department.getDepartmentsByCollegeId(collegeId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}