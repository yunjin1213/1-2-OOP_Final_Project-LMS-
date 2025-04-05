package control;

import dao.LoginDAO;
import remoteInterface.IUser;
import valueObject.VUser;

public class CUser implements IUser {
    private LoginDAO loginDAO;

    public CUser(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @Override
    public VUser getUser(String userId) {
        return loginDAO.getUserDetails(userId);
    }
}