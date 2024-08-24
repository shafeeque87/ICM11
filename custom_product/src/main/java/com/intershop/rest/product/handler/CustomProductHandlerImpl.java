package com.intershop.rest.product.handler;

import java.util.Collections;

import com.google.inject.Inject;
import com.intershop.beehive.core.capi.domain.Domain;
import com.intershop.beehive.core.capi.domain.PersistentObjectBOExtension;
import com.intershop.beehive.core.capi.domain.PersistentObjectPO;
import com.intershop.beehive.core.capi.localization.provider.CurrentLocaleProvider;
import com.intershop.component.application.capi.CurrentApplicationBOProvider;
import com.intershop.component.catalog.capi.CatalogCategoryBO;
import com.intershop.component.customer.capi.CustomerBO;
import com.intershop.component.customer.capi.provider.CurrentCustomerBOProvider;
import com.intershop.component.customer.orm.internal.orm.CustomerPO;
import com.intershop.component.product.capi.ProductBO;
import com.intershop.component.rest.capi.RestException;
import com.intershop.component.rest.capi.paging.Paging;
import com.intershop.component.search.capi.SearchIndexQuery;
import com.intershop.sellside.rest.common.internal.resource.product.ProductSearchHandlerImpl;

public class CustomProductHandlerImpl extends ProductSearchHandlerImpl
{

    @Inject
    CurrentCustomerBOProvider currentCustomerBOProvider;

    @Inject
    CurrentLocaleProvider currentLocaleProvider;

    @Inject
    CurrentApplicationBOProvider applicationBOProvider;


    @Override
    public ProductBO getProductBySKU(Domain currentChannel, String sku, String localeId)
    {
        String countryCode = applicationBOProvider.get().getDefaultLocale().getISO3Country();

        CustomerBO customerBO = currentCustomerBOProvider.getCurrentCustomerBO();

        if (null != customerBO)
        {
            PersistentObjectBOExtension poext = customerBO.getExtension(PersistentObjectBOExtension.class);

            String customerCountry = null;
            if (poext != null && (poext.getPersistentObject() instanceof PersistentObjectPO))
            {
                CustomerPO customer = poext.getPersistentObject();

                customerCountry = customer.getString("Country");
            }

            if (customerCountry == null)
            {
                return null;
            }

            if ((customerCountry.equals(countryCode)))
            {
                return super.getProductBySKU(currentChannel, sku, localeId);
            }

            else
            {
                return null;
            }

        }

        else
        {
            return null;
        }

    }

    @Override
    public ProductBOSearchResult getProductsByCategory(Domain currentChannel, CatalogCategoryBO category,
                    String localeId, String sortingAttribute, String searchQueryDefinitionID, Paging pagingPreference,
                    SearchIndexQuery searchIndexQuery, String searchTerm) throws RestException
    {

        String countryCode = applicationBOProvider.get().getDefaultLocale().getISO3Country();
        CustomerBO customerBO = currentCustomerBOProvider.getCurrentCustomerBO();
        
        if (null != customerBO)
        {
            PersistentObjectBOExtension poext = customerBO.getExtension(PersistentObjectBOExtension.class);
            String customerCountry = null;
            if (poext != null && (poext.getPersistentObject() instanceof PersistentObjectPO))
            {
                CustomerPO customer = poext.getPersistentObject();

                customerCountry = customer.getString("Country");
            }
            if (customerCountry == null)
            {
                return new ProductBOSearchResult(Collections.emptyIterator(), 0, Collections.emptyIterator());
            }
            if ((customerCountry.equals(countryCode)))
            {
                return super.getProductsByCategory(currentChannel, category, localeId, sortingAttribute,
                                searchQueryDefinitionID, pagingPreference, searchIndexQuery, searchTerm);
            }
            else
            {
                return new ProductBOSearchResult(Collections.emptyIterator(), 0, Collections.emptyIterator());
            }

        }
        else
        {
            return new ProductBOSearchResult(Collections.emptyIterator(), 0, Collections.emptyIterator());
        }
    }

}
