package valueObject;

import java.io.Serializable;

public class VCampus extends VValueObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String code;

    public VCampus(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
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

    @Override
    public String toString() {
        return name;
    }
}