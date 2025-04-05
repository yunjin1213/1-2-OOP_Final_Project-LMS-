package control;

import dao.LoginDAO;
import remoteInterface.ILogin;
import valueObject.VLogin;
import valueObject.VLoginResult;
import valueObject.VUser;

import java.rmi.RemoteException;

public class CLogin implements ILogin {

	private LoginDAO loginDAO;

	// 생성자
	public CLogin(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	// 로그인 메서드
	@Override
	public VLoginResult login(VLogin vLogin) throws RemoteException {
		boolean isAuthenticated = loginDAO.validateLogin(vLogin.getUserId(), vLogin.getPassword());
		return isAuthenticated ? new VLoginResult(vLogin.getUserId(), "로그인 성공") : null;
	}

	// 중복된 사용자 ID 확인
	@Override
	public boolean checkDuplicateUserId(String userId) throws RemoteException {
		return loginDAO.isUserIdDuplicate(userId);
	}

	// 회원가입
	@Override
	public boolean registerUser(VUser user, VLogin login) throws RemoteException {
		return loginDAO.registerUser(user, login);
	}

	// ID 찾기 메서드
	@Override
	public String findUserId(String name, String email) throws RemoteException {
		return loginDAO.findUserId(name, email);
	}

	// 비밀번호 찾기 메서드
	@Override
	public String findPassword(String userId, String email) throws RemoteException {
		return loginDAO.findPassword(userId, email);
	}
}