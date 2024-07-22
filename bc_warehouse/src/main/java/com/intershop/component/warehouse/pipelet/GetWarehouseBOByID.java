package com.intershop.component.warehouse.pipelet;

import com.intershop.beehive.core.capi.pipeline.Pipelet;
import com.intershop.beehive.core.capi.pipeline.PipeletExecutionException;
import com.intershop.beehive.core.capi.pipeline.PipelineDictionary;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;

public class GetWarehouseBOByID extends Pipelet
{
    public static final String DN_WAREHOUSE_ID = "WarehouseID";
    
    public static final String DN_WAREHOUSE = "WarehouseBO";
    
    public static final String DN_WAREHOUSE_BOREPOSITORY = "WarehouseBORepository";

    @Override
    public int execute(PipelineDictionary aPipelineDictionary) throws PipeletExecutionException
    {
        WarehouseBORepository warehouseBORepository = aPipelineDictionary.getRequired(DN_WAREHOUSE_BOREPOSITORY);
        
        String warehouseID = aPipelineDictionary.get(DN_WAREHOUSE_ID);
        
        if(null == warehouseID){
            throw new PipeletExecutionException("Mandatory input parameter 'UUID' not available in pipeline dictionary.");
        }
        
        WarehouseBO warehouseBO = warehouseBORepository.getWarehouseBOByID(warehouseID);
        
        if(warehouseBO != null){
            aPipelineDictionary.put(DN_WAREHOUSE, warehouseBO);
            return PIPELET_NEXT;
        }
        else {
            
            return PIPELET_ERROR;
        }

    }

}
