package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

import valueObject.VLogin;
import valueObject.VLoginResult;
import valueObject.VUser;

public interface ILogin extends Remote {
	public static final String OBJECT_NAME = "CLOGIN";

	VLoginResult login(VLogin vLogin) throws RemoteException;

	boolean checkDuplicateUserId(String userId) throws RemoteException;

	boolean registerUser(VUser user, VLogin login) throws RemoteException;

	String findUserId(String name, String email) throws RemoteException;

	// 비밀번호 찾기 메서드
	String findPassword(String userId, String email) throws RemoteException;
}