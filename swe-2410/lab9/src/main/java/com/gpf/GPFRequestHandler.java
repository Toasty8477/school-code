package com.gpf;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import com.fasterxml.jackson.jr.ob.JSON;

public class GPFRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final String QUEUE_NAME = "numbers";

    private final Logger logger = LoggerFactory.getLogger(GPFRequestHandler.class);

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            GPFRequest gpfRequest = JSON.std.beanFrom(GPFRequest.class, input.getBody());
            logger.info("Getting JSON data of " + JSON.std.asString(gpfRequest));

            final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
            String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
            CreateQueueResult create_result = sqs.createQueue(QUEUE_NAME);
            logger.info("Getting queue");

            SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(JSON.std.asString(gpfRequest));

            sqs.sendMessage(send_msg_request);
            logger.info("Sending message to queue");

            return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody("Recived number to process" + JSON.std.asString(gpfRequest));
        } catch (IOException | NullPointerException e) {
            logger.error("An error occured processing the request: " + e.getMessage());
            return new APIGatewayProxyResponseEvent()
                .withStatusCode(400)
                .withBody("An error occured processing the request");
        }
    }
    
}
