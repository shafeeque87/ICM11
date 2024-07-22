package com.intershop.sellside.rest.stock.v1.internal.modules;

import java.util.Collection;
import java.util.function.Function;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.intershop.component.warehouse.capi.StockBO;
import com.intershop.sellside.rest.common.v1.capi.mapper.CollectionFunction;
import com.intershop.sellside.rest.stock.v1.capi.resourceobject.stock.StockRO;
import com.intershop.sellside.rest.stock.v1.internal.mapper.stock.StockROMapper;

public class AppSfRestStockMapperModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(new TypeLiteral<Function<StockBO, StockRO>>() {}).to(StockROMapper.class);
        bind(new TypeLiteral<Function<Collection<StockBO>, Collection<StockRO>>>() {})
                        .to(new TypeLiteral<CollectionFunction<StockBO, StockRO>>() {});
    }
}
