import { useState } from "react"

const SearchBar = (props) => {

    const onChangeHandler = (event) => {
        const inputText = event.target.value
        props.onChange(inputText)
    }

    return (
        <>
            <div className="mb-3">
                <input className="form-control" id="searchBar" placeholder="Search..." onChange={onChangeHandler}></input>
            </div>
        </>
    )
}

export default SearchBar