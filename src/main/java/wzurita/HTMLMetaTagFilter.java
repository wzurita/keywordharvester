package wzurita;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HTMLMetaTagFilter implements Filter {

	@Override
	public String filter(String filter) {
		org.htmlparser.Parser parser = new Parser();
		try {
			AndFilter metaTagFilter = new AndFilter();
			NodeFilter[] predicates = new NodeFilter[2];
			predicates[0] = new NodeClassFilter(MetaTag.class);
			predicates[1] = new HasAttributeFilter("name", "keywords");
			metaTagFilter.setPredicates(predicates);
			parser.setInputHTML(filter);
			NodeList metaTagList = parser.parse(metaTagFilter);
			Node[] metaTagArray = new Node[metaTagList.size()];
			metaTagList.copyToNodeArray(metaTagArray);
			StringBuilder builder = new StringBuilder();
			MetaTag metaTag;
			for (int i = 0; i < metaTagArray.length; i++) {
				metaTag = (MetaTag) metaTagArray[i];
				String content = metaTag.getMetaContent();
				if (content != null && !("".equals(content.trim()))) {
					builder.append(content);
					builder.append(",");
				}
			}
			return builder.toString();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}
}
