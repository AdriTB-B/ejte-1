package com.adri.application;

import com.adri.domain.Persona;
import com.adri.infrastructure.MongoRepository.PersonaRepository;
import com.adri.infrastructure.dto.PersonaDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonaServiceImplTest {
    @Mock
    PersonaRepository personaRepo;
    @InjectMocks
    PersonaServiceImpl underTest;

    private Persona persona = new Persona(new PersonaDTO("Test", 32));
    private List<Persona> personas = List.of(persona);

    @BeforeEach
    void setUp(){
        persona.setId("1234");
    }

    @Test
    void save() {
        Mockito.when(personaRepo.save(Mockito.any(Persona.class))).thenReturn(persona);
        assertEquals(persona, underTest.save(persona));
    }

    @Test
    void getAll() {
        when(personaRepo.findAll()).thenReturn(personas);
        assertEquals(personas, underTest.getAll());
    }

    @Test
    void findByName() {
        when(personaRepo.findByName(Mockito.any(String.class))).thenReturn(personas);
        assertEquals(personas, underTest.findByName("Test"));
    }

    @Test
    void deleteById() {
        when(personaRepo.findById(Mockito.any(String.class))).thenReturn(Optional.of(new Persona()));
        underTest.deleteById("1234");
        verify(personaRepo).deleteById(Mockito.any(String.class));
    }

    @Test
    void update() {
        when(personaRepo.findById(Mockito.any(String.class))).thenReturn(Optional.of(persona));
        when(personaRepo.save(Mockito.any(Persona.class))).thenReturn(persona);
        assertEquals(persona, underTest.update("1234", persona));
    }
}