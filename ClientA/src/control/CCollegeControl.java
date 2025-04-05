// Client - CCollegeControl.java
package control;

import remoteInterface.ICollege;
import valueObject.VCollege;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class CCollegeControl {
    private ICollege college;

    public CCollegeControl() {
        try {
            this.college = (ICollege) Naming.lookup(ICollege.OBJECT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VCollege> getCollegesByCampusId(int campusId) {
        try {
            return college.getCollegesByCampusId(campusId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}