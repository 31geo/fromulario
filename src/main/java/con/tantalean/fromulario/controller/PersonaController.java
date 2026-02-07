package con.tantalean.fromulario.controller;



import java.util.List;


import con.tantalean.fromulario.entity.Persona;
import con.tantalean.fromulario.service.PersonaService;
import con.tantalean.fromulario.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/formularios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    // POST - Crear persona (enviar formulario)
    @PostMapping
    public ResponseEntity<ApiResponse<Persona>> crear(@RequestBody Persona persona) {
        try {
            persona.setId(null); // Asegurar que es una entidad nueva
            Persona guardada = personaService.guardar(persona);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Formulario enviado exitosamente", guardada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al guardar: " + e.getMessage(), null));
        }
    }

    // GET - Listar todas las personas
    @GetMapping
    public ResponseEntity<List<Persona>> listarTodos() {
        List<Persona> personas = personaService.listarTodos();
        return ResponseEntity.ok(personas);
    }

    // GET - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Persona>> buscarPorId(@PathVariable Long id) {
        return personaService.buscarPorId(id)
                .map(persona -> ResponseEntity.ok(new ApiResponse<>(true, "Persona encontrada", persona)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Persona no encontrada con id: " + id, null)));
    }

    // PUT - Actualizar persona
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Persona>> actualizar(@PathVariable Long id, @RequestBody Persona persona) {
        try {
            Persona actualizada = personaService.actualizar(id, persona);
            return ResponseEntity.ok(new ApiResponse<>(true, "Persona actualizada exitosamente", actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // DELETE - Eliminar persona
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            personaService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Persona eliminada exitosamente", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
