package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import steps.ScrappingSteps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RunWith (SerenityRunner.class)
public class ScrappingTests {

//    public String category = "Travel";


    public static final String excelPath = "C:/ScrappingBooks/%s.xlsx";

    @Managed
    WebDriver driver;
    String URL = "https://books.toscrape.com/index.html";

    @Steps
    ScrappingSteps scrappingSteps;

    @Before
    public void beforeTest()  {
        driver.navigate().to(URL);
        driver.manage().window().maximize();
        //Thread.sleep(5000);
    }


    @Test
    public void scrappingSite() throws IOException, InterruptedException {
//        scrappingSteps.selectCategory(category);
        // Create two lists for categories names and links
        List<String> names = scrappingSteps.getFileName();
        List<String> bookCategories = scrappingSteps.getCategoriesList ();
        int count = 0;
        // For every category, create an excel file with the corresponded name
        for(String link :bookCategories){
            System.out.println (names.get (count));
            String EXCEL_FILE_PATH = String.format (excelPath, names.get (count));
            FileOutputStream out = new FileOutputStream(new File (EXCEL_FILE_PATH));
            System.out.println (link);
            driver.navigate ().to (link);


            Thread.sleep (2000);
            boolean done = false;
            List<String> books = new ArrayList<> ();
//        int count = 0;
            // Navigate to each category-link and get any book-link in a list for every page
            while(!done){
                books = scrappingSteps.getProductList(books);
                done = true;
                if(scrappingSteps.isNextDisplayed()){
                    scrappingSteps.clickNextButton();
                    done = false;
                }
//            count++;
//            if(count == 1)
//                break;
            }
            List<String> bookTitle = new ArrayList<> ();
            List<String> bookUPC = new ArrayList<> ();
            List<String> bookType = new ArrayList<> ();
            List<String> bookPriceWithTax = new ArrayList<> ();
            List<String> bookPriceWithoutTax = new ArrayList<> ();
            List<String> bookTax = new ArrayList<> ();
            List<String> bookAvailability = new ArrayList<> ();
            List<String> bookReviews = new ArrayList<> ();
            // Navigate to each book-link and separate the information of a book in different lists
            for(String book : books){
                System.out.println (book);
                driver.navigate ().to (book);
                List<String> info = new ArrayList<> ();
                bookTitle.add (scrappingSteps.getTitle());
                scrappingSteps.getBookDetails (info);
                bookUPC.add(info.get (0));
                bookType.add(info.get (1));
                bookPriceWithTax.add(info.get (2));
                bookPriceWithoutTax.add(info.get (3));
                bookTax.add(info.get (4));
                bookAvailability.add(info.get (5));
                bookReviews.add(info.get (6));

            }
            System.out.println (bookTitle);
            System.out.println (bookUPC);
            System.out.println (bookType);
            System.out.println (bookPriceWithTax);
            System.out.println (bookPriceWithoutTax);
            System.out.println (bookTax);
            System.out.println (bookAvailability);
            System.out.println (bookReviews);

            // create an Excel file, name it properly and export it

            XSSFWorkbook workbook = new XSSFWorkbook();
            String spreadsheet_name = String.format (names.get (count), "books");
            XSSFSheet spreadsheet = workbook.createSheet(spreadsheet_name);

            XSSFRow row = spreadsheet.createRow(0);

            Map<String, Object[]> booksData
                    = new TreeMap<String, Object[]> ();

            booksData.put("1",new Object[]
                    {"Title","UPC","Product Type", "Price (incl. Tax)", "Price(excl. Tax)", "Tax", "Availability", "Number of Reviews"}
            );

            for (int i = 0 ; i < bookTitle.size() ; i++) {
                booksData.put(String.valueOf(i+2),new Object[]{
                        bookTitle.get(i),
                        bookUPC.get(i),
                        bookType.get(i),
                        bookPriceWithTax.get(i),
                        bookPriceWithoutTax.get(i),
                        bookTax.get(i),
                        bookAvailability.get(i),
                        bookReviews.get(i)
                });
            }

            Set<String> keyId = booksData.keySet();

            int rowId = 0;
            System.out.println(keyId);
            for (String key : keyId){
                row = spreadsheet.createRow(rowId++);
                Object[] objArr = booksData.get(key);
                int cellid = 0;
                for (Object obj : objArr){
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String)obj);
                }
            }



            workbook.write(out);

            out.close();
            System.out.println("final ok!!!!!");
            count++;
            if(count == 3)
                break;
        }


    }


    @After
    public void afterTest() {
        //Thread.sleep(5000);
        driver.close();
    }
}
