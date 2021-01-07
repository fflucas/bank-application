package fabio.zup.banco.config.validation;

import com.verifalia.api.VerifaliaRestClient;
import com.verifalia.api.emailvalidations.WaitingStrategy;
import com.verifalia.api.emailvalidations.models.Validation;
import com.verifalia.api.emailvalidations.models.ValidationEntry;
import com.verifalia.api.exceptions.VerifaliaException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContactEmailValidator implements ConstraintValidator<ContactEmailConstraint, String> {

    private VerifaliaRestClient verifalia;

    @Override
    public void initialize(ContactEmailConstraint constraintAnnotation) {
        this.verifalia = new VerifaliaRestClient(
                "${banco.verifalia.username}",
                "${banco.verifalia.password}");
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        try {
            Validation validation = verifalia
                    .getEmailValidations()
                    .submit(email, new WaitingStrategy(true));
            ValidationEntry entry = validation.getEntries().get(0);
            verifalia
                    .getEmailValidations()
                    .delete(validation.getOverview().getId());
            return entry.getStatus().toString().equals("Success");
        } catch (VerifaliaException e) {
            e.printStackTrace();
            return false;
        }
    }
}
