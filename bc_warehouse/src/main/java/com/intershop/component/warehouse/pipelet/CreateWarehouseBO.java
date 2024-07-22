package com.intershop.component.warehouse.pipelet;

import com.intershop.beehive.core.capi.localization.LocaleInformation;
import com.intershop.beehive.core.capi.log.Logger;
import com.intershop.beehive.core.capi.pipeline.Pipelet;
import com.intershop.beehive.core.capi.pipeline.PipeletExecutionException;
import com.intershop.beehive.core.capi.pipeline.PipelineDictionary;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;

public class CreateWarehouseBO extends Pipelet
{
    @Override
    public int execute(PipelineDictionary dict)
    throws PipeletExecutionException 
    {        
        // lookup 'LocaleInformation' in pipeline dictionary
        LocaleInformation localeInformation = dict.getRequired("LocaleInformation");
        
        // lookup 'WarehouseBORepository' in pipeline dictionary
        WarehouseBORepository warehouseBORepository = dict.getRequired("WarehouseBORepository");
        
        // lookup 'WarehouseDescription' in pipeline dictionary
        String warehouseDescription = (String)dict.get("WarehouseDescription");
   
        // lookup 'Location' in pipeline dictionary
        String warehouseLocation = (String)dict.get("WarehouseLocation");

        // lookup 'WarehouseName' in pipeline dictionary
        String warehouseName = (String)dict.get("WarehouseName");
        if (null == warehouseName)
        {
            throw new PipeletExecutionException("Mandatory input parameter 'WarehouseName' not available in pipeline dictionary.");
        }
       
        Integer warehouseCapacity = dict.get("WarehouseCapacity");
        
        WarehouseBO warehouseBO = null;
        
        try
        {                   
            warehouseBO = warehouseBORepository.getWarehouseBOByName(warehouseName);
            
            if (warehouseBO != null)
            {
                dict.put("ErrorMsg", "DuplicateWarehouseName");
                return PIPELET_ERROR;
            }
            
            warehouseBO = warehouseBORepository.createWarehouseBO(warehouseName);
        }
        catch (Exception ex)
        {
            Logger.error(this, "Warehouse pipelet creation failed {}", ex.toString());
            return PIPELET_ERROR;
        }
            
        if (warehouseLocation != null && warehouseLocation.length()>0)
        {
            warehouseBO.setLocation(warehouseLocation);
        }
        
        if (warehouseDescription != null && warehouseDescription.length()>0)
        {
            warehouseBO.setDescription(warehouseDescription, localeInformation);
        }
        
        if (warehouseCapacity != null)
        {
            warehouseBO.setCapacity(warehouseCapacity);
        }
        
        // store 'WarehouseBO' in pipeline dictionary
        dict.put("WarehouseBO", warehouseBO);
        
        return PIPELET_NEXT;
    }
}


