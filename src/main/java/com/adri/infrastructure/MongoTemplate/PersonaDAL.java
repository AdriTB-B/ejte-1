package com.adri.infrastructure.MongoTemplate;

import com.adri.domain.Persona;

import java.util.List;

public interface PersonaDAL {
    Persona save(Persona persona);

    List<Persona> getAll();
    List<Persona> findByName(String name);
    Persona findById(String Id);

    void delete(Persona persona);

    Persona update(String id, Persona persona);
}
