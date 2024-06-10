package beans;

import com.google.gson.Gson;

import java.time.LocalDate;

public class Movie {
    private int id;
    private String title;
    private LocalDate releaseDate;
    private int[] actors;

    public Movie(){

    }

    public Movie(int id, String title, LocalDate releaseDate, int[] actors) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.actors = actors;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int[] getActors() {
        return actors;
    }

    public void setActors(int[] actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this.actors);
        return String.format("Movie [id=%d, title=%s, releaseDate=%s, actors=[%s]]", id, title, releaseDate, json);
    }
}
