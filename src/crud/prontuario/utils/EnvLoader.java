package crud.prontuario.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvLoader {
    private static final Properties props = new Properties();

    static {
        try {
            props.load(new FileInputStream(".env"));
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo .env");
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
