/*
 * Class: SWE2511 - React Filter
 *
 * App component
 */

import { useState } from 'react';
import SearchBar from './SearchBar'
import TextDisplay from './TextDisplay'

const App = (props) => {

    const [searchString, setSearchString] = useState("");

    // Search event handler - called when text for the
    //    search field is changed
    const onSearchChanged = (text) => {

        // Grab the text from the search bar
        const newSearchString = text;

        // Set the state for the new string filter
        //   this will cause a re-render of the App
        setSearchString(newSearchString);
    }

    // Filter the words based on the current search text
    const words = props.text.split(" ");
    const filteredWords = words.filter((word) => {
        return word.toLowerCase().includes(searchString.toLowerCase());
    });
    const filteredString = filteredWords.join(" ");

    // Return the rendered App
    return (
        <div className="m-3">
            <SearchBar onSearchChange={onSearchChanged} />
            <TextDisplay text={filteredString} />
        </div>
    );
};

export default App;
