/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 3 - Design a Garden
 * Name: Alex Horton
 * Created: 2/3/2025
 */

package com.unicorn.location;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.jr.ob.JSON;

/**
 * Lambda handler for processing API Gateway requests related to unicorn
 * locations.
 */
public class UnicornPostLocationHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final Logger logger = LoggerFactory.getLogger(UnicornPostLocationHandler.class);

    /**
     * Handles the incoming request.
     *
     * @param input   the API Gateway request event (includes request data)
     * @param context the Lambda execution context
     * @return a response with status code and message body
     */
    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
            final Context context) {
        final int successStatusCode = 200;
        final int errorStatusCode = 400;
        final double boundry = -88;
        try {
            UnicornLocation unicornLocation =
                    JSON.std.beanFrom(UnicornLocation.class, input.getBody());

            String location = "";
            if (Double.parseDouble(unicornLocation.getLongitude()) < boundry) {
                location += " Unicorn is west of Milwaukee";
            } else {
                location += " Unicorn is east of Milwaukee";
            }

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(successStatusCode)
                    .withBody("Received unicorn location:"
                            + JSON.std.asString(unicornLocation) + location);
        } catch (IOException | NullPointerException e) {
            logger.error("Error while processing the request", e);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(errorStatusCode)
                    .withBody("Error processing the request");
        }
    }
}
