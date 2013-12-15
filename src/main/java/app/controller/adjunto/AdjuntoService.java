package app.controller.adjunto;

import app.model.AdjuntoMensaje;
import app.model.AdjuntoTicket;
import app.model.Ticket;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AdjuntoService {

    String upload(MultipartFile multipartFile, List<AdjuntoMensaje> adjuntos) throws IOException;

    void remove(String fileName, List<AdjuntoMensaje> adjuntos);

    String upload(MultipartFile multipartFile, Ticket ticket) throws IOException;

    void remove(String fileName, Ticket ticket);

    void saveAdjuntosTicket(List<AdjuntoTicket> adjuntos);

    void saveAdjuntosMensaje(List<AdjuntoMensaje> adjuntos);
}
