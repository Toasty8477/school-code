### Positioning
- static  
What: The default value. Positions the element in the normal flow.  
When: For most of the content on a webpage.  
Example: `div { position: static }`
- absolute  
What: The element is taken out of the normal flow and positioned relative to the nearest positioned ancestor.  
When: Could be useful for modals if a browser doesn’t support html modals yet.  
Example: `div { position: absolute; inset-block-start: 30px; }`
- relative  
What: Positions the element relative to where it would be in the document  
When: Could be used to create a collage of images on a website using multiple individual images so that they may be changed.  
Example: `div { position: relative; top: 25px; left 25px; }`
- fixed  
What: Positions the element in a fixed spot in the viewport
When: Create a floating button that stays on the screen such as to return to the top of the page.  
Example: `div { position: fixed; bottom: 2em; right: 2em; }`  
- sticky  
What: Positions the element normally but keeps it on screen if scrolling would hide it  
When: Keep table headings on screen if viewing a long table.  
Example: `th { position: sticky; top: 10px; }`  

Nearest positioned ancestor means the nearest ancestor of the element that isn’t static or the body of the document if none exists.
