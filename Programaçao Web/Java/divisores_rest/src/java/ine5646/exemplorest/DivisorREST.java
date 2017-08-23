/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ine5646.exemplorest;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Pedro
 */
@Path("divisores/{numero}")
public class DivisorREST {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DivisorREST
     */
    public DivisorREST() {
    }

    // http://localhost:8080/divisores_rest/webresources/divisores/36
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String divisores(@PathParam("numero") int numero) {
        List<Integer> listaDivisores = new ArrayList<>();
        
        IntStream.rangeClosed(1, numero).filter(x -> numero % x == 0).forEach(listaDivisores::add);
        
        return new Gson().toJson(listaDivisores);
    }    
}
