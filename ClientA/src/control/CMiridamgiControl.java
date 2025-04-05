package control;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

import remoteInterface.IMiridamgi;
import valueObject.VGangjwa;

public class CMiridamgiControl {

    private IMiridamgi miridamgiService;

    public CMiridamgiControl() {
        try {
            this.miridamgiService = (IMiridamgi) Naming.lookup(IMiridamgi.OBJECT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMiridamgi(String userId, List<VGangjwa> vGangjwas) {
        try {
            miridamgiService.saveMiridamgi(userId, vGangjwas);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<VGangjwa> getMiridamgi(String userId) {
        try {
            return miridamgiService.getMiridamgi(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 강좌 삭제 메서드 추가
    public void deleteMiridamgi(String userId, List<VGangjwa> vGangjwas) {
        try {
            miridamgiService.deleteMiridamgi(userId, vGangjwas);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}