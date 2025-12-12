## Tailwind CSS
### How to install
- Tailwind provides many options for installation depending on your framework. Tailwind provides a CDN that is intended for development purposes.
- You can add tailwind using the cdn by first adding a script tag with the provided cdn link and the adding a style tag with "text/tailwindcss" as the type.

### Components
- Columns
  - Set the number of columns in an element by number or size
  - Set gap using gap-*
- Order
  - set the order items appear in a flex box different then the order in the html
  - use `order-<number>`
- Font smoothing
  - Controls whether the font is antialiased or subpixel antialiased
  - set using `antialiased` or `subpixel-antialiased`
- hyphens
  - Controls hyphenating of words
  - set using `hyphens-none`, `hyphens-manual`, or `hyphens-auto`
- box-shadow
  - Sets a box shadow on an element
  - set using `shadow-<size>` or `shadow-none`