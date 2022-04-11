package quru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import quru.qa.components.Student;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonParsingTest {

    String JSON_PATH = "src\\test\\resources\\files\\simple.json";

    @Test
    void parseJsonTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Student student = mapper.readValue(new File(JSON_PATH), Student.class);
        assertThat(student.name).isEqualTo("Karl");
        assertThat(student.surname).isEqualTo("Marx");
        assertThat(student.favoriteMusic.get(0)).contains("Metallica");
        assertThat(student.favoriteMusic.get(1)).contains("Nirvana");
        assertThat(student.address.street).isEqualTo("Bruckergasse");
        assertThat(student.address.house).isEqualTo(664);

    }
}
