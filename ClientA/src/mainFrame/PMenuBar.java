package mainFrame;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	private JLabel timeLabel; // 시간을 표시할 라벨
	private Timer timer; // 시간을 갱신하기 위한 타이머

	public PMenuBar() {




		// 시간 라벨 추가 (가운데 정렬)
		this.timeLabel = new JLabel();
		this.timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		this.add(Box.createHorizontalGlue()); // 왼쪽 메뉴와 시간 라벨 사이의 간격
		this.add(this.timeLabel);
		this.add(Box.createHorizontalGlue()); // 시간 라벨과 오른쪽 메뉴 사이의 간격

		// 타이머를 이용하여 시간 갱신
		this.timer = new Timer(1000, e -> updateTime());
		this.timer.start();
		updateTime(); // 초기 시간을 설정
	}

	public void initialize() {
		// 초기화 관련 추가 작업이 필요하면 작성
	}

	// 현재 시간을 갱신하는 메서드
	private void updateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		String currentTime = sdf.format(new Date());
		this.timeLabel.setText(currentTime);
	}
}