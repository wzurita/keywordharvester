package wzurita;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GoogleSearchServiceTest {
	private GoogleSearchService target;
	//Paris Hilton is the example in the Google Code docs. :S.
	private String urlResponses[] = { "http://www.parishilton.com/", "http://en.wikipedia.org/wiki/Paris_Hilton",
			"http://www.imdb.com/name/nm0385296/", "http://www.myspace.com/parishilton" };

	@Before
	public void setUp() throws Exception {
		target = new GoogleSearchService() {
			@Override
			protected String getGoogleDataResponse(String keyword, int number) throws MalformedURLException, IOException {
				if("testCorrect".equals(keyword)) return "{\"responseData\": {\"results\":[{\"GsearchResultClass\":\"GwebSearch\",\"unescapedUrl\":\"http://www.parishilton.com/\",\"url\":\"http://www.parishilton.com/\",\"visibleUrl\":\"www.parishilton.com\",\"cacheUrl\":\"http://www.google.com/search?q\u003dcache:EgDCCgd54xQJ:www.parishilton.com\",\"title\":\"\u003cb\u003eParis Hilton\u003c/b\u003e | The Official Website\",\"titleNoFormatting\":\"Paris Hilton | The Official Website\",\"content\":\"\u003cb\u003eParisHilton\u003c/b\u003e.com \u003cb\u003eParis Hilton\u003c/b\u003e, Nicky \u003cb\u003eHilton\u003c/b\u003e Fashion, Pictures, Apparel, Jewellery  , Film, and Fun.\"},{\"GsearchResultClass\":\"GwebSearch\",\"unescapedUrl\":\"http://en.wikipedia.org/wiki/Paris_Hilton\",\"url\":\"http://en.wikipedia.org/wiki/Paris_Hilton\",\"visibleUrl\":\"en.wikipedia.org\",\"cacheUrl\":\"http://www.google.com/search?q\u003dcache:TwrPfhd22hYJ:en.wikipedia.org\",\"title\":\"\u003cb\u003eParis Hilton\u003c/b\u003e - Wikipedia, the free encyclopedia\",\"titleNoFormatting\":\"Paris Hilton - Wikipedia, the free encyclopedia\",\"content\":\"\u003cb\u003eParis\u003c/b\u003e Whitney \u003cb\u003eHilton\u003c/b\u003e (born February 17, 1981) is an American celebutante,   television personality, actress, singer and model. \u003cb\u003e...\u003c/b\u003e\"},{\"GsearchResultClass\":\"GwebSearch\",\"unescapedUrl\":\"http://www.imdb.com/name/nm0385296/\",\"url\":\"http://www.imdb.com/name/nm0385296/\",\"visibleUrl\":\"www.imdb.com\",\"cacheUrl\":\"http://www.google.com/search?q\u003dcache:1i34KkqnsooJ:www.imdb.com\",\"title\":\"\u003cb\u003eParis Hilton\u003c/b\u003e\",\"titleNoFormatting\":\"Paris Hilton\",\"content\":\"Self: Zoolander. Socialite \u003cb\u003eParis Hilton\u003c/b\u003e was born on February 17, 1981 in New   York City... Visit IMDb for Photos, Filmography, Discussions, Bio, News, \u003cb\u003e...\u003c/b\u003e\"},{\"GsearchResultClass\":\"GwebSearch\",\"unescapedUrl\":\"http://www.myspace.com/parishilton\",\"url\":\"http://www.myspace.com/parishilton\",\"visibleUrl\":\"www.myspace.com\",\"cacheUrl\":\"http://www.google.com/search?q\u003dcache:DTzCq3_Z7C0J:www.myspace.com\",\"title\":\"MySpace.com - \u003cb\u003eParis Hilton\u003c/b\u003e - 27 - Female - California - www \u003cb\u003e...\u003c/b\u003e\",\"titleNoFormatting\":\"MySpace.com - Paris Hilton - 27 - Female - California - www ...\",\"content\":\"\u0026quot;The Official \u003cb\u003eParis Hilton\u003c/b\u003e MySpace Page \u0026quot; Female 27 years old California United   States \u003cb\u003e...\u003c/b\u003e \u003cb\u003eParis Hilton\u0026#39;s\u003c/b\u003e Latest Blog Entry [Subscribe to this Blog] \u003cb\u003e...\u003c/b\u003e\"}],\"cursor\":{\"pages\":[{\"start\":\"0\",\"label\":1},{\"start\":\"4\",\"label\":2},{\"start\":\"8\",\"label\":3},{\"start\":\"12\",\"label\":4},{\"start\":\"16\",\"label\":5},{\"start\":\"20\",\"label\":6},{\"start\":\"24\",\"label\":7},{\"start\":\"28\",\"label\":8}],\"estimatedResultCount\":\"6160000\",\"currentPageIndex\":0,\"moreResultsUrl\":\"http://www.google.com/search?oe\u003dutf8\u0026ie\u003dutf8\u0026source\u003duds\u0026start\u003d0\u0026hl\u003den\u0026q\u003dParis+Hilton\"}}, \"responseDetails\": null, \"responseStatus\": 200}";
				return "{\"responseData\": {\"results\":[{\"GsearchResultClass\":\"GwebSearch\",\"unescapedUrl\":\"http://www.parishilton.com/\",\"url\":\"http://www.parishilton.com/\",\"visibleUrl\":\"www.parishilton.com\",\"cacheUrl\":\"http://www.google.com/search?q\u003dcache:EgDCCgd54xQJ:www.parishilton.com\",\"title\":\"\u003cb\u003eParis Hilton\u003c/b\u003e | The Official Website\",\"titleNoFormatting\":\"Paris Hilton | The Official Website\",\"content\":\"\u003cb\u003eParisHilton\u003c/b\u003e.com \u003cb\u003eParis Hilton\u003c/b\u003e, Nicky \u003cb\u003eHilton\u003c/b\u003e Fashion, Pictures, Apparel, Jewellery  , Film, and Fun.\"},{\"GsearchResultClass\":\"GwebSearch\",\"unescapedUrl\":\"http://en.wikipedia.org/wiki/Paris_Hilton\",\"url\":\"http://en.wikipedia.org/wiki/Paris_Hilton\",\"visibleUrl\":\"en.wikipedia.org\",\"cacheUrl\":\"http://www.google.com/search?q\u003dcache:TwrPfhd22hYJ:en.wikipedia.org\",\"title\":\"\u003cb\u003eParis Hilton\u003c/b\u003e - Wikipedia, the free encyclopedia\",\"titleNoFormatting\":\"Paris Hilton - Wikipedia, the free encyclopedia\",\"content\":\"\u003cb\u003eParis\u003c/b\u003e Whitney \u003cb\u003eHilton\u003c/b\u003e (born February 17, 1981) is an American celebutante,   television personality, actress, singer and model. \u003cb\u003e...\u003c/b\u003e\"},{\"GsearchResultClass\":\"GwebSearch\",\"unescapedUrl\":\"http://www.imdb.com/name/nm0385296/\",\"url\":\"http://www.imdb.com/name/nm0385296/\",\"visibleUrl\":\"www.imdb.com\",\"cacheUrl\":\"http://www.google.com/search?q\u003dcache:1i34KkqnsooJ:www.imdb.com\",\"title\":\"\u003cb\u003eParis Hilton\u003c/b\u003e\",\"titleNoFormatting\":\"Paris Hilton\",\"content\":\"Self: Zoolander. Socialite \u003cb\u003eParis Hilton\u003c/b\u003e was born on February 17, 1981 in New   York City... Visit IMDb for Photos, Filmography, Discussions, Bio, News, \u003cb\u003e...\u003c/b\u003e\"},{\"GsearchResultClass\":\"GwebSearch\",\"unescapedUrl\":\"http://www.myspace.com/parishilton\",\"url\":\"http://www.myspace.com/parishilton\",\"visibleUrl\":\"www.myspace.com\",\"cacheUrl\":\"http://www.google.com/search?q\u003dcache:DTzCq3_Z7C0J:www.myspace.com\",\"title\":\"MySpace.com - \u003cb\u003eParis Hilton\u003c/b\u003e - 27 - Female - California - www \u003cb\u003e...\u003c/b\u003e\",\"titleNoFormatting\":\"MySpace.com - Paris Hilton - 27 - Female - California - www ...\",\"content\":\"\u0026quot;The Official \u003cb\u003eParis Hilton\u003c/b\u003e MySpace Page \u0026quot; Female 27 years old California United   States \u003cb\u003e...\u003c/b\u003e \u003cb\u003eParis Hilton\u0026#39;s\u003c/b\u003e Latest Blog Entry [Subscribe to this Blog] \u003cb\u003e...\u003c/b\u003e\"}],\"cursor\":{\"pages\":[{\"start\":\"0\",\"label\":1},{\"start\":\"4\",\"label\":2},{\"start\":\"8\",\"label\":3},{\"start\":\"12\",\"label\":4},{\"start\":\"16\",\"label\":5},{\"start\":\"20\",\"label\":6},{\"start\":\"24\",\"label\":7},{\"start\":\"28\",\"label\":8}],\"estimatedResultCount\":\"6160000\",\"currentPageIndex\":0,\"moreResultsUrl\":\"http://www.google.com/search?oe\u003dutf8\u0026ie\u003dutf8\u0026source\u003duds\u0026start\u003d0\u0026hl\u003den\u0026q\u003dParis+Hilton\"}}, \"responseDetails\": null, \"responseStatus\": 500}";
			}

			@Override
			protected String fetchPage(String urlStr) throws IOException {
				return urlStr;
			}
		};
	}

	@Test
	public void buildsResponseCorrectly() {
		String[] rawData = target.searchRawData("testCorrect");
		Assert.assertEquals(urlResponses, rawData);
	}
	
	@Test
	public void DoesntBuildsResponseCorrectly() {
		String[] rawData = target.searchRawData("testIncorrect");
		Assert.assertNull(rawData);
	}

}
