package com.adri.infrastructure;

import com.adri.domain.Persona;
import com.adri.infrastructure.MongoRepository.PersonaRepository;
import com.adri.infrastructure.dto.PersonaDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonaMRControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    PersonaRepository personaRepo;

    @LocalServerPort
    private int port;

    private String url;
    private PersonaDTO personaIn = new PersonaDTO("Test", 32);
    private Persona persona = null;

    @BeforeEach
    void setUp(){
        url = "http://localhost:" + port + "/repository/persona";
        persona = personaRepo.save(new Persona(personaIn));
    }
    @Test
    void testRestTemplate(){
        assertNotNull(testRestTemplate);
    }

    @Test
    void save_whenPersonaNotExist_thenSavedPersonaDTO() throws URISyntaxException {
        URI uri = new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PersonaDTO> request = new HttpEntity<>(personaIn, headers);
        ResponseEntity<PersonaDTO> result = this.testRestTemplate.postForEntity(uri, request, PersonaDTO.class);
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void getAll_whenFindAny_thenReturnListOfPersonaDTO() throws URISyntaxException {
        URI uri = new URI(url);
        ResponseEntity<List> result = this.testRestTemplate.getForEntity(uri, List.class);
        assertEquals(200, result.getStatusCodeValue());

    }

    @Test
    void getByName_whenFindAnyPersonaWithThisName_thenReturnListOfPersonaDTO() throws URISyntaxException {
        URI uri = new URI(url + "/name/" + persona.getName());
        ResponseEntity<List> result = this.testRestTemplate.getForEntity(uri.toString(), List.class);
        assertEquals(200, result.getStatusCodeValue());
        assertNotEquals(List.of(), result.getBody());
    }

    @Test
    void update_whenPersonaWithIdExistAndSavePersonaWithNewValues_thenReturnSavedPersonaDTOWithNewValues() throws URISyntaxException {
        String id = persona.getId();
        URI uri = new URI(url + "/" + id);
        String newName = "Test1";
        personaIn.setName(newName);
        Persona personaEntity = new Persona(personaIn);
        personaEntity.setId(id);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PersonaDTO> request = new HttpEntity<>(personaIn, headers);

        ResponseEntity<PersonaDTO> result = this.testRestTemplate.exchange(uri.toString(), HttpMethod.PUT, request, PersonaDTO.class);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(new PersonaDTO(personaEntity), result.getBody());
    }

    @Test
    void delete_whenPersonaExist_thenDelete() throws URISyntaxException {
        String id = persona.getId();
        URI uri = new URI(url + "/" + id);
        ResponseEntity<String> result = this.testRestTemplate.exchange(uri.toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertEquals(200, result.getStatusCodeValue());
    }
    @Test
    void delete_whenPersonaNotExist_thenResponse404() throws URISyntaxException {
        String id = "FailID";
        URI uri = new URI(url + "/" + id);
        ResponseEntity<String> result = this.testRestTemplate.exchange(uri.toString(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertEquals(404, result.getStatusCodeValue());
    }

    @AfterEach
    void deleteAll(){
        personaRepo.deleteAll();
    }
}