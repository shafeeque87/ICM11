package com.intershop.sellside.rest.stock.v1.internal.handler;

import static com.intershop.sellside.rest.stock.v1.capi.StockConstantsREST.ERROR_STOCK_NOT_FOUND;

import com.intershop.sellside.rest.common.v1.capi.handler.FeedbackHandlerImpl;
import com.intershop.sellside.rest.common.v1.capi.resourceobject.feedback.FeedbackCtnrRO;
import com.intershop.sellside.rest.common.v1.capi.resourceobject.feedback.FeedbackRO;
import com.intershop.sellside.rest.stock.v1.capi.handler.StockFeedbackHandler;

import jakarta.ws.rs.core.Response;


public class StockFeedbackHandlerImpl extends FeedbackHandlerImpl implements StockFeedbackHandler
{

    @Override
    public Response getStockNotFoundResponse()
    {
        FeedbackRO feedbackRO = feedbackBuilderProvider.get()
                        .withStatus(Response.Status.NOT_FOUND) //
                        .withCode(ERROR_STOCK_NOT_FOUND) //
                        .build();
        FeedbackCtnrRO containerRO = new FeedbackCtnrRO();
        containerRO.addError(feedbackRO);
        return Response.status(Response.Status.NOT_FOUND).entity(containerRO).build();
    }

}
