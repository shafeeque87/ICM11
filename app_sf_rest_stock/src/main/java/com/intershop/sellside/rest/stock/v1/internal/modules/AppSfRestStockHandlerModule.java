package com.intershop.sellside.rest.stock.v1.internal.modules;

import com.google.inject.AbstractModule;
import com.intershop.sellside.rest.stock.v1.capi.handler.StockFeedbackHandler;
import com.intershop.sellside.rest.stock.v1.capi.handler.StockHandler;
import com.intershop.sellside.rest.stock.v1.internal.handler.StockFeedbackHandlerImpl;
import com.intershop.sellside.rest.stock.v1.internal.handler.StockHandlerImpl;

public class AppSfRestStockHandlerModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(StockFeedbackHandler.class).to(StockFeedbackHandlerImpl.class);
        bind(StockHandler.class).to(StockHandlerImpl.class);
    }
}
