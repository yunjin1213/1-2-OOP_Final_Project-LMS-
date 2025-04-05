package control;

import dao.SugangSincheongDAO;
import model.MGangjwa;
import remoteInterface.ISugangSincheong;
import valueObject.VGangjwa;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CSugangSincheong implements ISugangSincheong {

    private final SugangSincheongDAO sugangSincheongDAO;

    public CSugangSincheong() throws RemoteException {
        this.sugangSincheongDAO = new SugangSincheongDAO();
    }

    // List<VGangjwa>를 받아서 강좌들을 저장
    @Override
    public void saveSugangSincheong(String userId, List<VGangjwa> vGangjwas) throws RemoteException {
        List<MGangjwa> mGangjwas = new ArrayList<>();

        // VGangjwa 객체를 MGangjwa로 변환
        for (VGangjwa vGangjwa : vGangjwas) {
            MGangjwa mGangjwa = new MGangjwa();
            mGangjwa.setGangjwaId(vGangjwa.getGangjwaId());
            mGangjwa.setCourseName(vGangjwa.getCourseName());
            mGangjwa.setInstructor(vGangjwa.getInstructor());
            mGangjwa.setCredit(vGangjwa.getCredit());
            mGangjwa.setScheduleTime(vGangjwa.getScheduleTime());
            mGangjwas.add(mGangjwa);
        }

        // DAO를 통해 강좌 목록 저장
        sugangSincheongDAO.saveSugangSincheong(userId, mGangjwas);
    }

    // 수강신청 강좌 목록 조회
    @Override
    public List<VGangjwa> getSugangSincheong(String userId) throws RemoteException {
        List<MGangjwa> mGangjwas = sugangSincheongDAO.getSugangSincheong(userId);
        List<VGangjwa> vGangjwas = new ArrayList<>();

        // MGangjwa 객체를 VGangjwa로 변환
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