package com.intershop.component.warehouse.pipelet;

import com.intershop.beehive.core.capi.pipeline.Pipelet;
import com.intershop.beehive.core.capi.pipeline.PipeletExecutionException;
import com.intershop.beehive.core.capi.pipeline.PipelineDictionary;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;

public class RemoveWarehouseBO extends Pipelet
{


    @Override
    public int execute(PipelineDictionary aPipelineDictionary) throws PipeletExecutionException
    {
        WarehouseBORepository warehouseBORepository = aPipelineDictionary.getRequired("WarehouseBORepository");
        
        WarehouseBO warehouseBO =  aPipelineDictionary.get("WarehouseBO");
        if(null == warehouseBO){
            throw new PipeletExecutionException("Mandatory input parameter 'Warehouse' not available in pipeline dictionary.");
        }
        
        warehouseBORepository.removeWarehouseBO(warehouseBO.getID());

        return PIPELET_NEXT;
    }


}
