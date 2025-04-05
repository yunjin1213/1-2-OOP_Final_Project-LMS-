package control;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

import remoteInterface.IGangjwa;
import valueObject.VGangjwa;

public class CGangjwaControl {
    private IGangjwa gangjwa;

    public CGangjwaControl() {
        try {
            // RMI 레지스트리에서 IGangjwa 객체를 가져옴
            this.gangjwa = (IGangjwa) Naming.lookup(IGangjwa.OBJECT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VGangjwa> getGangjwasByDepartmentId(int departmentId) {
        if (gangjwa == null) {
            System.out.println("Gangjwa RMI Object is null");
            return null;
        }

        try {
            List<VGangjwa> gangjwas = gangjwa.getGangjwasByDepartmentId(departmentId);
            System.out.println("Control Fetched Gangjwas: " + gangjwas); // 디버깅 추가
            return gangjwas;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}