public class A{
    public static InputStreamReader getInputStreamReader(String name) throws java.io.IOException {
        URL url = getURL(name);
        if (url != null) {
            return new InputStreamReader(url.openStream());
        }
        throw new FileNotFoundException("UniverseData: Resource \"" + name + "\" not found.");
    }
}