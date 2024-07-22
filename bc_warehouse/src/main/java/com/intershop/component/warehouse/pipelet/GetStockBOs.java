package com.intershop.component.warehouse.pipelet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.intershop.beehive.core.capi.pipeline.Pipelet;
import com.intershop.beehive.core.capi.pipeline.PipeletExecutionException;
import com.intershop.beehive.core.capi.pipeline.PipelineDictionary;
import com.intershop.beehive.xcs.capi.product.Product;
import com.intershop.component.warehouse.capi.StockBO;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;

public class GetStockBOs extends Pipelet
{


    @Override
    public int execute(PipelineDictionary dict) throws PipeletExecutionException 
    {        
        // lookup 'WarehouseBORepository' in pipeline dictionary
        WarehouseBORepository warehouseBORepository = dict.getRequired("WarehouseBORepository");
        
        // lookup 'Product' in pipeline dictionary
        Product product = (Product)dict.get("Product");
        if (null == product)
        {
            throw new PipeletExecutionException("Mandatory input parameter 'Product' not available in pipeline dictionary.");
        }           

        Collection<WarehouseBO> warehouseBOs =warehouseBORepository.getWarehouseBOs();
        List<StockBO> stockBOs = new ArrayList<>();
        for (WarehouseBO warehouseBO : warehouseBOs)
        {            
            StockBO stockBO = warehouseBO.getStockBO(product.getUUID());
            if(null != stockBO)
            {
                stockBOs.add(stockBO);
            }
        }
        
        // store 'Inventories' in pipeline dictionary
        dict.put("Inventories", stockBOs);
        return PIPELET_NEXT;
    }
}
