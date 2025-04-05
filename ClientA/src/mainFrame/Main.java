package mainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import aspect.ExceptionManager;
import control.CLoginControl;
import control.DataManager;
import valueObject.VUser;

public class Main {
    private PLoginDialog pLoginDialog;
    private PMainFrame pMainFrame;

    private ExceptionManager exceptionManager;
    private CLoginControl loginControl;
    private DataManager dataManager;

    // Main 생성자
    public Main() throws MalformedURLException, NotBoundException, RemoteException {
        // 초기화
        this.exceptionManager = new ExceptionManager();
        this.loginControl = new CLoginControl();
        this.dataManager = new DataManager();

        // 로그인 창 생성
        this.pLoginDialog = new PLoginDialog(new ActionHandler(), loginControl, dataManager);
    }

    // 애플리케이션 초기화
    public void initialize() {
        showLoginDialog(); // 로그인 창 띄우기
    }

    // 로그인 창 표시
    public void showLoginDialog() {
        if (pMainFrame != null) {
            pMainFrame.dispose();
            pMainFrame = null;
        }
        if (pLoginDialog == null) { // pLoginDialog가 null인 경우 새로 생성
            pLoginDialog = new PLoginDialog(new ActionHandler(), loginControl, dataManager);
        }
        pLoginDialog.initialize();
    }

    // 메인 화면 표시
    public void showMainFrame(VUser vUser) {
        if (pLoginDialog != null) {
            pLoginDialog.dispose();
            pLoginDialog = null;
        }
        this.pMainFrame = new PMainFrame(this.dataManager, this); // Main 전달
        try {
            this.pMainFrame.initialize(vUser); // 예외 처리 추가
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            e.printStackTrace(); // 예외 로그 출력
            exceptionManager.process(e); // 예외 처리 매니저 호출
        }
    }

    // 사용자 검증
    private void validateUser(Object source) {
        try {
            // 로그인 창에서 사용자 정보 검증
            VUser vUser = this.pLoginDialog.validateUser(source);
            if (vUser != null) {
                showMainFrame(vUser); // 로그인 성공 시 메인 화면 표시
            }
        } catch (Exception e) {
            // 기타 예외 처리
            exceptionManager.process(e);
        }
    }

    // 로그인 버튼 이벤트 핸들러
    public class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            validateUser(event.getSource()); // 로그인 시 validateUser 호출
        }
    }

    // 메인 메서드
    public static void main(String[] args) {
        try {
            Main main = new Main();
            main.initialize(); // 애플리케이션 초기화
        } catch (Exception e) {
            // 초기화 실패 시 예외 출력
            e.printStackTrace();
        }
    }
}