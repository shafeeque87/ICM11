package com.intershop.component.warehouse.capi;

import java.util.Collection;

import com.intershop.beehive.businessobject.capi.BusinessObjectRepository;
import com.intershop.component.product.capi.ProductBO;

public interface WarehouseBORepository extends BusinessObjectRepository

 {

        static final String EXTENSION_ID = "WarehouseBORepository";
        
        WarehouseBO createWarehouseBO(String name);
        WarehouseBO getWarehouseBOByName(String name);
        WarehouseBO getWarehouseBOByID(String id);
        Collection<WarehouseBO> getWarehouseBOs();
        void removeWarehouseBO(String id);
        int getProductStockCount(ProductBO productBO);
       
    }

