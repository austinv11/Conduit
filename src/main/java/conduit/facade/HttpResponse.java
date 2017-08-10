package conduit.facade;

import conduit.common.Headers;

import javax.annotation.Nullable;

public interface HttpResponse<OUT> {
	
	int status();
	
	Headers getHeaders();
	
	@Nullable OUT getResponseData();
}
