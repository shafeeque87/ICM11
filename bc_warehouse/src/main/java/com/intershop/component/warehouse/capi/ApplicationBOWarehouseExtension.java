package com.intershop.component.warehouse.capi;

import com.intershop.beehive.businessobject.capi.BusinessObjectExtension;
import com.intershop.component.application.capi.ApplicationBO;

public interface ApplicationBOWarehouseExtension extends BusinessObjectExtension<ApplicationBO>
{
    public static final String EXTENSION_ID = "Warehouse";
    
    public WarehouseBORepository getBoRepository(String repositoryID);
    
    public WarehouseBORepository getWarehouseBORepository();
}
