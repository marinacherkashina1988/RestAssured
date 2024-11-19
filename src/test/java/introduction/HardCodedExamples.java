package introduction;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class HardCodedExamples {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    RequestSpecification request;
    Response response;
    public static String employee_id;

    @Test
    public void A_createAnEmployee (){
       request = given().
               header("Content-Type", "application/json").
               header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MzE5ODIxOTcsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTczMjAyNTM5NywidXNlcklkIjoiNjk3OSJ9.JX4DBfBkHFp3Y_PgptHFxGyOENx-hk1Ywt3zvZgNPto").
               body("{\n" +
                "  \"emp_firstname\": \"Christina\",\n" +
                "  \"emp_lastname\": \"Jason\",\n" +
                "  \"emp_middle_name\": \"Maria\",\n" +
                "  \"emp_gender\": \"F\",\n" +
                "  \"emp_birthday\": \"2000-11-05\",\n" +
                "  \"emp_status\": \"employeed\",\n" +
                "  \"emp_job_title\": \"Software Engineer\"\n" +
                "}");

       response = request.when().post("/createEmployee.php");
       response.prettyPrint();

       response.then().assertThat().statusCode(201);
       response.then().assertThat().body("Message",equalTo("Employee Created"));
       response.then().assertThat().body("Employee.emp_firstname", equalTo("Christina"));

       employee_id = response.jsonPath().getString("Employee.employee_id");
    }

    @Test
    public void B_getEmployee(){
        request = given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MzE5ODIxOTcsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTczMjAyNTM5NywidXNlcklkIjoiNjk3OSJ9.JX4DBfBkHFp3Y_PgptHFxGyOENx-hk1Ywt3zvZgNPto").
                queryParam("employee_id", employee_id);

        response = request.when().get("/getOneEmployee.php");
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void C_updateEmployee(){
        request=given().
                header("Content-Type", "application/json").
                header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MzE5ODIxOTcsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTczMjAyNTM5NywidXNlcklkIjoiNjk3OSJ9.JX4DBfBkHFp3Y_PgptHFxGyOENx-hk1Ywt3zvZgNPto").
                queryParam("employee_id", employee_id).
                body("{\n" +
                        "  \"employee_id\": \""+employee_id+"\",\n" +
                        "  \"emp_firstname\": \"Diana\",\n" +
                        "  \"emp_lastname\": \"Corsa\",\n" +
                        "  \"emp_middle_name\": \"Ms\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"2004-11-11\",\n" +
                        "  \"emp_status\": \"Intern\",\n" +
                        "  \"emp_job_title\": \"IT Support\"\n" +
                        "}");

        response = request.when().put("/updateEmployee.php");
        response.then().statusCode(200);
        response.prettyPrint();
    }
}
