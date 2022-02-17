package steps;

import elements.ScrappingPage;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.ArrayList;
import java.util.List;

public class ScrappingSteps {

    public ScrappingPage scrappingPage;

    public List<String> getProductList(List<String> links) {
        List<WebElementFacade> books = scrappingPage.getAllProducts ();
        int count = 0;
        for(WebElementFacade book : books){
                links.add (book.getAttribute ("href"));
            }
        return links;
    }

    public boolean isNextDisplayed() {
        boolean answer;
        try{
            WebElementFacade element = scrappingPage.getNextButton ();
            answer = element.isDisplayed();
            System.out.println (answer);
            System.out.println("Try");
        }catch (Exception e){
            answer = false;
            System.out.println("Catch");
        }

        return answer;
    }

    public void clickNextButton() {
        scrappingPage.getNextButton ().click ();
    }

    public void getBookDetails(List<String> info) {
        List<WebElementFacade> details = scrappingPage.getProductInformation ();
        for(WebElementFacade type_of_info : details){
            info.add (type_of_info.getText ());
        }
    }

    public String getTitle() {
        return scrappingPage.getBookTitle().getText ();
    }

//    public void selectCategory(String category) {
//        scrappingPage.getCategory (category).click ();
//    }

    public List<String> getCategoriesList(){
        List<WebElementFacade> categories = scrappingPage.getCategories ();
        List<String> links = new ArrayList<> ();
        for(WebElementFacade category : categories){
            links.add(category.getAttribute ("href"));
        }
        return links;
    }

    public List<String> getFileName() {
        List<WebElementFacade> categories = scrappingPage.getCategories ();
        List<String> names = new ArrayList<> ();
        for(WebElementFacade category : categories){
            names.add (category.getText ());
        }
        return names;
    }
}
