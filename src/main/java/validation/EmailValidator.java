package validation;

import java.util.regex.Pattern;

public class EmailValidator implements Validator<String> {
    private static final Pattern emailPattern = Pattern.compile("^\\S+@\\S+\\.\\S+$");

    @Override
    public void validate(String entity) throws ValidationException {
        if (entity == null)
            throw new ValidationException("Email nesetat!");
        if (entity.equals(""))
            throw new ValidationException("Email gol!");
        if (!emailPattern.matcher(entity).matches())
            throw new ValidationException("Valoarea nu reprezinta un email!");
    }
}
