// =============================================================================
// File: StockPOFactory.java
// Generated by JGen Code Generator from INTERSHOP Communications AG.
// Generator template: ORMFactory.xpt(checksum: 6e6bf2256972a853c83260dcdcdafcb3)
// =============================================================================
// The JGen Code Generator software is the property of INTERSHOP Communications AG. 
// Any rights to use are granted under the license agreement. 
// =============================================================================
package com.intershop.component.warehouse.internal;

import com.intershop.beehive.core.capi.common.Factory;
import com.intershop.beehive.core.capi.common.FinderException;
import com.intershop.beehive.orm.capi.common.FindMode;
import com.intershop.beehive.orm.capi.description.ClassDescription;
import com.intershop.beehive.orm.capi.engine.ORMEngine;

/**
 * This class provides life cycle management methods for the class StockPO.
 *
 * @see com.intershop.training.component.warehouse.internal.StockPO
 * @generated
 */
public class StockPOFactory extends Factory
{
    /**
     * The name which can be used to lookup a factory from the naming manager.
     * 
     * @deprecated use "NamingMgr.getInstance().lookupFactory(StockPO.class)" instead
     * @generated
     */
    @Deprecated
    public static final String FACTORY_NAME = "com.intershop.training.component.warehouse.internal.StockPO";

    /**
     * The constructor.
     *
     * @generated
     */
    public StockPOFactory(ORMEngine engine, ClassDescription classDescription)
    {
        super(engine, classDescription);

        StockPO.countAttribute = classDescription.getAttributeDescription("count");
        StockPO.productIDAttribute = classDescription.getAttributeDescription("productID");
        StockPO.domainIDAttribute = classDescription.getAttributeDescription("domainID");
        StockPO.warehouseIDAttribute = classDescription.getAttributeDescription("warehouseID");
        StockPO.ocaAttribute = classDescription.getAttributeDescription("oca");

        StockPO.warehousePORelation = classDescription.getRelationDescription("warehousePO");

    }

    /**
     * Creates a new instance of class StockPO.
     *
     * @return the new instance of class StockPO
     * @generated modifiable
     */
    public StockPO create(String productID, String domainID, WarehousePO warehousePO)
    {

        String warehouseID = warehousePO.getUUID();

        StockPOKey key = new StockPOKey(productID, domainID, warehouseID);
        StockPO instance = (StockPO)getEngine().getPersistenceManager()
                        .createObject(key, getClassDescription());

        instance.setRelatedObject(StockPO.warehousePORelation, warehousePO);

        // {{ create
        // put your custom create code here
        // }} create

        return instance;
    }

    /**
     * Removes an object by its primary key.
     *
     * @generated
     */
    public void remove(StockPOKey key)
    {
        StockPO object = getObjectByPrimaryKey(key);
        if (object != null)
        {
            object.remove();
        }
    }

    /**
     * Locates an instance of class StockPO based on its primary key.
     *
     * @param key the primary key to use for lookup
     * @return the object for the specified primary key
     * @exception com.intershop.beehive.core.capi.common.FinderException if the object wasn't found
     * @deprecated use getObjectByPrimaryKey now
     * @generated
     */
    @Deprecated
    public StockPO findByPrimaryKey(StockPOKey key) throws FinderException
    {
        return (StockPO)super.findByPrimaryKeyObject(key);
    }

    /**
     * Locates an instance of class StockPO based on its primary key.
     *
     * @param key the primary key to use for lookup
     * @return the object for the specified primary key or null, if it wasn't found
     * @generated
     */
    public StockPO getObjectByPrimaryKey(StockPOKey key)
    {
        return (StockPO)super.getObjectByPrimaryKeyObject(key);
    }

    /**
     * Locates an instance of class StockPO based on its primary key.
     *
     * @param key the primary key to use for lookup
     * @param findMode the strategy to use for the lookup
     * @return the object for the specified primary key
     * @exception com.intershop.beehive.core.capi.common.FinderException if the object wasn't found
     * @deprecated use getObjectByPrimaryKey now
     * @generated
     */
    @Deprecated
    public StockPO findByPrimaryKey(StockPOKey key, FindMode mode) throws FinderException
    {
        return (StockPO)super.findByPrimaryKeyObject(key, mode);
    }

    /**
     * Locates an instance of class StockPO based on its primary key.
     *
     * @param key the primary key to use for lookup
     * @param findMode the strategy to use for the lookup
     * @return the object for the specified primary key or null, if it wasn't found
     * @generated
     */
    public StockPO getObjectByPrimaryKey(StockPOKey key, FindMode mode)
    {
        return (StockPO)super.getObjectByPrimaryKeyObject(key, mode);
    }

}
