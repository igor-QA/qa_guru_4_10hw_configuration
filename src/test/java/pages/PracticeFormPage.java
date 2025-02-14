package pages;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import tests.TestBase;

import java.io.File;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utils.RandomUtils.*;

public class PracticeFormPage extends TestBase {
    Faker faker = new Faker();
    String name = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = getRandomEmail();
    String gender = getRandomGender();
    String hobby = getRandomHobby();
    String phoneNumber = faker.phoneNumber().subscriberNumber(10);
    String monthOfBirth = getRandomMonth();
    String yearOfBirth = getRandomYear();
    int dayOfBirth = getRandomInt(10,29);
    String subject = "Chemistry";
    String currentAddress = faker.address().fullAddress();
    String state = "Haryana";
    String city = "Karnal";
    String filePath="./src/test/resources/pict.jpg";

    @Step("Open practice form")
    public void openPage() {
        open("https://demoqa.com/automation-practice-form");
        $(".main-header").shouldHave(text("Practice Form"));
    }

    @Step("Fill practice form")
    public void fillForm() {
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        setBirthDate(yearOfBirth, monthOfBirth, dayOfBirth);
        $("#subjectsInput").setValue("c");
        $(byText(subject)).click();
        $(byText(hobby)).click();
        $("#uploadPicture").uploadFile(new File(filePath));
        $("#currentAddress").setValue(currentAddress);
        $("#state").click();
        $(byText(state)).click();
        $("#city").click();
        $(byText(city)).click();
        $("#submit").click();
    }

    @Step("Set date of birth")
        public void setBirthDate(String year, String month, int day){
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(year);
        $(".react-datepicker__day--0" + day).click();
    }

    @Step("Check successful form submit")
    public void checkData() {
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $$("tbody tr").filterBy(text("Student name")).shouldHave(texts(name + " " + lastName));
        $$("tbody tr").filterBy(text("Student Email")).shouldHave(texts(email));
        $$("tbody tr").filterBy(text("Gender")).shouldHave(texts(gender));
        $$("tbody tr").filterBy(text("Mobile")).shouldHave(texts(phoneNumber));
        $$("tbody tr").filterBy(text("Date of Birth")).shouldHave(texts(dayOfBirth+" "+monthOfBirth+","+yearOfBirth));
        $$("tbody tr").filterBy(text("Subjects")).shouldHave(texts(subject));
        $$("tbody tr").filterBy(text("Hobbies")).shouldHave(texts(hobby));
        $$("tbody tr").filterBy(text("Picture")).shouldHave(texts("pict.jpg"));
        $$("tbody tr").filterBy(text("Address")).shouldHave(texts(currentAddress));
        $$("tbody tr").filterBy(text("State and City")).shouldHave(texts(state+" "+city));
       //String pngTestAssert = screenshot("scr_assert_test");
        $("button#closeLargeModal").click();
        $("#example-modal-sizes-title-lg").shouldBe(hidden);
        //String pngTestAssertClose = screenshot("scr_assertClose_test");
    }
}
