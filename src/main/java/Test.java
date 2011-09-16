import com.jakewharton.trakt.ServiceManager;
import com.jakewharton.trakt.services.UserService;

public class Test {
    public static void main(String... args) {
        ServiceManager manager = new ServiceManager();

        manager.setApiKey("7f9fb61a46ed0d8ecc917b789154d397");
        manager.setAuthentication("trakt-java", "e00e0e31fe07213b59e5784c9942cfe220771827");
        
        UserService service = manager.userService();
        
        String user = "JakeWharton";
        System.out.println("Watched: " + service.watchedMovies(user).fire().size());
        System.out.println("Library: " + service.libraryMoviesAll(user).fire().size());
    }
}
