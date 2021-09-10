package it.marczuk.resttest.service.db;

import it.marczuk.resttest.model.Category;
import it.marczuk.resttest.model.Joke;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseJokeDao {

    private final DatabaseJokeService databaseJokeService;
    private final Logger log = LoggerFactory.getLogger(DatabaseJokeDao.class);

    @Autowired
    public DatabaseJokeDao(DatabaseJokeService databaseJokeService) {
        this.databaseJokeService = databaseJokeService;
    }

    @Transactional
    public Joke saveJokeIfNotExist(Joke joke) {
        if(databaseJokeService.findJokeById(joke.getId()).isEmpty()) {
            log.debug("Joke id: {} does not exist in the database", joke.getId());
            databaseJokeService.saveJoke(joke);
        }
        return joke;
    }

    @Transactional
    public void addCategory(List<Category> restCategories) {
        List<Category> databaseCategories = databaseJokeService.findAllCategories();
        databaseJokeService.saveAllCategories(getNewCategories(databaseCategories, restCategories));
        databaseJokeService.deleteAllCategories(getOldCategories(databaseCategories, restCategories));
    }
    
    public boolean isItCategory(String category) {
        List<Category> databaseCategories = databaseJokeService.findAllCategories();
        for(Category x: databaseCategories) {
            if(x.getCategory().equals(category)){
                return true;
            }
        }
        return false;
    }

    private List<Category> getNewCategories(List<Category> databaseCategories, List<Category> restCategories) {
        return restCategories.stream().filter(e -> !databaseCategories.contains(e)).collect(Collectors.toList());
    }

    private List<Category> getOldCategories(List<Category> databaseCategories, List<Category> restCategories) {
        return databaseCategories.stream().filter(e -> !restCategories.contains(e)).collect(Collectors.toList());
    }
}
