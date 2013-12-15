package app.controller.adjunto;

import app.dao.AdjuntoMensajeDAO;
import app.dao.AdjuntoTicketDAO;
import app.model.AdjuntoMensaje;
import app.model.AdjuntoTicket;
import app.model.Mensaje;
import app.model.Ticket;
import app.zelper.constant.AppConstant;
import app.zelper.misc.FileSave;
import app.zelper.misc.TypesUtil;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdjuntoServiceImp implements AdjuntoService {

    @Autowired
    AdjuntoTicketDAO adjuntoTicketDAO;
    @Autowired
    AdjuntoMensajeDAO adjuntoMensajeDAO;

    @Override
    public String upload(MultipartFile multipartFile, Ticket ticket) throws IOException {

        String fileName = TypesUtil.getUnixTime() + "." + TypesUtil.getClean(multipartFile.getOriginalFilename());
        String absoluteName = AppConstant.TMP_DIR + fileName;
        FileSave.saveToDisk(multipartFile, absoluteName);

        AdjuntoTicket adjunto = new AdjuntoTicket();
        adjunto.setFechaCreacion(new Date());
        adjunto.setTitulo(multipartFile.getOriginalFilename());
        adjunto.setNombre(fileName);
        adjunto.setContentType(multipartFile.getContentType());
        adjunto.setSize(multipartFile.getSize());
        adjunto.setTicket(ticket);

        ticket.getAdjuntos().add(adjunto);

        return fileName;

    }

    @Override
    public void remove(String fileName, Ticket ticket) {
        String absoluteName = AppConstant.TMP_DIR + fileName;

        List<AdjuntoTicket> adjuntos = ticket.getAdjuntos();
        for (AdjuntoTicket a : adjuntos) {
            if (a.getNombre().equals(fileName)) {

                FileSave.deleteFromDisk(absoluteName);
                adjuntos.remove(a);
                break;
            }
        }
    }

    @Override
    public void saveAdjuntosTicket(List<AdjuntoTicket> adjuntos) {
        for (AdjuntoTicket adjunto : adjuntos) {
            String oldAbsName = AppConstant.TMP_DIR + adjunto.getNombre();

            String newDir = AppConstant.ROOT_DIR + adjunto.getTicket().getCodigo();
            String newAbsName = newDir + File.separator + adjunto.getNombre();

            FileSave.createDirectory(newDir);
            FileSave.renameFiles(oldAbsName, newAbsName);

            adjuntoTicketDAO.addDAO(adjunto);

        }
    }

    @Override
    public String upload(MultipartFile multipartFile, List<AdjuntoMensaje> adjuntos) throws IOException {

        String fileName = TypesUtil.getUnixTime() + "." + TypesUtil.getClean(multipartFile.getOriginalFilename());
        String absoluteName = AppConstant.TMP_DIR + fileName;
        FileSave.saveToDisk(multipartFile, absoluteName);

        AdjuntoMensaje adjunto = new AdjuntoMensaje();
        adjunto.setFechaCreacion(new Date());
        adjunto.setTitulo(multipartFile.getOriginalFilename());
        adjunto.setNombre(fileName);
        adjunto.setContentType(multipartFile.getContentType());
        adjunto.setSize(multipartFile.getSize());

        adjuntos.add(adjunto);
        
        
        return fileName;

    }

    @Override
    public void remove(String fileName, List<AdjuntoMensaje> adjuntos) {
        String absoluteName = AppConstant.TMP_DIR + fileName;
      
        for (AdjuntoMensaje a : adjuntos) {
            if (a.getNombre().equals(fileName)) {

                FileSave.deleteFromDisk(absoluteName);
                adjuntos.remove(a);
                break;
            }
        }
    }

    @Override
    public void saveAdjuntosMensaje(List<AdjuntoMensaje> adjuntos) {
        for (AdjuntoMensaje adjunto : adjuntos) {
            String oldAbsName = AppConstant.TMP_DIR + adjunto.getNombre();

            String newDir = AppConstant.ROOT_DIR + adjunto.getMensaje().getTicket().getCodigo();
            String newAbsName = newDir + File.separator + adjunto.getNombre();

            FileSave.createDirectory(newDir);
            FileSave.renameFiles(oldAbsName, newAbsName);

            adjuntoMensajeDAO.addDAO(adjunto);

        }
    }
}
