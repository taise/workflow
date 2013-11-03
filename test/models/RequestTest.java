package models;

import models.Request;
import org.junit.*;
import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Date;

import play.test.WithApplication;
import static play.test.Helpers.*;

public class RequestTest extends WithApplication {
  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
  }

  @Test
  public void tryNewRequest() {
    String title = "Request test";
    String description = "tryNewRequest";
    String requestDate = "2013-11-03";

    Request req = new Request(title, description, requestDate);

    assertEquals(title, req.title);
    assertEquals(description, req.description);
    assertEquals(requestDate, req.requestDate);

    //TODO: assertDatesAlmostEqual(new Date(), req.createdAt);
    assertThat(new Date(), greaterThanOrEqualTo(req.createdAt));
    assertThat(new Date(), greaterThanOrEqualTo(req.updatedAt));
  }
}
