package com.intershop.sellside.rest.stock.v1.capi.handler;

import java.util.Collection;

import com.intershop.component.warehouse.capi.StockBO;

public interface StockHandler
{
    Collection<StockBO> getStockBOs(String productSKU);
    
    
}
