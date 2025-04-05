package model;

public class MCampus {
    private int id;          // 캠퍼스 ID
    private String name;     // 캠퍼스 이름
    private String code;     // 캠퍼스 코드

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

    @Override
    public String toString() {
        return "Campus [id=" + id + ", name=" + name + ", code=" + code + "]";
    }
}