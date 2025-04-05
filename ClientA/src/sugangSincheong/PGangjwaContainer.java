package sugangSincheong;

import java.util.List;
import javax.swing.JPanel;
import valueObject.VGangjwa;

public abstract class PGangjwaContainer extends JPanel {

    private static final long serialVersionUID = 1L;

    // 강좌 추가 메서드
    public abstract void addGangjwas(List<VGangjwa> vSelectedGangjwas);

    // 강좌 제거 메서드
    public abstract List<VGangjwa> removeSelectedGangjwas();

    // 강좌 선택만 반환 (제거하지 않음)
    public abstract List<VGangjwa> getSelectedGangjwas();

    // 강좌 전체 목록 반환
    public abstract List<VGangjwa> getGangjwas();
}
