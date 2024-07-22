package com.intershop.sellside.rest.stock.v1.internal.mapper.stock;

import java.util.function.Function;

import com.intershop.component.warehouse.capi.StockBO;
import com.intershop.sellside.rest.stock.v1.capi.resourceobject.stock.StockRO;

public class StockROMapper implements Function<StockBO, StockRO>
{

    @Override
    public StockRO apply(StockBO stockBO)
    {
        return new StockRO(stockBO);
    }

}
