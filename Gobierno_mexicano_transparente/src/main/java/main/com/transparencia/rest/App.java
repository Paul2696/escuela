package com.transparencia.rest;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import java.util.Set;
import java.util.HashSet;

@ApplicationPath("resources")
public class App extends Application{
	public Set<Class<?>> getClasses(){
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(DocumentEndpoint.class);
		s.add(FuncionarioEndpoint.class);
		return s;
	}
}