package wzurita;

import java.io.Reader;
import java.util.Collections;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.memory.PatternAnalyzer;

public class MetaTagKeywordsAnalyzer extends Analyzer {
	private String[] stopWords;

	public MetaTagKeywordsAnalyzer(String[] stopWords) {
		this.stopWords = stopWords;
	}

	@Override
	public TokenStream tokenStream(String field, Reader reader) {
		PatternAnalyzer analyzer = new PatternAnalyzer(Pattern.compile("[\\,]+"), true, Collections.EMPTY_SET);
		return new MetaTagKeywordsFilter(analyzer.tokenStream(field, reader),stopWords);
		
	}

}
