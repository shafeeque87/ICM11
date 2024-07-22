package com.intershop.sellside.rest.stock.v1.internal.modules;

import java.util.Collection;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.intershop.sellside.rest.common.v1.capi.resourceobject.common.ContainerROBuilder;
import com.intershop.sellside.rest.stock.v1.capi.resourceobject.stock.StockRO;

public class AppSfRestStockBuilderModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(new TypeLiteral<ContainerROBuilder<Collection<StockRO>>>() {});
    }
}
