package org.exoplatform.groovy.test

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("groovy-test")
public class Test2 {
  
  
  public Test2() {
  }
  
  @GET
  @Path("/groovy2/{param}/")
  def method(@PathParam("param") String name) {
    return "Hello from groovy to >>>>> " + name
  }
  
}
