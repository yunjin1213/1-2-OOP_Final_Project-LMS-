// ICollegeDirectory.java
package remoteInterface;

import valueObject.VCollege;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICollege extends Remote {
	public final static String OBJECT_NAME = "CCollege";
    List<VCollege> getCollegesByCampusId(int campusId) throws RemoteException;
}