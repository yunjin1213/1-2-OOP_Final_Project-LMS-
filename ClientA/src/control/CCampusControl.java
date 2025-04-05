// Client - CCampusControl.java
package control;

import remoteInterface.ICampus;
import valueObject.VCampus;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class CCampusControl {
    private ICampus campus;

    public CCampusControl() {
        try {
            this.campus = (ICampus) Naming.lookup(ICampus.OBJECT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VCampus> getCampuses() {
        try {
            return campus.getCampuses();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}