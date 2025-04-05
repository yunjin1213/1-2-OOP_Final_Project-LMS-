package control;

import java.rmi.Naming;
import java.rmi.RemoteException;

import remoteInterface.ILogin;
import remoteInterface.IUser;
import valueObject.VLogin;
import valueObject.VLoginResult;
import valueObject.VUser;

public class CLoginControl {
	private ILogin cLogin;
	private IUser cUser;

	// 생성자
	public CLoginControl() {
		try {
			this.cLogin = (ILogin) Naming.lookup(ILogin.OBJECT_NAME);
			this.cUser = (IUser) Naming.lookup(IUser.OBJECT_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 로그인 기능
	public VLoginResult login(VLogin vLogin) throws RemoteException {
		return cLogin.login(vLogin);
	}

	// 사용자 정보 조회 기능
	public VUser getUser(String userId) throws RemoteException {
		return cUser.getUser(userId);
	}

	// 아이디 중복 검사 기능
	public boolean checkDuplicateUserId(String userId) throws RemoteException {
		return cLogin.checkDuplicateUserId(userId);
	}

	// 회원가입 기능
	public boolean registerUser(VUser user, VLogin login) throws RemoteException {
		return cLogin.registerUser(user, login);
	}

	// ID 찾기 기능 추가
	public String findUserId(String name, String email) throws RemoteException {
		return cLogin.findUserId(name, email);
	}
	
	public String findPassword(String userId, String email) throws RemoteException {
        return cLogin.findPassword(userId, email);
    }

}