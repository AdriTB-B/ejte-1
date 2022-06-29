package com.adri.infrastructure;

import com.adri.application.port.PersonaService;
import com.adri.domain.Persona;
import com.adri.infrastructure.dto.PersonaDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void delete(@PathVariable("id") String id){
        personaService.deleteById(id);
    }

}
