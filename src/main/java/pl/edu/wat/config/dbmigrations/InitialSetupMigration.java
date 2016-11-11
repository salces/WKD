package pl.edu.wat.config.dbmigrations;

import au.com.bytecode.opencsv.CSVReader;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import pl.edu.wat.bookstore.book.domain.Book;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

    private Map<String, String>[] authoritiesUser = new Map[]{new HashMap<>()};

    private Map<String, String>[] authoritiesAdminAndUser = new Map[]{new HashMap<>(), new HashMap<>()};

    {
        authoritiesUser[0].put("_id", "ROLE_USER");
        authoritiesAdminAndUser[0].put("_id", "ROLE_USER");
        authoritiesAdminAndUser[1].put("_id", "ROLE_ADMIN");
    }

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
    public void addAuthorities(DB db) {
        DBCollection authorityCollection = db.getCollection("jhi_authority");
        authorityCollection.insert(
            BasicDBObjectBuilder.start()
                .add("_id", "ROLE_ADMIN")
                .get());
        authorityCollection.insert(
            BasicDBObjectBuilder.start()
                .add("_id", "ROLE_USER")
                .get());
    }

    @ChangeSet(order = "02", author = "initiator", id = "02-addUsers")
    public void addUsers(DB db) {
        DBCollection usersCollection = db.getCollection("jhi_user");
        usersCollection.createIndex("login");
        usersCollection.createIndex("email");
        usersCollection.insert(BasicDBObjectBuilder.start()
            .add("_id", "user-0")
            .add("login", "system")
            .add("password", "$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG")
            .add("first_name", "")
            .add("last_name", "System")
            .add("email", "system@localhost")
            .add("activated", "true")
            .add("lang_key", "en")
            .add("created_by", "system")
            .add("created_date", new Date())
            .add("authorities", authoritiesAdminAndUser)
            .get()
        );
        usersCollection.insert(BasicDBObjectBuilder.start()
            .add("_id", "user-1")
            .add("login", "anonymousUser")
            .add("password", "$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO")
            .add("first_name", "Anonymous")
            .add("last_name", "User")
            .add("email", "anonymous@localhost")
            .add("activated", "true")
            .add("lang_key", "en")
            .add("created_by", "system")
            .add("created_date", new Date())
            .add("authorities", new Map[]{})
            .get()
        );
        usersCollection.insert(BasicDBObjectBuilder.start()
            .add("_id", "user-2")
            .add("login", "admin")
            .add("password", "$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC")
            .add("first_name", "admin")
            .add("last_name", "Administrator")
            .add("email", "admin@localhost")
            .add("activated", "true")
            .add("lang_key", "en")
            .add("created_by", "system")
            .add("created_date", new Date())
            .add("authorities", authoritiesAdminAndUser)
            .get()
        );
        usersCollection.insert(BasicDBObjectBuilder.start()
            .add("_id", "user-3")
            .add("login", "user")
            .add("password", "$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K")
            .add("first_name", "")
            .add("last_name", "User")
            .add("email", "user@localhost")
            .add("activated", "true")
            .add("lang_key", "en")
            .add("created_by", "system")
            .add("created_date", new Date())
            .add("authorities", authoritiesUser)
            .get()
        );
    }

    @ChangeSet(order = "03",author = "initiator", id="03-addBooks")
    public void addBooks(DB db) throws IOException {
        DBCollection booksCollection = db.getCollection("books");
        CSVReader reader = new CSVReader(new FileReader("src/main/resources/dataSource/books.csv"),';','"',1);
        List<Book> books = getBooks(reader);
        books.forEach(b ->
            booksCollection.insert(toDocument(b))
        );
        reader.close();
    }

    private List<Book> getBooks(CSVReader reader) throws IOException {
        List<Book> books = new ArrayList<>();
        String[] values;
        while ((values = reader.readNext()) != null) {
            Book book = toBook(values);
            books.add(book);
        }
        return books;
    }

    private Book toBook(String[] values) {
        return Book
            .builder()
            .ISBN(values[0])
            .tittle(values[1])
            .author(values[2])
            .publicationYear(Integer.parseInt(values[3]))
            .publisher(values[4])
            .smallImageUrl(values[5])
            .mediumImageUrl(values[6])
            .largeImageUrl(values[7])
            .build();
    }

    private DBObject toDocument(Book book){
       return BasicDBObjectBuilder.start()
            .add("ISBN",book.getISBN())
            .add("tittle",book.getTittle())
            .add("author",book.getAuthor())
            .add("publicationYear",book.getPublicationYear())
            .add("publisher",book.getPublisher())
            .add("smallImageUrl",book.getSmallImageUrl())
            .add("mediumImageUrl",book.getMediumImageUrl())
            .add("largeImageUrl",book.getLargeImageUrl())
            .get();
    }
}
