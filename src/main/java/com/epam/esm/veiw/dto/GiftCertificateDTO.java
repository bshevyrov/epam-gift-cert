package com.epam.esm.veiw.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GiftCertificateDTO is the data class, which used for data transportation .
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GiftCertificateDTO extends BaseDTO {
    private String description;
    private double price;
    private int duration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;
    private List<TagDTO> tags;
}
