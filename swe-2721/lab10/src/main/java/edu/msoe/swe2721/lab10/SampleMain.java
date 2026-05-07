package edu.msoe.swe2721.lab10;

public class SampleMain {
    public static void main(String[] args) {
        System.out.println("OMGHAI!");
        RefactoredItem[] items = new RefactoredItem[] {
                new RefactoredItem("+5 Dexterity Vest", 10, 20), //
                new RefactoredItem("Aged Brie", 2, 0), //
                new RefactoredItem("Elixir of the Mongoose", 5, 7), //
                new RefactoredItem("Sulfuras, Hand of Ragnaros", 0, 80), //
                new RefactoredItem("Sulfuras, Hand of Ragnaros", -1, 80),
                new RefactoredItem("Backstage passes to a TAFKAL80ETC concert", 12, 20),
                new RefactoredItem("Backstage passes to a TAFKAL80ETC concert", 8, 36),
                new RefactoredItem("Backstage passes to a TAFKAL80ETC concert", 5, 49)
        };

        GildedRose app = new GildedRose(items);

        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- Day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();
            app.updateQuality();
        }
    }

}
