package com.websushibar.hprofpersist.utils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.websushibar.hprofpersist.hprofentries.HasId;
import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.InstanceDump;

import java.lang.reflect.InvocationTargetException;

import static java.lang.Character.toUpperCase;

/**
 * Utilities that did not fit into any other class in this package.
 */
public class Utils {

    /**
     * This one will apply a getter of the field and return the value.
     * It is presumed that the getter is named <pre>&lt;get_CapitalizedFldName&gt;</pre>
     * (Why doesn't guava have this?)
     *
     * @param arg
     * @param fieldName
     * @param <Arg> type of the object, whose field we are trying to get.
     * @param <Val> type of the field.
     * @return the field value.
     */
    public static <Arg, Val> Function<Arg, Val> getField(Arg arg, final String fieldName) {
        return new Function<Arg, Val>() {
            @Override
            public Val apply(Arg arg) {
                String getter = "get"
                        + toUpperCase(fieldName.charAt(0))
                        + fieldName.substring(1);
                try {
                    return (Val) arg.getClass().getMethod(getter).invoke(arg);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    /**
     * Guava function object transforming a HasId object to its <code>IDField id</code>
     */
    public static final Function<HasId, IDField> TO_ID = new Function<HasId, IDField>() {
        @Override
        public IDField apply(HasId hasId) {
            return hasId.getId();
        }
    };

    public static Predicate<HasId> idEquals(final IDField id) {
        return new Predicate<HasId>() {
            @Override
            public boolean apply(HasId hasId) {

                if (hasId == null) {
                    return false;
                }
                if (id == null) {
                    return hasId.getId() == null;
                }

                return id.equals(hasId.getId());
            }
        };
    }


    public static Predicate<HasId> idEquals(final long idNum) {
        return new Predicate<HasId>() {
            @Override
            public boolean apply(HasId hasId) {

                if (hasId == null) {
                    return false;
                }

                return new IDField(idNum).equals(hasId.getId());
            }
        };
    }

    public static Predicate<InstanceDump> isOfClass(final IDField classId) {
        return new Predicate<InstanceDump>() {
            @Override
            public boolean apply(InstanceDump instanceDump) {
                return instanceDump.getClassObjId().equals(classId);
            }
        };
    }
}
