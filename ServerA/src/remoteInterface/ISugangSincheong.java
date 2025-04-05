package remoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import valueObject.VGangjwa;

public interface ISugangSincheong extends Remote {
	String OBJECT_NAME = "CSugangSincheong";

	// 수강 신청 저장 메서드 (리스트 지원)
	void saveSugangSincheong(String userId, List<VGangjwa> vGangjwas) throws RemoteException;

	// 수강 신청 로딩 메서드
	List<VGangjwa> getSugangSincheong(String userId) throws RemoteException;
}