# HTML Drawinator 3000 - Feature Documentation
**Class:** SWE2511 - Drawing  
**Names:** Alex Horton and Colin Glynn  
**Section:** 111

---

### Feature 1: Color Picker
**Description:** Lets the user choose a pen color using a color input.  
**How it Works:** Updates the `penColor` variable on color change; the canvas uses that color for strokes.  
**Use:** Click the color box and select a color to draw with.  
**Usefulness:** Enables multi-colored drawings.

---

### Feature 2: Pen Size Selector (with Validation)
**Description:** Allows the user to change pen thickness.  
**How it Works:** Reads value from a number input (1–50). If out of range, shows an alert and resets to the last valid value.  
**Use:** Enter or adjust a size, then draw.  
**Usefulness:** Gives users control over line width.

---

### Feature 3: Eraser
**Description:** Switches the pen to erase mode.  
**How it Works:** Toggles a flag (`isEraser`) that changes stroke color to white.  
**Use:** Click “Eraser” to switch modes, click again to disable.  
**Usefulness:** Allows quick correction of mistakes.

---

### Feature 4: Clear Canvas (with Confirmation)
**Description:** Clears the entire drawing after confirmation.  
**How it Works:** Prompts the user with `confirm()`. If confirmed, `clearRect()` wipes the canvas.  
**Use:** Click “Clear” → confirm → canvas resets.  
**Usefulness:** Prevents accidental erasure while allowing a full reset.

---

### Feature 5: Save as Image
**Description:** Saves the current drawing as a PNG file.  
**How it Works:** Converts the canvas to a data URL and triggers a download.  
**Use:** Click “Save Image” to download your drawing.  
**Usefulness:** Lets users save their work locally.

---

### Feature 6: Local Storage Save/Load
**Description:** Automatically saves and restores the canvas image.  
**How it Works:** On each mouse release, saves the canvas to `localStorage`. On page load, restores if found.  
**Use:** Draw → reload the page → your work reappears.  
**Usefulness:** Protects work from browser crashes or accidental refreshes.

---

### 3rd Party Library: Bootstrap
**URL:** [https://getbootstrap.com/](https://getbootstrap.com/)  
**Description:** Used for button styling and layout. Provides prebuilt classes for consistent, responsive design.

---

### External Resource
**URL:** [https://www.w3schools.com/tags/att_input_type_color.asp](https://www.w3schools.com/tags/att_input_type_color.asp)  
**Description:** Referenced for implementing the HTML color picker input element.