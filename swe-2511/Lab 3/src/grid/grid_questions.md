### Grid Container Properties
- grid-template-columns – Sets the number and width of columns in the grid. You would use this when setting up a grid.
- grid-template-rows – Sets up the number and height of rows in the grid. You would use this when setting up a grid.
- grid-template-areas – Sets up the whole grid giving each area a name. You might use this to layout items in a grid easier.
- grid-area – Specifies an items position in a grid. You would use this to position an item in a named area or make an item stretch multiple grid cells.

[Source](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout)

### fr Unit
A fractional unit represents a fraction of the space in a flex box. It automaticaly sizes based on the total number of fr used in all the elements. For example when making a grid you can use `grid-template-columns: 1fr 1fr 1fr;` to set up a grid with three columns that take up 1/3 of the space each. If you used `grid-template-columns: 1fr 1fr 2fr;` you would have a grid with two columns that take up 1/4 of the space each and one column that takes up 2/4 of the space.