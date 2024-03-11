package ru.javaops.startup.ref;

import ru.javaops.startup.MatcherFactory;
import ru.javaops.startup.ref.model.RefEntity;
import ru.javaops.startup.ref.to.RefTo;

import static ru.javaops.startup.ref.model.RefType.CONTACT;

public class RefTestData {
    public static final MatcherFactory.Matcher<RefEntity> REF_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RefEntity.class, "id", "startpoint");
    public static final MatcherFactory.Matcher<RefTo> REF_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RefTo.class, "id");

    public static final String TG_CODE = "C_TG";
    public static final RefEntity refTg = new RefEntity(TG_CODE, CONTACT.getCode(), 10, null);
    public static final RefEntity refWa = new RefEntity("C_WA", CONTACT.getCode(), 20, null);
    public static final RefEntity refPh = new RefEntity("C_PH", CONTACT.getCode(), 30, null);
    public static final RefEntity refSk = new RefEntity("C_SK", CONTACT.getCode(), 40, null);

    static {
        refSk.setEnabled(false);
    }

    public static RefEntity getNew() {
        return new RefEntity("C_FB", CONTACT.getCode(), 50, null);
    }

    public static RefEntity getUpdated() {
        return new RefEntity(TG_CODE, CONTACT.getCode(), 25, null);
    }
}
