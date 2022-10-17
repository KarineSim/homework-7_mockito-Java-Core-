package sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MessageSenderImplTest {

    @MethodSource("source")
    @ParameterizedTest
    public void testSend(String ip, String expected) {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(ip.startsWith("172") ? new Location(null, Country.RUSSIA, null, 0) : new Location(null, Country.USA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Mockito.any()))
                .thenReturn(ip.startsWith("172") ? "Добро пожаловать" : "Welcome");

        MessageSenderImpl sut = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        String actual = sut.send(headers);

        assertEquals(expected, actual);
    }

    public static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of("172.110.11.12", "Добро пожаловать"),
                Arguments.of("96.10.111.112", "Welcome")
        );
    }
}