package to;

import models.core.Choice;
import models.core.Question;
import models.core.Rule;
import models.core.User;

public class Transformer {
    public static QuestionTO convert(Question q) {
        QuestionTO qto = new QuestionTO();
        qto.id = q.id;
        qto.text = q.questionText;
        qto.ruleId = q.rule.id;
        return qto;
    }

    public static ChoiceTO convert(Choice c) {
        ChoiceTO cto = new ChoiceTO();
        cto.id = c.id;
        cto.text = c.choiceText.text;
        cto.correct = c.correct;
        cto.questionId = c.question.id;
        return cto;
    }

    public static UserTO convert(User u) {
        UserTO uto = new UserTO();
        uto.id = u.id;
        uto.fullname = u.fullname;
        uto.isAdmin = u.isAdmin;
        return uto;
    }

    public static RuleTO convert(Rule r) {
        RuleTO rto = new RuleTO();
        rto.id = r.id;
        rto.ruleText =r.ruleText;
        return rto;
    }
}
