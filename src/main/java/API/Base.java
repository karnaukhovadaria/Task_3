package API;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static API.Constants.MAIN_PAGE;
import static io.restassured.http.ContentType.JSON;

public class Base {

public static RequestSpecification spec() {
    return new RequestSpecBuilder()
            .setContentType(JSON)
            .setBaseUri(MAIN_PAGE)
            .log(LogDetail.ALL)
            .build();
}

}
