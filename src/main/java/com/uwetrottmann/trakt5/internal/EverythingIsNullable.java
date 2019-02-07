package com.uwetrottmann.trakt5.internal;

import javax.annotation.Nullable;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Extends {@code ParametersAreNullableByDefault} to also apply to method results and fields.
 *
 * @see javax.annotation.ParametersAreNullableByDefault
 */
@Documented
@Nullable
@TypeQualifierDefault({
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
public @interface EverythingIsNullable {
}
