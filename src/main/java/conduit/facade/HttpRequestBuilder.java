package conduit.facade;

import conduit.common.Header;
import conduit.common.Headers;

import javax.annotation.Nullable;

public interface HttpRequestBuilder<IN, OUT> {
	
	HttpRequestBuilder<IN, OUT> addHeader(Header header);
	
	HttpRequestBuilder<IN, OUT> addHeaders(Headers header);
	
	HttpRequestBuilder<IN, OUT> setHeaders(Headers header);
	
	<T> HttpRequestBuilder<T, OUT> addInputTransformer(Transformer<IN, T> transformer);
	
	<T> HttpRequestBuilder<IN, OUT> addOutputTransformer(Transformer<OUT, T> transformer);
	
	HttpRequestBuilder<IN, OUT> setPacketData(@Nullable IN input);
	
	HttpRequest<IN, OUT> build();
}
