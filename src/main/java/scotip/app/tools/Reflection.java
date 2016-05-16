/*
 * Copyright (c) 2016. Pierre BOURGEOIS
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software, and
 *  to permit persons to whom the Software is furnished to do so, subject
 *  to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

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
