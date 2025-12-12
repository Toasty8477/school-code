/*
 * Class: SWE2511 - Text File Manager
 *
 * Text file manager client interface logic
 */

/**
 * Display and clear error messages
 *
 * displayError: displays the error
 * @param message - Error message text
 *
 * clearError: clears the error
 */
const displayError = (message) => {
    const errorDisplay = document.getElementById("errorDisplay");
    errorDisplay.innerText = `${message}`;
    errorDisplay.classList.remove('visually-hidden');
}
const clearError = () => {
    const errorDisplay = document.getElementById("errorDisplay");
    errorDisplay.classList.add('visually-hidden');
    errorDisplay.innerText = '';
}

/**
 * Display and clear warning messages
 *
 * displayError: displays the warning
 * @param message - Warning message text
 *
 * clearError: clears the warning
 */
const displayWarning = (message) => {
    const warningDisplay = document.getElementById("warningDisplay");
    warningDisplay.innerText = `Warning: ${message}`;
    warningDisplay.classList.remove('visually-hidden');
}
const clearWarning = () => {
    const warningDisplay = document.getElementById("warningDisplay");
    warningDisplay.classList.add('visually-hidden');
    warningDisplay.innerText = '';
}

/**
 * Enable and disable create new inputs
 */
const enableCreateNew = () => {
    document.getElementById("fileNameInput").disabled = false;
    document.getElementById("createFile").disabled = false;
}
const disableCreateNew = () => {
    document.getElementById("fileNameInput").disabled = true;
    document.getElementById("createFile").disabled = true;
}

/**
 * Enable and disable save buttons
 */
const enableSave = () => {
    document.getElementById("saveFile").disabled = false;
}
const disableSave = () => {
    document.getElementById("saveFile").disabled = true;
}

/**
 * Enable and disable the file select list
 */
const enableSingleFileSelect = (menuItem) => {
    // Disable the menu item
    menuItem.disabled = false;
    menuItem.classList.remove("disabled");

    // Find all child buttons (for delete or other operations)
    //   and disable all
    const childButtons = menuItem.getElementsByTagName("button");
    for(const button of childButtons) {
        button.disabled = false;
        button.classList.remove("disabled");
    }
}
const enableFileSelect = () => {
    // Search for all file names in the menu selector
    const fileNameList = document.getElementById("fileNames");

    // Disable all the file name menu items
    for(const fileNameItem of fileNameList.children) {
        enableSingleFileSelect(fileNameItem);
    }
}
const disableSingleFileSelect = (menuItem) => {
    // Enable the menu item
    menuItem.disabled = true;
    menuItem.classList.add("disabled");

    // Find all child buttons (for delete or other operations)
    //   and enable all
    const childButtons = menuItem.getElementsByTagName("button");
    for(const button of childButtons) {
        button.disabled = true;
        button.classList.add("disabled");
    }
}
const disableFileSelect = () => {
    // Search for all file names in the menu selector
    const fileNameList = document.getElementById("fileNames");

    // Enable all the file name menu items
    for(const fileNameItem of fileNameList.children) {
        disableSingleFileSelect(fileNameItem);
    }
}

/**
 * Mark functions to mark a file as changed or not
 */
const markFileChanged = () => {

    // File marked changed,
    //    so it can be saved, but not other operations can be performed

    // Enable the save button
    enableSave();

    // Disable create new
    disableCreateNew();

    // Disable selecting a new file
    disableFileSelect();

    // Alert the user of changed file
    displayWarning("File contains unsaved changes");
}
const markFileUnchanged = () => {

    // File marked unchanged,
    //    so it can't be saved (it has no changed), but other operations can be performed

    // Disable the save button
    disableSave();

    // Enable creating a new file
    enableCreateNew();

    // Enable the file select menu
    enableFileSelect();

    // Clear any existing warnings
    clearWarning();
}

/**
 * Active file operations
 *    setting and clearing the active file
 */
const getActiveFileName = () => {
    const activeFileNameDisplay = document.getElementById("activeFile");
    return activeFileNameDisplay.innerText;
}
const clearActiveFiles = () => {
    // Enable the menu select to allow the selecting of other files
    enableFileSelect();

    // No active file so clear the active class for all menu items
    const fileNameList = document.getElementById("fileNames");
    for(const fileNameItem of fileNameList.children) {
        fileNameItem.classList.remove("active");
    }
}
const setActiveFile = (menuItem, fileName) => {
    // Clear any current active files
    clearActiveFiles();

    // Set the new active file text
    const activeFileNameDisplay = document.getElementById("activeFile");
    activeFileNameDisplay.innerText = fileName;

    // Set the active file css class
    if(isDefined(menuItem)) {
        menuItem.classList.add("active");
    }
}

/**
 * createFileButton - creates a selector button/menu item for a new file
 */
