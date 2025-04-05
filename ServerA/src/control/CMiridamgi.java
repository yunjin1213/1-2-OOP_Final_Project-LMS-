package control;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import dao.MiridamgiDAO;
import model.MGangjwa;
import remoteInterface.IMiridamgi;
import valueObject.VGangjwa;

public class CMiridamgi implements IMiridamgi {

    private final MiridamgiDAO miridamgiDAO;

    public CMiridamgi() throws RemoteException {
        this.miridamgiDAO = new MiridamgiDAO();
    }

    @Override
    public void saveMiridamgi(String userId, List<VGangjwa> vGangjwas) throws RemoteException {
        List<MGangjwa> mGangjwas = convertToMGangjwas(vGangjwas);
        miridamgiDAO.saveMiridamgi(userId, mGangjwas);
    }

    @Override
    public List<VGangjwa> getMiridamgi(String userId) throws RemoteException {
        List<MGangjwa> mGangjwas = miridamgiDAO.getMiridamgi(userId);
        return convertToVGangjwas(mGangjwas);
    }

    @Override
    public void deleteMiridamgi(String userId, List<VGangjwa> vGangjwas) throws RemoteException {
        List<MGangjwa> mGangjwas = convertToMGangjwas(vGangjwas);
        miridamgiDAO.deleteMiridamgi(userId, mGangjwas);
        System.out.println("[DEBUG] 미리담기 삭제 완료: " + userId);
    }

    // VGangjwa → MGangjwa 변환 메서드
    private List<MGangjwa> convertToMGangjwas(List<VGangjwa> vGangjwas) {
        List<MGangjwa> mGangjwas = new ArrayList<>();
        for (VGangjwa vGangjwa : vGangjwas) {
            MGangjwa mGangjwa = new MGangjwa();
            mGangjwa.setGangjwaId(vGangjwa.getGangjwaId());
            mGangjwa.setCourseName(vGangjwa.getCourseName());
            mGangjwa.setInstructor(vGangjwa.getInstructor());
            mGangjwa.setCredit(vGangjwa.getCredit());
            mGangjwa.setScheduleTime(vGangjwa.getScheduleTime());
            mGangjwas.add(mGangjwa);
        }
        return mGangjwas;
    }

    // MGangjwa → VGangjwa 변환 메서드
    private List<VGangjwa> convertToVGangjwas(List<MGangjwa> mGangjwas) {
        List<VGangjwa> vGangjwas = new ArrayList<>();
        for (MGangjwa mGangjwa : mGangjwas) {
            VGangjwa vGangjwa = new VGangjwa(
                mGangjwa.getGangjwaId(),
                mGangjwa.getCourseName(),
                mGangjwa.getInstructor(),
                mGangjwa.getCredit(),
                mGangjwa.getScheduleTime()
            );
            vGangjwas.add(vGangjwa);
        }
        return vGangjwas;
    }
}