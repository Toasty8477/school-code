package com.gpf;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class GPFDatabaseDumpingHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final Logger logger = LoggerFactory.getLogger(GPFDatabaseDumpingHandler.class);

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        String result = getAllRecords(logger);
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(result);
    }

    /**
     * Grabs the entries from the DynamoDB database and prints them into the log.
     * 
     * @param logger Logger to print the key and value for all the database entries.
     */
    public static String getAllRecords(Logger logger) {
        // Connect to the DynamoDB database
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        ScanRequest scanRequest = new ScanRequest().withTableName(GPFCompute.TABLE_NAME);
        // Retrieve all the entries
        ScanResult result = client.scan(scanRequest);
        StringBuilder sb = new StringBuilder();
        sb.append("Number\t\tGPF\n");
        // For each entry in the database
        for (Map<String, AttributeValue> entry : result.getItems()) {
            try {
                if (entry != null) {
                    Set<String> keys = entry.keySet();
                    logger.info("Returned item " + entry);
                    // Each entry is composed of two keys: number and gpf
                    for (String key : keys) {
                        AttributeValue value = entry.get(key);
                        // Get back the String that was put into the AttributeValue
                        String v = value.getS();
                        logger.info(String.format("%s: %s\n", key, v));
                        sb.append(String.format("%s\t\t", v));
                    }
                    sb.append("\n");
                } else {
                    logger.error("No items found in the table " + GPFCompute.TABLE_NAME + "\n");
                }
            } catch (AmazonServiceException e) {
                logger.error("AWS Service Exception from Database Dump: " + e.getErrorMessage());
            }
        }
        return sb.toString();
    }

}
