package ar.edu.utn.frba.dds.metamapa.models.repositories;

import java.util.List;
import ar.edu.utn.frba.dds.metamapa.models.entities.hechos.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IHechosRepository extends JpaRepository<Hecho, Long> {

    // --- MÉTODOS DE GESTIÓN ---

    @Query("SELECT h FROM Hecho h WHERE h.usuario.email = :email")
    List<Hecho> findAllByEmail(@Param("email") String email);

    @Query("SELECT h FROM Hecho h WHERE h.eliminado = FALSE AND LOWER(h.estado) = 'pendiente'")
    List<Hecho> findAllPendientes();

    // Gestión: Listar aceptados (Aquí usamos nativeQuery con BIT=0 para asegurarnos)
    @Query(value = "SELECT * FROM hecho h WHERE h.eliminado = 0 AND h.estado = 'ACEPTADA' ORDER BY h.fecha_acontecimiento DESC LIMIT :limit", nativeQuery = true)
    List<Hecho> findAllAceptados(@Param("limit") Integer limit);

    List<Hecho> findAllByEliminadoFalse();

    // Utilidad: Buscar sin provincia
    @Query(value = "SELECT * FROM hecho h WHERE (h.provincia IS NULL OR h.provincia = '') AND h.eliminado = 0", nativeQuery = true)
    List<Hecho> findAllSinProvincia();


    // --- MÉTODOS DE ESTADÍSTICAS (MODO ROBUSTO MYSQL) ---

    // 1. Provincia con más hechos en una colección
    // Eliminamos filtros de estado para que aparezcan datos si o si.
    // Usamos eliminado = 0 (BIT)
    @Query(value = "SELECT h.provincia FROM hecho h " +
            "JOIN coleccion_hecho ch ON h.id = ch.hecho_id " +
            "JOIN coleccion c ON ch.coleccion_id = c.id " +
            "WHERE c.handle = :coleccionHandle " +
            "AND h.eliminado = 0 " +
            "AND h.provincia IS NOT NULL " +
            "GROUP BY h.provincia " +
            "ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String findProvinciaConMasHechosPorColeccion(@Param("coleccionHandle") String coleccionHandle);

    // 2. Categoría con más hechos (Global)
    @Query(value = "SELECT h.categoria FROM hecho h " +
            "WHERE h.eliminado = 0 " +
            "GROUP BY h.categoria " +
            "ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String findCategoriaConMasHechos();

    // 3. Hora más común (Por categoría)
    @Query(value = "SELECT HOUR(h.fecha_acontecimiento) as hora FROM hecho h " +
            "WHERE h.categoria = :categoria " +
            "AND h.eliminado = 0 " +
            "AND h.fecha_acontecimiento IS NOT NULL " +
            "GROUP BY HOUR(h.fecha_acontecimiento) " +
            "ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    Integer findHoraMasComunDeCategoria(@Param("categoria") String categoria);

    // 4. Provincia con más hechos (Por categoría)
    @Query(value = "SELECT h.provincia FROM hecho h " +
            "WHERE h.categoria = :categoria " +
            "AND h.eliminado = 0 " +
            "AND h.provincia IS NOT NULL " +
            "GROUP BY h.provincia " +
            "ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String findProvinciaConMasHechosPorCategoria(@Param("categoria") String categoria);
}