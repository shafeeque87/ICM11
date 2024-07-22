package com.intershop.component.warehouse.internal;

import com.intershop.beehive.businessobject.capi.BusinessObjectExtension;
import com.intershop.component.repository.capi.AbstractDomainRepositoryBOExtensionFactory;
import com.intershop.component.repository.capi.RepositoryBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;
public class ORMWarehouseBORepositoryImplFactory extends AbstractDomainRepositoryBOExtensionFactory  
{

    private static final String EXTENSION_ID = "WarehouseBORepository";
    @Override
    public BusinessObjectExtension<RepositoryBO> createExtension(RepositoryBO object)
    {
        
        ORMWarehouseBORepositoryImpl warehouseBORepositoryImpl = new ORMWarehouseBORepositoryImpl(EXTENSION_ID, object);
        return warehouseBORepositoryImpl;
    }
    

    @Override
    public String getExtensionID()
    {
        return WarehouseBORepository.EXTENSION_ID;
    }

}