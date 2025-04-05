package valueObject;

import java.io.Serializable;

public class VCollege extends VValueObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String code;
    private int campusId;
    private int maxCredits;

    public VCollege(int id, String name, String code, int campusId, int maxCredits) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.campusId = campusId;
        this.maxCredits = maxCredits;
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

    public int getCampusId() {
        return campusId;
    }

    public int getMaxCredits() {
        return maxCredits;
    }

    @Override
    public String toString() {
        return name + " (Max Credits: " + maxCredits + ")";
    }
}