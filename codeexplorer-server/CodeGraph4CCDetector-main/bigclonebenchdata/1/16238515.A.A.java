public class A{
    public void register(URL codeBase, String filePath) throws Exception {
        Properties properties = new Properties();
        URL url = new URL(codeBase + filePath);
        properties.load(url.openStream());
        initializeContext(codeBase, properties);
    }
}