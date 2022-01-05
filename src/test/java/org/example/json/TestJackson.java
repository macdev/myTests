package org.example.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.vo.Foo;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestJackson {

    @Test
    public void jackson으로문자열변환_후_다시객체로변환()
            throws IOException {
        Foo foo = new Foo(1, "first");
        ObjectMapper mapper = new ObjectMapper();

        String jsonStr = mapper.writeValueAsString(foo);
        Foo result = mapper.readValue(jsonStr, Foo.class);
        assertEquals(foo.getId(), result.getId());
    }

    @Test
    public void jackson으로json객체변환() {

    }
}
