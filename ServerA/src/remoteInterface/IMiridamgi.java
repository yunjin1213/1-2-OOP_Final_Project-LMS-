package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import valueObject.VGangjwa;

public interface IMiridamgi extends Remote {
    String OBJECT_NAME = "CMiridamgi";

    void saveMiridamgi(String userId, List<VGangjwa> vGangjwas) throws RemoteException;

    List<VGangjwa> getMiridamgi(String userId) throws RemoteException;

    void deleteMiridamgi(String userId, List<VGangjwa> vGangjwas) throws RemoteException; // 삭제 메서드
}