package remoteInterface;

import valueObject.VUser;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUser extends Remote {
	public final static String OBJECT_NAME = "CUSER";
	public VUser getUser(String userId) throws RemoteException;
}
