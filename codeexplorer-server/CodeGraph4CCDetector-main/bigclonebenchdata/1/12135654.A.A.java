public class A{
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    return new URLConnection(url) {

                        @Override
                        public void connect() throws IOException {
                        }

                        @Override
                        public InputStream getInputStream() throws IOException {
                            ThemeResource f = getFacelet(getURL().getFile());
                            return new ByteArrayInputStream(f.getText().getBytes());
                        }
                    };
                }
}