package model;

public class MDepartment {
    private int id;          // 학과 ID
    private String name;     // 학과 이름
    private String code;     // 학과 코드
    private int collegeId;   // 해당 학과의 대학 ID

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

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + ", code=" + code + ", collegeId=" + collegeId + "]";
    }
}