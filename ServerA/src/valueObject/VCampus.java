package valueObject;

public class VCampus extends VValueObject {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String code;

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
        return "VCampus [id=" + id + ", name=" + name + ", code=" + code + "]";
    }
}