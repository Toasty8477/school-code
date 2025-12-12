### 1. What large language model did you use?
- I used GPT-5, developed by OpenAI. I used it to create a D3.js chart that visualizes the relationship between wolf and moose populations from 1980 to 2019.

### 2. What did the model do for you?

The model generated a complete D3.js script that creates a dual-line chart showing the population trends of wolves and moose over time. It included axes, labels, tooltips, and color-coded lines to make the comparison clear. The output was well-structured and easy to customize.

### 3. What mistakes did the model make?

The main issues were minor — the chart wasn’t responsive, there was no legend, and the tooltip sometimes overlapped the cursor. These didn’t break the chart, but they affected usability and accessibility. The model’s code also used fixed pixel sizes instead of scalable units.

### 4. What happened when you ran your HTML and CSS through the validators?

The HTML and CSS files both passed their respective validators (on the first try, no revision was necessary).

### 5. What mistakes did the model make that caused validation or runtime errors?

The model didn’t cause any runtime errors, which was good. It did lack a viewBox for responsive scaling, which isn’t technically an error but is considered a best practice. 

### 6. How could you have corrected or avoided these mistakes?

I could have specified more detailed requirements in my prompt, such as “make it responsive” or “include a legend.” This would have guided the model toward a more complete solution. Careful review and testing in a browser also helped me catch and fix minor issues quickly.

### 7. What prompt did you use to get this output?

My prompt was:
“Create a chart for the wolves data using D3.js. Use many of the features from this library to show how it differs from Google Charts.”