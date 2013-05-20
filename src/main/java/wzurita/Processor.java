package wzurita;

public interface Processor {
	RelatedKeyword[] process(String[] rawData, String keyword);
}
