package liv.bradford.utilities;

import com.github.javafaker.Faker;

import java.util.LinkedHashMap;
import java.util.Map;

public class LibraryUtil {
    public static Map<String,Object> getRandomBookMap(){

        Faker faker = new Faker() ;
        Map<String,Object> bookMap = new LinkedHashMap<>();
        String randomBookName = faker.book().title() + faker.number().numberBetween(0, 10);
        bookMap.put("name", randomBookName);
        bookMap.put("isbn", faker.code().isbn10()   );
        bookMap.put("year", faker.number().numberBetween(1000,2021)   );
        bookMap.put("author",faker.book().author()   );
        bookMap.put("book_category_id", faker.number().numberBetween(1,20)   );  // in library app valid category_id is 1-20
        bookMap.put("description", faker.chuckNorris().fact() );

        return bookMap ;
    }
}
