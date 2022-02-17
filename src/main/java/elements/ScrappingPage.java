package elements;


import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.util.List;

public class ScrappingPage extends PageObject {

    String NAVIGATE_TO_PRODUCT = "//li//article//div//a";
    String NEXT_BUTTON = "//li[@class='next']//a";
    String BOOK_TITLE = "//div[@class='col-sm-6 product_main']//h1";
    String PRODUCT_INFORMATION = "//tr//td";
//    String CATEGORIES = "//ul[@class='nav nav-list']//a[contains(text(),'%s')]";
    String CATEGORIES = "//ul[@class='nav nav-list']//ul//li//a";
    public List<WebElementFacade> getAllProducts(){
        return findAll(By.xpath (NAVIGATE_TO_PRODUCT));
    }

    public WebElementFacade getNextButton(){
        return  $(By.xpath (NEXT_BUTTON));
    }

    public WebElementFacade getBookTitle() {
        return $(By.xpath (BOOK_TITLE));
    }

    public List<WebElementFacade> getProductInformation(){
        return findAll (By.xpath (PRODUCT_INFORMATION));
    }

    // Get only one category
//    public WebElementFacade getCategory(String category){
//        String selector = String.format (CATEGORIES, category);
//        return $ (By.xpath (selector));
//    }

    // Get all categories' href
    public List<WebElementFacade> getCategories(){
        return findAll (By.xpath (CATEGORIES));
    }

}
