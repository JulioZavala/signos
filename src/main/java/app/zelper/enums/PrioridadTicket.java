package app.zelper.enums;

import java.util.HashMap;
import java.util.Map;

public enum PrioridadTicket {

    Normal(0),
    Alta(1),
    Crítica(2);
    
    
    private Integer val;
    private static final Map<Integer, PrioridadTicket> lookup = new HashMap<Integer, PrioridadTicket>();

    PrioridadTicket(Integer val) {
        this.val = val;
    }

    static {
        for (PrioridadTicket d : PrioridadTicket.values()) {
            lookup.put(d.getValue(), d);
        }
    }

    public int getValue() {
        return val;
    }

    public static PrioridadTicket get(Integer abbreviation) {
        return lookup.get(abbreviation);
    }
}
