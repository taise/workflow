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

public class AuthTest extends WithApplication {
  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
    Ebean.save((List) Yaml.load("testData/users.yml"));
  }

  @Test
  public void authenticated() {
    Result result = callAction(
        controllers.routes.ref.Requests.index(),
        fakeRequest().withSession("email", "alice@email.com")
        );
    assertEquals(200, status(result));
  }

  @Test
  public void notAuthenticated() {
    Result result = callAction(
        controllers.routes.ref.Requests.index(),
        fakeRequest()
        );
    assertEquals(303, status(result));
    assertEquals("/login", header("Location", result));
  }
}
