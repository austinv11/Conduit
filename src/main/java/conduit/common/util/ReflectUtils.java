package conduit.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class ReflectUtils {
	
	public static Type[] getGenericParamTypes(Class clazz) {
		return ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
	}
	
	public static Class getClassFromTypeSafely(Type type) {
		try {
			if (type instanceof Class)
				return (Class) type;

			if (type instanceof ParameterizedType)
				return getClassFromTypeSafely(((ParameterizedType) type).getRawType());

			if (type instanceof TypeVariable) {
				Type[] bounds = ((TypeVariable) type).getBounds();
				return getClassFromTypeSafely(bounds[bounds.length - 1]);
			}
			
			//TODO add more special type cases
			
			return Class.forName(type.getTypeName()); //Very naive and bound to fail FIXME
		} catch (Exception e) {
			return Object.class;
		}
	}
}
