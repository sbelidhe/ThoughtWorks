package com.xp.assignment.rest;

import java.util.function.BiFunction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/calc")
public class Calculator {
 
    @GET
    @Path("/add/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public String addPlainText(@PathParam("a") double a, @PathParam("b") double b) {
    	return response(a,b,false,(x, y) -> a + b);
    }
     
    @GET
    @Path("/sub/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public String subPlainText(@PathParam("a") double a, @PathParam("b") double b) {
    	return response(a,b,false,(x, y) -> a - b);
    }
    
    @GET
    @Path("/mul/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public String mulPlainText(@PathParam("a") double a, @PathParam("b") double b) {
    	return response(a,b,false,(x, y) -> a * b);    
    }
 
    @GET
    @Path("/div/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public String divPlainText(@PathParam("a") double a, @PathParam("b") double b) {
    	return response(a,b,true,(x, y) -> a / b);
    }
    
    private String response (Double a , Double b , boolean divFlag,BiFunction<Double,Double,Double> func) {
    	if (Double.isNaN(a) || Double.isNaN(b)) {
    		return Response.Status.BAD_REQUEST.getStatusCode() + "";
    	} else if (divFlag && b == 0) {
    		return Response.Status.BAD_REQUEST.getStatusCode()+ "";
    	} else {
    		return func.apply(a, b) + "" ;
    	}
    } 
}
