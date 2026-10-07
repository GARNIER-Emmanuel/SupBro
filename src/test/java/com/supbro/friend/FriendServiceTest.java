package com.supbro.friend;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import com.supbro.friend.dto.CreateFriendRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendServiceTest {
    @Mock
    FriendRepository repository;

    FriendService service;

    @BeforeEach
    void setUp() {
        service = new FriendService(repository, new FriendMapper());
    }

    @Test
    void createsFriendAndReturnsSavedEntity() {
        Friend saved = new Friend();
        saved.setFirstName("Lucas enregistré");
        when(repository.save(any(Friend.class))).thenReturn(saved);

        var response = service.create(new CreateFriendRequest(
                "Lucas", "Martin", null, null, "Sport"));

        var captor = ArgumentCaptor.forClass(Friend.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getFirstname()).isEqualTo("Lucas");
        assertThat(captor.getValue().getLastName()).isEqualTo("Martin");
        assertThat(captor.getValue().getNotes()).isEqualTo("Sport");
        assertThat(response.firstname()).isEqualTo("Lucas enregistré");
    }

    @Test
    void listsFriends() {
        Friend friend = new Friend();
        friend.setFirstName("Lucas");
        when(repository.findAll(Sort.by("id"))).thenReturn(List.of(friend));

        assertThat(service.findAll()).extracting(r -> r.firstname())
                .containsExactly("Lucas");
    }

    @Test
    void returnsEmptyList() {
        when(repository.findAll(Sort.by("id"))).thenReturn(List.of());
        assertThat(service.findAll()).isEmpty();
    }

}
