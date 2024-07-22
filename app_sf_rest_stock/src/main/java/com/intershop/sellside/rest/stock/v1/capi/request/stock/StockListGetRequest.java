package com.intershop.sellside.rest.stock.v1.capi.request.stock;

import java.util.Collection;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Provider;

import com.intershop.component.rest.capi.resource.RestResourceCacheHandler;
import com.intershop.component.warehouse.capi.StockBO;
import com.intershop.sellside.rest.common.v1.capi.resourceobject.common.ContainerRO;
import com.intershop.sellside.rest.common.v1.capi.resourceobject.common.ContainerROBuilder;
import com.intershop.sellside.rest.stock.v1.capi.handler.StockFeedbackHandler;
import com.intershop.sellside.rest.stock.v1.capi.handler.StockHandler;
import com.intershop.sellside.rest.stock.v1.capi.resourceobject.stock.StockRO;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

public class StockListGetRequest
{
    private StockHandler stockHandler;
    private RestResourceCacheHandler cacheHandler;
    private Function<Collection<StockBO>, Collection<StockRO>> stockROMapper;
    private Provider<ContainerROBuilder<Collection<StockRO>>> containerROBuilderProvider;
    private StockFeedbackHandler stockFeedbackHandler;
    
    @Inject
    public StockListGetRequest(StockHandler stockHandler, RestResourceCacheHandler cacheHandler,
                    Function<Collection<StockBO>, Collection<StockRO>> versionROMapper,
                    Provider<ContainerROBuilder<Collection<StockRO>>> containerROBuilderProvider)
    {
        this.stockHandler = stockHandler;
        this.cacheHandler = cacheHandler;
        this.stockROMapper = versionROMapper;
        this.containerROBuilderProvider = containerROBuilderProvider;
    }
    
    public Response invoke(UriInfo uriInfo,String productSKU)
    {
        cacheHandler.setCacheExpires(0);
   
        if(productSKU == null) {
            return stockFeedbackHandler.getStockNotFoundResponse();
        }
        Collection<StockBO> stockBOs = stockHandler.getStockBOs(productSKU);
        
   
        
        Collection<StockRO> stockROs = stockROMapper.apply(stockBOs);
        
        
        
        ContainerRO<Collection<StockRO>> containerRO = containerROBuilderProvider.get() //
                        .withData(stockROs)    //
                        .withUriInfo(uriInfo) //
                        .build();
        
        return Response.ok(containerRO).build();
    }

}
