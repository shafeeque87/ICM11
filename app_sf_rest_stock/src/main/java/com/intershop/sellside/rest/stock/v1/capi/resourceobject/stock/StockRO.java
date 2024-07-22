package com.intershop.sellside.rest.stock.v1.capi.resourceobject.stock;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.intershop.component.rest.capi.resourceobject.AbstractResourceObject;
import com.intershop.component.warehouse.capi.StockBO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "StockRO_v1", description = "Describes a Stock object.")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = true)
public class StockRO extends AbstractResourceObject
{
    public final static String TYPENAME = "Stock";
    public final static String NAME = "Stock";

    private int count;
    private String productID;
    private String warehouseID;
    private String warehouseName;

    public StockRO(StockBO stockBO)
    {
        super(NAME);
        setCount(stockBO.getCount());
        setProductID(stockBO.getProductID());
        setWarehouseID(stockBO.getWarehouseID());
        setWarehouseName(stockBO.getWarehouseName());
    }

    @Schema(description = "The stock Count", example = "US", accessMode = Schema.AccessMode.READ_ONLY,
                    type = "java.lang.Integer")
    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    @Schema(description = "The ProductID", example = "US", accessMode = Schema.AccessMode.READ_ONLY,
                    type = "java.lang.String")
    public String getProductID()
    {
        return productID;
    }

    public void setProductID(String productID)
    {
        this.productID = productID;
    }

    @Schema(description = "The WarehouseID", example = "US", accessMode = Schema.AccessMode.READ_ONLY,
                    type = "java.lang.String")
    public String getWarehouseID()
    {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID)
    {
        this.warehouseID = warehouseID;
    }

    @Schema(description = "The Warehouse Name", example = "US", accessMode = Schema.AccessMode.READ_ONLY,
                    type = "java.lang.String")
    public String getWarehouseName()
    {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName)
    {
        this.warehouseName = warehouseName;
    }

}