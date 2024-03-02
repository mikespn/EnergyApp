package org.energyapp.exceptions;

import org.springframework.core.convert.ConversionException;

public class ConvertException extends ConversionException {

    /**
     * Construct a new conversion exception.
     *
     * @param message the exception message
     */
    public ConvertException(String message) {
        super(message);
    }

    /**
     * Construct a new conversion exception.
     *
     * @param message the exception message
     * @param cause   the cause
     */
    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Return the detail message, including the message from the nested exception
     * if there is one.
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * Retrieve the innermost cause of this exception, if any.
     *
     * @return the innermost exception, or {@code null} if none
     * @since 2.0
     */
    @Override
    public Throwable getRootCause() {
        return super.getRootCause();
    }

    /**
     * Retrieve the most specific cause of this exception, that is,
     * either the innermost cause (root cause) or this exception itself.
     * <p>Differs from {@link #getRootCause()} in that it falls back
     * to the present exception if there is no root cause.
     *
     * @return the most specific cause (never {@code null})
     * @since 2.0.3
     */
    @Override
    public Throwable getMostSpecificCause() {
        return super.getMostSpecificCause();
    }

    /**
     * Check whether this exception contains an exception of the given type:
     * either it is of the given class itself or it contains a nested cause
     * of the given type.
     *
     * @param exType the exception type to look for
     * @return whether there is a nested exception of the specified type
     */
    @Override
    public boolean contains(Class<?> exType) {
        return super.contains(exType);
    }
}
