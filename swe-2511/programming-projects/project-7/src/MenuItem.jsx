const MenuItem = (props) => {

    return (
        <div className="col">
            <div className="card mb-3 h-100">
                <div className="card-body">
                    <h5 className="card-title">{props.itemName}</h5>
                    <h6 className="card-subtitle mb-2 text-body-secondary">{props.itemType}</h6>
                    <p className="card-text mb-0">Allergens:</p>
                    {props.allergens.map(allergen => <span className="badge rounded-pill text-bg-primary me-1">{allergen}</span>)}
                </div>
            </div>
        </div>
    )
}

export default MenuItem