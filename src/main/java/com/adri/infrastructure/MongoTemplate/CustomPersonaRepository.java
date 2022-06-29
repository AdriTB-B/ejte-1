package com.adri.infrastructure.MongoTemplate;

import com.adri.domain.Persona;

public interface CustomPersonaRepository {
    Persona updatePersona(String id, Persona persona);
}
