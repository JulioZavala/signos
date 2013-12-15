package app.zelper.enums;

import java.util.HashMap;
import java.util.Map;

public enum Usuarios {

    Administrador(0),
    Staff(1),
    Stakeholder(2);
    
    
    private Integer val;
    private static final Map<Integer, Usuarios> lookup = new HashMap<Integer, Usuarios>();

    Usuarios(Integer val) {
        this.val = val;
    }

    static {
        for (Usuarios d : Usuarios.values()) {
            lookup.put(d.getValue(), d);
        }
    }

    public int getValue() {
        return val;
    }

    public static Usuarios get(Integer abbreviation) {
        return lookup.get(abbreviation);
    }
}
