package be.condorcet.gestioncoloniemartienne.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestApplication extends Application {
    // On laisse vide. L'annotation fait tout le travail.
}