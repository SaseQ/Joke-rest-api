package it.marczuk.resttest.service.joke;

import com.google.common.base.Suppliers;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import it.marczuk.resttest.exception.JokeNotFoundExeption;
import it.marczuk.resttest.model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
@Primary
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

        private final Supplier<Joke> memoizedSupplier = Suppliers.memoize(() -> defaultJokeService.getRandomJoke());

        @ParametersAreNonnullByDefault
        private final LoadingCache<String, Joke> randomJokeByCategoryCache = CacheBuilder
                .newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES) //- remove records that have been idle for 10 minutes
                .maximumSize(MAX_CACHE_SIZE) //- limit the size of our cache
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
            return of(memoizedSupplier.get());
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
