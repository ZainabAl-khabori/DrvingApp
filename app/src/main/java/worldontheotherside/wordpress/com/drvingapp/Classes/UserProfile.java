package worldontheotherside.wordpress.com.drvingapp.Classes;

/**
 * Created by wafooy on 08/12/17.
 */

public class UserProfile {
    private String header;

    private String profileContent;

    public UserProfile(String header, String profileContent) {
        this.header = header;
        this.profileContent = profileContent;
    }



    public String getHeader() {
        return header;
    }

    public String getProfileContent() {
        return profileContent;
    }
}
