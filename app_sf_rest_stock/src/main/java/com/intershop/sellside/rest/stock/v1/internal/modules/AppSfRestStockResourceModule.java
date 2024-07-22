package com.intershop.sellside.rest.stock.v1.internal.modules;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.intershop.component.rest.capi.resource.SubResourceProvider;
import com.intershop.sellside.rest.stock.v1.internal.resource.StockListResourceProvider;

public class AppSfRestStockResourceModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        Multibinder<SubResourceProvider> subResourcesBinder = Multibinder.newSetBinder(binder(),
                        SubResourceProvider.class);
        subResourcesBinder.addBinding().to(StockListResourceProvider.class);
    }
}
