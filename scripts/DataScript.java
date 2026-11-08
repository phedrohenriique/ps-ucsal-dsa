import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

//  mvn -f script-pom.xml clean compile exec:java"
public class DataScript {

    public static void main(String[] args) throws Exception {

        System.out.println("SCRIPT STARTED");

        Database db = new Database();
        db.generateData();

        System.out.println("SCRIPT FINISHED");

    }

}

class Configuration {

    private final String dbURL;
    private final String dbUser;
    private final String dbPassword;
    private final String device;

    public Configuration() {

        // environment variables already passed in the container
        // Dotenv library loads from file, wich does not exist in script
        this.dbURL = System.getenv("DB_URL_EXTERNAL");
        this.dbUser = System.getenv("DB_USER");
        this.dbPassword = System.getenv("DB_PASSWORD");
        this.device = System.getenv("DEVICE");

        if (this.dbURL == null || this.dbURL.isBlank()) {
            throw new IllegalStateException("DB_URL IS NOT CONFIGURED");
        }
    }

    public String getDbUrl() {
        return this.dbURL;
    }

    public String getDbUser() {
        return this.dbUser;
    }

    public String getDbPassword() {
        return this.dbPassword;
    }

    public String getDevice() {
        return this.device;
    }

}

class DataSQL {

    public String insertClient() {
        String sql = """
                    INSERT INTO clients (
                        name_client,
                        contact_client,
                        document_client,
                        document_type_client
                        )
                    VALUES (?, ?, ?, ?);
                """;

        return sql;
    }

}

class Database {

    private final Configuration configuration = new Configuration();
    private final DataSQL sql = new DataSQL();
    private final Random random = new Random();
    private Integer rows = 0;

    private final String[] FIRST_NAMES = {
            "Pedro", "Joao", "Maria", "Ana",
            "Lucas", "Gabriel", "Julia", "Mariana",
            "Rafael", "Camila", "Bruno", "Fernanda",
            "André", "Alexandre", "Antônio", "Augusto",
            "Beatriz", "Bianca", "Caio", "Carolina",
            "Cecília", "Daniel", "Débora", "Eduardo",
            "Elisa", "Felipe", "Flávia", "Gustavo",
            "Helena", "Igor", "Isabela", "Jéssica",
            "Joana", "Leonardo", "Letícia", "Luana",
            "Marcelo", "Marcos", "Mateus", "Natália",
            "Otávio", "Patrícia", "Paulo", "Renata",
            "Ricardo", "Roberto", "Samuel", "Sofia",
            "Thiago", "Vinícius"
    };

    private final String[] LAST_NAMES = {
            "Silva", "Santos", "Oliveira", "Souza",
            "Costa", "Pereira", "Rodrigues", "Almeida",
            "Nascimento", "Lima", "Carvalho", "Ferreira",
            "Mendes", "Barbosa", "Ribeiro", "Martins",
            "Gomes", "Lopes", "Araújo", "Monteiro",
            "Cardoso", "Rocha", "Dias", "Correia",
            "Teixeira", "Moreira", "Nunes", "Moura",
            "Cavalcanti", "Freitas", "Batista", "Campos",
            "Machado", "Azevedo", "Cunha", "Pinto",
            "Farias", "Miranda", "Fonseca", "Melo",
            "Dantas", "Borges", "Tavares", "Rezende",
            "Peixoto", "Andrade", "Guimarães", "Siqueira",
            "Vasconcelos", "Queiroz"
    };

    private final int[] BRAZILIAN_DDDS = {
            11, 12, 13, 14, 15, 16, 17, 18, 19,
            21, 22, 24, 27, 28,
            31, 32, 33, 34, 35, 37, 38,
            41, 42, 43, 44, 45, 46, 47, 48, 49,
            51, 53, 54, 55,
            61, 62, 63, 64, 65, 66, 67, 68, 69,
            71, 73, 74, 75, 77, 79,
            81, 82, 83, 84, 85, 86, 87, 88, 89,
            91, 92, 93, 94, 95, 96, 97, 98, 99
    };

    private final String[] DOCUMENT_TYPE = { "CPF", "CNPJ" };

    public void generateData() {

        try (
                Connection connection = DriverManager.getConnection(
                        this.configuration.getDbUrl(),
                        this.configuration.getDbUser(),
                        this.configuration.getDbPassword());
                PreparedStatement stmt = connection.prepareStatement(sql.insertClient());) {

            for (Integer i = 0; i < 200000; i++) {
                try {

                    stmt.setString(1, getRandomName());
                    stmt.setString(2, getRandomContact());

                    String documentType = getRandomDocumentType();
                    String document = getRandomDocument(documentType);

                    stmt.setString(3, document);
                    stmt.setString(4, documentType);

                    stmt.executeUpdate();

                    this.rows++;

                } catch (Exception exception) {
                    System.out.println("ERROR: " + exception.getMessage());

                }

                System.out.println("ROWS INSERTED - " + this.rows);

            }

        } catch (SQLException exception) {

            System.out.println("ERROR INSERT IN TABLE - data : " + exception.getMessage());
        }

    }

    private String getRandomName() {

        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

        return firstName + " " + lastName;
    }

    private String getRandomContact() {

        int ddd = BRAZILIAN_DDDS[random.nextInt(BRAZILIAN_DDDS.length)];

        StringBuilder number = new StringBuilder();

        number.append(ddd);
        number.append(9);

        for (int i = 0; i < 8; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }

    private String getRandomDocument(String type) {

        if (type == "CPF") {
            String cpf = generateCPF();
            return cpf;

        }

        if (type == "CNPJ") {
            String cnpj = generateCNPJ();
            return cnpj;
        }

        return "";
    }

    private String getRandomDocumentType() {

        String documentType = DOCUMENT_TYPE[random.nextInt(DOCUMENT_TYPE.length)];
        return documentType;

    }

    public String generateCPF() {
        // 11 digits

        StringBuilder cpf = new StringBuilder();

        for (int i = 0; i < 11; i++) {
            cpf.append(random.nextInt(10));
        }

        return cpf.toString();
    }

    public String generateCNPJ() {
        // 14 digits
        StringBuilder cnpj = new StringBuilder();

        for (int i = 0; i < 14; i++) {
            cnpj.append(random.nextInt(10));
        }

        return cnpj.toString();
    }

}
