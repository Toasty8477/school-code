import { useState } from "react"
import AllergenSelect from "./AllergenSelect"
import SearchBar from "./SearchBar"
import TypeSelect from "./TypeSelect"

const FilterBar = (props) => {


    const onSearchTextChange = (value) => {
        props.onSearchChange(value)
    }

    const onTypeChange = (value) => {
        props.onTypeChange(value)
    }

    const onFilterChange = (target) => {
        props.onFilterChange(target)
    }

    return (
        <>
            <SearchBar onChange={onSearchTextChange}/>
            <TypeSelect onChange={onTypeChange}/>
            <AllergenSelect onChange={onFilterChange}/>
        </>
    )
}

export default FilterBar