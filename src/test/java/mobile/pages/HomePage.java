package mobile.pages;

import org.openqa.selenium.By;
// import org.openqa.selenium.Platform;

import io.appium.java_client.AppiumDriver;

public class HomePage {

    private final AppiumDriver driver;

    public HomePage(AppiumDriver driver, String platform) {
        this.driver = driver;
    }

    private final By enableNotifSplashScreenTitleText = By.xpath("//android.widget.TextView[@text='2']");
    private final By enableNotifSplashScreenContentText = By.xpath("//*[@resource-id='infoDialogContent']");
    private final By enableNotifSplashScreenButton = By
            .xpath("//*[@resource-id='buttonTitle']//android.widget.Button");
    private final By enableNotifSplashScreenSkipText = By
            .xpath("//*[@resource-id='buttonTitle']");
    private final By cryptoSplashScreenTitleText = By
            .xpath("//android.widget.TextView[@text='Brilliant, your wallet is ready!']");
    private final By cryptoSplashScreenBuyCryptoButton = By
            .xpath("//android.widget.TextView[@text='Buy Crypto']");
    private final By cryptoSplashScreenDepositCryptoButton = By
            .xpath("//android.widget.TextView[@text='Deposit Crypto']");
    private final By cryptoSplashScreenSkipText = By
            .xpath("//android.widget.TextView[@text='Skip, I'll do it later']");
    private final By homeWalletNameText = By
            .xpath("//*[@resource-id='topBarWalletName']");
    private final By homeMainBalanceText = By
            .xpath("//*[@resource-id='mainBalance']");
    private final By homeSendButton = By
            .xpath("//*[@resource-id='HomeSendButton']");
    private final By homeReceiveButton = By
            .xpath("//*[@resource-id='HomeReceiveButton']");
    private final By homeBuyButton = By
            .xpath("//*[@resource-id='HomeBuyButton']");
    private final By homeSellButton = By
            .xpath("//*[@resource-id='HomeSellButton']");

    // Actions
    public void enableNotifSplashScreenTitleDisplayed() {
        driver.findElement(enableNotifSplashScreenTitleText).isDisplayed();
    }

    public void enableNotifSplashScreenContentDisplayed() {
        driver.findElement(enableNotifSplashScreenContentText).isDisplayed();
    }

    public void enableNotifSplashScreenButtonDisplayed() {
        driver.findElement(enableNotifSplashScreenButton).isDisplayed();
    }

    public void enableNotifSplashScreenSkipTextDisplayed() {
        driver.findElement(enableNotifSplashScreenSkipText).isDisplayed();
    }

    public void cryptoSplashScreenTitleDisplayed() {
        driver.findElement(cryptoSplashScreenTitleText).isDisplayed();
    }

    public void cryptoSplashScreenBuyCryptoButtonDisplayed() {
        driver.findElement(cryptoSplashScreenBuyCryptoButton).isDisplayed();
    }

    public void cryptoSplashScreenDepositCryptoButtonDisplayed() {
        driver.findElement(cryptoSplashScreenDepositCryptoButton).isDisplayed();
    }

    public void cryptoSplashScreenSkipTextDisplayed() {
        driver.findElement(cryptoSplashScreenSkipText).isDisplayed();
    }

    public void successfullyLandToEnableNotificationSplashScreen() {
        enableNotifSplashScreenTitleDisplayed();
        enableNotifSplashScreenContentDisplayed();
        enableNotifSplashScreenButtonDisplayed();
        enableNotifSplashScreenSkipTextDisplayed();
    }

    public void clickSkipEnableNotifSplashScreenText() {
        driver.findElement(enableNotifSplashScreenSkipText).click();
    }

    public void successfullyLandToCryptoSplashScreen() {
        cryptoSplashScreenTitleDisplayed();
        cryptoSplashScreenBuyCryptoButtonDisplayed();
        cryptoSplashScreenDepositCryptoButtonDisplayed();
        cryptoSplashScreenSkipTextDisplayed();
    }

    public void clickSkipCryptoSplashScreenText() {
        driver.findElement(cryptoSplashScreenSkipText).click();
    }

    public void homeWalletNameTextDisplayed() {
        driver.findElement(homeWalletNameText).isDisplayed();
    }

    public void homeMainBalanceDisplayed() {
        driver.findElement(homeMainBalanceText).isDisplayed();
    }

    public void homeSendButtonDisplayed() {
        driver.findElement(homeSendButton).isDisplayed();
    }

    public void homeReceivedButtonDisplayed() {
        driver.findElement(homeReceiveButton).isDisplayed();
    }

    public void homeBuyButtonDisplayed() {
        driver.findElement(homeBuyButton).isDisplayed();
    }

    public void homeSellButtonDisplayed() {
        driver.findElement(homeSellButton).isDisplayed();
    }

    public void successfullyLandedOnHomepage() {
        homeWalletNameTextDisplayed();
        homeMainBalanceDisplayed();
        homeSendButtonDisplayed();
        homeReceivedButtonDisplayed();
        homeBuyButtonDisplayed();
        homeSellButtonDisplayed();
    }

}
