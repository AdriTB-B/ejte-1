package com.adri.infrastructure;

import com.adri.infrastructure.MongoTemplate.PersonaDAL;
import com.adri.domain.Persona;
import com.adri.infrastructure.dto.PersonaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/template/persona")
public class PersonaMTController {
    @Autowired
    PersonaDAL personaDAL;

    @PostMapping
    public PersonaDTO save(@RequestBody PersonaDTO personaDTO){
        return new PersonaDTO(personaDAL.save(new Persona(personaDTO)));
    }

    @GetMapping
    public List<PersonaDTO> getAll(){
        return personaDAL.getAll().stream().map(PersonaDTO::new).collect(Collectors.toList());
    }
    @GetMapping("/name/{name}")
    public List<PersonaDTO> getByName(@PathVariable("name")String name){
        return personaDAL.findByName(name).stream().map(PersonaDTO::new).collect(Collectors.toList());
    }

    @PutMapping("{id}")
    public PersonaDTO update(@PathVariable("id")String id, @RequestBody PersonaDTO persona){
        return new PersonaDTO(personaDAL.update(id, new Persona(persona)));
    }

    @DeleteMapping
    public void delete(@RequestBody PersonaDTO persona){
        personaDAL.delete(new Persona(persona));
    }

}
