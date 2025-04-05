package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import valueObject.VGangjwa;

public interface IGangjwa extends Remote {
    public final static String OBJECT_NAME = "CGangjwa";

    // 특정 학과의 강좌 목록 가져오기
    List<VGangjwa> getGangjwasByDepartmentId(int departmentId) throws RemoteException;
}