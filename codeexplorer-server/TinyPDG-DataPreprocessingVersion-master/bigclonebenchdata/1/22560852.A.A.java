public class A{
    private void loadDBConfig(final String adapter) throws IOException {
        final URL url = getClass().getClassLoader().getResource("adapter/" + adapter + ".properties");
        _props = new Properties();
        _props.load(url.openStream());
        _init = true;
    }
}