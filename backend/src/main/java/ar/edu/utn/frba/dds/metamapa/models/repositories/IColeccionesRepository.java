package ar.edu.utn.frba.dds.metamapa.models.repositories;

import java.util.List;

import ar.edu.utn.frba.dds.metamapa.models.entities.hechos.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IColeccionesRepository extends JpaRepository<Coleccion, Long> {
  Coleccion findColeccionByHandle(String handle);

  boolean existsByHandle(String candidato);

  void deleteColeccionByHandle(String handle);

  @Query("SELECT DISTINCT h.categoria FROM Coleccion col " +
      "JOIN col.hechos h " +
      "WHERE col.handle = :handle")
  List<String> findDistinctCategoriasByHandle(@Param("handle") String handle);
}