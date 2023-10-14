import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageObjects.ClientInfoForm;
import pageObjects.ConfirmOrderBanner;
import pageObjects.MainPageYandexScooter;
import pageObjects.RentInfoForm;


@RunWith(Parameterized.class)
public class OrderTest extends YandexScooterTest {

    private final String firstName;
    private final String secondName;
    private final String address;
    private final String metroStation;
    private final String phoneNumber;

    private final String rentDate;
    private final String comment;


    public OrderTest(String firstName, String secondName, String address,
                     String metroStation, String phoneNumber, String rentDate, String comment) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.rentDate = rentDate;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getTextData() {
        return new Object[][] {
                {"Костя", "Костин", "Новоясеневский проспект, 16", "Ясенево", "89092334432", "11.11.2023", "Привет!"},
                {"Вася", "Васин", "Московская, 12", "Баррикадная", "89768985647", "12.11.2023", "123"},
                {"Яндекс", "Самокат", "Пушная, 3", "Станция", "55555", "01.10.2023", "Комментарий"},
        };
    }


    @Test
    public void orderByHeaderButtonTest() {
        MainPageYandexScooter mainPage = new MainPageYandexScooter(getDriver());
        mainPage.clickCookieAcceptButton();
        mainPage.clickHeaderOrderButton();

        ClientInfoForm clientInfoForm = new ClientInfoForm(getDriver());
        clientInfoForm.fillCustomerInfoForm(firstName, secondName, address, metroStation, phoneNumber);

        RentInfoForm rentInfoForm = new RentInfoForm(getDriver());
        rentInfoForm.fillRentInfoForm(rentDate, comment);

        ConfirmOrderBanner confirmOrderBanner = new ConfirmOrderBanner(getDriver());
        confirmOrderBanner.confirmOrder();

        Assert.assertTrue("Заказ не оформлен!",
                confirmOrderBanner.isOrderProcessedMessageDisplayed());
    }

    @Test
    public void orderByMiddleSectionButtonTest() {
        MainPageYandexScooter mainPage = new MainPageYandexScooter(getDriver());
        mainPage.clickCookieAcceptButton();
        mainPage.scrollToMiddleSectionButton();
        mainPage.clickMiddleSectionOrderButton();

        ClientInfoForm clientInfoForm = new ClientInfoForm(getDriver());

        Assert.assertTrue("Форма ввода данных заказа не открылась!",
                clientInfoForm.isClientInfoFormDisplayed());
    }
}