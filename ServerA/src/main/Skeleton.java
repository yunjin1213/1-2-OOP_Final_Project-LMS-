// Server - Skeleton.java
package main;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import constants.Cofiguration;
import control.CCampus;
import control.CCollege;
import control.CDepartment;
import control.CGangjwa;
import control.CLogin;
import control.CMiridamgi;
import control.CSugangSincheong;
import control.CUser;
import dao.LoginDAO;
import remoteInterface.ICampus;
import remoteInterface.ICollege;
import remoteInterface.IDepartment;
import remoteInterface.IGangjwa;
import remoteInterface.ILogin;
import remoteInterface.IMiridamgi;
import remoteInterface.ISugangSincheong;
import remoteInterface.IUser;

public class Skeleton {
    private Registry registry;

    public Skeleton() throws RemoteException {
        this.registry = LocateRegistry.createRegistry(Cofiguration.PORT_NUM);
    }

    public void register(String objectName, Remote object) throws RemoteException, AlreadyBoundException {
        Remote remote = UnicastRemoteObject.exportObject(object, 0);
        this.registry.rebind(objectName, remote);
        System.out.println(objectName + " 등록되었습니다.");
    }

    public void initialize() throws RemoteException, AlreadyBoundException {
        try {
            // DAO 초기화
            LoginDAO loginDAO = new LoginDAO();

            // 로그인 및 사용자 관련 객체 등록
            this.register(ILogin.OBJECT_NAME, new CLogin(loginDAO));
            this.register(IUser.OBJECT_NAME, new CUser(loginDAO));

            // **강좌 관련 등록 추가**
            this.register(ICampus.OBJECT_NAME, new CCampus()); 
            this.register(ICollege.OBJECT_NAME, new CCollege()); 
            this.register(IDepartment.OBJECT_NAME, new CDepartment());

            // **이 부분이 누락됨 → 추가**
            this.register(IGangjwa.OBJECT_NAME, new CGangjwa());

            // 수강 신청 및 미리담기 등록
            this.register(IMiridamgi.OBJECT_NAME, new CMiridamgi());       
            this.register(ISugangSincheong.OBJECT_NAME, new CSugangSincheong());

            System.out.println("서버 초기화 완료!");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("초기화 중 오류 발생: " + e.getMessage());
        }
    }

    public void run() throws AlreadyBoundException, RemoteException {
        System.out.println("Skeleton 서버가 실행 중입니다.");
        initialize();  // 서버 초기화 호출
    }
}