package model;

public class MCollege {
    private int id;          // 대학 ID
    private String name;     // 대학 이름
    private String code;     // 대학 코드
    private int campusId;    // 해당 대학의 캠퍼스 ID
    private int maxCredits;  // 수강 신청 가능 학점

    // Getter와 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCampusId() {
        return campusId;
    }

    public void setCampusId(int campusId) {
        this.campusId = campusId;
    }

    public int getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(int maxCredits) {
        this.maxCredits = maxCredits;
    }

    @Override
    public String toString() {
        return "College [id=" + id + ", name=" + name + ", code=" + code + ", campusId=" + campusId + ", maxCredits=" + maxCredits + "]";
    }
}