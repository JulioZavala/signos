package app.zelper.enums;

import java.util.HashMap;
import java.util.Map;

public enum EstadoTicket {

    Activo(0),
    Respondido(1),
    Resuelto(2);
    
    
    private Integer val;
    private static final Map<Integer, EstadoTicket> lookup = new HashMap<Integer, EstadoTicket>();

    EstadoTicket(Integer val) {
        this.val = val;
    }

    static {
        for (EstadoTicket d : EstadoTicket.values()) {
            lookup.put(d.getValue(), d);
        }
    }

    public int getValue() {
        return val;
    }

    public static EstadoTicket get(Integer abbreviation) {
        return lookup.get(abbreviation);
    }
}
