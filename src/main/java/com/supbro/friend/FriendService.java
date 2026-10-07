package com.supbro.friend;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supbro.friend.dto.CreateFriendRequest;
import com.supbro.friend.dto.FriendResponse;

@Service
public class FriendService {
    private final FriendRepository repository;
    private final FriendMapper mapper;

    public FriendService(FriendRepository repository, FriendMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public FriendResponse create(CreateFriendRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Transactional(readOnly = true)
    public List<FriendResponse> findAll() {
        return repository.findAll(Sort.by("id")).stream()
                .map(mapper::toResponse)
                .toList();

    }

}
