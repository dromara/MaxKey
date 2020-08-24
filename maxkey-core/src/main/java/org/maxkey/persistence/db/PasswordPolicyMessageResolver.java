package org.maxkey.persistence.db;

import java.util.Locale;

import org.passay.MessageResolver;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResultDetail;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;


public class PasswordPolicyMessageResolver  implements MessageResolver{

    /** A accessor for Spring's {@link MessageSource} */
    private final MessageSourceAccessor messageSourceAccessor;

    /** The {@link MessageResolver} for fallback */
    private final MessageResolver fallbackMessageResolver = new PropertiesMessageResolver();

    /**
     * Create a new instance with the locale associated with the current thread.
     * @param messageSource a message source managed by spring
     */
    public PasswordPolicyMessageResolver(final MessageSource messageSource)
    {
      this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    /**
     * Create a new instance with the specified locale.
     * @param messageSource a message source managed by spring
     * @param locale the locale to use for message access
     */
    public PasswordPolicyMessageResolver(final MessageSource messageSource, final Locale locale)
    {
      this.messageSourceAccessor = new MessageSourceAccessor(messageSource, locale);
    }

    /**
     * Resolves the message for the supplied rule result detail using Spring's {@link MessageSource}.
     * (If the message can't retrieve from a {@link MessageSource}, return default message provided by passay)
     * @param detail rule result detail
     * @return message for the detail error code
     */
    @Override
    public String resolve(final RuleResultDetail detail)
    {
      try {
        return this.messageSourceAccessor.getMessage(detail.getErrorCode().toLowerCase(), detail.getValues());
      } catch (NoSuchMessageException e) {
        return this.fallbackMessageResolver.resolve(detail);
      }
    }
}
