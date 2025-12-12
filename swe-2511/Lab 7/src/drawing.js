// Class: SWE2511 - Drawing
// Names: Alex Horton and Colin Glynn
// Class Section: 111

window.onload = () => {
    /* TODO - Set up event handlers and other initialization */
    const canvas = document.getElementById("my_canvas")
    const context = canvas.getContext("2d")

    // Fill background white to enable proper flood fill (for vibe coded feature)
    context.fillStyle = "white";
    context.fillRect(0, 0, canvas.width, canvas.height);

    let penDown = false

    let mouseX = 0
    let mouseY = 0

    // added feature variables
    let penColor = document.getElementById("colorPicker").value;
    let penSize = parseInt(document.getElementById("penSize").value);
    let isEraser = false;

    // allows for smoother lines
    context.lineCap = "round";

    const getPos = (e) => {
        mouseX = e.clientX - canvas.offsetLeft
        mouseY = e.clientY - canvas.offsetTop
    }

    canvas.onmousedown = (e) => {
        penDown = true
        getPos(e)
    }

    canvas.onmouseup = () => {
        penDown = false
        saveToLocal();
    }

    canvas.onmousemove = (e) => {
        if (penDown) {
            context.beginPath()
            context.moveTo(mouseX, mouseY)
            getPos(e)
            context.lineTo(mouseX, mouseY)
            // added for features
            context.strokeStyle = isEraser ? "#FFFFFF" : penColor;
            context.lineWidth = penSize;
            context.stroke()
            context.closePath()
        }
    }

    // Feature #1 Color Picker
    document.getElementById("colorPicker").oninput = (e) => {
        penColor = e.target.value;
        isEraser = false;
    };

    // Feature #2 Pen Size with Validation
    document.getElementById("penSize").onchange = (e) => {
        const size = parseInt(e.target.value);
        if (isNaN(size) || size < 1 || size > 50) {
            alert("Please enter a size between 1 and 50!");
            e.target.value = penSize;
        } else {
            penSize = size;
        }
    };

    // Feature #3 Eraser
    document.getElementById("eraserBtn").onclick = () => {
        isEraser = !isEraser;
        document.getElementById("eraserBtn").classList.toggle("btn-dark", isEraser);
    };

    // --- Vibe-Coded Feature: Fill Tool ---
    let fillMode = false;

    const fillBtn = document.getElementById("fillBtn");
    fillBtn.onclick = () => {
        fillMode = !fillMode;
        fillBtn.classList.toggle("btn-dark", fillMode);
        if (fillMode) isEraser = false;
    };

// Use offsetX/offsetY for reliable coords (works for canvas)
    canvas.addEventListener("click", (e) => {
        if (!fillMode) return;
        const rect = canvas.getBoundingClientRect();
        // Use offset coordinates relative to canvas
        const x = Math.floor(e.clientX - rect.left);
        const y = Math.floor(e.clientY - rect.top);

        // bounds guard
        if (x < 0 || y < 0 || x >= canvas.width || y >= canvas.height) return;

        const fillColor = hexToRgba(penColor); // [r,g,b,255]
        floodFill(x, y, fillColor);
        saveToLocal();
    });

// --- corrected floodFill ---
    function floodFill(startX, startY, fillColorRGBA) {
        const w = canvas.width;
        const h = canvas.height;
        const imageData = context.getImageData(0, 0, w, h);
        const data = imageData.data; // Uint8ClampedArray
        const startColor = getPixelColor(startX, startY, data, w);

        // If start color already matches fill color (within tolerance) -> nothing to do
        if (colorsMatch(startColor, fillColorRGBA, 5)) return;

        const stack = [{ x: startX, y: startY }];

        while (stack.length > 0) {
            const { x, y } = stack.pop();

            // bounds check
            if (x < 0 || y < 0 || x >= w || y >= h) continue;

            const idx = (y * w + x) * 4;
            const current = [data[idx], data[idx + 1], data[idx + 2], data[idx + 3]];

            // If current pixel doesn't match the target start color within tolerance, skip
            if (!colorsMatch(current, startColor, 25)) continue;

            // Set pixel to fill color
            data[idx]     = fillColorRGBA[0];
            data[idx + 1] = fillColorRGBA[1];
            data[idx + 2] = fillColorRGBA[2];
            data[idx + 3] = 255;

            // Push neighbors (N, S, E, W) with bounds check
            if (x + 1 < w)  stack.push({ x: x + 1, y: y });
            if (x - 1 >= 0) stack.push({ x: x - 1, y: y });
            if (y + 1 < h)  stack.push({ x: x,     y: y + 1 });
            if (y - 1 >= 0) stack.push({ x: x,     y: y - 1 });
        }

        context.putImageData(imageData, 0, 0);
    }

// --- helpers (ensure these are defined once in your file) ---
    function getPixelColor(x, y, data, width) {
        const index = (y * width + x) * 4;
        return [data[index], data[index + 1], data[index + 2], data[index + 3]];
    }

    function hexToRgba(hex) {
        // Accepts "#rrggbb"
        const clean = (hex || "#000000").replace("#", "");
        const bigint = parseInt(clean, 16);
        const r = (bigint >> 16) & 255;
        const g = (bigint >> 8) & 255;
        const b = bigint & 255;
        return [r, g, b, 255];
    }

    function colorsMatch(a, b, tolerance = 0) {
        // Compare RGB only; ignore alpha in matching
        return (
            Math.abs(a[0] - b[0]) <= tolerance &&
            Math.abs(a[1] - b[1]) <= tolerance &&
            Math.abs(a[2] - b[2]) <= tolerance
        );
    }

    // Feature #4 Clear Canvas (with a confirmation prompt)
    document.getElementById("clearBtn").onclick = () => {
        const confirmClear = confirm("Are you sure you want to clear your drawing?");
        if (confirmClear) {
            context.clearRect(0, 0, canvas.width, canvas.height);
            saveToLocal();
        }
    };

    // Feature #5 Save as Image
    document.getElementById("saveBtn").onclick = () => {
        const link = document.createElement("a");
        link.download = "my_drawing.png";
        link.href = canvas.toDataURL("image/png");
        link.click();
    };

    // Feature #6 Local Storage
    function saveToLocal() {
        localStorage.setItem("savedDrawing", canvas.toDataURL());
    };

    function loadFromLocal() {
        const saved = localStorage.getItem("savedDrawing");
        if (saved) {
            const img = new Image();
            img.src = saved;
            img.onload = () => context.drawImage(img, 0, 0);
        }
    }

    loadFromLocal();
};