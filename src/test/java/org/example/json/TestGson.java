package org.example.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.example.vo.Foo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGson {

    @Test
    public void gson으로문자열변환_후_다시객체로변환(){
        Gson gson = new Gson();
        Foo foo = new Foo(1,"first");

        String jsonStr = gson.toJson(foo);
        Foo result = gson.fromJson(jsonStr, Foo.class);
        assertEquals(foo.getId(),result.getId());
    }

//    @Test
//    public void gson으로json객체변환() {
//        Gson gson = new Gson();
//        Foo foo = new Foo(1,"first");
//
//        JsonElement json = gson.toJsonTree(foo);
//
//        assertEquals("first", json.getAsJsonObject("name").getAsString());
//    }
}
