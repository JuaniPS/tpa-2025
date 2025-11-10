package ar.edu.utn.frba.dds.metamapa_front.controllers;

import ar.edu.utn.frba.dds.metamapa_front.dtos.ColeccionDTO;
import ar.edu.utn.frba.dds.metamapa_front.dtos.HechoDTO;
import ar.edu.utn.frba.dds.metamapa_front.services.ColeccionService;
import ar.edu.utn.frba.dds.metamapa_front.services.HechosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LandingController {

  private final HechosService hechosService;
  private final ColeccionService coleccionService;

  @GetMapping("/")
  public String landing(Model model) {
    model.addAttribute("titulo", "Información Colaborativa para el Bien Social");
    return "landing/landing";
  }

  @GetMapping("/404")
  public String notFound(Model model) {
    model.addAttribute("titulo", "No encontrado");
    return "error/404";
  }

  @GetMapping("/403")
  public String forbidden(Model model) {
    model.addAttribute("titulo", "Acceso denegado");
    return "error/403";
  }
}
