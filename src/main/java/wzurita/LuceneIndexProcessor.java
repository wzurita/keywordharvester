package wzurita;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;

public class LuceneIndexProcessor implements Processor {
	private String[] stopWords;
	private Filter filter;
	private static final String onlyField = "only";

	@Override
	public RelatedKeyword[] process(String[] rawData, String keyword) {
		try {
			String mergedRawData = mergeRawData(rawData);
			RAMDirectory indexDirectory = new RAMDirectory();
			writeIndex(mergedRawData, indexDirectory, keyword);
			IndexReader reader = IndexReader.open(indexDirectory);
			TermFreqVector freqVector = reader.getTermFreqVector(0, onlyField);
			int[] termFrequencies = freqVector.getTermFrequencies();
			String[] terms = freqVector.getTerms();
			float sumOfAllFrequencies = sumOfAllFrequencies(termFrequencies);
			RelatedKeyword[] relatedKeywords = buildRelatedKeywords(keyword, termFrequencies, terms, sumOfAllFrequencies);
			Arrays.sort(relatedKeywords, new Comparator<RelatedKeyword>() {
				@Override
				public int compare(RelatedKeyword o1, RelatedKeyword o2) {
					return -(o1.compareTo(o2));
				}
			});
			return relatedKeywords;
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private RelatedKeyword[] buildRelatedKeywords(String keyword, int[] termFrequencies, String[] terms, float sumOfAllFrequencies) {
		RelatedKeyword[] relatedKeywords = new RelatedKeyword[termFrequencies.length];
		for (int i = 0; i < termFrequencies.length; i++) {
			RelatedKeyword related = new RelatedKeyword();
			related.setKeyword(keyword);
			related.setRelatedKeyword(terms[i]);
			related.setRelevance(termFrequencies[i] / sumOfAllFrequencies);
			relatedKeywords[i] = related;
		}
		return relatedKeywords;
	}

	private void writeIndex(String mergedRawData, RAMDirectory indexDirectory, String keyword) throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriter writer = new IndexWriter(indexDirectory, analyzer(keyword), MaxFieldLength.UNLIMITED);
		Document doc = new Document();
		doc.add(new Field(onlyField, mergedRawData, Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.YES));
		writer.addDocument(doc);
		writer.close();
	}

	private Analyzer analyzer(String keyword) {
		String[] stopWordsWithKeyword = { keyword };
		if (stopWords != null) {
			stopWordsWithKeyword = Arrays.copyOf(stopWords, stopWords.length + 1);
			stopWordsWithKeyword[stopWordsWithKeyword.length - 1] = keyword;
		}
		Analyzer analyzer = new MetaTagKeywordsAnalyzer(stopWordsWithKeyword);
		return analyzer;
	}

	private String mergeRawData(String[] rawData) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < rawData.length; i++) {
			builder.append(filter.filter(rawData[i]));
			builder.append(",");
		}
		String mergedRawData = builder.toString();
		return mergedRawData;
	}

	private float sumOfAllFrequencies(int[] termFrequencies) {
		float result = 0;
		for (int i = 0; i < termFrequencies.length; i++) {
			result += termFrequencies[i];
		}
		return result;
	}

	public String[] getStopWords() {
		return stopWords;
	}

	public void setStopWords(String[] stopWords) {
		this.stopWords = stopWords;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

}
