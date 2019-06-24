package io.project.app.dto;

/**
 *
 * @author armena
 */
public enum PackageType {

    Pallets("Pallets", "Pallets"),
    Boxes("Boxes", "Boxes"),
    Cartons("Cartons", "Cartons"),
    Crates("Crates", "Crates"),
    Drums("Drums", "Drums"),
    Bags("Bags", "Bags"),
    Bales("Bales", "Bales"),
    Bundles("Bundles", "Bundles"),
    Cans("Cans", "Cans"),
    Carboys("Carboys", "Carboys"),
    Carpets("Carpets", "Carpets"),
    Cases("Cases", "Cases"),
    Coils("Coils", "Coils"),
    Cylinders("Cylinders", "Cylinders"),
    Other("Other", "Other");

    private final String key;

    private final String value;

    PackageType(String key, String value) {
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
