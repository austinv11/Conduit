package conduit.common;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Headers {
	
	//Common header keys
	public static final String USER_AGENT_KEY = "user-agent";
	
	//Common header values
	public static final String JSON_CONTENT_TYPE = "application/json";
	
	private final List<Header> headers;
	
	private Headers() {
		this.headers = new LinkedList<>();
	}
	
	private Headers(List<Header> headers) {
		this.headers = headers;
	}
	
	private Headers(Headers headers) {
		this(new LinkedList<>(headers.headers));
	}
	
	public int size() {
		return headers.size();
	}
	
	public boolean isEmpty() {
		return headers.isEmpty();
	}
	
	public boolean containsKey(String key) {
		return headers.stream().anyMatch(h -> h.getKey().equalsIgnoreCase(key));
	}
	
	public boolean contains(String key, @Nullable String value) {
		return headers.stream().anyMatch(h -> h.getKey().equalsIgnoreCase(key) && Objects.equals(h.getValue(), value));
	}
	
	public Headers add(String key, @Nullable String value) {
		headers.add(Header.from(key, value));
		return this;
	}
	
	public Headers add(Header header) {
		headers.add(header);
		return this;
	}
	
	public Headers addAll(Headers headers) {
		this.headers.addAll(headers.headers);
		return this;
	}
	
	public boolean remove(Header header) {
		return headers.remove(header);
	}
	
	public @Nullable Header remove(String key) {
		Header first = headers.stream().filter(h -> h.getKey().equalsIgnoreCase(key)).findFirst().orElse(null);
		
		if (first != null)
			remove(first);
		
		return first;
	}
	
	public List<Header> removeAll(String key) {
		List<Header> toRemove = headers.stream().filter(h -> h.getKey().equalsIgnoreCase(key)).collect(Collectors.toList());
		
		toRemove.forEach(this::remove);
		
		return toRemove;
	}
	
	public @Nullable Header get(String key) {
		return headers.stream().filter(h -> h.getKey().equalsIgnoreCase(key)).findFirst().orElse(null);
	}
	
	public List<Header> getAll(String key) {
		return headers.stream().filter(h -> h.getKey().equalsIgnoreCase(key)).collect(Collectors.toList());
	}
	
	public Stream<Header> stream() {
		return headers.stream();
	}
	
	public Headers copy() {
		return new Headers(headers);
	}
}
