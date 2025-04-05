package valueObject;

import java.io.Serializable;

public class VUser extends VValueObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String name;
    private String address;
    private String email;
    private int campusId;
    private int collegeId;
    private int departmentId;
    private int maxCredits;
    private int currentCredits;

    // 기본 생성자
    public VUser() {}

    // 모든 필드를 포함하는 생성자
    public VUser(String userId, String name, String address, String email, 
                 int campusId, int collegeId, int departmentId, int maxCredits, int currentCredits) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.campusId = campusId;
        this.collegeId = collegeId;
        this.departmentId = departmentId;
        this.maxCredits = maxCredits;
        this.currentCredits = currentCredits;
    }

    // Getter & Setter 메서드
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCampusId() {
        return campusId;
    }

    public void setCampusId(int campusId) {
        this.campusId = campusId;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(int maxCredits) {
        this.maxCredits = maxCredits;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public void setCurrentCredits(int currentCredits) {
        this.currentCredits = currentCredits;
    }
}