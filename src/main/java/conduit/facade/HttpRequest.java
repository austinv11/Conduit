package conduit.facade;

import conduit.common.Headers;
import conduit.common.RequestMethod;

import javax.annotation.Nullable;

public interface HttpRequest<IN, OUT> {
	
	Headers getHeaders();
	
	@Nullable IN getRequestData();
	
	RequestMethod getMethod();
	
	String getUri();
}
