package app.controller.adm.staff;

import app.model.Persona;
import java.util.List;

public interface AdmStaffService {

    public List<Persona> getAllStaff();

    public Persona getStaff(Persona staff);

    public void addStaff(Persona staff);

    public void updateStaff(Persona staff);

    public void deleteStaff(Persona staff);

}
