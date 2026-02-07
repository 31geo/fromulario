package con.tantalean.fromulario.service;

import con.tantalean.fromulario.entity.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaService {

    Persona guardar(Persona persona);

    List<Persona> listarTodos();

    Optional<Persona> buscarPorId(Long id);

    Persona actualizar(Long id, Persona persona);

    void eliminar(Long id);
}
