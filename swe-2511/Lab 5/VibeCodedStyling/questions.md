# Vibe Coding Assignment Questions”

## 1. Name and Creator of the Language Model
The model used was **GPT-5**, created by **OpenAI**. GPT-5 is an advanced large language model capable of generating natural language and structured code. It was used to create a responsive website using **Tailwind CSS** for styling and layout.


## 2. Critique of the Generated Code
The generated code produced a clean, responsive home-buying website similar to Zillow. It used Tailwind CSS effectively and included input validation for the search form. However, the mobile menu button didn’t function properly, and accessibility tags like `aria-labels` which are common in Tailwind CSS were missing.


## 3. HTML and CSS Validator Results
When run through the W3C validators, the page showed minor warnings. These included invalid Tailwind background value syntax (`bg-[url(...)]`) and missing `alt` text on images. These issues were non-critical and could be easily fixed by adding proper attributes or using inline styles. There was also an issue with trailing slashes on void elements, which was an easy fix.


## 4. Mistakes Made by the Model
The model did not implement a working hamburger menu for mobile navigation. It also failed to include lazy loading for images, which could improve performance. Additionally, the form validation could be made more user-friendly with clearer error messages.


## 5. Fixes for the Mistakes
To fix these issues, JavaScript can be added to make the mobile menu toggle visible and functional. Adding `loading="lazy"` to images and descriptive `alt` text improves accessibility and performance. The validation logic can be expanded to highlight specific input errors for better usability.


## 6. Refining Prompts for Better Results
Future prompts could specify that the navigation menu should function on all screen sizes and that accessibility features must be included. Asking for validated HTML and CSS output would help reduce syntax errors. Including directions for performance optimization would also lead to cleaner, more efficient code.


## 7. Exact Prompts Used
**Initial Prompt:**  
“Create a website for buying homes (similar to Zillow). Make sure the resulting website uses responsive design and looks user-friendly on different screen sizes. Consider adding form elements with input validation. You should use Tailwind CSS, and make the UI non-functional (front-end, little back-end logic).”