package edu.msoe.swe2721.lab10;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;


public class Tests {

    public Item i;

    @DataProvider(name = "monoProvider")
    public Object[][] monoProvider() {
        return new Object[][] {
            {"Aged Brie", 51, 4, 51, 3}, // Brie at max quality should decrease sellin but not increase quality
            {"Aged Brie", 49, 12, 50, 11}, // Brie should increase quality by one and decrease sellin by one
            {"Aged Brie", 48, -1, 50, -2}, // Brie should increase in quality by two after sellin has passed
            {"Aged Brie", 51, -1, 51, -2}, // Brie should increase in quality by two after sellin has passed but not over max
            {"Backstage passes to a TAFKAL80ETC concert", 51, 12, 51, 11}, // Concert at max quality over 10 days should decrease sellin but not increase quality
            {"Backstage passes to a TAFKAL80ETC concert", 49, 12, 50, 11}, // Concert over 10 days increase quality by one and decrease sellin by one
            {"Backstage passes to a TAFKAL80ETC concert", 49, 10, 50, 9}, // Concert at 10 days or less but more than 5 days increase quality by two, but only to max quality, and decrease sellin by one
            {"Backstage passes to a TAFKAL80ETC concert", 48, 10, 50, 9}, // Concert at 10 days or less but more than 5 days increase quality by two and decrease sellin by one
            {"Backstage passes to a TAFKAL80ETC concert", 51, 10, 51, 9}, // Concert at max quality at 10 days or less but more than 5 days should decrease sellin but not increase quality
            {"Backstage passes to a TAFKAL80ETC concert", 48, 4, 50, 3}, // Concert under 5 days increase quality by three, but only to max quality, and decrease sellin by one
            {"Backstage passes to a TAFKAL80ETC concert", 51, 7, 51, 6}, // Concert at max quality under 10 days but over 5 should decrease sellin but not increase quality
            {"Backstage passes to a TAFKAL80ETC concert", 47, 4, 50, 3}, // Concert under 5 days increase quality by three and decrease sellin by one
            {"Backstage passes to a TAFKAL80ETC concert", 51, 4, 51, 3}, // Concert at max quality under 5 days should decrease sellin but not increase quality
            {"Backstage passes to a TAFKAL80ETC concert", 51, -1, 0, -2}, // Concert sellin 0 or less should set quality to 0
            {"Sulfuras, Hand of Ragnaros", 49, 4, 49, 4}, // Hand should not increase or decrease quality or sellin
            {"Sulfuras, Hand of Ragnaros", 49, -1, 49, -1}, // Hand should not increase or decrease quality or sellin
            {"Fish", 0, 4, 0, 3}, // Normal should decrease sellin
            {"Fish", 51, 4, 50, 3}, // Normal should decrease quality when over 0 and sellin
            {"Fish", 0, -1, 0, -2}, // Normal should decrease by 2 after sellin is less than 0 but not lower than 0
            {"Fish", 51, -1, 49, -2}, // Normal should decrease by 2 after sellin is less than 0
        };
    }

    @Test(dataProvider = "monoProvider", groups = {"all", "student"})
    public void testItemUpdateQuality(String name, int quality, int sellIn, int expectedQuality, int expectedSellIn) {
        // Arrange
        i = new Item(name, sellIn, quality);
        int actualSellIn;
        int actualQuality;
        // Act
        i.updateQuality();
        actualSellIn = i.getSellIn();
        actualQuality = i.getQuality();
        // Assert
        assertEquals(actualSellIn, expectedSellIn);
        assertEquals(actualQuality, expectedQuality);
    }

    @Test(dataProvider = "monoProvider", groups = {"all", "student"})
    public void testRefactoredItemUpdateQuality(String name, int quality, int sellIn, int expectedQuality, int expectedSellIn) {
        // Arrange
        i = new RefactoredItem(name, sellIn, quality);
        int actualSellIn;
        int actualQuality;
        // Act
        i.updateQuality();
        actualSellIn = i.getSellIn();
        actualQuality = i.getQuality();
        // Assert
        assertEquals(actualSellIn, expectedSellIn);
        assertEquals(actualQuality, expectedQuality);
    }
}
