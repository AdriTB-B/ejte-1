package com.adri.application.port;

import com.adri.domain.Persona;

import java.util.List;

public interface PersonaService {
    public Persona save(Persona persona);
    public List<Persona> getAll();
    public List<Persona> findByName(String name);
    public void deleteById(String id);
    public Persona update(String id, Persona persona);
}
