package ar.edu.utn.frba.dds.metamapa_front.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudEliminacionDTO {
  private Long idHecho;
  private String razon;
}
