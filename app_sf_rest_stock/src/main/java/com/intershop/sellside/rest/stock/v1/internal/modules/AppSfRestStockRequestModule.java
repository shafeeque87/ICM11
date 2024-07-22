package com.intershop.sellside.rest.stock.v1.internal.modules;

import com.google.inject.AbstractModule;
import com.intershop.sellside.rest.stock.v1.capi.request.stock.StockListGetRequest;

public class AppSfRestStockRequestModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(StockListGetRequest.class);
    }
}
