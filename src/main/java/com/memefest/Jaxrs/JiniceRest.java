package com.memefest.Jaxrs;

import jakarta.annotation.security.DeclareRoles;
import org.glassfish.jersey.servlet.ServletContainer;

@DeclareRoles({"User"})
public class JiniceRest extends ServletContainer {}
