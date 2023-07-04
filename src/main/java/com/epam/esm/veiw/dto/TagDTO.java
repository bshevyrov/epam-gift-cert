package com.epam.esm.veiw.dto;

/**
 * TagDTO is the data class, which used for data transportation.
 **/
public class TagDTO extends BaseDTO {
    public TagDTO() {
    }

    public TagDTO(String name) {
        super();
        super.setName(name);
    }
}
