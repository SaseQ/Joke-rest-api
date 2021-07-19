package it.marczuk.resttest.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import it.marczuk.resttest.exception.JokeNotFoundExeption;
import it.marczuk.resttest.model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
public class CacheJokeService implements JokeService {

    private final DefaultJokeService defaultJokeService;
    private final DefaultJokeCache defaultJokeCache = new DefaultJokeCache();

    @Autowired
    public CacheJokeService(DefaultJokeService defaultJokeService) {
        this.defaultJokeService = defaultJokeService;
    }

    @Override
    public Joke getRandomJoke() {
        return defaultJokeCache.getJokeCache().orElseThrow(() -> new JokeNotFoundExeption("Could not find joke"));
    }

    @Override
    public Joke getRandomJokeByCategory(String category) {
        return defaultJokeCache.getJokeByCategoryCache(category).orElseThrow(() -> new JokeNotFoundExeption("Could not find joke by category: " + category));
    }

    @Override
    public List<Joke> getJokeByQuery(String query) {
        return defaultJokeCache.getJokeByQueryCache(query);
    }

    private final class DefaultJokeCache {

        private static final int MAX_CACHE_SIZE = 100;

        private final LoadingCache<String, Joke> randomJokeCache = CacheBuilder
                .newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES) //- remove records that have been idle for 10 minutes
                .maximumSize(MAX_CACHE_SIZE) //- limit the size of our cache
                .build(CacheLoader.from(c -> defaultJokeService.getRandomJoke()));

        @ParametersAreNonnullByDefault
        private final LoadingCache<String, Joke> randomJokeByCategoryCache = CacheBuilder
                .newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(MAX_CACHE_SIZE)
                .build(new CacheLoader<>() {
                    @Override
                    public Joke load(String catergory) {
                        return defaultJokeService.getRandomJokeByCategory(catergory);
                    }
                });

        @ParametersAreNonnullByDefault
        private final LoadingCache<String, List<Joke>> randomJokeByQueryCache = CacheBuilder
                .newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(MAX_CACHE_SIZE)
                .build(new CacheLoader<>() {
                    @Override
                    public List<Joke> load(String query) {
                        return defaultJokeService.getJokeByQuery(query);
                    }
                });

        public Optional<Joke> getJokeCache() {
            try {
//LoadingCache bez argumentu                  \/
                return of(randomJokeCache.get(""));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return empty();
        }

        public Optional<Joke> getJokeByCategoryCache(String category) {
            try {
                return of(randomJokeByCategoryCache.get(category));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return empty();
        }

        public List<Joke> getJokeByQueryCache(String query) {
            try {
                return randomJokeByQueryCache.get(query);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        }
    }
}
