Keyword Harvester
================
This application is a really simple "related keyword" search tool.  For more info on the purpose of such a tool, take a look at the links in https://www.google.com/search?q=related+keywords (a nice explanaton appears at http://www.wordstream.com/related-keywords)

The application is really simple to use.  Running 
		mvn install 
creates a 
		keywordharvester-1.0-SNAPSHOT-jar-with-dependencies.jar in the target directory

This jar is runnable, meaning that double-click should work, as should running from command line, like: 
		java -jar keywordharvester-1.0-SNAPSHOT-jar-with-dependencies.jar

The tool
--------

In the tool tab: Write some text, press the "harvest!" button and wait for the response. If a keyword is clicked the textbox will be filled automatically.

In the options tab: A short list (3 items) with some application parameters appears. Double click in a value to modify it. The parameters are:

* `page_limit`: a number, the search will only fetch that number of pages at most.

* `google_request_limit`: a number, The google search request return at most 8 hits, so to reach `page_limit` it may be necessary to make several requests. However, it won't be more than this limit.

	Both values limit the same, the number of search results that are involved in the processing. However, `google_request_limit` can only limit by multiples of 8 (8, 16, 24,32) with page limit is possible to be more specific (10, 15, 20, 25).

* `stop_words`: comma-separated list of terms. If a related keyword contains one of this terms, is excluded from further processing. Notice that this differs from the usual sense of the "stop word" usage. Example: If a related keyword found is "price of persia"  and the `stop_words` list is "but,of,the" then the related keyword "prince of persia" will be excluded, as it contains "of". This behaviour is further explained and justified later in this document.  

About the code:
--------------


The code uses lucene (2.4.0), htmlparser, json, and jUnit (for the tests). the JAR file includes this libraries.



Theres 4 important classes in the processing.

* GoogleSearchService: it connects to the google search API and makes a search with the keyword in the textbox. Each search result is then then fetched and returned as a string (this is called "raw data" in the context of the application)

* HTMLMetaTagFilter: it takes "raw data", this is, a web page, and extracts the META tag with name "keywords".

* LuceneIndexProcessor: it create a single-document, single-field lucene index of all the keywords (See how are they analyzed below), and returns an ordered list in order of relevance (relevance is defined as the quotient `appeareances_of_term/sum_of_all_appearances`).  

* MetaTagKeywordsAnalyzer: This is a (shortened) meta tag example (wikipedia used to respond like this): 
`<meta name="keywords" content="Mobile phone,Protection policy,Citation needed,Articles with unsourced statements since September 2007,Articles with unsourced statements since October 2008,Articles with unsourced statements since May 2008,Category:Mobile phones" />` 



	In it, every keyword is separated by a comma, and they all are more of "keyphrases" instead of keywords.

	So it seems better that the tokenization process just breaks on the commas, and not in the spaces like it normally does. This also solves (at least helps) with the problem of the compound words. It also eliminate the problem of the stop words, as now the most common words, irrelevant for this problem, are not counted as a word anymore, altering results.

	However, there are some new problems with this approach. Now, "Articles with unsourced statements since May 2008" is seen as a relevant keyword, and it will probably will look relevant with any keyword we use, as wikipedia is now so popular. The solution is to use the "stopwords" to make away with this kind of contamination. The analyzer looks that each keyword does not contain any of the stopwords before returning it as an acceptable token. This is why the only predetermined stopword in the options is "unsourced".  

Final comments
--------------

After working with it a bit, I think that further modifications would help in the harvester work. One would be to make the single document indexed to have several fields, one "meta" and one "body" the first using the "MetaTagKeywordsAnalyzer" and the other with a more conventional one, maybe with different weights. Right now, the harvester works only with the information in the meta tag.

The interface was build with the eclipse VE project (a visual editor).




