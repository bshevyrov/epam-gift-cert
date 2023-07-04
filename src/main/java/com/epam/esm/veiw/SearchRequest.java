package com.epam.esm.veiw;

import java.util.Optional;

/**
 * SearchRequest is the data class, which used for transferring search parameters.
 */
public class SearchRequest {

    public SearchRequest() {
    }

    private Optional<String> tagName = Optional.empty();
    private Optional<String> giftCertificateName = Optional.empty();
    private Optional<String> description = Optional.empty();
    private String sortField = "name";
    private String sortType = "asc";

    public Optional<String> getTagName() {
        return tagName;
    }

    public void setTagName(Optional<String> tagName) {
        this.tagName = tagName;
    }

    public Optional<String> getGiftCertificateName() {
        return giftCertificateName;
    }

    public void setGiftCertificateName(Optional<String> giftCertificateName) {
        this.giftCertificateName = giftCertificateName;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
