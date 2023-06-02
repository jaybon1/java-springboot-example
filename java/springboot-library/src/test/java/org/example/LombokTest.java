package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LombokTest {

    @Test
    public void 롬복() {
        // given
        User user = new User();

        // when
        user.setIdx(1);
        user.setName("test");

        // then
        Assertions.assertEquals(1, user.getIdx());
        Assertions.assertEquals("test", user.getName());
    }
}
