package app.zelper.enums;

import java.util.HashMap;
import java.util.Map;

public enum DiaEnum {

    LUN(1),
    MAR(2),
    MIE(3),
    JUE(4),
    VIE(5),
    SAB(6),
    DOM(7);
   
    private final Integer value;
    private static final Map<Integer, DiaEnum> lookup = new HashMap<Integer, DiaEnum>();

    static {
        for (DiaEnum d : DiaEnum.values()) {
            lookup.put(d.getValue(), d);
        }
    }

    private DiaEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static DiaEnum get(Integer abbreviation) {
        return lookup.get(abbreviation);
    }
    
}
