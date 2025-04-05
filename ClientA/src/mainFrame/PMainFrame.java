package mainFrame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import constants.Constants.EMainFrame;
import sugangSincheong.PSugangSincheongPanel;
import control.DataManager;
import valueObject.VUser;

public class PMainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private DataManager dataManager;
    private Main mainApp;

    private PMenuBar pMenuBar;
    private PToolBar pToolBar;
    private PSugangSincheongPanel pSugangSincheongPanel;

    private WindowListener windowsHandler;
    private VUser vUser;

    // 생성자
    public PMainFrame(DataManager dataManager, Main mainApp) {
        this.dataManager = dataManager;
        this.mainApp = mainApp;

        // 기본 프레임 설정
        this.setSize(EMainFrame.width.getInt(), EMainFrame.height.getInt());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // 윈도우 리스너 추가
        this.windowsHandler = new WindowsHandler();
        this.addWindowListener(this.windowsHandler);

        // UI 컴포넌트 생성
        this.pMenuBar = new PMenuBar();
        this.setJMenuBar(this.pMenuBar);
    }

    public void initialize(VUser vUser) throws MalformedURLException, NotBoundException, RemoteException {
        this.vUser = vUser;
        this.setVisible(true);

        // PSugangSincheongPanel 먼저 생성
        this.pSugangSincheongPanel = new PSugangSincheongPanel(this.dataManager, vUser.getUserId());
        this.add(this.pSugangSincheongPanel, BorderLayout.CENTER);

        // PToolBar 생성 후 PSugangSincheongPanel과 연결
        this.pToolBar = new PToolBar(this.pSugangSincheongPanel, this.mainApp);
        this.add(this.pToolBar, BorderLayout.NORTH);

        // UI 초기화
        this.pMenuBar.initialize();
        this.pSugangSincheongPanel.initialize(this.vUser);
    }

    // 종료 메서드
    private void finish() throws RemoteException {
        if (this.pSugangSincheongPanel != null) {
            this.pSugangSincheongPanel.finish();
        }
    }

    // 윈도우 리스너
    private class WindowsHandler implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            try {
                finish();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }
}