package com.intershop.rest.custom.module;

import com.google.inject.AbstractModule;
import com.intershop.rest.product.handler.CustomProductHandlerImpl;
import com.intershop.sellside.rest.common.internal.resource.product.ProductSearchHandlerImpl;

public class CustomHandlerModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ProductSearchHandlerImpl.class).to(CustomProductHandlerImpl.class);
        // bind(CategoryHandlerImpl.class).to(CustomCategoryHandlerImpl.class);
        // bind(PromotionHandlerImpl.class).to(CustomPromotionhandlerImpl.class);
    }
}
