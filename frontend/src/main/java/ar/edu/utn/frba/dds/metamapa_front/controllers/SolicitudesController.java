package ar.edu.utn.frba.dds.metamapa_front.controllers;

import ar.edu.utn.frba.dds.metamapa_front.exceptions.NotFoundException;
import ar.edu.utn.frba.dds.metamapa_front.services.SolicitudesService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitudes")
@RequiredArgsConstructor
public class SolicitudesController {
  private static final Logger log = LoggerFactory.getLogger(SolicitudesController.class);
  private final SolicitudesService solicitudesService;

  @PostMapping("/{id}/aceptar")
  @PreAuthorize("hasRole('ADMIN')")
  public String aceptarSolicitud(@PathVariable Long id,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes
  ) {
    try {
      solicitudesService.aceptarSolicitud(id);
      return "redirect:/admin/solicitudes";
    } catch (NotFoundException e) {
      return "redirect:/404";
    } catch (Exception e) {
      log.error("Error al aceptar solicitud", e);
      model.addAttribute("titulo", "Solicitudes de eliminación");
      return "admin/solicitudes";
    }
  }

  @PostMapping("/{id}/rechazar")
  @PreAuthorize("hasRole('ADMIN')")
  public String rechazarSolicitud(@PathVariable Long id,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes
  ) {
    try {
      solicitudesService.rechazarSolicitud(id);
      return "redirect:/admin/solicitudes";
    } catch (NotFoundException e) {
      return "redirect:/404";
    } catch (Exception e) {
      log.error("Error al aceptar solicitud", e);
      model.addAttribute("titulo", "Solicitudes de eliminación");
      return "admin/solicitudes";
    }
  }
}
