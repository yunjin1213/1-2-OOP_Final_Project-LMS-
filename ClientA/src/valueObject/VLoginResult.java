package valueObject;

import java.io.Serializable;

public class VLoginResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String message;

    // 기본 생성자
    public VLoginResult() {
    }

    // 매개변수 있는 생성자
    public VLoginResult(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    // Getter & Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}