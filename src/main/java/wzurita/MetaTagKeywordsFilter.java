package wzurita;

import java.io.IOException;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class MetaTagKeywordsFilter extends TokenFilter {
	private String[] stopWords;

	public MetaTagKeywordsFilter(TokenStream input, String[] stopWords) {
		super(input);
		this.stopWords = stopWords;
	}

	public Token next(Token reusableToken) throws IOException {
		skipToken: for (Token nextToken = input.next(reusableToken); nextToken != null; nextToken = input.next(reusableToken)) {
			if (stopWords != null)
				for (int j = 0; j < stopWords.length; j++) {
					if (nextToken.term().toLowerCase().contains(stopWords[j].toLowerCase()))
						continue skipToken;
				}
			nextToken.setTermBuffer(nextToken.term().trim());
			return nextToken;
		}
		return null;
	}
}
