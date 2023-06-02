package org.example;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SqlTest {

    @Test
    public void 마이바티스() throws IOException {
        // given
        SqlSession sqlSession = DBConn.getSqlSession();
        MyMapper myMapper = sqlSession.getMapper(MyMapper.class);

        // when
        Integer result = myMapper.getOne(1);

        // then
        Assertions.assertEquals(1, result);
    }
}
