package wzurita;

import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

public class GUIProcessingRunnable implements Runnable {

	private String keyword;
	private Properties toolOptions;
	private String[] relatedKeywords = null;
	private DefaultTableModel dataModel;
	private JProgressBar progressBar;
	private JButton button;

	@Override
	public void run() {
		this.button.setEnabled(false);
		this.progressBar.setVisible(true);
		this.progressBar.setIndeterminate(true);		
		GoogleSearchService googleService = new GoogleSearchService();
		try {
			int googleRequestLimit = Integer.parseInt(toolOptions.getProperty("google_request_limit"));
			googleService.setGoogleRequestsLimit(googleRequestLimit);
		} catch (NumberFormatException nfe) {

		}
		try {
			int pageLimit = Integer.parseInt(toolOptions.getProperty("page_limit"));
			googleService.setPageLimit(pageLimit);
		} catch (NumberFormatException nfe) {

		}
		LuceneIndexProcessor luceneProcessor = new LuceneIndexProcessor();
		luceneProcessor.setFilter(new HTMLMetaTagFilter());
		String[] stopWords = toolOptions.getProperty("stop_words").split("\\,");
		luceneProcessor.setStopWords(stopWords);
		KeywordHarvester harvester = new KeywordHarvester();
		harvester.setSearchService(googleService);
		harvester.setProcessor(luceneProcessor);
		RelatedKeyword[] answer = harvester.harvest(keyword);
		String[][] dataVector = new String[answer.length][2];
		relatedKeywords = new String[answer.length];
		for (int i = 0; i < answer.length; i++) {
			dataVector[i][0] = answer[i].getRelatedKeyword();
			dataVector[i][1] = answer[i].getRelevance() + "";
			relatedKeywords[i] = answer[i].getRelatedKeyword();
		}
		String[] columnIdentifiers = { "Related Keyword", "Relevance" };
		dataModel.setDataVector(dataVector, columnIdentifiers);
		this.progressBar.setIndeterminate(false);	
		this.progressBar.setVisible(false);
		this.button.setEnabled(true);
	}

	public String[] getRelatedKeywords() {
		return relatedKeywords;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setToolOptions(Properties toolOptions) {
		this.toolOptions = toolOptions;
	}

	public void setDataModel(DefaultTableModel dataModel) {
		this.dataModel = dataModel;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public void setJButton(JButton button) {
		this.button = button;
	}
}
