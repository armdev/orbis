package io.project.app.constant.data;

/**
 *
 * @author armena
 */
public enum EquipmentType {

    Flatbed("Flatbed", "Flatbed"),
    StretchedFlatbed("Stretched Flatbed", "Stretched Flatbed"),
    Lowboy("Lowboy", "Lowboy"),
    FlatbedDouble("FlatbedDouble", "Flatbed Double"),
    RemovableGooseneck("RemovableGooseneck", "Removable Gooseneck"),
    AutoCarrier("AutoCarrier", "Auto Carrier"),
    Reefer("Reefer", "Reefer"),
    DryVan("DryVan", "Dry Van"),
    AirRideVan("AirRideVan", "Air Ride Van"),
    VanDouble("VanDouble", "VanDouble"),
    Van("Van", "Van"),
    StepDeck("StepDeck", "Step Deck"),
    Other("Other", "Other");

    private final String key;
    private final String value;

    EquipmentType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