const createFileButton = (name) => {

    // Create the list item to add to the file list
    const listItem = document.createElement("li");
    listItem.classList.add("d-flex", "justify-content-between", "list-group-item", "list-group-item-action");
    listItem.onclick = async (event) => {
        event.stopPropagation();
        await onLoadFile(listItem, name);
    };

    // Create a span element to easily get the file name
    const fileNameDisplay = document.createElement("span");
    fileNameDisplay.innerText = name;

    // Create a delete button to delete the file
    const deleteButton = document.createElement("button");
    deleteButton.classList.add("btn-close");
    deleteButton.onclick = async (event) => {
        event.stopPropagation();
        await onDeleteFile(listItem, name);
    };

    // Add all elements to the menu
    listItem.appendChild(fileNameDisplay);
    listItem.appendChild(deleteButton);
    return listItem;
}

/**
 * Action handlers
 *   User types text
 *   User clicks on save button
 *   User clicks on a delete button
 *   User clicks on a menu item
 */

/**
 * Mark the file as changed when the user types a character
 */
const onTextChange = () => {
    markFileChanged();
}

/**
 * onSaveFile saves the file data
 */
const onSaveFile = async () => {
    try {
        // Clear existing error (if any)
        clearError();

        // Retrieve the file data and name
        const fileData = document.getElementById("fileData").value;
        const fileName = getActiveFileName();

        // Save the file
        document.getElementById("fileData").value = await saveFileData(fileName, fileData);

        // Mark that the file has been saved and is no longer changed
        markFileUnchanged();
    } catch (error) {
        displayError(`Unexpected error: ${error.name} : ${error.message}`);
    }
}

/**
 * onLoadFile loads the file data
 */
const onLoadFile = async (menuItem, fileName) => {
    try {
        // Clear existing error (if any)
        clearError();

        // Retrieve and set the file data
        const fileData = await getFileData(fileName);
        const fileDataDisplay = document.getElementById("fileData");
        fileDataDisplay.value = fileData;

        // Enable the editor to allow changes to the text
        document.getElementById("fileData").disabled = false;

        // Set the active file and mark that the file hasn't been changed
        setActiveFile(menuItem, fileName);
        markFileUnchanged();
    } catch (error) {
        displayError(`Unexpected error: ${error.name} : ${error.message}`);
    }
}

/**
 * onDeleteFile - delete a file by name
 */
const onDeleteFile = async (menuItem, fileName) => {
    try {
        // Clear existing error (if any)
        clearError();

        // Delete the file by name
        await deleteFileData(fileName);

        // Remove the file's menu item from the display
        menuItem.remove();

        // The user might have deleted the active file
        //   If so, then the interface needs to be updated to show no active file
        if(fileName === getActiveFileName()) {
            clearActiveFiles();
            const fileDataDisplay = document.getElementById("fileData");
            fileDataDisplay.value = '';
            fileDataDisplay.disabled = true;
            setActiveFile(null, '');
        }
    } catch (error) {
        displayError(`Unexpected error: ${error.name} : ${error.message}`);
    }
}

/**
 * onCreateFile - creates a new file for editing
 */
const onCreateFile = async () => {
    try {
        // Clear existing error (if any)
        clearError();

        // Retrieve and validate the file name to create
        const fileNameInput = document.getElementById("fileNameInput");
        const fileName = fileNameInput.value;
        if(!isNonEmptyString(fileName)) {
            displayError("Name of new file is required");
            return;
        }
        if(!isAlphaNumeric(fileName)) {
            displayError("Name of new file must contain only letters or numbers or dot (.)");
            return;
        }
        fileNameInput.value = '';

        // Create the file on the server
        const createdFileName = await createNewFile(fileName);

        // Clear out the existing text display to reflect new file data
        const fileDataDisplay = document.getElementById("fileData");
        fileDataDisplay.value = '';
        document.getElementById("fileData").disabled = false;

        // Create a selector button for the new file
        const newFileButton = createFileButton(createdFileName);
        const fileNameDisplay = document.getElementById("fileNames");
        fileNameDisplay.appendChild(newFileButton);

        // Set the active file and mark that it has not been changed
        setActiveFile(newFileButton, createdFileName);
        markFileUnchanged();
    } catch (error) {
        displayError(`Unexpected error: ${error.name} : ${error.message}`);
    }
}

/**
 * loadFiles - loads the file names from the server
 */
const loadFiles = async () => {
    try {
        clearError();
        const fileNames = await getFiles();
        const fileNameDisplay = document.getElementById("fileNames");
        fileNames.forEach((name) => {
            fileNameDisplay.appendChild(createFileButton(name));
        });
    } catch(error) {
        displayError(`Unexpected error: ${error.name} : ${error.message}`);
    }
}

window.onload = async () => {
    document.getElementById("fileData").onkeydown = onTextChange;
    document.getElementById("saveFile").onclick = onSaveFile;
    document.getElementById("createFile").onclick = onCreateFile;

    await loadFiles();
}