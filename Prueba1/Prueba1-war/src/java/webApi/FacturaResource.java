package webApi;

import com.Facturacion.BillNotFoundException;
import com.Facturacion.Factura;
import com.Facturacion.InvalidBillException;
import com.Facturacion.Linea;
import com.Facturacion.UnrecognizedCurrencyException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.Facturacion.FacturaBeanLocal;
import java.util.Collection;


@Path("facturas")
public class FacturaResource {
    
    @EJB
    private FacturaBeanLocal facturaBean;

    @Context
    private UriInfo context;
    private Gson gson;

    public FacturaResource() {
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    } 

    @GET
    @Produces("application/json")
    public Response getJson() {
        Collection<Factura> facturas = this.facturaBean.getFacturas();
        String facturasJson = gson.toJson(facturas);
        return Response
                .status(Response.Status.OK)
                .entity(facturasJson)
                .build();
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getJsonById(@PathParam("id") Long id) {
        try {
            Factura factura = this.facturaBean.getFactura(id);
            String facturasJson = gson.toJson(factura);
            return Response
                .status(Response.Status.OK)
                .entity(facturasJson)
                .build();
        }
        catch (BillNotFoundException e) {
            ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
            String jsonResponse = gson.toJson(response);
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity(jsonResponse)
                .build();
        }
    }
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postJson(String content) {
        Factura factura = gson.fromJson(content, Factura.class);
        try {
            factura = facturaBean.createFactura(factura);
            String facturaJson = gson.toJson(factura);
            return Response
                .status(Response.Status.OK)
                .entity(facturaJson)
                .build();
        }
        catch (InvalidBillException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 400);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(errorResponse))
                    .build();
        }
        
    }
    
    @PUT
    @Path("{id}")
    @Produces("application/json")
    public Response putJson(@PathParam("id") Long id, String content) {
        Factura factura = gson.fromJson(content, Factura.class);
        factura.setId(id);
        String facturaJson = gson.toJson(factura);
        return Response
            .status(Response.Status.OK)
            .entity(facturaJson)
            .build();
    }
    
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteFactura(@PathParam("id") Long id) {
        return Response
            .status(204)
            .build();
    }
    
    @POST
    @Path("{id}/lineas")
    @Consumes("application/json")
    @Produces("application/json")
    public Response postLineaJson(@PathParam("id") Long id, String content) {
        Linea linea = gson.fromJson(content, Linea.class);
        linea.setId(id);
        String lineaJson = gson.toJson(linea);
        return Response
            .status(Response.Status.OK)
            .entity(lineaJson)
            .build();
    }
    
    @GET
    @Path("{id}/total")
    @Produces("application/json")
    public Response getTotalInCurrency(@PathParam("id") Long id, @QueryParam("moneda") String moneda) {
        try {
            float convertedTotal = this.facturaBean.getTotalInCurrency(id, moneda);
            ConversionResponse response = new ConversionResponse(convertedTotal, moneda);
            String jsonResponse = gson.toJson(response);
            return Response
                .status(Response.Status.OK)
                .entity(jsonResponse)
                .build(); 
        }
        catch (UnrecognizedCurrencyException e) {
            ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
            String jsonResponse = gson.toJson(response);
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity(jsonResponse)
                .build();
        }
        catch (BillNotFoundException e) {
            ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
            String jsonResponse = gson.toJson(response);
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity(jsonResponse)
                .build();
        }
    }
}
