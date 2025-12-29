public class A{
    private BufferedReader getReader(final String fileUrl) throws IOException {
        InputStreamReader reader;
        try {
            reader = new FileReader(fileUrl);
        } catch (FileNotFoundException e) {
            URL url = new URL(fileUrl);
            reader = new InputStreamReader(url.openStream());
        }
        return new BufferedReader(reader);
    }
}