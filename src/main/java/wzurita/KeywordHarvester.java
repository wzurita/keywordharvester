package wzurita;

public class KeywordHarvester {
	private SearchService searchService;
	private Processor processor;

	public RelatedKeyword[] harvest(String keyword) {
		String[] rawData = searchService.searchRawData(keyword);
		return processor.process(rawData, keyword);
	}

	private static String[] stopWords(String[] args) {
		String[] stopWords = { "unsourced" };
		if (args != null && args.length > 0) {
			stopWords = new String[args.length - 1];
			for (int i = 1; i < args.length; i++) {
				stopWords[i - 1] = args[i];
			}

		}
		return stopWords;
	}

	private static String keyword(String[] args) {
		if (args != null && args.length>0)
			return args[0];
		return "mobile phone";
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public Processor getProcessor() {
		return processor;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public static void main(String args[]) {
		KeywordHarvester harvester = new KeywordHarvester();
		String keyword = keyword(args);
		String[] stopWords = stopWords(args);
		GoogleSearchService googleService = new GoogleSearchService();
		googleService.setGoogleRequestsLimit(3);
		googleService.setPageLimit(15);
		LuceneIndexProcessor luceneProcessor = new LuceneIndexProcessor();
		HTMLMetaTagFilter htmlMetaTagFilter = new HTMLMetaTagFilter();
		luceneProcessor.setFilter(htmlMetaTagFilter);
		luceneProcessor.setStopWords(stopWords);
		harvester.setSearchService(googleService);
		harvester.setProcessor(luceneProcessor);
		RelatedKeyword[] answer = harvester.harvest(keyword);
		for (int i = 0; i < answer.length; i++) {
			System.out.println(answer[i]);
		}
	}

}
