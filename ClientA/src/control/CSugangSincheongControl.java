package control;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

import remoteInterface.ISugangSincheong;
import valueObject.VGangjwa;

public class CSugangSincheongControl {
    private ISugangSincheong sugangService;

    public CSugangSincheongControl() {
        try {
            this.sugangService = (ISugangSincheong) Naming.lookup(ISugangSincheong.OBJECT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 목록 전체 저장 지원
    public void saveSugangSincheong(String userId, List<VGangjwa> vGangjwas) {
        try {
            sugangService.saveSugangSincheong(userId, vGangjwas);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<VGangjwa> getSugangSincheong(String userId) {
        try {
            return sugangService.getSugangSincheong(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}