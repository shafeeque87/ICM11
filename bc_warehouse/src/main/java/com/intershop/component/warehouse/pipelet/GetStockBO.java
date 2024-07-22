package com.intershop.component.warehouse.pipelet;

import com.intershop.beehive.core.capi.pipeline.Pipelet;
import com.intershop.beehive.core.capi.pipeline.PipeletExecutionException;
import com.intershop.beehive.core.capi.pipeline.PipelineDictionary;
import com.intershop.beehive.xcs.capi.product.Product;
import com.intershop.component.warehouse.capi.StockBO;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;

public class GetStockBO extends Pipelet
{


    @Override
    public int execute(PipelineDictionary aPipelineDictionary) throws PipeletExecutionException
    {
        // lookup 'WarehouseBORepository' in pipeline dictionary
        WarehouseBORepository warehouseBORepository = aPipelineDictionary.getRequired("WarehouseBORepository");
        
        // lookup 'Product' in pipeline dictionary
        Product product = (Product)aPipelineDictionary.get("Product");
        if (null == product)
        {
            throw new PipeletExecutionException("Mandatory input parameter 'Product' not available in pipeline dictionary.");

        }
        
        // lookup 'WarehouseID' in pipeline dictionary
        String warehouseID = (String)aPipelineDictionary.get("WarehouseID");
        if (null == warehouseID)
        {
            throw new PipeletExecutionException("Mandatory input parameter 'WarehouseID' not available in pipeline dictionary.");
        }
        
        WarehouseBO warehouseBO = warehouseBORepository.getWarehouseBOByID(warehouseID);
        StockBO stockBO = warehouseBO.getStockBO(product.getUUID());
       
        // store 'StockBO' in pipeline dictionary
        aPipelineDictionary.put("StockBO", stockBO);
        return PIPELET_NEXT;
    }

}
