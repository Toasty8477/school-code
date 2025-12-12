export const mapAllergies = (allergens) => {
    const allergenNames = ["soy", "egg", "milk", "fish", "peanut", "shellfish", "treeNut", "gluten", "sesame"]
    const toReturn = []
    for (let i = 0; i < allergens.length; i++) {
        if (allergens[i]) {
            toReturn.push(allergenNames[i])
        }
    }
    return toReturn
}