package com.intershop.component.warehouse.pipelet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.intershop.beehive.core.capi.pipeline.Pipelet;
import com.intershop.beehive.core.capi.pipeline.PipeletExecutionException;
import com.intershop.beehive.core.capi.pipeline.PipelineDictionary;
import com.intershop.component.warehouse.capi.StockBO;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;

public class GetUseableWarehouseBOs extends Pipelet
{


    @Override
    public int execute(PipelineDictionary aPipelineDictionary) throws PipeletExecutionException
    {        
        // lookup 'WarehouseBORepository' in pipeline dictionary
        WarehouseBORepository warehouseBORepository = aPipelineDictionary.getRequired("WarehouseBORepository");
        
                
        // lookup 'ProductUUID' in pipeline dictionary
        String productUUID = (String)aPipelineDictionary.get("ProductUUID");
        if (null == productUUID)
        {
            throw new PipeletExecutionException("Mandatory input parameter 'ProductUUID' not available in pipeline dictionary.");
        }
 
        
        Collection<WarehouseBO> warehouses = warehouseBORepository.getWarehouseBOs();
        List<WarehouseBO> useableWarehouses = new ArrayList<>();
        
        for (WarehouseBO warehouseBO : warehouses)
        {            
            StockBO stockBO = warehouseBO.getStockBO(productUUID);
            if(null == stockBO)
            {
                useableWarehouses.add(warehouseBO);
            }
        }
        
        // store 'WarehouseBOs' in pipeline dictionary
        aPipelineDictionary.put("WarehouseBOs", useableWarehouses);
        return PIPELET_NEXT;
    }

}
