package configs;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.itis.vhsroni.config.ValidatorConfig;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorConfigTest {


    @Test
    void testMessageSourceReturnsReloadableResourceBundleMessageSource() {
        ValidatorConfig config = new ValidatorConfig();
        MessageSource messageSource = config.messageSource();
        assertNotNull(messageSource);
        ReloadableResourceBundleMessageSource reloadableSource = (ReloadableResourceBundleMessageSource) messageSource;
        assertTrue(reloadableSource.getBasenameSet().contains("classpath:messages"));
    }
}
