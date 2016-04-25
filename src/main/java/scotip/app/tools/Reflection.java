package scotip.app.tools;

import java.lang.reflect.Field;

/**
 * Created by Pierre on 25/04/2016.
 */
public class Reflection {
    public static <T1 extends Object, T2 extends Object> void copy(T1 entity, T2 entity2) throws IllegalAccessException, NoSuchFieldException {
        Class<? extends Object> copy1 = entity.getClass();
        Class<? extends Object> copy2 = entity2.getClass();

        Field[] fromFields = copy1.getDeclaredFields();
        Field[] toFields = copy2.getDeclaredFields();

        Object value = null;

        for (Field field : fromFields) {

            Field field1 = copy2.getDeclaredField(field.getName());

            System.out.println(field.getName());
            value = field.get(entity);
            field1.set(entity2, value);
        }
    }
}
