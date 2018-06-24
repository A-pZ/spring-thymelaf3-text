package com.github.apz.springsample.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@PropertySource("classpath:mail/emailconfig.properties")
public class ThymeleafConfig implements ApplicationContextAware, EnvironmentAware {
	@Bean
    public TemplateEngine textTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // Resolver for TEXT emails
        templateEngine.addTemplateResolver(textTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

	private ITemplateResolver textTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(1));
        //templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
        templateResolver.setPrefix("/mail/");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding("utf-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("mail/MailMessages");
        return messageSource;
    }

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private ApplicationContext applicationContext;
    private Environment environment;
}
