package com.intershop.sellside.rest.stock.v1.internal.resource;


import com.intershop.component.rest.capi.resource.RestResource;
import com.intershop.component.rest.capi.resource.RootResource;
import com.intershop.component.rest.capi.resource.SubResourceProvider;
import com.intershop.sellside.rest.stock.v1.capi.StockConstantsREST;
import com.intershop.sellside.rest.stock.v1.capi.resource.stock.StockListResource;

import jakarta.ws.rs.container.ResourceContext;

public class StockListResourceProvider implements SubResourceProvider
{
    public static final String STOCK_RESOURCE_ACL_PATH = "resources/app_sf_rest_stock/rest/stock-acl.properties";

    @Override
    public Object getSubResource(RestResource parent, ResourceContext rc, String subResourceName)
    {
        if (isApplicable("app_sf_rest_stock") && StockConstantsREST.RESOURCE_PATH_STOCK_V1.equals(subResourceName))
        {
                parent.getRootResource().addACLRuleIfSupported(STOCK_RESOURCE_ACL_PATH);
                Object resource = getSubResource(parent);
                rc.initResource(resource);
                return resource;
            }
       
        return null;
    }

    @Override
    public Object getSubResource(RestResource parent)
    {
        // this method is only used for generating the API model, so no need to check the media type and path here
        if (parent instanceof RootResource && isApplicable("app_sf_rest_stock"))
        {
            return new StockListResource();
        }
        return null;
    }
}
