package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JacksonTest {

    @Test
    public void 잭슨() throws JsonProcessingException {
        // given
        User testUser = new User(123, "test");

        // when
        String json = JsonParser.toJson(testUser);
        User user = JsonParser.fromJson(json, User.class);

        // then
        Assertions.assertEquals(json.charAt(0), '{');
        Assertions.assertEquals(testUser, user);
    }
}
