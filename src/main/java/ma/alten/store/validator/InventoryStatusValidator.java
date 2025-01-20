package ma.alten.store.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ma.alten.store.enums.InventoryStatus;

public class InventoryStatusValidator implements
        ConstraintValidator<InventoryStatusConstraint, String> {

    @Override
    public void initialize(InventoryStatusConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        try{
            InventoryStatus.valueOf(value);
            return true;
        } catch(IllegalArgumentException e){
            return false;
        }
    }
}
