package com.supbro.friend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record CreateFriendRequest(
                @NotBlank(message = "name is requierd") String firstname,
                String lastname,
                String nickname,
                LocalDate birthday,
                String notes) {

}
