package com.intershop.component.warehouse.pipelet;

import java.util.Collection;

import com.intershop.beehive.core.capi.pipeline.Pipelet;
import com.intershop.beehive.core.capi.pipeline.PipeletExecutionException;
import com.intershop.beehive.core.capi.pipeline.PipelineDictionary;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;

public class GetWarehouseBOs extends Pipelet
{
    public static final String DN_WAREHOUSES_ITERATOR = "WarehouseBOs";

    public static final String DN_WAREHOUSE_BOREPOSITORY = "WarehouseBORepository";

    @Override
    public int execute(PipelineDictionary dict) throws PipeletExecutionException

    {
        WarehouseBORepository warehouseBORepository = dict.getRequired(DN_WAREHOUSE_BOREPOSITORY);
        Collection<WarehouseBO> warehouseBOs = warehouseBORepository.getWarehouseBOs();

        // store 'WarehouseBOs' in pipeline dictionary
        dict.put(DN_WAREHOUSES_ITERATOR, warehouseBOs);
        return PIPELET_NEXT;
    }
}
