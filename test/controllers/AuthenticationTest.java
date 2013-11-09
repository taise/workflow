package controllers;

import org.junit.*;
import java.util.*;
import play.libs.Yaml;

import play.mvc.*;
import play.test.*;
import com.avaje.ebean.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

public class AuthenticationTest {
  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
    Ebean.save((List) Yaml.load("testData/users.yml"));
  }

  @Test
  public void callLoginForm() {
    Result result = callAction(
        controllers.routes.ref.Authentication.loginForm()
        );
    assertResultOk(result);
    assertThat(contentAsString(result)).contains("Email");
    assertThat(contentAsString(result)).contains("Password");
    assertThat(contentAsString(result)).contains("ログイン");
    assertNull(session(result).get("email"));
  }

  @Test
  public void successLogin() {
    Result result
      = loginRequest("alice@email.com", "alice1234!");

    assertEquals(303, status(result));
    assertNotNull(session(result).get("email"));
  }

  @Test
  public void wrongEmail() {
    Result result
      = loginRequest("alice1@email.com", "alice1234!");

    assertEquals(400, status(result));
    assertNull(session(result).get("email"));
    assertThat(contentAsString(result)).contains("alice1@email.com");
  }

  @Test
  public void wrongPassword() {
    Result result
      = loginRequest("alice@email.com", "12345678");

    assertEquals(400, status(result));
    assertNull(session(result).get("email"));
    assertThat(contentAsString(result)).contains("alice@email.com");
  }

  @Test
  public void nullEmail() {
    Result result
      = loginRequest(null, "alice1234!");
    
    assertEquals(400, status(result));
    assertNull(session(result).get("email"));
  }

  @Test
  public void nullPassword() {
    Result result
      = loginRequest("alice@email.com", null);
    
    assertEquals(400, status(result));
    assertNull(session(result).get("email"));
    assertThat(contentAsString(result)).contains("alice@email.com");
  }

  public void assertResultOk(Result result) {
    assertThat(status(result)).isEqualTo(OK);
    assertThat(contentType(result)).isEqualTo("text/html");
    assertThat(charset(result)).isEqualTo("utf-8");
  }

  public Map<String,String> defaultParams(String email, String password) {
    Map<String,String> params = new HashMap<String,String>();
    params.put("email", email);
    params.put("password", password);
    return params;
  }

  public Result loginRequest(String email, String password) {
    Map<String,String> params = defaultParams(email, password);
    Result result = callAction(
        controllers.routes.ref.Authentication.login(),
        fakeRequest().withFormUrlEncodedBody(params)
        );
    return result;
  }
}
