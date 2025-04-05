// IDepartmentDirectory.java
package remoteInterface;

import valueObject.VDepartment;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IDepartment extends Remote {
	public final static String OBJECT_NAME = "CDepartment";
    List<VDepartment> getDepartmentsByCollegeId(int collegeId) throws RemoteException;
}