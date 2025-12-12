import { useEffect } from "react"

const AllergenSelect = (props) => {

    // False if allergen is allowed, true if not allowed
    let filterArray
    filterArray = [false, false, false, false, false, false, false, false]

    const onChangeHandler = (event) => {
        filterArray[event.target.id] = event.target.checked
        console.log(event.target)
        console.log(filterArray[event.target.id])
        props.onChange(event.target)
    }

    return (
        <>
            <p>Filter By Allergen:</p>
            <div className="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="0" onChange={onChangeHandler}></input>
                <label class="form-check-label" for="0">Soy Free</label>
            </div>
            <div className="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="1" onChange={onChangeHandler}></input>
                <label class="form-check-label" for="1">Egg Free</label>
            </div>
            <div className="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="2" onChange={onChangeHandler}></input>
                <label class="form-check-label" for="2">Milk Free</label>
            </div>
            <div className="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="3" onChange={onChangeHandler}></input>
                <label class="form-check-label" for="3">Fish Free</label>
            </div>
            <div className="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="4" onChange={onChangeHandler}></input>
                <label class="form-check-label" for="4">Peanut Free</label>
            </div>
            <div className="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="5" onChange={onChangeHandler}></input>
                <label class="form-check-label" for="5">Shellfish Free</label>
            </div>
            <div className="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="6" onChange={onChangeHandler}></input>
                <label class="form-check-label" for="6">Tree Nut Free</label>
            </div>
            <div className="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="7" onChange={onChangeHandler}></input>
                <label class="form-check-label" for="7">Gluten Free</label>
            </div>
            <div className="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="8" onChange={onChangeHandler}></input>
                <label class="form-check-label" for="8">Sesame Free</label>
            </div>
        </>
    )
}

export default AllergenSelect