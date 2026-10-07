package com.supbro.friend;

import org.springframework.stereotype.Component;

import com.supbro.friend.dto.CreateFriendRequest;
import com.supbro.friend.dto.FriendResponse;

@Component
public class FriendMapper {

    public Friend toEntity(CreateFriendRequest request) {
        Friend friend = new Friend();
        friend.setFirstName(request.firstname());
        friend.setLastName(request.lastname());
        friend.setNickName(request.nickname());
        friend.setBirthday(request.birthday());
        friend.setNotes(request.notes());
        return friend;
    }

    public FriendResponse toResponse(Friend friend) {
        return new FriendResponse(
                friend.getId(),
                friend.getFirstname(),
                friend.getLastName(),
                friend.getNickName(),
                friend.getBirthday(),
                friend.getNotes(),
                friend.getCreatedAt(),
                friend.getUpdatedAt());
    }
}
