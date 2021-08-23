package it.marczuk.resttest.service;

import it.marczuk.resttest.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JokeCategoriesService {

    private final DefaultJokeService defaultJokeService;
    private final DatabaseJokeDao databaseJokeDao;

    @Autowired
    public JokeCategoriesService(DefaultJokeService defaultJokeService, DatabaseJokeDao databaseJokeDao) {
        this.defaultJokeService = defaultJokeService;
        this.databaseJokeDao = databaseJokeDao;
    }

    @Scheduled(cron = "0 0 */1 * * *")
    public void getAllJokeCategories() {
        List<String> categoriesString = defaultJokeService.callGetMethod("categories", ArrayList.class);
        List<Category> categoriesModel = new ArrayList<>();
        categoriesString.forEach(c -> categoriesModel.add(new Category(c)));
        databaseJokeDao.addCategory(categoriesModel);
    }
}
