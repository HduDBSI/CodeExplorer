public class A{
    public static InputStream openRemoteFile(URL urlParam) throws KExceptionClass {
        InputStream result = null;
        try {
            result = urlParam.openStream();
        } catch (IOException error) {
            String message = new String();
            message = "No se puede abrir el recurso [";
            message += urlParam.toString();
            message += "][";
            message += error.toString();
            message += "]";
            throw new KExceptionClass(message, error);
        }
        ;
        return (result);
    }
}