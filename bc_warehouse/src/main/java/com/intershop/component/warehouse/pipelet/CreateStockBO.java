package com.intershop.component.warehouse.pipelet;

import com.intershop.beehive.core.capi.pipeline.Pipelet;
import com.intershop.beehive.core.capi.pipeline.PipeletExecutionException;
import com.intershop.beehive.core.capi.pipeline.PipelineDictionary;
import com.intershop.beehive.xcs.capi.product.Product;
import com.intershop.component.warehouse.capi.WarehouseBO;

public class CreateStockBO extends Pipelet
{


    /**
     * Constant used to access the pipeline dictionary with key 'WarehouseBO'
     * The warehouse.
     */
    public static final String DN_WAREHOUSE = "WarehouseBO";
    /**
     * Constant used to access the pipeline dictionary with key 'Product'
     * The product.
     */
    public static final String DN_PRODUCT = "Product";
    /**
     * Constant used to access the pipeline dictionary with key 'Count'
     * The stock count.
     */
    public static final String DN_COUNT = "Count";


    /**
     * The pipelet's execute method is called whenever the pipelets gets
     * executed in the context of a pipeline and a request. The pipeline
     * dictionary valid for the currently executing thread is provided as
     * a parameter.
     *  
     * @param   dict The pipeline dictionary to be used.
     * @throws  PipeletExecutionException
     *          Thrown in case of severe errors that make the pipelet execute
     *          impossible (e.g. missing required input data).
     */
    @Override
    public int execute(PipelineDictionary dict) throws PipeletExecutionException 
    {        
        // lookup 'Count' in pipeline dictionary
        Integer count = Integer.parseInt(dict.get(DN_COUNT));
        if (null == count)
        {
            throw new PipeletExecutionException("Mandatory input parameter 'Count' not available in pipeline dictionary.");
        }
        // lookup 'Product' in pipeline dictionary
        Product product = (Product)dict.get(DN_PRODUCT);
        if (null == product)
        {
            throw new PipeletExecutionException("Mandatory input parameter 'Product' not available in pipeline dictionary.");
        }
        // lookup 'WarehouseBO' in pipeline dictionary
        WarehouseBO warehouseBO = (WarehouseBO)dict.get(DN_WAREHOUSE);
        if (null == warehouseBO)
        {
            throw new PipeletExecutionException("Mandatory input parameter 'Warehouse' not available in pipeline dictionary.");
        }                
        
        warehouseBO.createStockBO(product.getUUID(), count);
             
        return PIPELET_NEXT;
    }
}

