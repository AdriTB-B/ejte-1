package com.adri.infrastructure;

import com.adri.application.port.PersonaService;
import com.adri.domain.Persona;
import com.adri.infrastructure.dto.PersonaDTO;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/repository/persona")
public class PersonaMRController {
    @Autowired
    PersonaService personaService;

    @PostMapping
    public PersonaDTO save(@RequestBody PersonaDTO personaDTO){
        return new PersonaDTO(personaService.save(new Persona(personaDTO)));
    }

    @GetMapping
    public List<PersonaDTO> getAll(){
        return personaService.getAll().stream().map(PersonaDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/name/{name}")
    public List<PersonaDTO> getByName(@PathVariable("name")String name){
        return personaService.findByName(name).stream().map(PersonaDTO::new).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public PersonaDTO update(@PathVariable("id")String id, @RequestBody PersonaDTO persona){
        return new PersonaDTO(personaService.update(id, new Persona(persona)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id){
        try{
            personaService.deleteById(id);
            return new ResponseEntity<>("Registro con id: " + id + " eliminado", HttpStatus.OK);
        }catch(RuntimeException e){
            return new ResponseEntity<>("No se ha podido eliminar el registro con id: " + id, HttpStatus.NOT_FOUND);
        }
    }

}
