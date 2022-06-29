package com.adri.infrastructure.MongoRepository;

import com.adri.domain.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonaRepository extends MongoRepository<Persona, String> {
    @Query("{name:?0}")
    public List<Persona> findByName(String name);
}
