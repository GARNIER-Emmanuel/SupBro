package com.supbro.friend;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.supbro.friend.dto.CreateFriendRequest;
import com.supbro.friend.dto.FriendResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    private final FriendService service;

    public FriendController(FriendService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FriendResponse create(@Valid @RequestBody CreateFriendRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<FriendResponse> findAll() {
        return service.findAll();
    }
}
