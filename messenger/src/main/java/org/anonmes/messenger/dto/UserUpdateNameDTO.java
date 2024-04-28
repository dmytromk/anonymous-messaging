package org.anonmes.messenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Class used for requests for updating user's name
 */
@Data
@Schema(description = "A JSON object containing user's id and his new name")
public class UserUpdateNameDTO {
    private Long id;
    private String name;
}
