package org.anonmes.messenger.dto;

import lombok.Data;

/**
 * Class used for requests for updating user's name
 */
@Data
public class UserUpdateNameDTO {
    private Long id;
    private String name;
}
