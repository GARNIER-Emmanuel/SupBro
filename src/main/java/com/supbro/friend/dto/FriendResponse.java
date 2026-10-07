
package com.supbro.friend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FriendResponse(
        Long id,
        String firstname,
        String lastname,
        String nickname,
        LocalDate birthday,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
