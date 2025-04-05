package model;

public class MUser {
    private String userId;
    private String name;
    private String address;
    private String email;  // 이메일 필드 추가

    public MUser() {
    }

    // Getter & Setter 메서드 추가
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
}