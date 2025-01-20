package ma.alten.store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InventoryStatusValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InventoryStatusConstraint {
    String message() default "Invalid inventory status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
