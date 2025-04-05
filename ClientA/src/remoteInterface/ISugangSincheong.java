package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import valueObject.VGangjwa;

public interface ISugangSincheong extends Remote {
    String OBJECT_NAME = "CSugangSincheong";

    void saveSugangSincheong(String userId, List<VGangjwa> vGangjwas) throws RemoteException;

    List<VGangjwa> getSugangSincheong(String userId) throws RemoteException;
}