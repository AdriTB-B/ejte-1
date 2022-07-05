package com.adri.infrastructure.MongoRepository;

import com.adri.domain.Persona;
import com.adri.infrastructure.dto.PersonaDTO;
import lombok.ToString;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class PersonaRepositoryTest {
    @Autowired
    private PersonaRepository underTest;

    private Persona persona;
    private String name;

    @BeforeEach
    void savePersona(){
        name = "Test";
        persona = new Persona(new PersonaDTO(name, 32));
        underTest.save(persona);
    }

    @Test
    void savePersona_whenRecieveInstanceOfPersona_thenSaveAndReturn(){
        Persona personaToSave = new Persona(new PersonaDTO("Nombre", 18));
        assertNotEquals(personaToSave.getId(), underTest.save(personaToSave).getId());
    }

    @Test
    void findByName_whenPersonaExist_thenGetPersonaList() {
        assertEquals(List.of(persona), underTest.findByName(name));
    }
    @Test
    void findByName_whenPersonaNotExist_thenGetEmptyList(){
        assertEquals(List.of(), underTest.findByName(""));
    }
    @Test
    void findById_whenPersonaExist_thenGetOpptionalOfPersona(){
        String id = persona.getId();
        assertEquals(Optional.of(persona), underTest.findById(id));
    }
    @Test
    void findById_whenPersonaNotExist_thenGetEmptyOptional(){
        assertEquals(Optional.empty(), underTest.findById(""));
    }

    @Test
    void delete_whenPersonaExist_thenDeletePersona(){
        assertDoesNotThrow(()-> underTest.delete(persona));
    }
    @Test
    void delete_whenPersonaNotExist_thenThrowException(){
        assertThrows(Exception.class, ()-> underTest.delete(new Persona()));
    }

    @AfterEach
    void deleteAll(){
        underTest.deleteAll();
    }
}