package it.marczuk.resttest.service.categories;

import it.marczuk.resttest.model.Category;
import it.marczuk.resttest.service.db.DatabaseJokeDao;
import it.marczuk.resttest.service.joke.DefaultJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriesJokeService {

    private final DefaultJokeService defaultJokeService;
    private final DatabaseJokeDao databaseJokeDao;

    @Autowired
    public CategoriesJokeService(DefaultJokeService defaultJokeService, DatabaseJokeDao databaseJokeDao) {
        this.defaultJokeService = defaultJokeService;
        this.databaseJokeDao = databaseJokeDao;
    }

    @Scheduled(cron = "0 0 */1 * * *")
    public void getAllJokeCategories() {
        List<String> categoriesString = defaultJokeService.callGetMethod("categories", ArrayList.class);
        List<Category> categoriesModel = changeCategoriesToModel(categoriesString);
        databaseJokeDao.addCategory(categoriesModel);
    }

    private List<Category> changeCategoriesToModel(List<String> categoryList) {
        return categoryList.stream().map(Category::new).collect(Collectors.toList());
    }
}
