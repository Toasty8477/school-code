// Class: SWE2511 - Node.js
// Name: Colin Glynn
// Class Section: 111
//
// Vibe With Me - Markdown to PDF Service using Express.js and md-to-pdf

import express from "express";
import multer from "multer";
import fs from "fs";
import { mdToPdf } from "md-to-pdf";

const app = express();
const upload = multer({ dest: "uploads/" });

// Endpoint to upload Markdown and get PDF
app.post("/upload", upload.single("markdown"), async (req, res) => {
    try {
        const filePath = req.file.path;
        const outputPath = `${filePath}.pdf`;

        // Convert Markdown to PDF
        const pdf = await mdToPdf({ path: filePath });
        await fs.promises.writeFile(outputPath, pdf.content);

        // Send the PDF file to the user
        res.download(outputPath, "output.pdf", async () => {
            // Cleanup temporary files
            await fs.promises.unlink(filePath);
            await fs.promises.unlink(outputPath);
        });
    } catch (err) {
        console.error("Error converting Markdown to PDF:", err);
        res.status(500).send("Failed to convert Markdown to PDF.");
    }
});

// Home route
app.get("/", (req, res) => {
    res.send(`
    <h1>Markdown to PDF Converter</h1>
    <form action="/upload" method="post" enctype="multipart/form-data">
      <input type="file" name="markdown" accept=".md" required />
      <button type="submit">Convert to PDF</button>
    </form>
  `);
});

// Start the server
const PORT = 3000;
app.listen(PORT, () => console.log(`Server running at http://localhost:${PORT}`));
