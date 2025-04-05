package valueObject;

import java.io.Serializable;

public class VLogin extends VValueObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String password;

    // 기본 생성자
    public VLogin() {
    }

    // 매개변수 있는 생성자
    public VLogin(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    // Getter & Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}