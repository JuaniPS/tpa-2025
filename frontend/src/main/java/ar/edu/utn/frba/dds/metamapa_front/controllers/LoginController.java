package ar.edu.utn.frba.dds.metamapa_front.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("titulo", "Iniciar sesión");
    return "accounts/login";
  }

  @GetMapping("/register")
  public String register(Model model) {
    model.addAttribute("titulo", "Registro");
    return "accounts/register";
  }
}
