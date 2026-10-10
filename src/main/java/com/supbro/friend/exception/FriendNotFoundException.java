package com.supbro.friend.exception;

public class FriendNotFoundException extends RuntimeException {
    public FriendNotFoundException(Long id) {
        super("Ami" + id + "introuvable");
    }
}
