package configs;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.itis.vhsroni.config.WebMvcConfig;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebMvcConfigTest {

    private LocalValidatorFactoryBean validator;
    private WebMvcConfig config;

    @BeforeEach
    void beforeEach() {
        validator = mock(LocalValidatorFactoryBean.class);
        config = new WebMvcConfig(validator);
    }

    @Test
    void testGetValidator() {
        assertSame(validator, config.getValidator());
    }

    @Test
    void testFreeMarkerViewResolverNotNull() {
        FreeMarkerViewResolver resolver = config.freeMarkerViewResolver();
        assertNotNull(resolver);
    }

    @Test
    void testFreeMarkerConfigurerExpectedSettings() throws TemplateException, IOException {
        FreeMarkerConfigurer configurer = config.freeMarkerConfigurer();
        configurer.afterPropertiesSet();
        Configuration configuration = configurer.getConfiguration();
        assertEquals("UTF-8", configuration.getDefaultEncoding());
        assertSame(TemplateExceptionHandler.RETHROW_HANDLER, configuration.getTemplateExceptionHandler());
        assertTrue(configuration.getLogTemplateExceptions());
        assertFalse(configuration.getWrapUncheckedExceptions());
        assertTrue(configuration.getFallbackOnNullLoopVariable());
    }


    @Test
    void testAddResourceHandlersMapCssResources() {
        ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
        ResourceHandlerRegistration registration = mock(ResourceHandlerRegistration.class);
        when(registry.addResourceHandler("/css/**")).thenReturn(registration);
        when(registration.addResourceLocations("/css/")).thenReturn(registration);
        config.addResourceHandlers(registry);
        verify(registry).addResourceHandler("/css/**");
        verify(registration).addResourceLocations("/css/");
    }
}

