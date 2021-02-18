package org.load;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import org.junit.Test;

public class LoadTests {

  @Test
  public void checkMeanAndMaxTime() {

    Data responseDetails = performRequestToAPI("https://api.covid19api.com/summary", 50);

    assertThat(responseDetails.getAvg()).as("mean delay time").isLessThan(400);
    assertThat(responseDetails.getMax()).as("max delay time").isLessThan(600);
  }



  public Data performRequestToAPI(String endPoint, int requestNumber) {
    ArrayList<Long> delay = new ArrayList<Long>();
    double sum = 0;

    for (int i = 0; i < requestNumber; i++) {
      delay.add(given().get(endPoint).then().extract().time());
      sum += delay.get(i);
    }

    return new Data(sum / delay.size(), delay.size());
  }
}
