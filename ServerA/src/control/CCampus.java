// Server - CCampus.java
package control;

import dao.CampusDAO;
import model.MCampus;
import valueObject.VCampus;
import remoteInterface.ICampus;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CCampus implements ICampus {

    private final CampusDAO campusDAO;

    public CCampus() {
        this.campusDAO = new CampusDAO();
    }

    @Override
    public List<VCampus> getCampuses() throws RemoteException {
        List<MCampus> mCampuses = campusDAO.getAllCampuses();
        List<VCampus> vCampuses = new ArrayList<>();
        for (MCampus mCampus : mCampuses) {
            VCampus vCampus = new VCampus();
            vCampus.setId(mCampus.getId());
            vCampus.setName(mCampus.getName());
            vCampus.setCode(mCampus.getCode());
            vCampuses.add(vCampus);
        }
        return vCampuses;
    }
}