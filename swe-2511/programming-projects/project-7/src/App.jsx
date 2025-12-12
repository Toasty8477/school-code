/*
 * Class: SWE2511 - React Menu Filter
 *
 * App component
 */

import { useState } from 'react';
import Header from './Header';
import Footer from './Footer';
import MenuGrid from './MenuGrid';
import FilterBar from './FilterBar';
import { mapAllergies } from './utils';

// Import layout to use the holy grail layout
import './layout.css';

const App = (props) => {

    // Setup state and any other functions needed by the application
    const [searchFilter, setSearchFilter] = useState("")
    const [typeFilter, setTypeFilter] = useState("")
    const [allergenFilter, setAllergenFilter] = useState([false, false, false, false, false, false, false, false, false])

    const searchChangeHandler = (value) => {
        setSearchFilter(value)
    }

    const typeChangeHandler = (value) => {
        setTypeFilter(value)
    }

    const allergenChangeHandler = (target) => {
        const newFilter = allergenFilter.filter(() => true) // Evil way to create a copy of an array
        newFilter[target.id] = target.checked
        setAllergenFilter(newFilter)
    }

    const allergyFilter = (items) => {
        const filtered = items.filter(filterHelper)
        return filtered
    }

    const filterHelper = (item) => {
        let itemAllergies = mapAllergies([item.soy, item.egg, item.milk, item.fish, item.peanut, item.shellfish, item.treeNut, item.gluten, item.sesame])
        let filter = mapAllergies(allergenFilter)
        let allergenFree = true
        filter.forEach((e) => {
            if (itemAllergies.includes(e)) {
                allergenFree = false
                return
            }
        })
        return allergenFree
    }

    const categoryFilter = (type, items) => {
        const filtered = items.filter((item) => type ? item.type.toLowerCase() === type.toLowerCase() : item)
        return filtered
    }

    const nameFilter = (name, items) => {
        const filtered = items.filter((item) => item.name.toLowerCase().includes(name.toLowerCase()))
        return filtered
    }

    const fullFilter = (items) => {
        let filtered = allergyFilter(items)
        filtered = categoryFilter(typeFilter, filtered)
        filtered = nameFilter(searchFilter, filtered)
        return filtered
    }

    // Return the rendered App using the holy grail layout
    return (
        <div className="HolyGrail m-3">
            <div className="HolyGrail-header">
                <Header />
            </div>
            <div className="HolyGrail-content mb-3">
                <MenuGrid menuItems={fullFilter(props.menuItems)}/>
            </div>
            <div className="HolyGrail-nav">
                <FilterBar onSearchChange={searchChangeHandler} onTypeChange={typeChangeHandler} onFilterChange={allergenChangeHandler} />
            </div>
            <div className="HolyGrail-footer">
                <Footer />
            </div>
        </div>
    );
};

export default App;
