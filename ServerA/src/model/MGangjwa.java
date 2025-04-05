package model;

public class MGangjwa {
	private int gangjwaId; // gangjwa_id
	private String courseName; // course_name
	private String instructor; // instructor
	private int credit; // credit
	private String scheduleTime; // schedule_time

	// Getters and Setters
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
}