package qumu;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.testng.*;
import io.restassured.response.*;
import io.cucumber.java.Scenario;
import io.cucumber.datatable.DataTable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
public class APIStepDefinition {
    private Response response;
    private int totalUsers;
    private int totalUsersID;
    private int totalPages;
    private Scenario scenario;

    private final String BASE_URL = LoadProp.getproperty("apiURL");

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
    }


    @Given("I get the default list of users for on 1st page")
    public void i_get_the_default_list_of_users_for_on_1st_page() {
        response = given()
                .when()
                .get(BASE_URL + "/users?page=1")
                .then()
                .statusCode(200)
                .extract()
                .response();
        totalUsers = response.jsonPath().getInt("total");
        totalPages = response.jsonPath().getInt("total_pages");
        scenario.log("Total Users = " + totalUsers);
        scenario.log("Total Pages = " + totalPages);
    }

    @When("I get the list of all users within every page")
    public void i_get_the_list_of_all_users_within_every_page() {
        totalUsersID = 0;
        for (int i = 1; i <= totalPages; i++) {
            Response pageResponse = given()
                    .when()
                    .get(BASE_URL +"/users?page=" + i)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            int userIDsOnPage = pageResponse.jsonPath().getList("data.id").size();
            totalUsersID += userIDsOnPage;
            scenario.log("List of Users on page " + i + " = " + userIDsOnPage);

        }
    }

    @Then("I should see total users count equals the number of user ids")
    public void i_should_see_total_users_count_equals_the_number_of_user_ids() {
        scenario.log("Total Users = " + totalUsers);
        scenario.log("Total User ID's = " + totalUsersID);
        Assert.assertEquals(totalUsersID, totalUsers, "Total users do not match the expected total users.");
    }


    @Given("I make a search for user {int}")
    public void i_make_a_search_for_user(int userId) {
        response = given()
                .when()
                .get(BASE_URL +"/users/" + userId)
                .then()
                .extract()
                .response();
    }

    @Then("I should see the following user data")
    public void i_should_see_the_following_user_data(DataTable dataTable) {
        String expectedEmail = dataTable.cell(1, 1);
        String actualEmail = response.jsonPath().getString("data.email");
        scenario.log("Expected User data Email: " + expectedEmail );
        scenario.log("User data Email from response: " + actualEmail );
        Assert.assertEquals(actualEmail, expectedEmail, "Email does not match");


    }
    @Then("I receive error code {int} in response")
    public void i_receive_error_code_in_response(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Error code");
        scenario.log("Error code = " + response.getStatusCode());
    }

    @Given("I create a user with following {string} {string}")
    public void i_create_a_user_with_following(String name, String job) {
        response = given()
                .header("Content-Type", "application/json")
                .body("{ \"name\": \"" + name + "\", \"job\": \"" + job + "\" }")
                .when()
                .post(BASE_URL +"/api/users")
                .then()
                .statusCode(201)
                .extract()
                .response();
        scenario.log("Created user with name: " + name + " and job: " + job);
    }

    @Then("response should contain the following data")
    public void response_should_contain_the_following_data(DataTable dataTable) {
        String expectedName = dataTable.cell(1, 0);
        String expectedJob = dataTable.cell(1, 1);
        String actualName = response.jsonPath().getString("name");
        String actualJob = response.jsonPath().getString("job");
        String userId = response.jsonPath().getString("id");
        String createdAt = response.jsonPath().getString("createdAt");

        Assert.assertEquals(actualName, expectedName, "Name does not match");
        Assert.assertEquals(actualJob, expectedJob, "Job does not match");
        Assert.assertNotNull(userId, "ID is missing");
        Assert.assertNotNull(createdAt, "createdAt timestamp is missing");

        scenario.log("Created user: Name=" + actualName + ", Job=" + actualJob);
        scenario.log("User ID: " + userId);
        scenario.log("Created At: " + createdAt);
    }




    @Given("I login successfully with the following data")
    public void i_login_successfully_with_the_following_data(DataTable dataTable) {
        String email = dataTable.cell(1, 0);
        String password = dataTable.cell(1, 1).isEmpty() ? null : dataTable.cell(1, 1);
        response = given()
                .header("Content-Type", "application/json")
                .body("{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }")
                .when()
                .post(BASE_URL +"/login")
                .then()
                .extract()
                .response();
    }

    @Then("I should get a response code of {int}")
    public void i_should_get_a_response_code_of(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Status code");
        scenario.log("Response Code " + response.getStatusCode());
    }




    @Given("I login unsuccessfully with the following data")
    public void i_login_unsuccessfully_with_the_following_data(DataTable dataTable) {
        String email = dataTable.cell(1, 0);
        String password = dataTable.cell(1, 1);

        if (password == null || password.trim().isEmpty() || password.equals("[empty]")) {
            password = null;
        }

        response = given()
                .header("Content-Type", "application/json")
                .body(password == null ? "{ \"email\": \"" + email + "\" }" : "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }")
                .when()
                .post(BASE_URL +"/login")
                .then()
                .extract()
                .response();


    }

    @And("I should see the following response message:")
    public void i_should_see_the_following_response_message(DataTable dataTable) {
        String expectedMessage = dataTable.cell(1, 0).replace("\"", "");
        String actualMessage = response.jsonPath().getString("error");

        Assert.assertEquals(actualMessage, expectedMessage, "Error message does not match");
        scenario.log("Error Message: " + actualMessage);
    }

    @Given("I wait for the user list to load")
    public void i_wait_for_the_user_list_to_load() {
        response = given()
                .when()
                .get(BASE_URL +"/users?delay=3")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Then("I should see that every user has a unique id")
    public void i_should_see_that_every_user_has_a_unique_id() {
        List<Integer> userIds = response.jsonPath().getList("data.id");
        scenario.log("Original List of User IDs: " + userIds);

        Set<Integer> uniqueIds = new HashSet<>(userIds);
        scenario.log("Unique Set of User IDs: " + uniqueIds);

        Assert.assertEquals(uniqueIds.size(), userIds.size(), "Duplicate user IDs found");

    }

}
