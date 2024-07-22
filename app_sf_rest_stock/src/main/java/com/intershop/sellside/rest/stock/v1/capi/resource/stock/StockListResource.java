package com.intershop.sellside.rest.stock.v1.capi.resource.stock;

import static com.intershop.sellside.rest.stock.v1.capi.APIConstants.API_NAME;
import static com.intershop.sellside.rest.stock.v1.capi.APIConstants.API_VERSION;
import static com.intershop.sellside.rest.stock.v1.capi.APIConstants.TAG_STOCK;
import static com.intershop.sellside.rest.stock.v1.capi.StockConstantsREST.MEDIA_TYPE_STOCK_V1_JSON;
import static com.intershop.sellside.rest.stock.v1.capi.StockConstantsREST.RESOURCE_PATH_STOCK_V1;

import com.google.inject.Inject;
import com.intershop.component.rest.capi.openapi.OpenAPIConstants;
import com.intershop.sellside.rest.stock.v1.capi.request.stock.StockListGetRequest;
import com.intershop.sellside.rest.stock.v1.capi.resourceobject.stock.StockRO;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
@Tag(name = TAG_STOCK)
@OpenAPIDefinition(info = @Info(version = API_VERSION), extensions = @Extension(
        properties = {@ExtensionProperty(name = OpenAPIConstants.API_ID, value = API_NAME)}))
@Path(RESOURCE_PATH_STOCK_V1)
public class StockListResource
{
    
    @Inject
    private StockListGetRequest stockListGetRequest;
    @Context
    private UriInfo uriInfo;
    @Context    
    private ResourceContext context;

    @Operation(summary = "Returns the available stock.",
                    description = "Returns the available stock.")
    @ApiResponse(responseCode = "200", description = "The list of available stock.",
                    content = @Content(schema = @Schema(implementation = StockRO.class)))
    
    @GET
    @Produces({MEDIA_TYPE_STOCK_V1_JSON})
    public Response getAllStock_V1(@QueryParam("productSKU") String productSKU) {
        return stockListGetRequest.invoke(uriInfo, productSKU);
    }
}
