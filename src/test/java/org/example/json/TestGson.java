package org.example.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.example.vo.Foo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGson {

    @Test
    public void object_string_object_with_gson(){
        Gson gson = new Gson();
        Foo foo = new Foo(1,"first");

        String jsonStr = gson.toJson(foo);
        Foo result = gson.fromJson(jsonStr, Foo.class);
        assertEquals(foo.getId(),result.getId());
    }

//    @Test
//    public void obj_to_json_with_gson() {
//        Gson gson = new Gson();
//        Foo foo = new Foo(1,"first");
//
//        JsonElement json = gson.toJsonTree(foo);
//
//        assertEquals("first", json.getAsJsonObject("name").getAsString());
//    }
}
