package classes.proyecto.com;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JFileChooser;

public class TextProcessor {

    public static Path selectText() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(".txt"));
        chooser.showDialog(null, "Select");
        Path path = chooser.getSelectedFile().toPath();
        return path;
    }

    public static Map<Integer, String> groupWordsByLength(Path path) throws IOException {
        Stream<String> stream = Files.lines(path)
                .flatMap(Pattern.compile(" ")::splitAsStream) //Divide el String en palabras
                .map(string -> string.replaceAll("\\W", "")) //Borra los signos al final de las palabras
                .distinct();            
        ConcurrentMap<Integer, String> map;
        map = stream.collect(Collectors
                .toConcurrentMap(String::length, k -> k, (s1, s2) -> s1 + "," + s2));
        return map;
    }
    
    public static long countWords(Path path) throws IOException {
        long totalWords = Files.lines(path)
                .flatMap(Pattern.compile(" ")::splitAsStream)
                .count();
        return totalWords;
    }

    public static void main(String[] args) {
        try {
            Path path = TextProcessor.selectText();
            Map<Integer, String> map = TextProcessor.groupWordsByLength(path);
            System.out.println(map);
            System.out.println("Total of words = " + TextProcessor.countWords(path));
        } catch (IOException ex) {
            
        }
    }
}
