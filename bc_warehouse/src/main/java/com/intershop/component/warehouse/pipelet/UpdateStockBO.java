package com.intershop.component.warehouse.pipelet;

import com.intershop.beehive.core.capi.pipeline.Pipelet;
import com.intershop.beehive.core.capi.pipeline.PipeletExecutionException;
import com.intershop.beehive.core.capi.pipeline.PipelineDictionary;
import com.intershop.component.warehouse.capi.StockBO;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;

public class UpdateStockBO extends Pipelet
{


    @Override
    public int execute(PipelineDictionary aPipelineDictionary) throws PipeletExecutionException
    {
        // lookup 'WarehouseBORepository' in pipeline dictionary
        WarehouseBORepository warehouseBORepository = aPipelineDictionary.getRequired("WarehouseBORepository");
        
        // lookup 'Count' in pipeline dictionary
        Integer count = Integer.parseInt(aPipelineDictionary.get("Count"));
        if (null == count)
        {
            throw new PipeletExecutionException("Mandatory input parameter 'Count' not available in pipeline dictionary.");
        }
        
        // lookup 'StockBO' in pipeline dictionary
        StockBO stockBO = (StockBO)aPipelineDictionary.get("StockBO");
        if (null == stockBO)
        {
            throw new PipeletExecutionException("Mandatory input parameter 'Stock' not available in pipeline dictionary.");
        }              
        
        WarehouseBO warehouseBO = warehouseBORepository.getWarehouseBOByID(stockBO.getWarehouseID());
      
        warehouseBO.updateStockBO(stockBO.getProductID(), count);

        return PIPELET_NEXT;
    }
}
