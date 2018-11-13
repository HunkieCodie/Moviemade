package org.michaelbel.moviemade.modules_beta.genres;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.extensions.AndroidExtensions;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.ui.modules.main.ResultsMvp;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.utils.DateUtils;
import org.michaelbel.moviemade.utils.NetworkUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

@InjectViewState
public class GenreMoviesPresenter extends MvpPresenter<ResultsMvp> {

    public int page = 1;
    public int totalPages;
    public boolean isLoading = false;
    public boolean isLastPage = false;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadFirstPage(int genreId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        /*GENRES service = ApiFactory.createService2(GENRES.class);
        Observable<MoviesResponse> observable = service.getMovies(genreId, BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, AndroidUtils.includeAdult(), page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(MoviesResponse response) {
                totalPages = response.totalPages;
                List<TmdbObject> results = new ArrayList<>(response.movies);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_MOVIES);
            }

            @Override
            public void onComplete() {}
        }));*/
    }

    public void loadNextPage(int genreId) {
        /*GENRES service = ApiFactory.createService2(GENRES.class);
        Observable<MoviesResponse> observable = service.getMovies(genreId, BuildConfig.TMDB_API_KEY, ConstantsKt.en_US, AndroidUtils.includeAdult(), page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<MoviesResponse>() {
            @Override
            public void onNext(MoviesResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.movies);
                getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        }));*/
    }

    public void movieFavoritesChange(Movie m) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", m.getId()).findFirst();
        if (movie == null) {
            realm.beginTransaction();

            MovieRealm newMovie = realm.createObject(MovieRealm.class);
            newMovie.id = m.getId();
            newMovie.title = m.getTitle();
            newMovie.posterPath = m.getPosterPath();
            newMovie.releaseDate = AndroidExtensions.formatReleaseDate(m.getReleaseDate());
            //newMovie.originalTitle = m.originalTitle;
            //newMovie.originalLanguage = AndroidUtils.formatOriginalLanguage(m.originalLanguage);
            newMovie.overview = m.getOverview();
            newMovie.addedDate = DateUtils.getCurrentDateAndTimeWithMilliseconds();
            //newMovie.adult = m.adult;
            //newMovie.backdropPath = m.backdropPath;
            //newMovie.budget = AndroidUtils.formatCurrency(m.budget);
            //newMovie.revenue = AndroidUtils.formatCurrency(m.revenue);
            //newMovie.status = m.status;
            //newMovie.tagline = m.tagline;
            //newMovie.imdbId = m.imdbId;
            //newMovie.homepage = m.homepage;
            //newMovie.popularity = m.popularity;
            //newMovie.video = m.video;
            //newMovie.runtime = AndroidUtils.formatRuntime(m.runtime);
            newMovie.voteAverage = m.getVoteAverage();
            newMovie.voteCount = m.getVoteCount();
            //newMovie.favorite = true;

            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            MovieRealm movieRealm = realm.where(MovieRealm.class).equalTo("id", m.getId()).findFirst();
            //movieRealm.favorite = !movie.favorite;
            realm.commitTransaction();
        }
    }

    public void movieWatchlistChange(Movie m) {
        /*Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", m.id).findFirst();
        if (movie == null) {
            realm.beginTransaction();

            MovieRealm newMovie = realm.createObject(MovieRealm.class);
            newMovie.id = m.id;
            newMovie.title = m.title;
            newMovie.posterPath = m.posterPath;
            newMovie.releaseDate = AndroidExtensions.formatReleaseDate(m.releaseDate);
            newMovie.overview = m.overview;
            newMovie.addedDate = DateUtils.getCurrentDateAndTimeWithMilliseconds();
            newMovie.popularity = m.popularity;
            newMovie.voteAverage = m.voteAverage;
            newMovie.voteCount = m.voteCount;
            newMovie.watching = true;

            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            MovieRealm movieRealm = realm.where(MovieRealm.class).equalTo("id", m.id).findFirst();
            movieRealm.watching = !movie.watching;
            realm.commitTransaction();
        }*/
    }

    /*public boolean isMovieFavorite(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        return movie != null && movie.favorite;
    }*/

    public boolean isMovieWatchlist(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        return movie != null && movie.watching;
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}