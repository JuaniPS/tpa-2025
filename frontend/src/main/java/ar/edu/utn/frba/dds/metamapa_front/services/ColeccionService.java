package ar.edu.utn.frba.dds.metamapa_front.services;

import java.util.List;
import java.util.Optional;

import ar.edu.utn.frba.dds.metamapa_front.dtos.ColeccionDTO;
import ar.edu.utn.frba.dds.metamapa_front.dtos.HechoDTO;
import ar.edu.utn.frba.dds.metamapa_front.dtos.HechoFiltroDTO;
import ar.edu.utn.frba.dds.metamapa_front.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColeccionService {
  @Autowired
  private MetamapaApiService metamapaApiService;

  public List<ColeccionDTO> getAllColecciones() {
    return metamapaApiService.getAllColecciones();
  }

  public List<HechoDTO> getHechosByHandle(String handle, HechoFiltroDTO filtros) {
    return metamapaApiService.getHechosByHandle(handle, filtros);
  }

  public List<String> getCategoriasByHandle(String handle) {
    return metamapaApiService.getCategoriasByHandle(handle);
  }

  public ColeccionDTO crearColeccion(ColeccionDTO coleccionDTO) {
    return metamapaApiService.crearColeccion(coleccionDTO);
  }

  public Optional<ColeccionDTO> getColeccionByHandle(String handle) {
    try {
      ColeccionDTO coleccion = metamapaApiService.getColeccionByHandle(handle);
      return Optional.of(coleccion);
    } catch (NotFoundException e) {
      return Optional.empty();
    }
  }

  public ColeccionDTO actualizarColeccion(String handle, ColeccionDTO coleccionDTO) {
    return metamapaApiService.actualizarColeccion(handle, coleccionDTO);
  }

  public void eliminarColeccion(String handle) {
    metamapaApiService.eliminarColeccion(handle);
  }
}
