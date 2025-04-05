package valueObject;

import java.io.Serializable;

public class VDepartment extends VValueObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String code;
    private int collegeId;

    public VDepartment(int id, String name, String code, int collegeId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.collegeId = collegeId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getCollegeId() {
        return collegeId;
    }

    @Override
    public String toString() {
        return name;
    }
}