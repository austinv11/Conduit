package conduit.common;

import javax.annotation.Nullable;
import java.util.Objects;

public class Header {
	
	private final String key;
	private final @Nullable String value;
	
	public static Header from(String key, @Nullable String value) {
		return new Header(key.toLowerCase(), value);
	}
	
	private Header(String key, @Nullable String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public @Nullable String getValue() {
		return value;
	}
	
	@Override 
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Header header = (Header) o;
		return Objects.equals(getKey(), header.getKey()) &&
				Objects.equals(getValue(), header.getValue());
	}
	
	@Override 
	public int hashCode() {
		return Objects.hash(getKey(), getValue());
	}
	
	@Override 
	public String toString() {
		return '(' + key + ',' + value + ')';
	}
}
