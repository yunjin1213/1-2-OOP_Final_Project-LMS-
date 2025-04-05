package valueObject;

public class VDepartment extends VValueObject {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String code;
    private int collegeId;

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
        return "VDepartment [id=" + id + ", name=" + name + ", code=" + code + ", collegeId=" + collegeId + "]";
    }
}