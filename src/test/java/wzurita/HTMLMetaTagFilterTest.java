package wzurita;

import org.junit.Assert;
import org.junit.Test;


public class HTMLMetaTagFilterTest {
	private String miniHtmlPage = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
			"<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\" >\n" + 
			"<head>\n" + 
			"<title>Canon Global</title>\n" + 
			"<meta name=\"keywords\" content=\"canon global,corporate information, world gateway,Investor Relations\" />\n" +
			"<meta name=\"keywords\" content=\"Environmental Activities,Social and Cultural Support Activities, Procurement,Technology\" />\n" +
			"<meta name=\"Copyright\" content=\"&copy; Canon Inc.\" />\n" + 
			"<meta name=\"robots\" content=\"INDEX,FOLLOW\" />\n" + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" />\n" + 
			"<meta http-equiv=\"Content-Language\" content=\"en\" />\n" + 
			"<meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />\n" + 
			"<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n" + 
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"/00common/css07/head_foot.css\" />\n" + 
			"<script type=\"text/javascript\" src=\"/00common/js07/cngimg.js\"></script>\n" + 
			"<script type=\"text/javascript\" src=\"/00common/js07/putflash.js\"></script>\n" + 
			"</head>\n" + 
			"<body>\n" + 
			"<p class=\"navskip\"><a href=\"#news\" title=\"Skip navigation\" tabindex=\"10\">Skip navigation</a></p>\n" + 
			"</body>\n" + 
			"</html>";
	private String keywordMetaTagContent = "canon global,corporate information, world gateway,Investor Relations,Environmental Activities,Social and Cultural Support Activities, Procurement,Technology,";
	private HTMLMetaTagFilter target;

	@Test
	public void extractMetaTagContent() {
		target = new HTMLMetaTagFilter();
		String result = target.filter(miniHtmlPage);
		Assert.assertEquals(keywordMetaTagContent,result);
	}
}
