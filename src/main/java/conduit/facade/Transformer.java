package conduit.facade;

import conduit.common.util.ReflectUtils;

import javax.annotation.Nullable;

public interface Transformer<IN, OUT> {
	
	@Nullable OUT transform(@Nullable IN in);
	
	default Class<IN> in() {
		return ReflectUtils.getClassFromTypeSafely(ReflectUtils.getGenericParamTypes(this.getClass())[0]);
	}
	
	default Class<OUT> out() {
		return ReflectUtils.getClassFromTypeSafely(ReflectUtils.getGenericParamTypes(this.getClass())[1]);
	}
}
