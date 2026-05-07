package com.gpf;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.jr.ob.JSON;

public class GPFCompute implements RequestHandler<SQSEvent, Void> {

    public static final String TABLE_NAME = "gpfNumbers";

    private final Logger logger = LoggerFactory.getLogger(GPFCompute.class);

    @Override
    public Void handleRequest(SQSEvent input, Context context) {
        logger.info("Handling SQS request for GPFCompute");
        List<SQSEvent.SQSMessage> messages = input.getRecords();
        for (SQSEvent.SQSMessage message : messages) {
            try {
                logger.info("Getting body of message from queue");
                GPFRequest request = JSON.std.beanFrom(GPFRequest.class, message.getBody());
                createGPFRecord(request);
            } catch (IOException e) {
                logger.error("Could not get body of request");
            }
        }
        return null;
    }

    public void createGPFRecord(GPFRequest request) {
        logger.info("Calculating greatest prime factor");
        BigInteger gpf = calculateGreatestPrimeFactor(request.getNumber());
        logger.info("Number: " + request.getNumber() + " GPF: " + gpf);

        Map<String, AttributeValue> table = new HashMap<>();
        table.put("Number", new AttributeValue(String.valueOf(request.getNumber())));
        table.put("GPF", new AttributeValue(String.valueOf(gpf)));

        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
        try {
            ddb.putItem(TABLE_NAME, table);
            logger.info("Added to the database "+table);
        } catch (ResourceNotFoundException e) {
            logger.error("Error: The table \"{}\" cannot be found.\n", TABLE_NAME);
            logger.error("Be sure that it exists and that you have typed its name correctly!");
        } catch (AmazonServiceException e) {
            logger.error("Error from AWS: " + e.getMessage());
        }
    }

    public static BigInteger calculateGreatestPrimeFactor(BigInteger number) {
        BigInteger i = BigInteger.valueOf(2);
        BigInteger one = BigInteger.ONE;
        
        while(number.compareTo(one) > 0) {
            if (number.mod(i).equals(BigInteger.ZERO)) {
                number = number.divide(i);
            } else if (i.multiply(i).compareTo(number) > 0) {
                i = number;
            } else {
                i = i.add(BigInteger.ONE);
            }
        }
        return i;
    }
    
}
