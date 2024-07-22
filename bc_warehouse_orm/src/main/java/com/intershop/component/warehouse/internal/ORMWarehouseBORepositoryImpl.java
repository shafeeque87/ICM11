package com.intershop.component.warehouse.internal;

import java.util.Collection;

import javax.inject.Inject;

import com.intershop.beehive.core.capi.domain.Domain;
import com.intershop.beehive.core.capi.naming.NamingMgr;
import com.intershop.beehive.core.capi.util.ObjectMapper;
import com.intershop.beehive.core.capi.util.ObjectMappingCollection;
import com.intershop.component.product.capi.ProductBO;
import com.intershop.component.repository.capi.AbstractDomainRepositoryBOExtension;
import com.intershop.component.repository.capi.RepositoryBO;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;

public class ORMWarehouseBORepositoryImpl extends AbstractDomainRepositoryBOExtension
                implements WarehouseBORepository, ObjectMapper<WarehousePO, WarehouseBO>
{

    @Inject
    private WarehousePOFactory factory;
    private Domain domain = getDomain();

    public ORMWarehouseBORepositoryImpl(String extensionID, RepositoryBO extendedObject)
    {
        super(extensionID, extendedObject);
        factory = (WarehousePOFactory) NamingMgr.getInstance().lookupFactory(WarehousePO.class);
    }

    @Override
    public WarehouseBO resolve(WarehousePO source)
    {
        return new ORMWarehouseBOImpl(source, getContext());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<WarehouseBO> getWarehouseBOs()
    {
        String domainID = getDomain().getDomainID();
        return new ObjectMappingCollection<WarehousePO, WarehouseBO>(factory.getObjectsByDomainID(domainID), this);
    }

    @Override
    public WarehouseBO createWarehouseBO(String name)
    {
        if (name != null && name.length() > 0 && name.length() < 257)
        {
            WarehousePO warehousePO = factory.create(domain, name);
            WarehouseBO warehouseBO = resolve(warehousePO);

            return warehouseBO;
        }
        else
        {
            return null;
        }
    }

    @Override
    public WarehouseBO getWarehouseBOByName(String name)
    {
        WarehousePO warehousePO = factory
                        .getObjectByAlternateKey(new WarehousePOAlternateKey(name, domain.getDomainID()));
        if (warehousePO == null)
        {
            return null;
        }
        return resolve(warehousePO);
    }

    @Override
    public WarehouseBO getWarehouseBOByID(String id)
    {
        WarehousePO warehousePO = factory.getObjectByPrimaryKey(new WarehousePOKey(id));
        if (warehousePO == null)
        {
            return null;
        }
        return resolve(warehousePO);
    }

    @Override
    public void removeWarehouseBO(String id)
    {

        if (id != null)
        {
            factory.remove(new WarehousePOKey(id));
        }
        
    }

    @Override
    public int getProductStockCount(ProductBO productBO)
    {
        Collection<WarehouseBO> warehouseBOs = getWarehouseBOs();
        int count = 0;
        for (WarehouseBO wh : warehouseBOs)
        {
            if (wh.getStockBO(productBO.getID()) != null)
            {
                count = count + wh.getStockBO(productBO.getID()).getCount();
            }
        }
        return count;
    }

}
