package wzurita;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LuceneIndexProcessorTest {

	private LuceneIndexProcessor target;
	private String[] rawData = { "one,one,one", "two,two", "three" };

	@Before
	public void setUp() throws Exception {
		target = new LuceneIndexProcessor();
		Filter filter = new Filter() { //it doesn't filter, not really
			@Override
			public String filter(String filter) {
				return filter;
			}
			
		};
		target.setFilter(filter);
	}

	@Test
	public void stopsAtStopwords() {
		String[] stopWords = {"one", "two"};
		target.setStopWords(stopWords);
		RelatedKeyword[] result = target.process(rawData, "not stopword");
		Assert.assertEquals(1,result.length);
		Assert.assertEquals("three",result[0].getRelatedKeyword());
	}
	
	@Test
	public void useKeywordAsStopword() {
		RelatedKeyword[] result = target.process(rawData, "three");
		Assert.assertEquals(2,result.length);
		Assert.assertEquals("one",result[0].getRelatedKeyword());
		Assert.assertEquals("two",result[1].getRelatedKeyword());		
	}
	
	@Test
	public void findRelatedKeywords() {
		RelatedKeyword[] result = target.process(rawData, "not stopword");
		Assert.assertEquals(3,result.length);
		Assert.assertEquals("one",result[0].getRelatedKeyword());
		Assert.assertEquals("two",result[1].getRelatedKeyword());
		Assert.assertEquals("three",result[2].getRelatedKeyword());
	}

}
