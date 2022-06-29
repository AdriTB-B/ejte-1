package com.adri.infrastructure.dto;

import com.adri.domain.Persona;
import lombok.Data;

@Data
public class PersonaDTO {
    private String id;
    private String name;
    private int age;

    public PersonaDTO(Persona persona){
        setId(persona.getId());
        setName(persona.getName());
        setAge(persona.getAge());
    }

    public PersonaDTO(String name, int age){
        setName(name);
        setAge(age);
    }
}
