package control;

import dao.CollegeDAO;
import model.MCollege;
import valueObject.VCollege;
import remoteInterface.ICollege;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CCollege implements ICollege {

    private final CollegeDAO collegeDAO;

    public CCollege() {
        this.collegeDAO = new CollegeDAO();
    }

    @Override
    public List<VCollege> getCollegesByCampusId(int campusId) throws RemoteException {
        List<MCollege> mColleges = collegeDAO.getCollegesByCampusId(campusId);
        List<VCollege> vColleges = new ArrayList<>();
        for (MCollege mCollege : mColleges) {
            VCollege vCollege = new VCollege(
                mCollege.getId(),
                mCollege.getName(),
                mCollege.getCode(),
                mCollege.getCampusId(),
                mCollege.getMaxCredits() // max_credits 추가
            );
            vColleges.add(vCollege);
        }
        return vColleges;
    }
}