package com.adri.infrastructure.MongoTemplate;

import com.adri.domain.Persona;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class CustomPersonaRepositoryImpl implements CustomPersonaRepository{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Persona updatePersona(String id, Persona persona) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        if(!persona.getName().isEmpty()){
            update.set("name", persona.getName());
        }
        if(persona.getAge() > 0){
            update.set("age", persona.getAge());
        }
        UpdateResult result = mongoTemplate.updateFirst(query, update, Persona.class);
        if(result != null){
            persona.setId(id);
            return persona;
        }else{
            return null;
        }

    }
}
