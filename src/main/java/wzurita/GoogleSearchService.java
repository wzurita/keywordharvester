package wzurita;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSearchService implements SearchService {
	private String googleDataAPIURL = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&rsz=large&q=";
	private String refererSite = "http://www.keybroker.com/";
	private int requests = 1;
	private int pageLimit = Integer.MAX_VALUE;

	@Override
	public String[] searchRawData(String keyword) {
		try {
			List<String> result = new ArrayList<String>();
			harvest: for (int requestNumber = 0; requestNumber < this.requests; requestNumber++) {
				String googleApiResult = getGoogleDataResponse(URLEncoder.encode(keyword, "UTF-8"), requestNumber);
				JSONObject googleResponse = new JSONObject(googleApiResult);
				int returnCode = googleResponse.getInt("responseStatus");
				if (returnCode != 200) {
					System.err.print("Google status code:" + returnCode);
					return null;
				}
				JSONArray resultJSONArray = googleResponse.getJSONObject("responseData").getJSONArray("results");
				JSONObject json;
				for (int i = 0; i < resultJSONArray.length(); i++) {
					json = resultJSONArray.getJSONObject(i);
					String resultURL = json.getString("url");
					result.add(fetchPage(resultURL));
					if (result.size() >= pageLimit)
						break harvest;
				}
			}
			return result.toArray(new String[1]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Refactored to test the important method, searchRawData, not the URL
	// mechanism
	protected String fetchPage(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		URLConnection connection = url.openConnection();
		return fetchPage(connection);
	}

	private String fetchPage(URLConnection connection) {
		try {
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader;

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// Refactored to test the important method, searchRawData, not google API.
	protected String getGoogleDataResponse(String keyword, int requestNumber) throws MalformedURLException, IOException {
		URL url = new URL(googleDataAPIURL + keyword + "&start=" + (requestNumber * 8));
		URLConnection connection = url.openConnection();
		connection.addRequestProperty("Referer", refererSite);
		return fetchPage(connection);
	}

	public String getGoogleDataApiURL() {
		return googleDataAPIURL;
	}

	public void setGoogleDataApiURL(String googleDataApiURL) {
		this.googleDataAPIURL = googleDataApiURL;
	}

	public String getRefererSite() {
		return refererSite;
	}

	public void setRefererSite(String refererSite) {
		this.refererSite = refererSite;
	}

	public int getRequests() {
		return requests;
	}

	public void setGoogleRequestsLimit(int requests) {
		this.requests = requests;
	}

	public void setPageLimit(int i) {
		this.pageLimit = i;
	}

}
