package com.epam.esm.veiw.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

/**
 * GiftCertificateDTO is the data class, which used for data transportation .
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GiftCertificateDTO extends BaseDTO {
    @NonNull
    @Pattern(regexp = "^(?! )[A-Za-z\\s]*$")
    private String description;
    @NonNull
    @Min(0)
    private double price;
    @NonNull
    @Min(1)
    private int duration;
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;
    @JsonProperty("tags")
    @Valid
    private List<TagDTO> tagDTOS;
}
