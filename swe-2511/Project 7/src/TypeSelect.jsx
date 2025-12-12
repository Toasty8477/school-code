const TypeSelect = (props) => {

    const onChangeHandler = (event) => {
        const type = event.target.value
        props.onChange(type)
    }

    return (
        <>
            <p>Filter By Item Type</p>
            <select className="form-select" onChange={onChangeHandler}>
                <option selected value="">All</option>
                <option value="Burger">Burger</option>
                <option value="Chicken">Chicken</option>
                <option value="Side">Side</option>
                <option value="Dessert">Dessert</option>
                <option value="Seafodd">Seafood</option>
                <option value="Salad">Salad</option>
                <option value="Dressing">Dressing</option>
            </select>
        </>
    )
}

export default TypeSelect