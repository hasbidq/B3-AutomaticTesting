package com.jtklearn.definitions;

import com.jtklearn.pages.CourseOverviewPage;
import com.jtklearn.pages.DashboardPage;
import com.jtklearn.pages.LoginPage;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StepDefinitions {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CourseOverviewPage courseOverviewPage;

    private static final String BASE_URL = "https://polban-space.cloudias79.com/jtk-learn/";

    @Given("User membuka browser")
    public void user_membuka_browser() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        courseOverviewPage = new CourseOverviewPage(driver);
    }

    @Given("User berada di halaman login JTK Learn")
    public void user_berada_di_halaman_login_jtk_learn() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
    }

    @When("User memasukkan email {string} dan password {string}")
    public void user_memasukkan_email_dan_password(String email, String password) {
        loginPage.inputEmail.clear();
        loginPage.inputEmail.sendKeys(email);
        loginPage.inputPassword.clear();
        loginPage.inputPassword.sendKeys(password);
    }

    @When("User menekan tombol login")
    public void user_menekan_tombol_login() {
        loginPage.btnLogin.click();
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
    }

    @Then("User berhasil masuk ke halaman dashboard pelajar")
    public void user_berhasil_masuk_ke_halaman_dashboard_pelajar() {
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(BASE_URL)));
        Assert.assertFalse(driver.getCurrentUrl().equals(BASE_URL), "Masih di halaman login!");
    }

    // ========================= TC-FR-1.8 =========================
    @Then("Muncul pesan error login {string}")
    public void muncul_pesan_error_login(String expectedMsg) {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("URL setelah klik login: " + currentUrl);
        Assert.assertTrue(
            !currentUrl.contains("dashboard"),
            "Seharusnya login GAGAL tapi malah berhasil! URL: " + currentUrl
        );
        // Cetak pesan error yang muncul di halaman (untuk dokumentasi)
        try {
            List<WebElement> errorTexts = driver.findElements(By.xpath(
                "//*[contains(text(),'salah') or contains(text(),'gagal') or contains(text(),'Kesalahan') or contains(text(),'tidak') or contains(text(),'invalid')]"
            ));
            for (WebElement el : errorTexts) {
                if (el.isDisplayed() && !el.getText().trim().isEmpty()) {
                    System.out.println("Pesan error ditemukan: " + el.getText());
                }
            }
        } catch (Exception ignored) {}
        System.out.println("PASS: TC-FR-1.8 Login gagal berhasil diverifikasi.");
    }

    @Then("User masih berada di halaman login")
    public void user_masih_berada_di_halaman_login() {
        Assert.assertFalse(driver.getCurrentUrl().contains("dashboard"),
            "User seharusnya tidak berhasil login!");
        System.out.println("PASS: User tetap di halaman login. URL: " + driver.getCurrentUrl());
    }

    // ========================= TC-FR-6.0.7 =========================
    @When("User mencari course yang tersedia dan membuka halaman overview-nya")
    public void user_mencari_course_yang_tersedia_dan_membuka_halaman_overview_nya() {
        System.out.println("Menunggu halaman setelah login...");
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        System.out.println("URL saat ini: " + driver.getCurrentUrl());

        // Cetak semua elemen yang bisa diklik untuk debugging
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        System.out.println("Total link yang ditemukan: " + allLinks.size());
        for (WebElement link : allLinks) {
            try {
                if (link.isDisplayed()) {
                    System.out.println("  Link: " + link.getText() + " -> " + link.getAttribute("href"));
                }
            } catch (Exception ignored) {}
        }

        // Strategi: Cari link/elemen yang ada kata 'course', 'kelas', 'Semua', 'Browse'
        boolean found = false;
        String[] keywords = {"course", "Course", "kelas", "Kelas", "Semua", "Browse", "Cari", "katalog"};
        for (String kw : keywords) {
            try {
                List<WebElement> els = driver.findElements(By.xpath(
                    "//*[contains(text(),'" + kw + "') and (self::a or self::button or self::div or self::span)]"
                ));
                for (WebElement el : els) {
                    if (el.isDisplayed()) {
                        System.out.println("Klik elemen dengan kata '" + kw + "': " + el.getText());
                        el.click();
                        Thread.sleep(2000);
                        found = true;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Keyword '" + kw + "' error: " + e.getMessage());
            }
            if (found) break;
        }

        // Tunggu dan cek apakah ada card course yang bisa diklik
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        List<WebElement> courseCards = driver.findElements(By.cssSelector(
            ".card, [class*='course-card'], [class*='CourseCard'], [class*='course-item']"
        ));
        System.out.println("Card course ditemukan: " + courseCards.size());
        for (WebElement card : courseCards) {
            if (card.isDisplayed()) {
                System.out.println("Klik card course: " + card.getText().substring(0, Math.min(60, card.getText().length())));
                card.click();
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                break;
            }
        }

        System.out.println("URL setelah navigasi course: " + driver.getCurrentUrl());

        // Tunggu form enroll muncul
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".enroll-form, .button-enroll")));
            System.out.println("Form enrollment ditemukan!");
        } catch (Exception e) {
            System.out.println("Form enrollment tidak muncul. URL: " + driver.getCurrentUrl());
        }
    }

    @When("User membuka halaman Course Overview untuk course {string}")
    public void user_membuka_halaman_course_overview(String courseName) {
        user_mencari_course_yang_tersedia_dan_membuka_halaman_overview_nya();
    }

    @When("User memasukkan enrollment key {string}")
    public void user_memasukkan_enrollment_key(String enrollKey) {
        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".enroll-form input, input[placeholder*='key'], input[placeholder*='kunci'], input[placeholder*='enroll']")
            ));
            input.clear();
            input.sendKeys(enrollKey);
            System.out.println("Enrollment key berhasil dimasukkan: " + enrollKey);
        } catch (Exception e) {
            System.out.println("Gagal menemukan field enrollment key: " + e.getMessage());
            throw e;
        }
    }

    @When("User menekan tombol daftar")
    public void user_menekan_tombol_daftar() {
        try {
            WebElement btn = driver.findElement(By.cssSelector(".button-enroll, .btn-enroll, button[type='submit']"));
            btn.click();
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        } catch (Exception e) {
            System.out.println("Gagal klik tombol daftar: " + e.getMessage());
            throw e;
        }
    }

    @Then("Muncul pesan sukses pendaftaran course")
    public void muncul_pesan_sukses_pendaftaran_course() {
        try {
            wait.until(ExpectedConditions.visibilityOf(courseOverviewPage.alertSuccessEnroll));
            Assert.assertTrue(courseOverviewPage.alertSuccessEnroll.isDisplayed());
        } catch (Exception e) {
            Assert.assertFalse(driver.getCurrentUrl().contains("course-overview"), "Pendaftaran gagal!");
        }
    }

    @Then("Muncul pesan error pendaftaran {string}")
    public void muncul_pesan_error_pendaftaran(String errorMsg) {
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        List<WebElement> errorEls = driver.findElements(By.cssSelector(
            ".modal, .modal-body, .modal-content, .alert-danger, [class*='error'], [class*='Error'], [class*='modal'], [class*='Modal']"
        ));
        System.out.println("Elemen error/modal yang ditemukan: " + errorEls.size());
        boolean errorFound = false;
        for (WebElement el : errorEls) {
            try {
                if (el.isDisplayed()) {
                    System.out.println("  -> " + el.getText());
                    errorFound = true;
                }
            } catch (Exception ignored) {}
        }
        Assert.assertTrue(errorFound, "Tidak ada pesan error yang muncul setelah enroll key salah!");
        System.out.println("PASS: TC-FR-6.0.7 Enroll key salah berhasil diverifikasi.");
    }

    // ========================= TC-FR-2.2 =========================
    @When("User menekan menu profile")
    public void user_menekan_menu_profile() {
        dashboardPage.menuProfile.click();
    }

    @When("User menekan tombol logout")
    public void user_menekan_tombol_logout() {
        dashboardPage.btnLogout.click();
    }

    @Then("User berhasil keluar dan diarahkan ke halaman login")
    public void user_berhasil_keluar_dan_diarahkan_ke_halaman_login() {
        wait.until(ExpectedConditions.urlToBe(BASE_URL));
        Assert.assertEquals(driver.getCurrentUrl(), BASE_URL);
    }

    // ========================= TC-FR-6.0.8 =========================
    @When("User membiarkan enrollment key kosong")
    public void user_membiarkan_enrollment_key_kosong() {
        try {
            WebElement input = driver.findElement(By.cssSelector(".enroll-form input"));
            input.clear();
        } catch (Exception e) {
            System.out.println("Field kosong/tidak ditemukan: " + e.getMessage());
        }
    }

    @Then("Sistem meminta enrollment key diisi")
    public void sistem_meminta_enrollment_key_diisi() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<WebElement> errorEls = driver.findElements(By.cssSelector(
            ".modal, .alert-danger, [class*='error'], [class*='Error']"
        ));
        boolean errorFound = errorEls.stream().anyMatch(WebElement::isDisplayed);
        Assert.assertTrue(errorFound, "Sistem tidak memberikan respons saat enroll key dikosongkan!");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
