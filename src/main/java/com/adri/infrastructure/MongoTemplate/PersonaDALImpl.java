package com.adri.infrastructure.MongoTemplate;

import com.adri.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class PersonaDALImpl implements PersonaDAL {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CustomPersonaRepository customPersonaRepo;

    @Override
    public Persona save(Persona persona) {
        return mongoTemplate.save(persona);
    }

    @Override
    public List<Persona> getAll() {
        return mongoTemplate.findAll(Persona.class);
    }

    @Override
    public List<Persona> findByName(String name) {
        Query query = new Query().addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Persona.class);
    }

    @Override
    public Persona findById(String id){
        return mongoTemplate.findById(id, Persona.class);
    }

    @Override
    public void delete(Persona persona) {
        mongoTemplate.remove(persona);
    }

    @Override
    public Persona update(String id, Persona persona) {
        return customPersonaRepo.updatePersona(id, persona);
    }
}
