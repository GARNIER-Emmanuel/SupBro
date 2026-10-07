package com.supbro.friend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateFriendRequest(
        @NotBlank(message = "name is requierd") @Size(max = 255) String firstname,
        @Size(max = 255) String lastname,
        @Size(max = 255) String nickname,
        LocalDate birthday,
        @Size(max = 1000) String notes) {

}
