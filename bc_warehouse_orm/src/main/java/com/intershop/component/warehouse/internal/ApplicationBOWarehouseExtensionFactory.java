package com.intershop.component.warehouse.internal;

import com.intershop.beehive.businessobject.capi.AbstractBusinessObjectExtensionFactory;
import com.intershop.beehive.businessobject.capi.BusinessObjectExtension;
import com.intershop.component.application.capi.ApplicationBO;

public class ApplicationBOWarehouseExtensionFactory extends AbstractBusinessObjectExtensionFactory<ApplicationBO>
{
    public static final String EXTENSION_ID = "Warehouse";

    @Override
    public BusinessObjectExtension<ApplicationBO> createExtension(ApplicationBO applicationBO)
    {
        return new ApplicationBOWarehouseExtensionImpl(EXTENSION_ID, applicationBO);
    }

    @Override
    public Class<ApplicationBO> getExtendedType()
    {
        return ApplicationBO.class;
    }

}
