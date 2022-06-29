package com.adri.domain;

import com.adri.infrastructure.dto.PersonaDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection="personas")
public class Persona {
    @Id
    private String id;
    private String name;
    private int age;

    public Persona(PersonaDTO personaDTO){
        setId(personaDTO.getId());
        setName(personaDTO.getName());
        setAge(personaDTO.getAge());
    }
}
