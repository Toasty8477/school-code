package edu.msoe.swe2721.lab10;

public class RefactoredItem extends Item {

    /**
     * This method will instantiate a new instance of the item.
     * 
     * @param name    This is the name of the item.
     * @param sellIn  This is the number of days until the item must be sold.
     * @param quality This i9s the initial quality for the item.
     */
    public RefactoredItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void updateQuality() {
        if (name.equals("Sulfuras, Hand of Ragnaros")) {
            return;
        }

        if (name.equals("Aged Brie")) {
            if (quality < 50) {
                quality++;

                if (sellIn < 0) {
                    quality++;
                }
            }
        } else if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (quality < 50) {
                quality++;
                if (sellIn < 6) {
                    quality++;
                }
            }

            if (quality < 50 && sellIn < 11) {
                quality++;
            }

            if (sellIn < 0) {
                quality = 0;
            }

        } else {
            quality--;
            if (sellIn <= 0) {
                quality--;
            }
        }

        if (quality < 0) {
            quality = 0;
        }

        sellIn--;
    }
}
