public class A{
    public SimplePropertiesMessageRepository() {
        properties = new Properties();
        try {
            URL url = SimplePropertiesIconRepository.class.getResource(PROPERTIES_FILE_NAME);
            properties.load(url.openStream());
        } catch (Exception e) {
            throw new Error("Messages file not found: " + PROPERTIES_FILE_NAME);
        }
    }
}