import MenuItem from "./MenuItem";
import { mapAllergies } from "./utils"

const MenuGrid = (props) => {
    return (
        <>
            <div className="container">
                <div className="row row-cols-3 g-3">
                    {props.menuItems.map(item => <MenuItem itemName={item.name} itemType={item.type} allergens={
                        mapAllergies([item.soy, item.egg, item.milk, item.fish, item.peanut, item.shellfish, item.treeNut, item.gluten, item.sesame])
                        } />)}
                </div>
            </div>
        </>
    )
}

export default MenuGrid