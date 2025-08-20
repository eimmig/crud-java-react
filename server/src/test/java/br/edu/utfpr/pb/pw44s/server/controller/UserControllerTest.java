package br.edu.utfpr.pb.pw44s.server.controller;

import br.edu.utfpr.pb.pw44s.server.model.User;
import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldSaveUserCorrectly() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("Test Display");
        user.setPassword("test-p4ssword");

        ResponseEntity<Object> response = restTemplate.postForEntity("/users", user, Object.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldSaveAndCountUsersCorrectly() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("Updated Display");
        user.setPassword("updated-p4ssword");

        restTemplate.postForEntity("/users", user, Object.class);

        Assertions.assertThat(userRepository.count()).isEqualTo(1);
    }
}
