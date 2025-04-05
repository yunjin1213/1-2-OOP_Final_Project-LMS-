// ICampusDirectory.java
package remoteInterface;

import valueObject.VCampus;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICampus extends Remote {
	public final static String OBJECT_NAME = "CCampus";
    List<VCampus> getCampuses() throws RemoteException;
}