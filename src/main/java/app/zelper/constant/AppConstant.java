package app.zelper.constant;

import java.io.File;

public interface AppConstant {

    String EMAIL_SOPORTE = "soporte@albatross.pe";
    
    String APP_ERROR_MESSAGE = "Error";
    
    String SESSION_USUARIO = "SESSION_USUARIO";
    
    String NEW_TICKET_EMAIL = "NEW_TICKET_EMAIL";
    
    String MMM = "SESSION_ADJUNTOS_MENSAJE";
    
    String ROOT_DIR = File.separator + "signos" + File.separator;
    
    String TMP_DIR = File.separator + "signos" + File.separator + "tmp" + File.separator;
    
    int DEFAULT_BUFFER_SIZE_DOWNLOAD = 1024;
}
