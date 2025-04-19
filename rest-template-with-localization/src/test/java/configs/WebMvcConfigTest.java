package configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import ru.itis.vhsroni.configs.WebMvcConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class WebMvcConfigTest {

    private WebMvcConfig config;

    @BeforeEach
    void beforeEach() {
        config = new WebMvcConfig();
    }

    @Test
    void testRestTemplateNotNull() {
        RestTemplate restTemplate = config.restTemplate();
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void testObjectMapperNotNull() {
        ObjectMapper objectMapper = config.objectMapper();
        assertThat(objectMapper).isNotNull();
    }

    @Test
    void testMessageSourceLoadMessage() {
        MessageSource messageSource = config.messageSource();
        String message = messageSource.getMessage("greeting.message", null, Locale.ENGLISH);
        assertThat(message).isNotEmpty();
    }

    @Test
    void testConfigureMessageConvertersContainJacksonConverter() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        config.configureMessageConverters(converters);
        assertThat(converters).hasSize(1);
        assertThat(converters.get(0)).isInstanceOf(MappingJackson2HttpMessageConverter.class);
    }

    @Test
    void testLocaleChangeInterceptorUseCorrectParam() {
        LocaleChangeInterceptor interceptor = config.localeChangeInterceptor();
        assertThat(interceptor.getParamName()).isEqualTo("locale");
    }
}
