public class A{
    public static Properties load(String classPath) throws IOException {
        AssertUtility.notNullAndNotSpace(classPath);
        Properties props = new Properties();
        URL url = ClassLoader.getSystemResource(classPath);
        props.load(url.openStream());
        return props;
    }
}