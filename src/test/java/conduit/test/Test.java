package conduit.test;

import conduit.Conduit;
import conduit.common.Header;

public class Test {
	
	@org.junit.Test
	public void test() {
		Conduit conduit = new Conduit.Builder("https://google.com/").build();
		conduit.get("some/request", hrb -> hrb.addHeader(Header.from("user-agent", "idek"))
			.setPacketData(new byte[]{1, 3, 3, 7})).subscribe(r -> {
			byte[] data = r.getResponseData();
		});
	}
}
