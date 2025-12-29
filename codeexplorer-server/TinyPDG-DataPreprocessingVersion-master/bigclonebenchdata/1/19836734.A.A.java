public class A{
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            String resource = schemaMapping.get(systemId);
            if (resource != null) {
                URL url = getClass().getClassLoader().getResource(resource);
                return new InputSource(url.openStream());
            }
            return null;
        }
}