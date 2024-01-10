package hwjdev817.storage;

import hwjdev817.prefs.Prefs;
import org.flywaydb.core.Flyway;

public class DatabaseInitService {
    public static void initDb(Database database) {

        String connectiondbUrl = new Prefs().getString(Prefs.DB_URL);

        Flyway flyway= Flyway
                .configure()
                .dataSource(connectiondbUrl, null, null)
                .load();

        flyway.migrate();

    }
}
