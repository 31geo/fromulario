package con.tantalean.fromulario.service.impl;

import con.tantalean.fromulario.entity.Persona;
import con.tantalean.fromulario.repository.PersonaRepository;
import con.tantalean.fromulario.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;

    @Override
    public Persona guardar(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public List<Persona> listarTodos() {
        return personaRepository.findAll();
    }

    @Override
    public Optional<Persona> buscarPorId(Long id) {
        return personaRepository.findById(id);
    }

    @Override
    public Persona actualizar(Long id, Persona persona) {
        Persona existente = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));

        existente.setNombre(persona.getNombre());
        existente.setApellido(persona.getApellido());
        existente.setEmail(persona.getEmail());
        existente.setTelefono(persona.getTelefono());
        existente.setDireccion(persona.getDireccion());
        existente.setCiudad(persona.getCiudad());
        existente.setEdad(persona.getEdad());
        existente.setMensaje(persona.getMensaje());

        return personaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!personaRepository.existsById(id)) {
            throw new RuntimeException("Persona no encontrada con id: " + id);
        }
        personaRepository.deleteById(id);
    }
}
