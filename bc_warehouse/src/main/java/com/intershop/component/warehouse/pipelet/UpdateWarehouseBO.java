package com.intershop.component.warehouse.pipelet;

import com.intershop.beehive.core.capi.localization.LocaleInformation;
import com.intershop.beehive.core.capi.pipeline.Pipelet;
import com.intershop.beehive.core.capi.pipeline.PipeletExecutionException;
import com.intershop.beehive.core.capi.pipeline.PipelineDictionary;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;

public class UpdateWarehouseBO extends Pipelet
{

    @Override
    public int execute(PipelineDictionary dict) throws PipeletExecutionException
    {
        // lookup 'WarehouseBORepository' in pipeline dictionary
        WarehouseBORepository warehouseBORepository = dict.getRequired("WarehouseBORepository");

        // lookup 'LocaleInformation' in pipeline dictionary
        LocaleInformation localeInformation = dict.getRequired("LocaleInformation");

        // lookup 'WarehouseDescription' in pipeline dictionary
        String warehouseDescription = (String)dict.get("WarehouseDescription");

        // lookup 'Location' in pipeline dictionary
        String warehouseLocation = (String)dict.get("WarehouseLocation");

        // lookup 'WarehouseName' in pipeline dictionary
        String warehouseName = (String)dict.get("WarehouseName");
        if (null == warehouseName)
        {
            throw new PipeletExecutionException(
                            "Mandatory input parameter 'WarehouseName' not available in pipeline dictionary.");
        }

        Integer warehouseCapacity = dict.get("WarehouseCapacity");

        WarehouseBO warehouseBO, warehouseBO1;

        // lookup 'WarehouseBO' in pipeline dictionary
        warehouseBO = dict.getRequired("WarehouseBO");

        if (!warehouseName.equals(warehouseBO.getName().toString()))
        {
            // check if warehouse name already exist
            warehouseBO1 = warehouseBORepository.getWarehouseBOByName(warehouseName);
            if (warehouseBO1 == null)
            {
                if (warehouseName != null && warehouseName.length() > 0)
                {
                    warehouseBO.setName(warehouseName);
                }
            }
            else
            {
                dict.put("ErrorMsg", "DuplicateWarehouseName");
                return PIPELET_ERROR;
            }
        }

        if (warehouseLocation != null && warehouseLocation.length() > 0)
        {
            warehouseBO.setLocation(warehouseLocation);
        }

        if (warehouseDescription != null && warehouseDescription.length() > 0)
        {
            warehouseBO.setDescription(warehouseDescription, localeInformation);
        }

        if (warehouseCapacity != null)
        {
            warehouseBO.setCapacity(warehouseCapacity);
        }

        return PIPELET_NEXT;
    }
}
