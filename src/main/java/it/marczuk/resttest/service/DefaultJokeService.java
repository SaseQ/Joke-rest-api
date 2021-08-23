package it.marczuk.resttest.service;

import it.marczuk.resttest.exception.JokeNotFoundExeption;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.model.JokeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class DefaultJokeService implements JokeService {

    private static final String RANDOM_URL = "https://api.chucknorris.io/jokes/";
    private final RestTemplate restTemplate;
    private final DatabaseJokeDao databaseJokeDao;

    @Autowired
    public DefaultJokeService(RestTemplate restTemplate, DatabaseJokeDao databaseJokeDao) {
        this.restTemplate = restTemplate;
        this.databaseJokeDao = databaseJokeDao;
    }

    @Override
    public Joke getRandomJoke() {
        return databaseJokeDao.databaseOperation(callGetMethod("random", Joke.class));
    }

    @Override
    public Joke getRandomJokeByCategory(String category) {
        if(databaseJokeDao.isItCategory(category)) {
            return databaseJokeDao.databaseOperation(callGetMethod("random?category=" + category, Joke.class));
        }
        throw new JokeNotFoundExeption("Could not find category: " + category);
    }

    @Override
    public List<Joke> getJokeByQuery(String query) {
        JokeQuery jokeQuery = callGetMethod("search?query=" + query, JokeQuery.class);
        return jokeQuery != null ? jokeQuery.getResult() : Collections.emptyList();
    }

    public <T> T callGetMethod(String url, Class<T> responseType) {
        Optional<T> response = ofNullable(restTemplate.getForObject(RANDOM_URL + url, responseType));
        return response.orElseThrow();
    }

//    private final class DefaultJokeCache {
//
//        private final LoadingCache<String, Joke> randomJokeByCategoryCache = CacheBuilder
//                .newBuilder()
//                .expireAfterAccess(10, TimeUnit.MINUTES) //- remove records that have been idle for 10 minutes
//                //.expireAfterWrite(10, TimeUnit.MINUTES) //- remove records based on their total live time
//                .maximumSize(3) //- limit the size of our cache
//                .build(new CacheLoader<>() {
//                    @Override
//                    public Joke load(String s){
//                        return obtainRandomJokeByCategory(s);
//                    }
//                });
//
//        public Optional<Joke> getJokeByCategoryCache(String category) {
//            try {
//                return of(randomJokeByCategoryCache.get(category));
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            return empty();
//        }
//    }

//    private final class HeadersLocalCache
//    {
//        private static final int MAX_CACHE_SIZE = 1000;
//        private final LoadingCache<Pair<String, String>, Map<String, String>> mimeFolderHeadersCache = CacheBuilder.newBuilder()
//                .maximumSize(
//                        MAX_CACHE_SIZE)
//                .build(
//                        new CacheLoader<>()
//                        {
//                            @Override
//                            public Map<String, String> load(
//                                    final Pair<String, String> mimeFolderPair)
//                            {
//                                return getGlobalAndMimeAndFolderHeaders(
//                                        mimeFolderPair
//                                                .getLeft(),
//                                        mimeFolderPair
//                                                .getRight());
//                            }
//                        }
//                );

    //    implemetnacja cache'a dla categorii
    //    LoadingCache guava porownaj to z memoize
    //        restTemplate.

}
