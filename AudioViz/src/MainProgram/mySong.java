package MainProgram;

public class mySong {
    private String Title;
    private String Artist;
    private String URL;

    public mySong(String title, String artist, String url) {
        this.Title = title;
        this.Artist = artist;
        this.URL = url;
    }

    /**
     *
     * @param inputs Takes an array of size 2
     *               inputs[0] is the Song Title
     *               inputs[1] is the Song Artist
     *               inputs[2] is the Song location
     */
    public mySong(String[] inputs) {
        this.Title = inputs[0];
        this.Artist = inputs[1];
        this.URL = inputs[2];
    }

    public String getTitle() {
        return this.Title;
    }

    public String getURL() {
        return this.URL;
    }

    public String getArtist() {
        return this.Artist;
    }
}
