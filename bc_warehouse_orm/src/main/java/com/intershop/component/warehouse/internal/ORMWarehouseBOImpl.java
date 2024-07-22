package com.intershop.component.warehouse.internal;

import javax.inject.Inject;

import com.intershop.beehive.businessobject.capi.BusinessObjectContext;
import com.intershop.beehive.core.capi.domain.AbstractExtensibleObjectBO;
import com.intershop.beehive.core.capi.localization.LocaleInformation;
import com.intershop.beehive.core.capi.naming.NamingMgr;
import com.intershop.component.warehouse.capi.StockBO;
import com.intershop.component.warehouse.capi.WarehouseBO;

public class ORMWarehouseBOImpl extends AbstractExtensibleObjectBO<WarehousePO> implements WarehouseBO
{

    @Inject
    private StockPOFactory factory;
    private WarehousePO warehousePO = getExtensibleObject();
    private String domainID = warehousePO.getDomainID();

    public ORMWarehouseBOImpl(WarehousePO delegate, BusinessObjectContext context)
    {
        super(delegate, context);
        NamingMgr.injectMembers(this);
    }

    @Override
    public String getName()
    {
        return warehousePO.getName();
    }

    @Override
    public void setName(String name)
    {
        warehousePO.setName(name);
    }

    @Override
    public String getLocation()
    {
        return warehousePO.getLocation();
    }

    @Override
    public void setLocation(String location)
    {
        warehousePO.setLocation(location);
    }

    @Override
    public int getCapacity()
    {
        return warehousePO.getCapacity();
    }

    @Override
    public void setCapacity(int capacity)
    {
        warehousePO.setCapacity(capacity);
    }

    @Override
    public String getDescription()
    {
        return warehousePO.getDescription();
    }

    @Override
    public String getDescription(LocaleInformation localeInformation)
    {
        return warehousePO.getDescription(localeInformation);
    }

    @Override
    public void setDescription(String description, LocaleInformation localeInformation)
    {
        warehousePO.setDescription(description, localeInformation);
    }

    @Override
    public StockBO createStockBO(String productID, int count)
    {
        if (productID != null && productID.length() > 0)
        {
            StockPO stockPO = factory.getObjectByPrimaryKey(new StockPOKey(productID, domainID, warehousePO.getUUID()));
            if (stockPO == null)
            {
                stockPO = factory.create(productID, domainID, warehousePO);
            }
            stockPO.setCount(count);
            return new ORMStockBOImpl(stockPO, getContext());
        }
        return null;

    }

    @Override
    public StockBO updateStockBO(String productID, int count)
    {
        if (productID != null && productID.length() > 0)
        {
            StockPO stockPO = factory.getObjectByPrimaryKey(new StockPOKey(productID, domainID, warehousePO.getUUID()));
            if (stockPO == null)
            {
                return null;
            }
            stockPO.setCount(count);
            return new ORMStockBOImpl(stockPO, getContext());
        }
        return null;
    }

    @Override
    public StockBO getStockBO(String productID)
    {
        if (productID != null && productID.length() > 0)
        {
            StockPO stockPO = factory.getObjectByPrimaryKey(new StockPOKey(productID, domainID, warehousePO.getUUID()));
            if (stockPO == null)
            {
                return null;
            }
            return new ORMStockBOImpl(stockPO, getContext());
        }
        return null;
    }

    @Override
    public void removeStockBO(String productID)
    {
        if (productID != null && productID.length() > 0)
        {
            factory.remove(new StockPOKey(productID, domainID, warehousePO.getUUID()));
        }
    }

}
