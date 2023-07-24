package com.epam.esm.veiw.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Pattern;


/**
 * BaseDTO is the superclass to all DtoRequest entities
 */
@Data
@NoArgsConstructor
public abstract class BaseDTO {
    @Nullable
    private long id;
    @NonNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String name;
}
