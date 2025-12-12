const SearchBar = (props) => {

    const onChangeHandler = (event) => {
        const searchText = event.target.value
        props.onSearchChange(searchText)
    }

    return (
        <input
            type="text"
            className="form-control"
            placeholder="Search..."
            onChange={onChangeHandler}
        />
    )
}

export default SearchBar