package app.controller.adjunto;

import app.model.AdjuntoMensaje;
import app.model.Ticket;
import app.zelper.constant.AppConstant;
import app.zelper.controller.JsonResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("file")
@SessionAttributes("ticket")
public class AdjuntoController {

    @Autowired
    AdjuntoService service;

    @RequestMapping("download/{ticket}/{file:.*}")
    public void downloadFile(@PathVariable String ticket, @PathVariable String file, HttpServletResponse response) throws Exception {
        String fileNameRoot = AppConstant.ROOT_DIR + ticket + File.separator + file;

        File filex = new File(fileNameRoot);
        if (!filex.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        

        response.reset();
        response.setBufferSize(AppConstant.DEFAULT_BUFFER_SIZE_DOWNLOAD);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "inline; filename=\"" + file + "\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {

            input = new BufferedInputStream(new FileInputStream(filex), AppConstant.DEFAULT_BUFFER_SIZE_DOWNLOAD);
            output = new BufferedOutputStream(response.getOutputStream(), AppConstant.DEFAULT_BUFFER_SIZE_DOWNLOAD);
            IOUtils.copy(input, output);
            response.flushBuffer();
        } finally {
            close(output);
            close(input);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @ResponseBody
    @RequestMapping("upload")
    public JsonResponse uploadFile(@RequestParam("file") MultipartFile file, @ModelAttribute("ticket") Ticket ticket) {
        JsonResponse json = new JsonResponse();

        try {
            String fileName = service.upload(file, ticket);

            json.setMessage("Importación finalizada.");
            json.setData(fileName);
            json.setSuccess(true);

        } catch (Exception e) {
            json.setSuccess(false);
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            return json;
        }
    }

    @ResponseBody
    @RequestMapping("remove")
    public JsonResponse removeFile(@RequestParam String file, @ModelAttribute("ticket") Ticket ticket) {
        JsonResponse json = new JsonResponse();

        try {
            service.remove(file, ticket);

            json.setMessage("Adjunto eliminado.");
            json.setSuccess(true);

        } catch (Exception e) {
            json.setSuccess(false);
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            return json;
        }
    }

    @ResponseBody
    @RequestMapping("uploadMessage")
    public JsonResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String _cid, HttpSession session) {
        JsonResponse json = new JsonResponse();

        try {
            List<AdjuntoMensaje> adjuntos = (List<AdjuntoMensaje>) session.getAttribute(_cid + AppConstant.MMM);

            if (adjuntos == null) {
                adjuntos = new ArrayList<AdjuntoMensaje>();
                session.setAttribute(_cid + AppConstant.MMM, adjuntos);
            }

            String fileName = service.upload(file, adjuntos);



            json.setMessage("Importación finalizada.");
            json.setData(fileName);
            json.setSuccess(true);

        } catch (Exception e) {
            json.setSuccess(false);
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            return json;
        }
    }

    @ResponseBody
    @RequestMapping("removeMessage")
    public JsonResponse removeFile(@RequestParam String file, @RequestParam String _cid, HttpSession session) {
        JsonResponse json = new JsonResponse();

        try {
            List<AdjuntoMensaje> adjuntos = (List<AdjuntoMensaje>) session.getAttribute(_cid + AppConstant.MMM);

            service.remove(file, adjuntos);

            json.setMessage("Adjunto eliminado.");
            json.setSuccess(true);

        } catch (Exception e) {
            json.setSuccess(false);
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            return json;
        }
    }
}