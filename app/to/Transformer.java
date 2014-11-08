package to;

import models.core.Choice;
import models.core.Question;
import models.core.User;

import java.util.stream.Collectors;

public class Transformer {
    public static QuestionTO convert(Question q) {
        QuestionTO qto = new QuestionTO();
        qto.id = q.id;
        qto.text = q.questionText;
        qto.choices = q.choices.stream().map(c -> convert(c))
                .sorted((c1, c2) -> Integer.compare(c1.text.hashCode(), c2.text.hashCode()))
                .collect(Collectors.toList());
        return qto;
    }

    public static ChoiceTO convert(Choice c) {
        ChoiceTO cto = new ChoiceTO();
        cto.id = c.id;
        cto.text = c.choiceText.text;
        cto.correct = c.correct;
        return cto;
    }

    public static UserTO convert(User u) {
        UserTO uto = new UserTO();
        uto.id = u.id;
        uto.fullname = u.fullname;
        uto.isAdmin = u.isAdmin;
        return uto;
    }
}
