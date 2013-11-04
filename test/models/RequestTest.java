package models;

import models.Request;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import java.util.Date;
import java.util.List;
import play.libs.Yaml;

import play.test.WithApplication;
import static play.test.Helpers.*;

public class RequestTest extends WithApplication {
  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
  }

  @Test
  public void constructor() {
    String title = "Request test";
    String description = "tryNewRequest";
    String targetDate = "2013-11-03";

    Request req = new Request(title, description, targetDate);

    assertEquals(title, req.title);
    assertEquals(description, req.description);
    assertEquals(targetDate, req.targetDate);

    //TODO: assertDatesAlmostEqual(new Date(), req.createdAt);
    assertThat(new Date(), greaterThanOrEqualTo(req.createdAt));
    assertThat(new Date(), greaterThanOrEqualTo(req.updatedAt));
  }

  @Test
  public void all() {
    Ebean.save((List) Yaml.load("testData/requests.yml"));
    List<Request> requests = Request.all();
    assertEquals(3, requests.size());
    assertEquals("Request test", requests.get(0).title);
  }

  @Test
  public void count() {
    Ebean.save((List) Yaml.load("testData/requests.yml"));
    assertSame(3, Request.count());
  }
}
