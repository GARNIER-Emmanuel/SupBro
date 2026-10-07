package com.supbro.friend;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import com.supbro.PostgresTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class FriendApiIT extends PostgresTestSupport {
    @LocalServerPort
    int port;
    @Autowired
    FriendRepository repository;
    final HttpClient client = HttpClient.newHttpClient();

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    HttpResponse<String> post(String json) throws Exception {
        var request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + port + "/api/friends"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void createsAndListsPersistedFriend() throws Exception {
        var created = post("""
                {"firstname":"Lucas","lastname":"Martin","notes":"Sport"}
                """);
        assertThat(created.statusCode()).isEqualTo(201);
        assertThat(repository.count()).isEqualTo(1);
        var saved = repository.findAll().getFirst();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getFirstname()).isEqualTo("Lucas");
        assertThat(saved.getNotes()).isEqualTo("Sport");

        var request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + port + "/api/friends"))
                .timeout(Duration.ofSeconds(10)).GET().build();
        var listed = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(listed.statusCode()).isEqualTo(200);
        assertThat(listed.body()).contains("Lucas", "Martin", "Sport");
    }

    @Test
    void rejectsBlankFirstname() throws Exception {
        assertThat(post("{\"firstname\":\"   \"}").statusCode()).isEqualTo(400);
        assertThat(repository.count()).isZero();
    }

    @Test
    void rejectsTooLongNotes() throws Exception {
        var json = "{\"firstname\":\"Lucas\",\"notes\":\"" + "a".repeat(1001) + "\"}";
        assertThat(post(json).statusCode()).isEqualTo(400);
        assertThat(repository.count()).isZero();
    }
}