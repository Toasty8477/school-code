package edu.msoe.swe2721.lab11;

import edu.msoe.swe2721.lab11.exceptions.WebsiteConnectionError;

public class MockStockQuoteGenerator implements StockQuoteGeneratorInterface {

    private double previousClose;
    private double latestPrice;
    private double change;

    MockStockQuoteGenerator(double previousClose, double latestPrice, double change) {
        this.previousClose = previousClose;
        this.latestPrice = latestPrice;
        this.change = change;
    }

    MockStockQuoteGenerator() {
        this(265.805, 256.48, -9.325);
    }

    /**
     * Returns a stock quote with a symbol of your choice and dummy values.
     */
    @Override
    public StockQuoteInterface getCurrentQuote(String symbol) throws WebsiteConnectionError {
        // Throw an error in a predefined case for testing reasons
        if (symbol == "THR") {
            throw new WebsiteConnectionError("Cannot obtain quote for symbol" + symbol );
        }
        StockQuoteInterface quote = new StockQuote(symbol, previousClose, latestPrice, change, "2025-10-07 13:30:00");
        return quote;
    }

    @Override
    public StockQuoteGeneratorInterface createNewInstance(String symbol) {
        return this;
    }
}
