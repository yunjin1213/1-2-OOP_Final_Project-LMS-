package control;

import dao.GangjwaDAO;
import model.MGangjwa;
import remoteInterface.IGangjwa;
import valueObject.VGangjwa;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CGangjwa implements IGangjwa {

    private GangjwaDAO gangjwaDAO;

    public CGangjwa() {
        this.gangjwaDAO = new GangjwaDAO();
    }

    @Override
    public List<VGangjwa> getGangjwasByDepartmentId(int departmentId) throws RemoteException {
        List<MGangjwa> mGangjwas = gangjwaDAO.getGangjwasByDepartmentId(departmentId);
        List<VGangjwa> vGangjwas = mapMGangjwasToVGangjwas(mGangjwas);
        System.out.println("RMI Server Fetched Gangjwas: " + vGangjwas); // 디버깅 추가
        return vGangjwas;
    }

    private List<VGangjwa> mapMGangjwasToVGangjwas(List<MGangjwa> mGangjwas) {
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