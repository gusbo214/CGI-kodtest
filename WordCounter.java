import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WordCounter {
        /**
         * Counts the occurrences of each word in the given text.
         *
         * @param text The input text.
         * @return A map where the keys are words and the values are their respective counts.
         */
        public Map<String, Integer> countWords(String text) {
            var wordCounts = new HashMap<String, Integer>(); //Use HashMap because it allows null values and one null key
            var words = text.split("\\s+"); // Split the text into words using non-word characters as delimiters
            for (var word : words) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1); 
            }
            var sortedWordCounts = wordCounts.entrySet() 
            .stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(10)
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1, // If there are duplicate keys, keep the first one
                    LinkedHashMap::new
                    ));
            return sortedWordCounts;
        }
}
