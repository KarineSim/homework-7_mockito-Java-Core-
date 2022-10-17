package i18n;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LocalizationServiceImplTest {

    LocalizationServiceImpl sut;

    @BeforeEach
    public void initEachTest() {
        sut = new LocalizationServiceImpl();
    }

    @MethodSource("source")
    @ParameterizedTest
    public void testLocale(Country country, String expected) {
        String actual = sut.locale(country);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome")
        );
    }
}