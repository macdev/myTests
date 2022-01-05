package org.example.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.vo.Address;
import org.example.vo.Employee;
import org.example.vo.Foo;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestJackson {


    //json 객체에서 값을 갱신 하거나 지우기
    @Test
    public void update_or_remove_from_json_node_with_jackson() throws Exception {
        //read json file data to String
        //byte[] jsonData = Files.readAllBytes(Paths.get("employee.txt"));
        byte[] jsonData = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("plain/json/employee.txt").toURI()));

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //read JSON like DOM Parser
        JsonNode rootNode = objectMapper.readTree(jsonData);
        ((ObjectNode) rootNode).put("testA", "testB");
        ((ObjectNode) rootNode).remove("role");
        System.out.println(rootNode.path("role"));
        assertEquals("testB", rootNode.path("testA").asText());
        assertTrue(rootNode.path("testA").isValueNode());
        //(rootNode.path("role").isValueNode());            //exception
        //assertTrue(rootNode.path("role").isBoolean());    //exception
        //assertTrue(rootNode.path("role").isContainerNode()); //exception
        //assertTrue(rootNode.path("role").isEmpty());      //exception
        //assertTrue(rootNode.path("role").isNull());       //exception
        assertFalse(rootNode.has("role"));
        assertTrue(rootNode.hasNonNull("testA"));
        assertEquals(JsonNodeType.OBJECT, rootNode.path("properties").getNodeType());
        assertEquals(JsonNodeType.ARRAY, rootNode.path("phoneNumbers").getNodeType());
        assertEquals(JsonNodeType.STRING, rootNode.path("testA").getNodeType());
        assertEquals(JsonNodeType.MISSING, rootNode.path("role").getNodeType());

        //갱신한 값을 파일에 쓸수 있다.
        //objectMapper.writeValue(new File("updated_json_str.txt"), rootNode);
    }

    //json 객체에서 특정한 값을 읽어 오기
    @Test
    public void read_json_object_as_key_with_jackson() throws Exception {
        //read json file data to String
        //byte[] jsonData = Files.readAllBytes(Paths.get("employee.txt"));
        byte[] jsonData = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("plain/json/employee.txt").toURI()));

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //read JSON like DOM Parser
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode idNode = rootNode.path("id");
        //System.out.println("id = " + idNode.asInt());
        assertEquals(idNode.asInt(), 123);

        JsonNode phoneNosNode = rootNode.path("phoneNumbers");
        Iterator<JsonNode> elements = phoneNosNode.elements();
        while (elements.hasNext()) {
            JsonNode phone = elements.next();
            //System.out.println("Phone No = " + phone.asLong());
            assertEquals(123456, phone.asLong());
            break;
        }

        assertEquals("95129", rootNode.path("address").path("zipcode").asText());
        assertEquals(95129, rootNode.path("address").path("zipcode").asInt());
        assertEquals(95129L, rootNode.path("address").path("zipcode").asLong());

        //아무값도 path 도 없는 경우 오류가 나는가?
        assertEquals("", rootNode.path("nothing").path("anything").asText());
        assertEquals(0, rootNode.path("nothing").path("anything").asInt());
    }

    //vo를 json스트링으로 변환하고 다시 vo로 변환
    @Test
    public void object_string_object_with_jackson()
            throws IOException {
        Foo foo = new Foo(1, "first");
        ObjectMapper mapper = new ObjectMapper();

        String jsonStr = mapper.writeValueAsString(foo);
        Foo result = mapper.readValue(jsonStr, Foo.class);
        assertEquals(foo.getId(), result.getId());
    }

    //json string 을 map 으로 변환 하는 두가지 방법
    @Test
    public void converting_json_map_with_jackson() throws IOException {
        //converting json to Map
        //String mapData = byte[] mapData = Files.readAllBytes(Paths.get("{\"name\":\"David\",\"role\":\"Manager\",\"city\":\"Los Angeles\"}"));

        String mapData = "{\"name\":\"David\",\"role\":\"Manager\",\"city\":\"Los Angeles\"}";

        // 1번, class 지정
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> myMap1 = objectMapper.readValue(mapData, HashMap.class);
        //System.out.println("Map is: "+myMap);
        assertEquals(myMap1.get("city"), "Los Angeles");

        // 2번, TypeReference 사용
        HashMap<String, String> myMap2 = objectMapper.readValue(mapData, new TypeReference<HashMap<String, String>>() {});
        //System.out.println("Map using TypeReference: "+myMap2);
        assertEquals(myMap2.get("name"), "David");

    }

    //json string -> vo.toString()
    //vo -> json string 비교
    @Test
    public void vo_string_vs_jsonobj_string_with_jackson() throws IOException, URISyntaxException {
        //read json file data to String
        byte[] jsonData = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("plain/json/employee.txt").toURI()));

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //convert json string to object
        Employee emp = objectMapper.readValue(jsonData, Employee.class);

        assertThat(emp.getCities().get(1), is("New York"));
        //System.out.println("Employee Object\n"+emp);

        //convert Object to json string
        Employee emp1 = createEmployee();
        //configure Object mapper for pretty print
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        //writing to console, can write to any output stream such as file
        StringWriter stringEmp = new StringWriter();
        objectMapper.writeValue(stringEmp, emp1);
        assertThat(stringEmp.toString(), containsString("BTM 1st Stage"));
        //System.out.println("Employee JSON is\n"+stringEmp);
    }

    private static Employee createEmployee() {

        Employee emp = new Employee();
        emp.setId(100);
        emp.setName("David");
        emp.setPermanent(false);
        emp.setPhoneNumbers(new long[] { 123456, 987654 });
        emp.setRole("Manager");

        Address add = new Address();
        add.setCity("Bangalore");
        add.setStreet("BTM 1st Stage");
        add.setZipcode(560100);
        emp.setAddress(add);

        List<String> cities = new ArrayList<>();
        cities.add("Los Angeles");
        cities.add("New York");
        emp.setCities(cities);

        Map<String, String> props = new HashMap<String, String>();
        props.put("salary", "1000 Rs");
        props.put("age", "28 years");
        emp.setProperties(props);

        return emp;
    }


}
