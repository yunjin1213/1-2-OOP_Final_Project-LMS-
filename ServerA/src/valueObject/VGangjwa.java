package valueObject;

import java.io.Serializable;

public class VGangjwa extends VValueObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private int gangjwaId;     // 강좌 ID (정수)
    private String courseName; // 강좌 이름
    private String instructor; // 강좌 담당 교수
    private int credit;        // 학점 (정수)
    private String scheduleTime;  // 강의 시간표

    // 기본 생성자
    public VGangjwa() {}

    // 모든 필드를 초기화하는 생성자 추가
    public VGangjwa(int gangjwaId, String courseName, String instructor, int credit, String scheduleTime) {
        this.gangjwaId = gangjwaId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.credit = credit;
        this.scheduleTime = scheduleTime;
    }

    // Getter와 Setter
    public int getGangjwaId() {
        return gangjwaId;
    }

    public void setGangjwaId(int gangjwaId) {
        this.gangjwaId = gangjwaId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    @Override
    public String toString() {
        return "VGangjwa [gangjwaId=" + gangjwaId + ", courseName=" + courseName 
               + ", instructor=" + instructor + ", credit=" + credit 
               + ", scheduleTime=" + scheduleTime + "]";
    }
}