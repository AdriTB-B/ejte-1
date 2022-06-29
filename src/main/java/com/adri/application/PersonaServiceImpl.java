package com.adri.application;

import com.adri.application.port.PersonaService;
import com.adri.domain.Persona;
import com.adri.infrastructure.MongoRepository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {
    @Autowired
    PersonaRepository personaRepo;


    @Override
    public Persona save(Persona persona) {
        return personaRepo.save(persona);
    }

    @Override
    public List<Persona> getAll() {
        return personaRepo.findAll();
    }

    @Override
    public List<Persona> findByName(String name) {
        return personaRepo.findByName(name);
    }

    @Override
    public void deleteById(String id) {
        personaRepo.deleteById(id);
    }

    @Override
    public Persona update(String id, Persona persona) {
        Persona personaToUpdate = personaRepo.findById(id).orElseThrow();
        if(!persona.getName().isEmpty()){
            personaToUpdate.setName(persona.getName());
        }
        if(persona.getAge() > 0){
            personaToUpdate.setAge(persona.getAge());
        }
        return personaRepo.save(personaToUpdate);
    }
}
