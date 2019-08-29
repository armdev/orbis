package io.project.app.constant.data;

/**
 *
 * @author armena
 */
public enum AccountType {

    Shipper("Shipper", "Shipper"),
    Career("Career",  "Career");
    

    private final String key;
    private final String value;

    AccountType(String key, String value) {
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
