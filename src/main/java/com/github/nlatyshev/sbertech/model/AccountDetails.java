package com.github.nlatyshev.sbertech.model;

import java.util.Objects;

/**
 * @author nlatyshev on 16.06.2017.
 */
public class AccountDetails {
    private final String client;
    private final String assetType;

    public AccountDetails(String client, String assetType) {
        this.client = client;
        this.assetType = assetType;
    }

    public String getClient() {
        return client;
    }

    public String getAssetType() {
        return assetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountDetails that = (AccountDetails) o;
        return Objects.equals(client, that.client) &&
                Objects.equals(assetType, that.assetType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, assetType);
    }
}
