package geo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GeoServiceImplTest {

    GeoServiceImpl sut;

    @BeforeEach
    public void initEachTest() {
        sut = new GeoServiceImpl();
    }

    @MethodSource("sourceIp")
    @ParameterizedTest
    public void testByIp(String ip, Country expected) {
        Country actual = sut.byIp(ip).getCountry();
        assertEquals(expected, actual);
    }

    @MethodSource("sourceCoordinates")
    @ParameterizedTest
    public void testByCoordinates(double latitude, double longitude) {
        assertThrows(RuntimeException.class, () -> sut.byCoordinates(latitude, longitude));
    }

    public static Stream<Arguments> sourceIp() {
        return Stream.of(
                Arguments.of("172.110.11.12", Country.RUSSIA),
                Arguments.of("96.10.111.112", Country.USA)
        );
    }

    public static Stream<Arguments> sourceCoordinates() {
        return Stream.of(
                Arguments.of(12.21, 55.77)
        );
    }
}