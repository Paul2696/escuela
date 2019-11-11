package com.rapido.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class OfertaApp extends Application {

    @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> classes = new HashSet<>();
        classes.add(OfertaEndpoint.class);
        return classes;
    }
}
